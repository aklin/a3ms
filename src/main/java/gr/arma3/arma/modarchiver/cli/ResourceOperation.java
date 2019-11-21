package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.Getter;
import picocli.CommandLine;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.Callable;

@Getter
abstract class ResourceOperation
	implements Callable<OperationResult>, CommandLine.IExitCodeGenerator {

	@NotNull(message = "Resource must not be null.")
	private final ApiObject resource;
	private ExitCondition exitCondition = null;
	private final boolean valid;
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


	public ResourceOperation(final ApiObject resource) {
		this.resource = resource;
		this.valid = Utils.validate(this) && Utils.validate(resource);
	}

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

		return new OpResult(
			exitCondition,
			Optional
				.ofNullable(input)
				.orElse(null));
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
