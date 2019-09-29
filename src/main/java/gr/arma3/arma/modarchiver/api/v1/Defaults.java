package gr.arma3.arma.modarchiver.api.v1;

public interface Defaults extends Versionable {
	/**
	 * Default chunk size over which the checksum is calculated.
	 */
	int CHUNK_SIZE_KIB = 4;
}
