package gr.arma3.arma.modarchiver.cli.verbs;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.cli.App;
import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

@CommandLine.Command(
	description = "Get existing resource",
	name = "get",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
@Getter
@ToString
public class GetActionCLI extends AbstractCLIAction {


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
	protected OperationResult persistResult() throws IOException {
		final ApiObject o = processInput();
		return App.getState().get(o.getMeta().getName(), o.getType());
	}
}
