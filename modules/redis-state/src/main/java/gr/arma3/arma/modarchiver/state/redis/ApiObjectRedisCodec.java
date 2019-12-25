package gr.arma3.arma.modarchiver.state.redis;

import com.lambdaworks.redis.codec.RedisCodec;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ApiObjectRedisCodec extends RedisCodec<String, ApiObject> {

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
