package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import picocli.CommandLine;
import state.PersistedState;

import java.io.File;


@CommandLine.Command(
	description = "Delete a resource by name.",
	name = "delete",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
public class DeleteOperation extends ResourceOperation {


	@CommandLine.Option(
		defaultValue = ".",
		names = {"-f", "--file"},
		description = "Path to the mod folder(s)."
	)
	private File modFolder;

	public DeleteOperation(PersistedState state) {
		super(state);
	}

	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	@Override
	protected ExitCondition persistResult() {
		return null;
	}

	@Override
	protected ApiObject processInput() throws Exception {
		return null;
	}
}
