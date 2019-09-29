package gr.arma3.arma.modarchiver.api.v1;

import lombok.Builder;
import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;

/**
 * Repository connection info.
 *
 * @since 1.0
 */
@Data
public class Repository implements ApiObject {
	private final String friendlyName;

	private final Set<Modset> modsets;

	/**
	 * Repository description as set by the repo admin.
	 */
	private final String description;

	/**
	 * Repository hostname.
	 */
	private final InetAddress address;


	@Builder
	private Repository(
		String friendlyName, Set<Modset> modsets, String description,
		String address
	) throws UnknownHostException {

		this.friendlyName = friendlyName == null ? "" : friendlyName;
		this.description = description == null ? "" : description;
		this.modsets = modsets == null ? Collections.emptySet() : modsets;
		this.address = InetAddress.getByName(address);
	}

	/**
	 * Get most recent revision time from all included mods.
	 *
	 * @return Last revision time.
	 */
	public Instant getLastRevision() {
		return null;
	}
}
