package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import picocli.CommandLine;

@CommandLine.Command(
	description = "Get existing resource",
	name = "get",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetResource extends ResourceOperation {
	@CommandLine.ParentCommand
	private App app;

	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	@Override
	protected ExitCondition persistResult() {
		return ExitCode.App.OK;
	}

	@Override
	protected ApiObject processInput() throws Exception {
//		System.out.println(this);
		return null;
	}
}
