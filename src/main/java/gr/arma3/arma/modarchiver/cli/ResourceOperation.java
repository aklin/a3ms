package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.Getter;
import picocli.CommandLine;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;

@Getter
abstract class ResourceOperation<T extends ApiObject>
	implements Callable<T>, CommandLine.IExitCodeGenerator {

	private ExitCondition exitCondition;

	@NotNull(message = "Resource must not be null.")
	private final T resource;
	private final boolean valid;
	@CommandLine.Option(
		defaultValue = "false",
		names = {"--noValidation"},
		description = "Turns off input validation. Only use if you know what" +
			" " +
			"you're doing."
	)
	private boolean validateInput;
	@CommandLine.Option(
		defaultValue = "false",
		names = {"--dryRun"},
		description = "Do not change server state, but pretend to do so. " +
			"Ignores --noValidation."
	)
	private boolean dryRun;

	public ResourceOperation(final T resource) {
		this.resource = resource;
		this.valid = Utils.validate(this) && Utils.validate(resource);
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
