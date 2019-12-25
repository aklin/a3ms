package gr.arma3.arma.modarchiver.state.redis;

import com.lambdaworks.redis.codec.RedisCodec;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ApiObjectRedisCodec extends RedisCodec<String, ApiObject> {

	@Override
	public String decodeKey(ByteBuffer buffer) {
		return new String(buffer.array(), StandardCharsets.UTF_8);
	}

	@Override
	public ApiObject decodeValue(ByteBuffer buffer) {
		return Utils.deserialize(
			new String(buffer.array(), StandardCharsets.UTF_8));
	}

	@Override
	public byte[] encodeKey(String s) {
		return s.getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public byte[] encodeValue(ApiObject apiObject) {
		return Utils.serialize(apiObject)
			.getBytes(StandardCharsets.UTF_8);
	}
}
