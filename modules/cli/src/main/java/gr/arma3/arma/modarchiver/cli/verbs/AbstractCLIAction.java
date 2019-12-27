package gr.arma3.arma.modarchiver.cli.verbs;

import gr.arma3.arma.modarchiver.api.v1.OpResult;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.Getter;
import picocli.CommandLine;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.Callable;

@Getter
abstract class AbstractCLIAction
	implements Callable<OperationResult>, CommandLine.IExitCodeGenerator {
	private ExitCondition exitCondition = null;

	public abstract boolean isDryRun();

	public abstract String getResourceType();

	public abstract String getResourceIdentifier();

	public abstract File getModFolder();

	public final OperationResult call() {
		System.out.println("Executing!");
		System.out.println(this.toString());
		ApiObject input = null;


		try {
			input = processInput();
		} catch (Exception e) {
			exitCondition = ExitCode.ResourceOperation.PARSE_ERROR;
		}


		if (!isDryRun()) try {
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


	protected ApiObject processInput() throws Exception {
		final ApiObject obj =
			Utils.parseFile(getModFolder().toPath());

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
