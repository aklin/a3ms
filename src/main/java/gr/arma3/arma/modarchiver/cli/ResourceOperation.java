package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import lombok.Getter;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Getter
abstract class ResourceOperation
	implements Callable<OperationResult>, CommandLine.IExitCodeGenerator {

	private ExitCondition exitCondition = null;

	@CommandLine.Option(
		defaultValue = "false",
		names = {"--noValidation"},
		description = "Turns off input validation. Only use if you know what" +
			" you're doing."
	)
	private boolean validateInput;
	@CommandLine.Option(
		defaultValue = "false",
		names = {"--dryRun"},
		description = "Do not change server state, but pretend to do so. " +
			"Ignores --noValidation."
	)
	private boolean dryRun = false;


	public final OperationResult call() throws Exception {
		ApiObject input = null;


		try {
			input = processInput();
		} catch (Exception e) {
			exitCondition = ExitCode.ResourceOperation.PARSE_ERROR;
		}

		if (!this.dryRun) try {
			exitCondition = persistResult();
		} catch (Exception e) {
			exitCondition = ExitCode.ResourceOperation.PERSISTENCE_ERROR;
		}

		return new OpResult(exitCondition, input);
	}

	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	protected abstract ExitCondition persistResult();

	protected abstract ApiObject processInput() throws Exception;

	public final ExitCondition getExitCondition() {
		return exitCondition;
	}

	protected final void setExitCondition(final ExitCondition exitCondition) {

		if (exitCondition == null) {
			throw new IllegalArgumentException(
				"Exit condition must not be null.");
		}

		this.exitCondition = exitCondition;
	}

	public final boolean isExitConditionError() {
		return exitCondition != ExitCode.App.OK;
	}

	@Override
	public final int getExitCode() {
		return exitCondition.getExitCode();
	}

}
