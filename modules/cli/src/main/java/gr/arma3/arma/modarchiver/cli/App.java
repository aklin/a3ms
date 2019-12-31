package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.OpResult;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.cli.verbs.CreateActionCLI;
import gr.arma3.arma.modarchiver.cli.verbs.DeleteActionCLI;
import gr.arma3.arma.modarchiver.cli.verbs.GetActionCLI;
import gr.arma3.arma.modarchiver.cli.verbs.UpdateActionCLI;
import lombok.Getter;
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
@Getter
public class App implements Callable<OperationResult> {
	private static PersistedState state;

	public App() {
		state = state == null ? new MemoryPersistedState() : state;
	}

	@Override
	public OperationResult call() throws Exception {
		return new OpResult(ExitCode.App.OK);
	}

	public static PersistedState getState() {
		return state;
	}
}
