package gr.arma3.arma.modarchiver.api.v1;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
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
public class Modset implements ApiObject {

	@NotNull
	@Builder.Default
	private final List<Mod> modList = Collections.emptyList();
	private final Class<Modset> classRef = Modset.class;

	@NotNull
	private final Meta meta;
	private transient int modListHashCode;

	@Builder.Default
	private final String type = "Modset";

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

	public int size() {
		return getModList().size();
	}

	public boolean isEmpty() {
		return getModList().isEmpty();
	}
}
