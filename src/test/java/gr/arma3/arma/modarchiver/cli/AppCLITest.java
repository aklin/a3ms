package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.Main;
import org.junit.jupiter.api.Test;

class AppCLITest {

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
		Main.callWithArg("get mod test");
	}

}
