package gr.arma3.arma.modarchiver.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppCLITest {

	@Test
	void helpTest() {
		assertEquals(0, Main.callWithArgs("help"));
	}


	@Test
	void versionTest() {
		assertEquals(0, Main.callWithArgs("-V"));
	}

	@Test
	void getTest() {
		Main.callWithArgs("get");
	}

	@Test
	void createTest() {
		assertEquals(0, Main.callWithArgs("create", "mod", "foo"));
	}

	@Test
	void testNoArgs() {
		assertEquals(0, Main.callWithArgs(""));
	}
}
