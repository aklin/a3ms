package gr.arma3.arma.modarchiver.api.v1.util;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CfgTest {

	@Test
	void testConfigHasLoaded() {
		assertTrue(Cfg.size() > 0);
	}

	@Test
	void testBooleanFalse() {
		assertFalse(Cfg.contains(null));
		assertFalse(Cfg.contains("does not exist"));
	}

}
