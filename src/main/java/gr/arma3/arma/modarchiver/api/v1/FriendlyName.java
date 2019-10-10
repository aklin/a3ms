package gr.arma3.arma.modarchiver.api.v1;

/**
 * @since 1.0
 */

public interface FriendlyName {

	/**
	 * Get user-friendly name.
	 *
	 * @return user-friendly name
	 */
	default String getFriendlyName() {
		return Defaults.NAME;
	}
}
