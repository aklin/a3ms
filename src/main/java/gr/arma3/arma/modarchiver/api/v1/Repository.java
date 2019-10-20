package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Repository connection info.
 *
 * @since 1.0
 */
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Repository implements ApiObject {

	private final String type = "Repository";

	private final Meta meta;
	/**
	 * All modsets defined in this repository.
	 */
	private final Set<Modset> modsets;

	/**
	 * Repository description as set by the repo admin.
	 */
	@NotNull
	private final String description;

	/**
	 * Repository hostname.
	 */
	@NotEmpty(message = "Repository address must not be empty.")
	private final String address;

	@JsonCreator
	public Repository(
		@JsonProperty("meta") Meta meta,
		@JsonProperty("modsets") Set<Modset> modsets,
		@JsonProperty("description") String description,
		@JsonProperty("address") String address
	) {
		this.meta = meta;
		this.modsets = modsets;
		this.description = description != null ? description : "";
		this.address = address != null ? address : "";
	}

}
