package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

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
public class Mod extends AbstractV1ApiObject implements Revisable {
	private final String folderName;
	private final String description;
	private final String version;
	private final Instant lastRevision;
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
