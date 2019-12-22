package gr.arma3.arma.modarchiver.state;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisConnectionPool;
import com.lambdaworks.redis.RedisURI;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import gr.arma3.arma.modarchiver.state.operations.OperationException;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class RedisPersistentState
	implements PersistedState, AutoCloseable {
	public static final RedisPersistentState Singleton;
	private static final RedisClient client;
	private static final RedisConnectionPool<RedisConnection<String, String>> pool;

	static {
		client = new RedisClient(RedisURI.create("redis://localhost"));
		pool = client.pool(2, 5);
		pool.allocateConnection().ping();
		Singleton = new RedisPersistentState();
	}


	private RedisPersistentState() {

	}


	@Override
	public <E extends ApiObject> FilesystemPersistedState create(E resource) {
		return null;
	}

	/**
	 * @param resource
	 * @return State after the operation.
	 */
	@Override
	public @NotNull <E extends ApiObject> PersistedState update(E resource) {
		return null;
	}

	/**
	 * @param resource
	 * @return State after the operation.
	 */
	@Override
	public @NotNull <E extends ApiObject> PersistedState delete(E resource) {
		return null;
	}

	/**
	 * @param name      Resource name.
	 * @param direction
	 * @return
	 */
	@Override
	public <E extends ApiObject> @NotNull E get(
		String name,
		Lookup direction
	) throws OperationException {
		return null;
	}

	@Override
	public @NotNull ApiObject get(
		String name,
		Typeable type,
		Lookup direction
	) throws OperationException {
		return null;
	}

	/**
	 * Get entity.
	 *
	 * @param name Name.
	 * @param type Type.
	 * @return Object identified by name and/or type, or null.
	 */
	@Override
	public <E extends ApiObject> E get(
		@Nullable String name,
		@Nullable Typeable type
	) throws OperationException {
		return null;
	}

	@Override
	public void close() {
		pool.close();
		client.shutdown();
	}
}
