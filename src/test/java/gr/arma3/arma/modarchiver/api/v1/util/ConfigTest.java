package gr.arma3.arma.modarchiver.api.v1.util;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConfigTest {

	@Test
	@Order(1)
	void testStaticInitialization() {
		Config.print();
		Config.contains(null);
	}

	@Test
	@Order(2)
	void testConfigHasLoaded() {
		assertTrue(Config.size() > 0);
	}

	@Test
	void testBooleanFalse() {
		assertFalse(Config.contains(null));
		assertFalse(Config.contains("does not exist"));
	}

	//	@Test
	void testLangNamespacing() {

	}
}
