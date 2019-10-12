package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Checksum extends AbstractV1ApiObject {

	/**
	 * Checksums of all chunks of this file. Chunks size is obtained from
	 * {@link #getChunkSizeKiB()}.
	 */
	@Singular
	@NotEmpty
	@Size(min = 1, message = "At least 1 checksum must be present.")
	private final List<Long> checksums;

	/**
	 * CRC of the entire file.
	 */
	private final long fileHash;


	/**
	 * Chunk size in KiB. Must be > 0.
	 */
	@Min(value = 1, message = "Minimum chunk size is 1 KiB.")
	private final int chunkSizeKiB;

	/**
	 * File size in bytes.
	 */
	@Min(value = 0, message = "Minimum file size is 0 bytes.")
	private final int fileSizeBytes;


	@JsonCreator
	protected static Checksum deserialise(
		@JsonProperty("name") String name,
		@JsonProperty("fileHash") long fileHash,
		@JsonProperty("chunkSizeKiB") int chunkSizeKiB,
		@JsonProperty("fileSizeBytes") int fileSizeBytes
	) {
		return Checksum.builder()
			.fileHash(fileHash)
			.name(name)
			.chunkSizeKiB(chunkSizeKiB)
			.fileSizeBytes(fileSizeBytes)
			.build();
	}

}
