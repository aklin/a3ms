package gr.arma3.arma.modarchiver.cli.verbs;

import gr.arma3.arma.modarchiver.api.v1.Meta;
import gr.arma3.arma.modarchiver.api.v1.OpResult;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@Getter
abstract class AbstractCLIAction
	implements Callable<OperationResult>, CommandLine.IExitCodeGenerator {
	private ExitCondition exitCondition = null;

	public abstract boolean isDryRun();

	public abstract String getResourceType();

	public abstract String getResourceIdentifier();

	public abstract File getModFolder();

	@Setter
	protected List<ApiObject> results = Collections.emptyList();

	public final OperationResult call() {
		final OperationResult result;

		try {
			result = isDryRun()
				? new OpResult(ExitCode.App.OK_DRY_RUN)
				: persistResult();
			exitCondition = result.getExitCondition();
		} catch (IOException e) {
			return new OpResult(ExitCode.ResourceOperation.SAVE_ERROR);
		}

		return result;
	}

	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	protected abstract OperationResult persistResult() throws IOException;


	protected ApiObject processInput() throws IOException {
		if (getModFolder() == null) {
			return new DummyAPIObject();
		}

		final ApiObject obj = Utils.parseFile(getModFolder().toPath());

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

	@Getter
	protected class DummyAPIObject implements ApiObject {
		final String type = "ResourceOperation";
		final Class<DummyAPIObject> classRef = DummyAPIObject.class;
		final MetaInfo meta = Meta.builder()
			.name(getResourceIdentifier())
			.build();
	}

}
