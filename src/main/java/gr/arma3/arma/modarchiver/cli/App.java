package gr.arma3.arma.modarchiver.cli;

import picocli.CommandLine;

@CommandLine.Command(
	name = "create",
	description = "Create a new resource based on input.",
	mixinStandardHelpOptions = true,
	version = "1.0",
	subcommands = {
		CommandLine.HelpCommand.class,
		CreateResource.class
	}
)
public class App {
}
