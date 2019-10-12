package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;

/**
 * A file that belongs to a {@link Mod}.
 *
 * @since 1.0
 */

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = ModFile.ModFileBuilder.class)
public class ModFile extends AbstractV1ApiObject {

	/**
	 * File name, not including path.
	 */
	@NotEmpty(message = "name must not be empty.")
	private final String name;

	/**
	 * File path (including name) relative to the {@link Mod} directory.
	 */
	@NotNull(message = "filePath is required.")
	private final Path filePath;

	/**
	 * Checksum information. Client can use this info to determine which parts
	 * of the files they should update.
	 */
	@NotNull
	private final Checksum checksum;
}

