package gr.arma3.arma.modarchiver.cli;

import picocli.CommandLine;

@CommandLine.Command(
	name = "a3ms",
	description = "Arma 3 Mod Manager",
	mixinStandardHelpOptions = true,
	version = "1.0",
	subcommands = {
		CommandLine.HelpCommand.class,
		GetResource.class,
		CreateResource.class
	}
)
public class App {
}
