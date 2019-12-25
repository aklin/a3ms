package gr.arma3.arma.modarchiver.state;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisConnectionPool;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.codec.RedisCodec;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import gr.arma3.arma.modarchiver.state.operations.OperationException;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class RedisPersistentState
	implements PersistedState<RedisPersistentState>, AutoCloseable {
	public static final RedisPersistentState Singleton;
	private static final RedisClient client;
	private static final RedisConnectionPool<RedisConnection<String,
		ApiObject>> pool;

	static {
		client = new RedisClient(RedisURI.create("redis://localhost"));
		pool = client.pool(new ApiObjectRedisCodec(), 2, 5);
		pool.allocateConnection().ping();
		Singleton = new RedisPersistentState();
	}


	private RedisPersistentState() {

	}

	@Override
	public RedisPersistentState create(ApiObject resource) {
		final String type = resource.getType();
		final String name = resource.getMeta().getName();

		try (final RedisConnection<String, ApiObject> conn =
				 pool.allocateConnection()) {

			"OK".equalsIgnoreCase(conn.set(Utils.getFQN(resource), resource));
		}

		return this;
	}

	/**
	 * @param resource
	 * @return State after the operation.
	 */
	@Override
	public @NotNull RedisPersistentState update(ApiObject resource) {
		return null;
	}

	/**
	 * @param resource
	 * @return State after the operation.
	 */
	@Override
	public @NotNull RedisPersistentState delete(ApiObject resource) {
		return null;
	}

	/**
	 * @param name      Resource name.
	 * @param direction
	 * @return
	 */
	@Override
	public @NotNull ApiObject get(
		String name,
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
	public ApiObject get(
		@Nullable String name,
		@Nullable Typeable type
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

	private static class ApiObjectRedisCodec
		extends RedisCodec<String, ApiObject> {

		@Override
		public String decodeKey(ByteBuffer buffer) {
			return Arrays.toString(buffer.array());
		}

		@Override
		public ApiObject decodeValue(ByteBuffer buffer) {
			return Utils.deserialize(Arrays.toString(buffer.array()));
		}

		@Override
		public byte[] encodeKey(String s) {
			return s.getBytes();
		}

		@Override
		public byte[] encodeValue(ApiObject apiObject) {
			return Utils.serialize(apiObject).getBytes();
		}
	}

	@Override
	public void close() {
		pool.close();
		client.shutdown();
	}
}
