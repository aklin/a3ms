package gr.arma3.arma.modarchiver.cli;

import org.junit.jupiter.api.Test;

public class AppCLITest {

	@Test
	void helpTest() {
		Main.callWithArg("help");
	}


	@Test
	void versionTest() {
		Main.callWithArg("-V");
	}

	@Test
	void getTest() {
		Main.callWithArg("a3ms get ");
	}

	@Test
	void createTest() {
		Main.callWithArg("create ");
	}

}
