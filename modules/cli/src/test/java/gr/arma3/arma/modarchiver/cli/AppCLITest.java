package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class AppCLITest {

	@AfterEach
	void clearState() {
		App.clearState();
	}

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
	void updateTest() throws IOException {
		final String original = getContents("bar.yaml");
		final File updated = getResourceFile("bar-update.yaml");
		final ApiObject modset = Utils.deserialize(original);
		final ApiObject modsetUpd =
			Utils.deserialize(getContents(updated.getName()));
		final ApiObject updateResult;
		final OperationResult getOpResult;
		final OperationResult createOpResult;

		assertEquals(0,
			Main.callWithArgs(
				"create",
				modset.getType(),
				modset.getMeta().getName()
			));

		System.out.println(">[1]==================== State");
		System.out.println(Utils.serializeAny(App.getState()));

		createOpResult = Main.exec("update",
			"-f",
			updated.getPath()
		);

		getOpResult = Main.exec(
			"get",
			"modset",
			modset.getMeta().getName()
		);


		System.out.println(">======================= createResult");
		System.out.println(Utils.serializeAny(createOpResult));
		System.out.println(">======================= opResult");
		System.out.println(Utils.serializeAny(getOpResult));

		System.out.println(">[2]==================== State");
		System.out.println(Utils.serializeAny(App.getState()));

		final ApiObject o;

		assertFalse(getOpResult.getExitCondition().isError());
		assertEquals("get", getOpResult.getVerb());
		assertEquals(1, getOpResult.getResources().size());

		o = getOpResult.getResources().get(0);


		assertEquals("bar", o.getMeta().getLabels().get("foo"));

	}

	private String getContents(String resourceName) throws IOException {
		final File file = getResourceFile(resourceName);

		return Files.readString(file.toPath());
	}

	private File getResourceFile(String resourceName) throws IOException {
		final String path = URLDecoder.decode(getClass().getClassLoader()
				.getResource(resourceName).getFile(),
			StandardCharsets.UTF_8
		);

		return new File(path);
	}

	//	@Test
	void testStderr() throws IOException {
		final PrintStream stderr = System.err;
//		final PrintStream mim = new PrintWriter(new ObjectOutputStream());

		try {
			Utils.z_test_writeToStderr();

		} finally {
			System.setErr(stderr);
		}

	}


	@Test
	void testNoArgs() {
		assertEquals(0, Main.callWithArgs(""));
	}
}
