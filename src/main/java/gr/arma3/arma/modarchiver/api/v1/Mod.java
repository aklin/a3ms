package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Revisable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

	@NotEmpty(message = "type must not be empty.")
	@Builder.Default
	private final String type = "Mod";

	@NotNull(message = "Meta object must not be null.")
	@Builder.Default
	private final Meta meta = Meta.builder().build();

	/**
	 * Mod version. Auto-populated from mod.cpp and meta.cpp files.
	 */
	@NotNull(message = "Version field must not be null.")
	@Builder.Default
	private final String version = LocalDateTime.now().toString();
	/**
	 * Get the most recent modification date of all files described
	 * by this object.
	 */
	@NotNull(message = "lastRevision field must not be null.")
	@Builder.Default
	private final String lastRevision = "";

	/**
	 * Mod file and directory structure.
	 */
	private final TreeSet<ModFile> folderStructure;

	@JsonCreator
	protected static Mod deserialise(
		@JsonProperty("folderName") String folderName,
		@JsonProperty("meta") Meta meta,
		@JsonProperty("type") String type,
		@JsonProperty("version") String version,
		@JsonProperty("lastRevision") String lastRevision,
		@JsonProperty("folderStructure") TreeSet<ModFile> folderStructure
	) {
		return Mod.builder()
			.version(version)
			.meta(meta)
			.type(type)
			.folderStructure(folderStructure)
			.lastRevision(lastRevision)
			.build();
	}
}
