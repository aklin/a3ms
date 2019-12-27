package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import picocli.CommandLine;

@CommandLine.Command(
	description = "Create a new resource based on input.",
	name = "create",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
@Getter
@AllArgsConstructor
public class CreateResource extends ResourceOperation {

	@CommandLine.ParentCommand
	private ResourceOpCommand CLI;

	public CreateResource() {

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
