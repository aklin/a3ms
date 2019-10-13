package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Revisable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Immutable Modset object. Handles mod collections. Provides
 * utility methods to perform group operations on modsets.
 *
 * @since 1.0
 */

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Modset implements ApiObject, Revisable {
	@Nullable
	private final List<Mod> modList;
	@NotNull
	private final Meta meta;
	private transient int modListHashCode;

	@NotEmpty(message = "type must not be empty.")
	@Builder.Default
	private final String type = "Modset";

	@Override
	@JsonIgnore
	public String getLastRevision() {
		return modList.stream()
			.map(Mod::getLastRevision)
			.max(Comparator.naturalOrder())
			.orElse(String.valueOf(Instant.EPOCH));
	}

	/**
	 * Create a new modset containing the common mods found in
	 * both modsets.
	 * <p>
	 * Does not change the contents of this modset in any way.
	 *
	 * @param addAll Mods to remove from result
	 * @return New modset with mods present in both modsets.
	 */
	public Modset combine(final Modset addAll) {
		final List<Mod> result = new ArrayList<>(modList);
		result.addAll(addAll.modList);
		return Modset.builder().modList(result).build();
	}

	/**
	 * Create a new modset containing the common mods found in
	 * both modsets.
	 * <p>
	 * Does not change the contents of this modset in any way.
	 *
	 * @param retainAll Mods to remove from result
	 * @return New modset with mods present in both modsets.
	 */
	public Modset intersect(final Modset retainAll) {
		final List<Mod> result = new ArrayList<>(modList);
		result.retainAll(retainAll.modList);
		return Modset.builder().modList(result).build();
	}

	/**
	 * Create a new modset based on this and remove the mods specified
	 * in the parameter.
	 * <p>
	 * Does not change the contents of this modset in any way.
	 *
	 * @param removeAll Mods to remove from result
	 * @return New modset without the mods in the parameter.
	 */
	public Modset subtract(final Modset removeAll) {
		final List<Mod> result = new ArrayList<>(modList);
		result.removeAll(removeAll.modList);
		return Modset.builder().modList(result).build();
	}
}
