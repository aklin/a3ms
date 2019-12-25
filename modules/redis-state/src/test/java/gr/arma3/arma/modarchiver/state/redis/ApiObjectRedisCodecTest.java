package gr.arma3.arma.modarchiver.state.redis;

import com.lambdaworks.redis.codec.RedisCodec;
import gr.arma3.arma.modarchiver.api.v1.Meta;
import gr.arma3.arma.modarchiver.api.v1.Mod;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

class ApiObjectRedisCodecTest {

	@Test
	public void decodeKey(ByteBuffer buffer) {
	}

	@Test
	public void decodeValue(ByteBuffer buffer) {
	}

	@Test
	public void encodeKey(String s) {
	}

	@Test
	public void encodeValue() {
		final RedisCodec<String, ApiObject> codec = new ApiObjectRedisCodec();
		final ApiObject obj = Mod.builder()
			.meta(Meta.builder().name("junit").build())
			.build();

		final ApiObject result = Utils.deserialize(
			Arrays.toString(codec.encodeValue(obj)));


	}
}
