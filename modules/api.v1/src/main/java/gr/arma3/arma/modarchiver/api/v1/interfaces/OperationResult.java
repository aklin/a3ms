package gr.arma3.arma.modarchiver.api.v1.interfaces;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface OperationResult {

	@NotNull
	ExitCondition getExitCondition();

	/**
	 * Get resulting resource.
	 *
	 * @return Resource.
	 */
	@NotNull
	List<ApiObject> getResources();

	@NotEmpty
	String getVerb();
}
