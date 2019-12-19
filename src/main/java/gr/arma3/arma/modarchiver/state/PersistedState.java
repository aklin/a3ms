package gr.arma3.arma.modarchiver.state;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;

/**
 * State object. Used to retrieve resource hierarchies.
 */
public interface PersistedState {

	int LOOK_UP = 5521; //magic numbers
	int LOOK_DOWN = 5256;

	default <E extends ApiObject> E create(final E resource) {
		Utils.validate(resource);

		return null;
	}

	<E extends ApiObject> E update(final E resource);

	<E extends ApiObject> boolean delete(final E resource);

	/**
	 * @param name
	 * @param type
	 * @param direction
	 * @param <E>
	 * @return
	 */
	<E extends ApiObject> E get(
		final String name,
		final Typeable type,
		final Lookup direction
	);

	/**
	 * Get entity.
	 *
	 * @param name Name.
	 * @param type Type.
	 * @param <E>  Result type.
	 * @return Object identified by name and/or type, or null.
	 */
	<E extends ApiObject> E get(
		final String name,
		final Typeable type
	);

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
