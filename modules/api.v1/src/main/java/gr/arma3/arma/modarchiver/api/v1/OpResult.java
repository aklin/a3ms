package gr.arma3.arma.modarchiver.api.v1;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class OpResult implements OperationResult {

	private final ExitCondition exitCondition;
	private final List<ApiObject> resources;

	public OpResult(final ExitCondition exit) {
		this(exit, null);
	}

	public OpResult(
		final ExitCondition exit,
		final List<ApiObject> resources
	) {
		this.exitCondition = exit == null ? ExitCode.App.INIT_FAILURE : exit;
		this.resources = resources == null
			? Collections.emptyList()
			: resources;
	}
}
