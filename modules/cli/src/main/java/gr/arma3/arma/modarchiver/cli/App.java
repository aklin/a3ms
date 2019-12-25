package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.state.redis.RedisPersistentState;
import lombok.Getter;
import picocli.CommandLine;
import state.PersistedState;

import java.io.File;

@CommandLine.Command(
	name = "a3ms",
	description = "Arma 3 Mod Manager",
	mixinStandardHelpOptions = true,
	version = "1.0",
	subcommands = {
		CommandLine.HelpCommand.class,
		CreateResource.class,
		GetResource.class,
	}
)
@Getter
public class App {
	static final PersistedState state;

	static {
		state = new RedisPersistentState();
	}

	@CommandLine.Option(
		defaultValue = ".",
		names = {"-f", "--file"},
		description = "Path to the mod folder(s)."
	)
	protected File modFolder;
	@CommandLine.Option(
		defaultValue = "false",
		names = {"--noValidation"},
		description = "Turns off input validation. Only use if you know what" +
			" you're doing."
	)
	private boolean validateInput;
	@CommandLine.Option(
		defaultValue = "false",
		names = {"--dryRun"},
		description = "Do not change server state, but pretend to do so. " +
			"Ignores --noValidation."
	)
	private boolean dryRun = false;
	@CommandLine.Parameters(
		index = "0",
		defaultValue = "",
		paramLabel = "TYPE",
		description = "Resource type. Required."
	)
	private String resourceType;

	@CommandLine.Parameters(
		index = "1",
		defaultValue = "",
		paramLabel = "NAME",
		description = "Resource name. If left empty all resources of " +
			"specified type will be returned."
	)
	private String resourceIdentifier;

}
