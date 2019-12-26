package gr.arma3.arma.modarchiver.api.v1.interfaces;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Indicates that the data of this object can be updated.
 *
 * @since 1.0
 */
public interface Revisable<T extends Revisable> extends Comparable<T> {

	/**
	 * Get last revision time.
	 *
	 * @return Last revision time.
	 */
	@NotNull
	String getLastRevision();

	@Override
	default int compareTo(T o) {
		return (int) (
			Instant.parse(getLastRevision()).toEpochMilli() -
				Instant.parse(o.getLastRevision()).toEpochMilli()
		);
	}
}
