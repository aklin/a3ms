package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.Objects;


@Getter
@RequiredArgsConstructor
public class Main {

	private static String[] args;

	public static void main(String[] args) throws Exception {
		System.exit(callWithArgs(args));
	}

	private static void setArgs(String... args) {
		args = args == null || args.length == 0
			? new String[]{"-h"}
			: args;

		final Object[] argsObj = Arrays.stream(args)
			.filter(Objects::nonNull)
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.toArray();

		Main.args = fromObj(argsObj);
	}

	public static OperationResult exec(String... args) {
		final OperationResult result;

		setArgs(args);
		result = new Main().call();

		// Default output formatter decides what to do with the result.
		System.out.println(OutputFormatter.getDefault().format(result));

		return result;

	}

	public static int callWithArgs(String... args) {
		final OperationResult res = exec(args);
		return res == null ? 0 : res.getExitCondition().getExitCode();
	}

	public static String[] getArgs() {
		return Main.args;
	}

	private static String[] fromObj(final Object[] objects) {
		final String[] res = new String[objects.length];

		for (int i = 0; i < objects.length; i++) {
			res[i] = objects[i].toString();
		}

		return res;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	private OperationResult call() {
		final CommandLine cmd = new CommandLine(new App());

		cmd.setExecutionStrategy(new CommandLine.RunAll());

		if (cmd.isUsageHelpRequested() || cmd.isVersionHelpRequested()) {
			System.out.println("help detected");

			return exec(fromObj(Arrays.stream(Main.args)
				.filter(s -> !"-h".equals(s))
				.filter(s -> !"--help".equals(s))
				.filter(s -> !"-V".equals(s))
				.filter(s -> !"--version".equals(s))
				.toArray()));

//			System.out.println(cmd.getCommand().toString());
		}

		cmd.execute(args);

		return App.getLastOperation();
	}
}
