package state;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import state.operations.OperationException;

import javax.validation.constraints.NotNull;

/**
 * State object. Used to retrieve resource hierarchies.
 */
public interface PersistedState extends AutoCloseable {

	int LOOK_UP = 5521; //magic numbers
	int LOOK_DOWN = 5256;

	@NotNull OperationResult create(final ApiObject resource);

	/**
	 * @param resource Subject.
	 * @return Operation result.
	 */
	@NotNull OperationResult update(final ApiObject resource);

	/**
	 * @param resource Subject.
	 * @return Operation result.
	 */
	@NotNull OperationResult delete(final ApiObject resource);

	/**
	 * @param name Resource name. If null, all resources matching the given
	 *             type will be returned.
	 * @return This.
	 */
	@NotNull OperationResult get(
		final String name
	) throws OperationException;


	/**
	 * Get resource by name and type.
	 *
	 * @param name Resource name. If null, all resources matching the given
	 *             type will be returned.
	 * @param type Resource type. Can be null.
	 * @return Operation result.
	 */
	@NotNull
	OperationResult get(
		final String name,
		final Typeable type
	) throws OperationException;

}
