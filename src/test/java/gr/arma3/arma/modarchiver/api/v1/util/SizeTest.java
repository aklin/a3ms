package gr.arma3.arma.modarchiver.api.v1.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SizeTest {

	@Test
	void getBytes() {
		for (int i = 0; i < Size.values().length; i++) {
			final int factor = (i + 1) * 10;
			final Size s = Size.values()[i];
			assertEquals((int) Math.pow(2, factor),
				s.getBytes(),
				s.toString());
		}
	}
}
