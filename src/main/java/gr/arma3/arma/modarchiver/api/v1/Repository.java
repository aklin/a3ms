package gr.arma3.arma.modarchiver.api.v1;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.time.Instant;
import java.util.Set;

/**
 * Repository connection info.
 *
 * @since 1.0
 */
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Repository extends AbstractV1ApiObject {
	private final String friendlyName;

	@NotEmpty
	private final Set<Modset> modsets;

	/**
	 * Repository description as set by the repo admin.
	 */
	@NotNull
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
	public Instant getLastRevision() {
		return null;
	}
}
