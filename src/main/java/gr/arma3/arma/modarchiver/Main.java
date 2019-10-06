package gr.arma3.arma.modarchiver;

import gr.arma3.arma.modarchiver.cli.CreateResource;
import picocli.CommandLine;

@CommandLine.Command(
	description = "Create a new resource based on input.",
	name = "create",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
public class Main implements Runnable {

	@CommandLine.Parameters(index = "0", description = "hello")
	private String verb;

	public static void main(String[] args) {
		final String[] a = {"-f", "test.yaml"};

		new CommandLine(CreateResource.class).execute(a);
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {

	}
}
