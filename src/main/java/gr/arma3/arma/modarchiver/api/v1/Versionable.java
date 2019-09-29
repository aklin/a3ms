package gr.arma3.arma.modarchiver.api.v1;

/**
 * @since 1.0
 */

public interface Versionable {

	/**
	 * Get object version. No versioning scheme is implied. Different mods are
	 * expected to use their own schemes. Callers must know what format to
	 * expect and how to parse it.
	 *
	 * @return Object version.
	 */
	String getVersion();
}
