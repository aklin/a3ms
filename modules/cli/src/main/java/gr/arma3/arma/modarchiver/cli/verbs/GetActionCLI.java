package gr.arma3.arma.modarchiver.cli.verbs;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import picocli.CommandLine;

import java.io.File;

@CommandLine.Command(
	description = "Get existing resource",
	name = "get",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetActionCLI extends AbstractCLIAction {


	@CommandLine.Option(
		defaultValue = ".",
		names = {"-f", "--file"},
		description = "Folder or files to process."
	)
	private File modFolder;

	@CommandLine.Option(
		defaultValue = "false",
		names = {
			"--dryRun"
		},
		description = "Do not change server state, but pretend to do so."
	)
	private boolean dryRun;

	@CommandLine.Parameters(
		defaultValue = "",
		paramLabel = "TYPE",
		description = "Resource type. Required."
	)
	private String resourceType;

	@CommandLine.Parameters(
		defaultValue = "",
		paramLabel = "NAME",
		description = "Resource name."
	)
	private String resourceIdentifier;

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

		return null;
	}
}
