package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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

//	@Test
	void createTest() throws IOException {
		final String raw;
		final String path =
			URLDecoder.decode(getClass().getClassLoader()
					.getResource("bar.yaml").getFile(),
				StandardCharsets.UTF_8
			);
		final File validFile = new File(path);

		raw = Files.readString(validFile.toPath());
		System.out.println("Raw");
		System.out.println(raw);
		System.out.println("*********");

		final ApiObject modset = Utils.deserialize(raw);

		assertEquals(0,
			Main.callWithArgs("create", "-f", validFile.getPath()));
	}


	@Test
	void testNoArgs() {
		assertEquals(2, Main.callWithArgs(""));
	}
}
