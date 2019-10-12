package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Revisable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.TreeSet;

/**
 * Mod object. Corresponds to a directory which complies to Arma 3
 * mod structure.
 *
 * @since 1.0
 */
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Mod implements ApiObject, Revisable {

	private final MetaInfo meta;

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
		@JsonProperty("meta") MetaInfo meta,
		@JsonProperty("version") String version,
		@JsonProperty("lastRevision") Instant lastRevision,
		@JsonProperty("folderStructure") TreeSet<ModFile> folderStructure
	) {
		return Mod.builder()
			.version(version)
			.meta(meta)
			.folderStructure(folderStructure)
			.lastRevision(lastRevision)
			.build();
	}
}
