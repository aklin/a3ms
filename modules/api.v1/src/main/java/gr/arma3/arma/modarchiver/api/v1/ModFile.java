package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * A file that belongs to a {@link Mod}.
 *
 * @since 1.0
 */

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ModFile implements ApiObject {

	String type = "ModFile";
	/**
	 * File name, not including path.
	 */
	@NotNull
	Meta meta;

	/**
	 * File path (including name) relative to the {@link Mod} directory.
	 */
	@NotEmpty(message = "filePath must not be empty.")
	String filePath;

	/**
	 * Checksums of all chunks of this file. Chunks size is obtained from
	 * {@link #getChunkSizeKiB()}.
	 */
	@Singular
	@NotNull
	List<Long> checksums;

	/**
	 * CRC of the entire file.
	 */
	long fileHash;


	/**
	 * Chunk size in KiB. Must be > 0.
	 */
	@Min(value = 1, message = "Minimum chunk size is 1 KiB.")
	int chunkSizeKiB;

	/**
	 * File size in bytes.
	 */
	@Min(value = 0, message = "Minimum file size is 0 bytes.")
	long fileSizeBytes;


	@JsonCreator
	public ModFile(
		@JsonProperty("meta") Meta meta,
		@JsonProperty("filePath") String path,
		@JsonProperty("checksums") List<Long> checksums,
		@JsonProperty("fileHash") long fileHash,
		@JsonProperty("chunkSizeKiB") int chunkSizeKiB,
		@JsonProperty("fileSizeBytes") long fileSizeBytes
	) {

		this.filePath = path != null ? path : "";
		this.fileHash = fileHash;
		this.chunkSizeKiB = Math.max(1, chunkSizeKiB);
		this.fileSizeBytes = fileSizeBytes;
		this.meta = meta != null ? meta : Meta.builder().build();
		this.checksums = new ArrayList<>();

		this.checksums.addAll(checksums);
	}

}
