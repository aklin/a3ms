package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * A file that belongs to a {@link Mod}.
 *
 * @since 1.0
 */

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
//@JsonDeserialize(builder = ModFile.ModFileBuilder.class)
public class ModFile implements ApiObject {

	@NotEmpty(message = "type must not be empty.")
	@Builder.Default
	private final String type = "ModFile";

	/**
	 * File name, not including path.
	 */
	@NotNull(message = "meta must not be null.")
	@Builder.Default
	private final MetaInfo meta = Meta.builder().build();

	/**
	 * File path (including name) relative to the {@link Mod} directory.
	 */
	@NotEmpty(message = "filePath must not be empty.")
	private final String filePath;

	/**
	 * Checksums of all chunks of this file. Chunks size is obtained from
	 * {@link #getChunkSizeKiB()}.
	 */
	@Singular
	@NotNull
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
	private final long fileSizeBytes;


	@JsonCreator
	public static ModFile deserialize(
		@JsonProperty("meta") Meta meta,
		@JsonProperty("filePath") String path,
		@JsonProperty("fileHash") long fileHash,
		@JsonProperty("chunkSizeKiB") int chunkSizeKiB,
		@JsonProperty("checksums") List<Long> checksums,
		@JsonProperty("fileSizeBytes") int fileSizeBytes
	) {
		return ModFile.builder()
			.meta(meta)
			.filePath(path)
			.fileHash(fileHash)
			.chunkSizeKiB(chunkSizeKiB)
			.checksums(checksums)
			.fileSizeBytes(fileSizeBytes)
			.build();
	}

}

