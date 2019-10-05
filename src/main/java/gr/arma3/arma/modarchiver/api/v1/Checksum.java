package gr.arma3.arma.modarchiver.api.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Checksum implements ApiObject {

	/**
	 * Checksums of all chunks of this file. Chunks size is obtained from
	 * {@link #getChunkSizeKiB()}.
	 */
	@NotEmpty
	@Size(min = 1, message = "At least 1 checksum must be present.")
	private final long[] checksums;

	/**
	 * CRC of the entire file.
	 */
	private final long fileHash;


	/**
	 * Chunk size in KiB. Must be > 0.
	 */
	@Min(value = 1, message = "Minimum chunk size is 1 KiB.")
	private final int chunkSizeKiB;

	private final int fileSizeBytes;

}
