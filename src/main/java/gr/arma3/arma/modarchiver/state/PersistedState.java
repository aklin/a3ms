package gr.arma3.arma.modarchiver.state;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import gr.arma3.arma.modarchiver.state.operations.OperationException;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * State object. Used to retrieve resource hierarchies.
 */
public interface PersistedState {

	int LOOK_UP = 5521; //magic numbers
	int LOOK_DOWN = 5256;

	<E extends ApiObject> FilesystemPersistedState create(final E resource);

	/**
	 * @param resource
	 * @param <E>      Resource type.
	 * @return State after the operation.
	 */
	@NotNull <E extends ApiObject> PersistedState update(final E resource);

	/**
	 * @param resource
	 * @param <E>      Resource type.
	 * @return State after the operation.
	 */
	@NotNull <E extends ApiObject> PersistedState delete(final E resource);

	/**
	 * @param name      Resource name.
	 * @param direction
	 * @param <E>
	 * @return
	 */
	@NotNull <E extends ApiObject> E get(
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
	 * @param <E>  Result type.
	 * @return Object identified by name and/or type, or null.
	 */
	<E extends ApiObject> E get(
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
