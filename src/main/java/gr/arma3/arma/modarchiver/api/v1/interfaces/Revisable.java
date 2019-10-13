package gr.arma3.arma.modarchiver.api.v1.interfaces;

/**
 * Indicates that the data of this object can be updated.
 *
 * @since 1.0
 */
public interface Revisable {

	/**
	 * Get last revision time.
	 *
	 * @return Last revision time.
	 */
	String getLastRevision();
}
