package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.cli.verbs.CreateActionCLI;
import gr.arma3.arma.modarchiver.cli.verbs.DeleteActionCLI;
import gr.arma3.arma.modarchiver.cli.verbs.GetActionCLI;
import gr.arma3.arma.modarchiver.cli.verbs.UpdateActionCLI;
import picocli.CommandLine;
import state.MemoryPersistedState;
import state.PersistedState;

import java.util.concurrent.Callable;

@CommandLine.Command(
	name = "a3ms",
	description = "Arma 3 Mod Manager",
	mixinStandardHelpOptions = true,
	version = "1.0",
	subcommands = {
		CommandLine.HelpCommand.class,
		UpdateActionCLI.class,
		DeleteActionCLI.class,
		CreateActionCLI.class,
		GetActionCLI.class,
	}
)
public class App implements Callable<OperationResult> {
	private static PersistedState state;

	private static OperationResult lastOperation;


	public App() {
		state = state == null ? new MemoryPersistedState() : state;
	}

	/**
	 * Implementation note: Used by AbstractCLIAction to notify App (the
	 * "context") of the last executed operation. CLIAction does not know nor
	 * care about what App does with this information.
	 * <p>
	 * The reason why it's a horrible crime is, that I've been forced into a
	 * corner by picocli (not throwing shade here, just a consequence of
	 * early assumptions on my part that turned out to be wrong. For example,
	 * the assumption that picocli would actually fucking work) and I have to
	 * break modularity to get the thing chooching along.
	 *
	 * @param lastOperation
	 */
	public static void thisIsAHorribleCrime(final OperationResult lastOperation) {
		App.lastOperation = lastOperation;
	}

	public static OperationResult getLastOperation() {
		return App.lastOperation;
	}

	public static PersistedState getState() {
		return state;
	}

	@Override
	public OperationResult call() throws Exception {
		return lastOperation;
	}
}
