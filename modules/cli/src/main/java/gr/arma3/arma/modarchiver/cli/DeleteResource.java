package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import lombok.Getter;
import picocli.CommandLine;


@CommandLine.Command(
	description = "Delete a resource by name.",
	name = "delete",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
@Getter
public class DeleteResource extends ResourceOperation {

	@CommandLine.ParentCommand
	private ResourceOpCommand CLI;
	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	@Override
	protected ExitCondition persistResult() {
		return null;
	}

}
