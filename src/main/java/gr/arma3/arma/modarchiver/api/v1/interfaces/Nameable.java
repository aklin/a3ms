package gr.arma3.arma.modarchiver.api.v1.interfaces;

/**
 * @since 1.0
 */

public interface Nameable {

	/**
	 * Get user-friendly name.
	 *
	 * @return user-friendly name
	 */
	default String getName() {
		return "";
	}
}