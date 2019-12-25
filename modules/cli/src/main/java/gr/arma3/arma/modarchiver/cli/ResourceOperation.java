package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.Getter;
import picocli.CommandLine;

import java.util.Collections;
import java.util.concurrent.Callable;

@Getter
abstract class ResourceOperation
	implements Callable<OperationResult>, CommandLine.IExitCodeGenerator {
	private ExitCondition exitCondition = null;


	public final OperationResult call() {
		ApiObject input = null;


		try {
			input = processInput();
		} catch (Exception e) {
			exitCondition = ExitCode.ResourceOperation.PARSE_ERROR;
		}

		if (!getApp().isDryRun()) try {
			exitCondition = persistResult();
		} catch (Exception e) {
			exitCondition = ExitCode.ResourceOperation.PERSISTENCE_ERROR;
		}

		return new OpResult(exitCondition, Collections.singletonList(input));
	}

	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	protected abstract ExitCondition persistResult();

	protected abstract App getApp();

	protected ApiObject processInput() throws Exception {
		final ApiObject obj =
			Utils.parseFile(getApp().getModFolder().toPath());

		this.setExitCondition(
			Utils.validate(obj)
				? ExitCode.App.OK
				: ExitCode.ResourceOperation.NOT_FOUND
		);
		return obj;

	}

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
