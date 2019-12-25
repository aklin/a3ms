package gr.arma3.arma.modarchiver.state;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import gr.arma3.arma.modarchiver.state.operations.OperationException;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * State object. Used to retrieve resource hierarchies.
 *
 * @param <This> Subclass implementers should use their own names.
 */
public interface PersistedState<This extends PersistedState> {

	int LOOK_UP = 5521; //magic numbers
	int LOOK_DOWN = 5256;

	@NotNull This create(final ApiObject resource);

	/**
	 * @param resource
	 * @return State after the operation.
	 */
	@NotNull This update(final ApiObject resource);

	/**
	 * @param resource
	 * @return State after the operation.
	 */
	@NotNull This delete(final ApiObject resource);

	/**
	 * @param name      Resource name.
	 * @param direction Lookup direction.
	 * @return This.
	 */
	@NotNull ApiObject get(
		final String name,
		final Lookup direction
	) throws OperationException;


	@NotNull
	ApiObject get(
		final String name,
		final Typeable type,
		final Lookup direction
	) throws OperationException;

	/**
	 * Get entity.
	 *
	 * @param name Name.
	 * @param type Type.
	 * @return Object identified by name and/or type, or null.
	 */
	ApiObject get(
		@Nullable final String name,
		@Nullable final Typeable type
	) throws OperationException;

	enum Lookup {
		/**
		 * Retrieve a single result.
		 */
		SINGLE,

		/**
		 * Get resource and its parents.
		 */
		PARENTS,

		/**
		 * Get resource and its children.
		 */
		CHILDREN,
	}
}
