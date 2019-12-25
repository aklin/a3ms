package gr.arma3.arma.modarchiver.state.redis;

import com.lambdaworks.redis.codec.RedisCodec;
import gr.arma3.arma.modarchiver.api.v1.Meta;
import gr.arma3.arma.modarchiver.api.v1.Mod;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

class ApiObjectRedisCodecTest {
	private static RedisCodec<String, ApiObject> codec;

	@BeforeAll
	static void init() {
		codec = new ApiObjectRedisCodec();
	}

	@Test
	public void decodeKey() {
		final String keystr = "testKey";
		final byte[] key = keystr.getBytes(StandardCharsets.UTF_8);

		Assertions.assertEquals(keystr,
			codec.decodeKey(ByteBuffer.wrap(key)));
	}

	@Test
	public void decodeValue() {
		final ApiObject obj = Mod.builder()
			.meta(Meta.builder().name("junit").build())
			.build();


		final ApiObject result =
			codec.decodeValue(ByteBuffer.wrap(Utils.serialize(
				obj).getBytes(StandardCharsets.UTF_8)));

		Assertions.assertEquals(obj, result);
	}

	@Test
	public void encodeKey() {
		final String keystr = "testKey";
		final byte[] key = keystr.getBytes(StandardCharsets.UTF_8);

		Assertions.assertArrayEquals(key, codec.encodeKey(keystr));
	}

	@Test
	public void encodeValue() {

		final ApiObject obj = Mod.builder()
			.meta(Meta.builder().name("junit").build())
			.build();


		Assertions.assertArrayEquals(Utils.serialize(obj)
				.getBytes(StandardCharsets.UTF_8),
			codec.encodeValue(obj));
	}
}
