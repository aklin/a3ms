package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;

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
	private final MetaInfo meta;

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


	@JsonCreator
	public static ModFile deserialize(
		@JsonProperty("meta") MetaInfo meta,
		@JsonProperty("filePath") String path,
		@JsonProperty("checksum") Checksum checksum
	) {
		return ModFile.builder()
			.meta(meta)
			.filePath(Path.of(path))
			.checksum(checksum)
			.build();
	}

}

