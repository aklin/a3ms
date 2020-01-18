package gr.arma3.arma.modarchiver.cli.verbs;

import gr.arma3.arma.modarchiver.api.v1.Meta;
import gr.arma3.arma.modarchiver.api.v1.OpResult;
import gr.arma3.arma.modarchiver.api.v1.UserInfoMessage;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import gr.arma3.arma.modarchiver.cli.App;
import gr.arma3.arma.modarchiver.cli.ParseAction;
import gr.arma3.arma.modarchiver.cli.ServerStateAction;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.Callable;

@Getter
@RequiredArgsConstructor
abstract class AbstractCLIAction
	implements Callable<OperationResult>,
	ServerStateAction,
	ParseAction,
	CommandLine.IExitCodeGenerator {

	private final String verb;

	private ExitCondition exitCondition = null;
	private OperationResult result = null;

	public abstract boolean isDryRun();

/*	public abstract String getResourceType();

	public abstract String getResourceIdentifier();*/

	public abstract File getModFolder();

	/**
	 * Execute command. This method is not extendable, and relies on
	 * {@link #persistResult()} being implemented. Sets
	 * {@link #getExitCondition()} and {@link #getResult()}. Calling this
	 * method more than once will not execute the command again.
	 *
	 * @return Operation result. Never null.
	 */
	public final @NonNull OperationResult call() {

		try {
			result = result != null
				? result
				: isDryRun()
					? new OpResult(this.verb, ExitCode.App.OK_DRY_RUN)
					: persistResult();
		} catch (IOException e) {
			result = new OpResult(
				this.verb,
				ExitCode.ResourceOperation.SAVE_ERROR,
				Collections.singletonList(UserInfoMessage.builder()
					.severity(10)
					.message(e.getLocalizedMessage())
					.build())
			);
		}

		exitCondition = result.getExitCondition();
		App.thisIsAHorribleCrime(result);
		return result;
	}

	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	protected abstract OperationResult persistResult() throws IOException;


	protected ApiObject processInput() throws IOException {
		final ApiObject obj = getModFolder() == null
			? new DummyAPIObject()
			: Utils.parseFile(getModFolder().toPath());

		this.setExitCondition(
			Utils.validate(obj)
				? ExitCode.App.OK
				: ExitCode.ResourceOperation.NOT_FOUND
		);
		return obj;
	}

	/**
	 * Get exit condition. Will return null if invoked before {@link #call()}.
	 *
	 * @return Exit condition.
	 */
	@Nullable
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
		return (exitCondition == null
			? ExitCode.App.INIT_FAILURE
			: exitCondition
		).getExitCode();
	}

	@Getter
	protected class DummyAPIObject implements ApiObject {
		final String type = getResourceType();
		final MetaInfo meta = Meta.builder()
			.name(getResourceIdentifier())
			.build();
	}
}
