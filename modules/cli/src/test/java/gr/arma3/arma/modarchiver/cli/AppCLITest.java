package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class AppCLITest {

	@Test
	void helpTest() {
		assertEquals(0, Main.callWithArgs("help"));
	}


	@Test
	void deleteTest() throws IOException {
		final File validFile =
			new File(URLDecoder.decode(getClass().getClassLoader()
					.getResource("bar.yaml").getFile(),
				StandardCharsets.UTF_8
			));

		final ApiObject modset =
			Utils.deserialize(Files.readString(validFile.toPath()));

		assertTrue(Main.exec(
			"delete",
			modset.getType(),
			modset.getMeta().getName()
			).getExitCondition().isError()
		);

		assertFalse(Main.exec(
			"create",
			"-f",
			validFile.getPath()
			).getExitCondition().isError()
		);


		//this must succeed
		assertFalse(Main.exec(
			"delete",
			modset.getType(),
			modset.getMeta().getName()
		).getExitCondition().isError());
	}

	@Test
	void createTest() throws IOException {
		final String raw;
		final String path =
			URLDecoder.decode(getClass().getClassLoader()
					.getResource("bar.yaml").getFile(),
				StandardCharsets.UTF_8
			);
		final File validFile = new File(path);

		raw = Files.readString(validFile.toPath());

		final ApiObject modset = Utils.deserialize(raw);

		assertEquals(0,
			Main.callWithArgs(
				"create",
				"-f",
				validFile.getPath()
			));

		assertEquals(0,
			Main.callWithArgs(
				"get",
				modset.getType(),
				modset.getMeta().getName()
			));
	}


	@Test
	void testNoArgs() {
		assertEquals(0, Main.callWithArgs(""));
	}
}
