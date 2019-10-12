package gr.arma3.arma.modarchiver.api.v1.interfaces;

/**
 * @since 1.0
 */

public interface Describable {

	/**
	 * Get user-friendly description.
	 *
	 * @return user-friendly description
	 */
	default String getDescription() {
		return "";
	}
}
