package gr.arma3.arma.modarchiver.api.v1;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class OpResult implements OperationResult {

	private final ExitCondition exitCondition;
	private final List<ApiObject> resources;
	private final String verb;

	public OpResult(final String verb, final ExitCondition exit) {
		this(verb, exit, null);
	}

	public OpResult(
		@NonNull final String verb,
		final ExitCondition exit,
		final List<ApiObject> resources
	) {
		this.verb = verb;
		this.exitCondition = exit == null ? ExitCode.App.INIT_FAILURE : exit;
		this.resources = resources == null
			? Collections.emptyList()
			: resources;

		new RuntimeException().printStackTrace();
	}
}
