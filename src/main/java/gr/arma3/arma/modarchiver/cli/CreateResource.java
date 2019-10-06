package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.ToString;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

/*@CommandLine.Command(
	description = "Create a new resource based on input.",
	name = "create",
	mixinStandardHelpOptions = true,
	version = "1.0"
)*/
@ToString
public class CreateResource implements Callable<Boolean> {


	@CommandLine.Option(
		defaultValue = ".",
		names = {"-f", "--file"},
		description = "Path to the mod folder."
	)
	private File modFolder;

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Boolean call() throws Exception {
		System.out.println(this);
		return Utils.validate(Utils.parseFile(modFolder.toPath()));
	}
}
