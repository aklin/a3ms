package gr.arma3.arma.modarchiver.cli.verbs;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
class OpResult implements OperationResult {

	private final ExitCondition exitCondition;
	private final List<ApiObject> resources;
}
