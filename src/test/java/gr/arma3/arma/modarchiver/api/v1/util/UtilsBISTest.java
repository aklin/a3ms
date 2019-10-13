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
		testMod = new File("src/test/resources/@testMod");
	}

	@Test
	void readMod() {
		final Mod mod = UtilsBIS.readMod(testMod).build();
		final String lastRevision = Instant.ofEpochSecond(0).toString();

		assertNotNull(mod);
		assertNotNull(mod.getMeta());
		assertEquals(lastRevision.toString(), mod.getVersion());
		assertEquals(lastRevision, mod.getLastRevision());
		assertNotNull(mod.getMeta().getDescription());
		assertNotEquals("", mod.getMeta().getDescription());
//		assertEquals("", mod.getMeta());
		System.out.println(Utils.serialize(mod));
	}
}
