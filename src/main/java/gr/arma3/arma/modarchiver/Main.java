package gr.arma3.arma.modarchiver;

import gr.arma3.arma.modarchiver.api.v1.util.Errors;
import gr.arma3.arma.modarchiver.cli.App;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

import java.util.concurrent.Callable;


@Getter
@RequiredArgsConstructor
public class Main implements Callable<Integer> {

	@CommandLine.Parameters
	private static String[] args;

	public static void main(String[] args) throws Exception {
		System.exit(callWithArgs(args));
	}

	public static int callWithArg(String argLine) {
		return callWithArgs(argLine.split("\\s"));
	}

	public static int callWithArgs(String[] args) {
		Main.args = args;
		return new Main().call();
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Integer call() {
		final CommandLine cmd = new CommandLine(App.class);
		final CommandLine.ParseResult result;

		result = cmd.parseArgs(args);

		if (!result.unmatched().isEmpty()) {
			System.err.println("Unmatched arguments [" + result.unmatched()
				.size() + "]:");

			result.unmatched().forEach(s -> System.err.print(s + ", "));
			System.err.println();
			System.err.flush();

			return 1;
		}

		result.errors()
			.forEach(Errors::fromThrowable);

		return result.errors().isEmpty()
			? cmd.execute(args)
			: 1;
	}
}
