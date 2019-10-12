package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;

/**
 * A file that belongs to a mod.
 *
 * @since 1.0
 */

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ModFile.ModFileBuilder.class)
public class ModFile extends AbstractV1ApiObject {

	@NotEmpty(message = "name must not be empty.")
	private final String name;

	@NotNull(message = "filePath is required.")
	private final Path filePath;

	/**
	 * File size in bytes.
	 */
	@Min(value = 1, message = "Minimum file size is 0 bytes.")
	private final int fileSizeBytes;

	/**
	 * Checksum for the entire file. For quick and dirty comparisons.
	 */
	private final long checksum;
}

