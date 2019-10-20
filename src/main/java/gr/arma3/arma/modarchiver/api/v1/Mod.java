package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Revisable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;
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
public class Mod implements ApiObject, Revisable<Mod> {

	private final String type;

	private final Meta meta;

	/**
	 * Mod version. Auto-populated from mod.cpp and meta.cpp files.
	 */
	private final String version;

	/**
	 * Get the most recent modification date of all files described
	 * by this object.
	 */
	private final String lastRevision;

	/**
	 * Mod file and directory structure.
	 */
	private final TreeSet<ModFile> folderStructure;

	@JsonCreator
	public Mod(
		@JsonProperty("folderName") String folderName,
		@JsonProperty("meta") Meta meta,
		@JsonProperty("version") String version,
		@JsonProperty("lastRevision") String lastRevision,
		@JsonProperty("folderStructure") TreeSet<ModFile> folderStructure
	) {
		this.type = "Mod";
		this.meta = Optional.ofNullable(meta).orElse(Meta.builder().build());
		this.folderStructure = folderStructure;
		this.version = Optional.ofNullable(version).orElse(LocalDateTime.now()
			.toString());
		this.lastRevision = lastRevision == null ? this.version : lastRevision;
	}
}
