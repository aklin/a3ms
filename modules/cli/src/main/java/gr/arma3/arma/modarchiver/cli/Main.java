package gr.arma3.arma.modarchiver.cli;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;


@Getter
@RequiredArgsConstructor
public class Main {

	private static String[] args;

	public static void main(String[] args) throws Exception {
		System.exit(callWithArgs(args));
	}

	public static int callWithArgs(String... args) {
		Main.args = args == null || args.length == 0
			? new String[]{"-h"}
			: args;
		return new Main().call();
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	public int call() {
		return new CommandLine(App.class).execute(args);
	}
}
