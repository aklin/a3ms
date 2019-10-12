package gr.arma3.arma.modarchiver.api.v1.util;

import gr.arma3.arma.modarchiver.api.v1.Mod;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilsBISTest {
	private static File testMod;

	@BeforeAll
	static void initStatic() {
		testMod = new File("src/test/resources/@testMod");
	}

	@Test
	void readMod() {
		final Mod mod = UtilsBIS.readMod(testMod).build();
		assertNotNull(mod);
		System.out.println(mod);
	}
}
