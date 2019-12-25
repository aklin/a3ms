package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;
import picocli.CommandLine;
import state.PersistedState;

@CommandLine.Command(
	description = "Get existing resource",
	name = "get",
	mixinStandardHelpOptions = true,
	version = "1.0"
)
public class GetResource extends ResourceOperation {
	@CommandLine.Parameters(
		index = "0",
		defaultValue = "",
		paramLabel = "TYPE",
		description = "Resource type. Required."
	)
	private String resourceType;

	@CommandLine.Parameters(
		index = "1",
		defaultValue = "",
		paramLabel = "$.META.NAME",
		description = "Resource name. If left empty all resources of " +
			"specified type will be returned."
	)
	private String resourceIdentifier;

	public GetResource(PersistedState state) {
		super(state);
	}


	/**
	 * Implement this method with persistence logic.
	 *
	 * @return Exit condition
	 */
	@Override
	protected ExitCondition persistResult() {
		return ExitCode.App.OK;
	}

	@Override
	protected ApiObject processInput() throws Exception {
		System.out.println(this);
		return null;
	}
}
