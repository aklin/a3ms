package gr.arma3.arma.modarchiver.api.v1.util;

import gr.arma3.arma.modarchiver.api.v1.Mod;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class UtilsBISTest {
	private static File testMod;

	@BeforeAll
	static void initStatic() {
		try {
			testMod = new File("src/test/resources/@testMod");
			Utils.validate(null);

		} catch (Throwable t) {
			System.err.println(t.getMessage());
			System.err.println(t.getCause());
		}
	}

	@Test
	void readMod() {
		final Mod mod = UtilsBIS.readMod(testMod).build();
		final String lastRevision = Instant.ofEpochSecond(0).toString();
		final String serialized;

		assertNotNull(mod);
		assertNotNull(mod.getMeta());
		assertEquals(lastRevision, mod.getVersion());
		assertEquals(lastRevision, mod.getLastRevision());
		assertNotNull(mod.getMeta().getDescription());
		assertNotEquals("", mod.getMeta().getDescription());
//		assertEquals("", mod.getMeta());

		serialized = Utils.serialize(mod);
		assertNotNull(serialized);
		System.out.println(serialized);
	}
}
