package gr.arma3.arma.modarchiver.state.redis;

import com.lambdaworks.redis.*;
import gr.arma3.arma.modarchiver.api.v1.OpResult;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import state.PersistedState;
import state.StateUtils;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RedisPersistentState implements PersistedState {
	private final RedisClient client;
	private final RedisConnectionPool<RedisConnection<String, ApiObject>> pool;

	public RedisPersistentState() {
		this(RedisURI.create("redis://localhost"));
	}

	public RedisPersistentState(final RedisURI uri) {
		this.client = new RedisClient(uri);
		this.pool = this.client.pool(new ApiObjectRedisCodec(), 2, 5);
	}


	@Override
	public OperationResult create(ApiObject resource) {
		final String fqn = StateUtils.getFQN(resource);
		final OperationResult lookup = get(fqn);
		final long failCount;

		if (!lookup.getExitCondition().isError()
			&& lookup.getResources().size() == 0
		) try (final RedisConnection<String, ApiObject> conn =
				   pool.allocateConnection()) {

			conn.multi();

			failCount = conn.exec().stream()
				.map(Object::toString)
				.filter(s -> !"OK".equalsIgnoreCase(s))
				.count();

			return new OpResult(failCount == 0
				? ExitCode.App.OK
				: ExitCode.ResourceOperation.PERSISTENCE_ERROR,
				Collections.singletonList(resource));
		}

		return new OpResult(
			ExitCode.ResourceOperation.PERSISTENCE_ERROR,
			Collections.singletonList(resource));
	}

	/**
	 * @param resource Subject.
	 * @return Operation result.
	 */
	@Override
	public @NotNull OperationResult update(ApiObject resource) {
		final String fqn = StateUtils.getFQN(resource);
		final long failCount;

		try (final RedisConnection<String, ApiObject> conn =
				 pool.allocateConnection()) {

			conn.multi();
			conn.watch(fqn);
			conn.set(fqn, resource);

			failCount = conn.exec().stream()
				.map(Object::toString)
				.filter(s -> !"OK".equalsIgnoreCase(s))
				.count();

			return new OpResult(failCount == 0
				? ExitCode.App.OK
				: ExitCode.ResourceOperation.PERSISTENCE_ERROR,
				Collections.singletonList(resource));
		}
	}

	/**
	 * @param resource Subject.
	 * @return Operation result.
	 */
	@Override
	public @NotNull OperationResult delete(ApiObject resource) {
		final String fqn = StateUtils.getFQN(resource);
		final long failCount;

		try (final RedisConnection<String, ApiObject> conn =
				 pool.allocateConnection()) {

			conn.multi();
			conn.watch(fqn);

			if (!conn.exists(fqn)) {
				conn.discard();
				return new OpResult(ExitCode.ResourceOperation.NOT_FOUND);
			}
			conn.del(fqn);
			failCount = conn.exec().stream()
				.map(Object::toString)
				.filter(s -> !"OK".equalsIgnoreCase(s))
				.count();


			return new OpResult(
				failCount == 0
					? ExitCode.App.OK
					: ExitCode.ResourceOperation.PERSISTENCE_ERROR,
				Collections.singletonList(resource)
			);
		}
	}

	/**
	 * @param name Resource name. If null, all resources matching the given
	 *             type will be returned.
	 * @return This.
	 */
	@Override
	public @NotNull OperationResult get(String name) {
		return get(name, null);
	}

	/**
	 * Get resource by name and type.
	 *
	 * @param name Resource name. If null, all resources matching the given
	 *             type will be returned.
	 * @param type Resource type. Can be null.
	 * @return Operation result.
	 */
	@Override
	public @NotNull OperationResult get(String name, Typeable type) {
		try (final RedisConnection<String, ApiObject> conn =
				 pool.allocateConnection()) {
			final List<ApiObject> result;

			conn.multi();

			conn.scan(ScanArgs.Builder.matches(Utils.NAME_RGX + ":" + name))
				.getKeys()
				.stream()
				.peek(conn::watch)
				.forEach(conn::get);

			result = conn.exec().stream()
				.map(Object::toString)
				.map(Utils::deserialize)
				.collect(Collectors.toList());

			return new OpResult(ExitCode.App.OK, result);
		}

	}

	@Override
	public void close() {
		pool.close();
		client.shutdown();
	}
}
