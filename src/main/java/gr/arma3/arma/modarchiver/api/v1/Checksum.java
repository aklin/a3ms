package gr.arma3.arma.modarchiver.api.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class Checksum implements ApiObject {

	/**
	 * Checksums of all chunks of this file. Chunks size is obtained from
	 * {@link #getChunkSizeKiB()}.
	 */
	@NotNull
	private final long[] checksums;


	private final long fileHash;


	/**
	 * Chunk size in KiB. Set to 4 by default.
	 */
	@DecimalMin("1")
	private final int chunkSizeKiB;

}
