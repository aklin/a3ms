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
		initState();
	}

	private static void initState() {
		state = state == null ? new MemoryPersistedState() : state;
	}

	static void clearState() {
		state.reset();
	}


	/**
	 * Implementation note: Used by AbstractCLIAction to notify App (the
	 * "context") of the last executed operation. CLIAction does not know nor
	 * care about what App does with this information.
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
