package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.TreeSet;

/**
 * Mod object. Corresponds to a directory which complies to Arma 3
 * mod structure.
 *
 * @since 1.0
 */
@Getter
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class Mod extends AbstractV1ApiObject implements Revisable, Describable {
	/**
	 * Mod directory name. Begins with '@'.
	 */
	private final String folderName;
	/**
	 * Mod description. This is auto-populated from the mod's
	 * mod.cpp and meta.cpp files.
	 */
	@NotEmpty
	private final String description;
	/**
	 * Mod version. Auto-populated from mod.cpp and meta.cpp files.
	 */
	private final String version;
	/**
	 * Get the most recent modification date of all files described
	 * by this object.
	 */
	private final Instant lastRevision;

	/**
	 * Mod file and directory structure.
	 */
	private final TreeSet<ModFile> folderStructure;


	@JsonCreator
	protected static Mod deserialise(
		@JsonProperty("folderName") String folderName,
		@JsonProperty("description") String description,
		@JsonProperty("version") String version,
		@JsonProperty("lastRevision") Instant lastRevision,
		@JsonProperty("folderStructure") TreeSet<ModFile> folderStructure
	) {
		return Mod.builder()
			.folderName(folderName)
			.description(description)
			.version(version)
			.folderStructure(folderStructure)
			.lastRevision(lastRevision)
			.build();
	}
}
