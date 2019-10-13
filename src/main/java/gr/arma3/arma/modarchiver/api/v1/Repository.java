package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Revisable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.time.Instant;
import java.util.Comparator;
import java.util.Set;

/**
 * Repository connection info.
 *
 * @since 1.0
 */
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Repository implements ApiObject, Revisable {
	private final Meta meta;
	/**
	 * All modsets defined in this repository.
	 */
	@NotNull
	private final Set<Modset> modsets;

	/**
	 * Repository description as set by the repo admin.
	 */
	@NotEmpty
	private final String description;

	/**
	 * Repository hostname.
	 */
	@NotNull
	private final InetAddress address;


	/**
	 * Get most recent revision time from all included mods.
	 *
	 * @return Last revision time.
	 */
	@JsonIgnore
	@JsonProperty("lastRevision")
	public Instant getLastRevision() {
		return modsets.stream()
			.map(Modset::getLastRevision)
			.max(Comparator.naturalOrder())
			.orElse(Instant.EPOCH);
	}
}
