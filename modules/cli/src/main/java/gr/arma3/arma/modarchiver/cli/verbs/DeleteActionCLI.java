package gr.arma3.arma.modarchiver.cli.verbs;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

import java.io.File;


@CommandLine.Command(
	description = "Delete existing resource.",
	name = "delete",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
@Getter
@ToString
public class DeleteActionCLI extends AbstractCLIAction {

	@CommandLine.Option(
		names = {"-f", "--file"},
		description = "Folder or files to process."
	)
	private File modFolder;

	@CommandLine.Option(
		defaultValue = "false",
		names = {"--dryRun"},
		description = "Do not change server state, but pretend to do so."
	)
	private boolean dryRun;

	@CommandLine.Parameters(
		paramLabel = "TYPE",
		description = "Resource type. Required."
	)
	private String resourceType;

	@CommandLine.Parameters(
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
		return null;
	}

}
