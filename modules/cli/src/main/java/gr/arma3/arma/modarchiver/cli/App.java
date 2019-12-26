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
		DeleteResource.class,
	}
)
@Getter
public class App {
	private static PersistedState state;
	@CommandLine.Option(
		defaultValue = ".",
		arity = "0..*",
		names = {"-f", "--file"},
		description = "Folder or files to process."
	)
	private File modFolder;
	@CommandLine.Option(
		defaultValue = "false",
		names = {"--dryRun"},
		description = "Do not change server state, but pretend to do so."
	)
	private boolean dryRun = false;
	@CommandLine.Parameters(
		index = "1",
		defaultValue = "",
		paramLabel = "NAME",
		description = "Resource name."
	)
	private String resourceIdentifier;

	@CommandLine.Parameters(
		index = "0",
		defaultValue = "",
		paramLabel = "TYPE",
		description = "Resource type. Required."
	)
	private String resourceType;

	public App() {
		state = state == null ? new RedisPersistentState() : state;
	}

	@CommandLine.Command
	public void create() {
		new CreateResource(this).persistResult();
	}

	@CommandLine.Command
	public void get() {
		new GetResource(this).persistResult();
	}

}
