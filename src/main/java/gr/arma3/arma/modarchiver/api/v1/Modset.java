package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable Modset object. Handles mod collections. Provides
 * utility methods to perform group operations on modsets.
 *
 * @since 1.0
 */

@Getter
@Builder
@JsonDeserialize(builder = Modset.ModsetBuilder.class)
public class Modset implements ApiObject {
	private final String friendlyName;
	private final String description;
	private final List<Mod> modList;
	private transient int modListHashCode;


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
