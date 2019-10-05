package gr.arma3.arma.modarchiver.api.v1;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * A file that belongs to a mod.
 *
 * @since 1.0
 */

@Data
@Builder
public class ModFile implements ApiObject {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(ModFile.class.getName());
	}

	@NotEmpty(message = "friendlyName must not be empty.")
	private final String friendlyName;

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
	@Builder.Default
	private long checksum = 0;
}

