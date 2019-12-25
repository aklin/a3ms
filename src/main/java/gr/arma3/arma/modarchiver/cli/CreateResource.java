package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import picocli.CommandLine;

@CommandLine.Command(
	description = "Create a new resource based on input.",
	name = "create",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
public class CreateResource extends ResourceOperation {

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
