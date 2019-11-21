package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.Mod;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.ToString;
import picocli.CommandLine;

import java.io.File;

@CommandLine.Command(
	description = "Create a new resource based on input.",
	name = "create",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
@ToString
public class CreateResource extends ResourceOperation {

	@CommandLine.Option(
		defaultValue = ".",
		names = {"-f", "--file"},
		description = "Path to the mod folder(s)."
	)
	private File modFolder;

	CreateResource() {
		super(Mod.builder().build());
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	protected ApiObject processInput() throws Exception {
		System.out.println(this);
		final ApiObject obj = Utils.parseFile(modFolder.toPath());

		this.setExitCondition(
			Utils.validate(obj)
				? ExitCode.App.OK
				: ExitCode.ResourceOperation.NOT_FOUND
		);
		return obj;
	}

	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	@Override
	protected ExitCondition persistResult() {
		return ExitCode.App.INIT_FAILURE;
	}
}
