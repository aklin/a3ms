package gr.arma3.arma.modarchiver;

import gr.arma3.arma.modarchiver.api.v1.util.Errors;
import gr.arma3.arma.modarchiver.cli.App;
import picocli.CommandLine;

public class Main implements Runnable {

	@CommandLine.Parameters(index = "0", description = "hello")
	private String verb;

	public static void main(String[] args) {
		final String[] a = {"-f", "test.yaml"};
		final CommandLine cmd = new CommandLine(App.class);

		cmd.parseArgs(args)
			.errors()
			.forEach(Errors::fromThrowable);

		System.exit(cmd.execute(a));
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
