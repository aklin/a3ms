package state;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;

import javax.validation.constraints.NotNull;

/**
 * State object. Used to retrieve resource hierarchies.
 */
public interface PersistedState<This extends PersistedState>
	extends AutoCloseable, Cloneable {

	/**
	 * Create new resource.
	 * <p>
	 * THis operation will fail if the resource exists.
	 *
	 * @param resource Subject.
	 * @return Operation result.
	 */
	@NotNull OperationResult create(final ApiObject resource);

	/**
	 * Update existing resource.
	 * <p>
	 * If the resource cannot be found, this method must create it.
	 *
	 * @param resource Subject.
	 * @return Operation result.
	 */
	@NotNull OperationResult update(final ApiObject resource);

	/**
	 * Delete existing resource.
	 * <p>
	 * Resource must exist or this operation will report failure.
	 *
	 * @param resource Subject.
	 * @return Operation result.
	 */
	@NotNull OperationResult delete(final ApiObject resource);

	/**
	 * @param name Resource name. If null, all resources will be returned.
	 * @return This.
	 */
	@NotNull
	default OperationResult get(
		final String name
	) {
		return get(name, null);
	}


	/**
	 * Get resource by name and type.
	 *
	 * @param name Resource name. If null, all resources of matching type will
	 *             be returned.
	 * @param type Resource type. If null, all types will match.
	 * @return Operation result.
	 */
	@NotNull
	OperationResult get(
		final String name,
		final String type
	);


	@NotNull
	default OperationResult get(ApiObject resource) {
		return get(
			resource == null
				? null
				: resource.getMeta().getName(),
			resource == null
				? null
				: resource.getType()
		);
	}

	/**
	 * Optional operation. Clears state.
	 */
	default void reset() {

	}

	This clone();

}
