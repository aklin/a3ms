package gr.arma3.arma.modarchiver.cli.verbs;

import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.cli.App;
import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;


@CommandLine.Command(
	description = "Delete existing resource.",
	name = "delete",
	version = "1.0"
)
@Getter
@ToString
public class DeleteActionCLI extends AbstractCLIAction {

	@CommandLine.Option(
		names = {"-f", "--file"},
		hidden = true,
		description = Args.FILE_D
	)
	private File modFolder;

	@CommandLine.Option(
		defaultValue = "false",
		names = {"--dryRun"},
		hidden = true,
		description = Args.DRUN_D
	)
	private boolean dryRun;

	@CommandLine.Parameters(
		index = Args.TYPE_INDEX,
		arity = Args.TYPE_ARITY,
		paramLabel = Args.TYPE_L,
		description = Args.TYPE_D
	)
	private String resourceType;

	@CommandLine.Parameters(
		index = Args.NAME_INDEX,
		arity = Args.NAME_ARITY,
		paramLabel = Args.NAME_L,
		description = Args.NAME_D
	)
	private String resourceIdentifier;

	public DeleteActionCLI() {
		super("delete");
	}

	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	@Override
	protected OperationResult persistResult() throws IOException {
		return App.getState().delete(processInput());
	}

}
