package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCondition;

public interface OperationResult {

	ExitCondition getExitCondition();

	ApiObject getResource();
}
