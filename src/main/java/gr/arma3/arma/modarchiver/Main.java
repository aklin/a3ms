package gr.arma3.arma.modarchiver;

import gr.arma3.arma.modarchiver.api.v1.util.Errors;
import gr.arma3.arma.modarchiver.cli.App;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

@Getter
@RequiredArgsConstructor
public class Main implements Runnable {

	private final String[] args;
	private int exitCode;

	public static void main(String[] args) {
		final Main instance = new Main(args);

		instance.run();

		System.exit(instance.exitCode);
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
		final CommandLine cmd = new CommandLine(App.class);

		cmd.parseArgs(args)
			.errors()
			.stream()
			.peek(Errors::fromThrowable)
			.findAny().ifPresentOrElse(
			e -> this.exitCode = 1,
			() -> this.exitCode = cmd.execute(args));
	}
}
