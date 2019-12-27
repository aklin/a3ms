package gr.arma3.arma.modarchiver.cli;


import lombok.Getter;
import picocli.CommandLine;

import java.io.File;

@CommandLine.Command(
	name = "a3ms",
	description = "Arma 3 Mod Manager",
	mixinStandardHelpOptions = true,
	version = "1.0",
	subcommands = {
		CommandLine.HelpCommand.class,
		DeleteResource.class,
		CreateResource.class,
	}
)
@Getter
public class ResourceOpCommand implements Runnable {
	@CommandLine.Option(
		defaultValue = ".",
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
		description = "Resource name."
	)
	private String resourceIdentifier;

	@Override
	public void run() {

	}
}
