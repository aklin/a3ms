package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Revisable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.TreeSet;

/**
 * Mod object. Corresponds to a directory which complies to Arma 3
 * mod structure.
 *
 * @since 1.0
 */
@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
@JsonTypeName("Mod")
public class Mod implements ApiObject, Revisable<Mod> {

	private final String type = "Mod";

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
	@Builder(toBuilder = true)
	public Mod(
		@JsonProperty("meta") Meta meta,
		@JsonProperty("version") String version,
		@JsonProperty("lastRevision") String lastRevision,
		@JsonProperty("folderStructure") TreeSet<ModFile> folderStructure
	) {
		this.folderStructure = folderStructure;
		this.meta = meta != null
			? meta
			: Meta.builder().build();
		this.version = version != null
			? version
			: LocalDateTime.now().toString();
		this.lastRevision = lastRevision == null
			? this.version
			: lastRevision;
	}
}
