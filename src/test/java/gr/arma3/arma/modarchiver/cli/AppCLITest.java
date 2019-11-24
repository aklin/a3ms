package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.Main;
import org.junit.jupiter.api.Test;

class AppCLITest {

	@Test
	void helpTest() {

		final String[] args = {"help"};
		final Main main = new Main(args);

		main.run();
	}


	@Test
	void versionTest() {

		final String[] args = {"-V"};
		final Main main = new Main(args);

		main.run();
	}

}
