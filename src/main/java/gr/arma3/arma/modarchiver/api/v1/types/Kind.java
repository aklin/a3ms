package gr.arma3.arma.modarchiver.api.v1.types;

import java.util.Arrays;

/**
 * A Kind denotes a sub-type of @{@link Type}. All Types
 */
public interface Kind {

	/**
	 * Get valid Kind values.
	 *
	 * @return Kind values or empty array.
	 */
	default String[] getKinds() {
		return new String[0];
	}

	/**
	 * Get the default Kind if none explicitly assigned.
	 * Returned value must also be in {@link #getKinds()};
	 *
	 * @return Default kind.
	 */
	String getDefaultKind();

	default boolean isValidKind(final String in) {
		return Arrays.stream(getKinds()).anyMatch(kind -> kind.equalsIgnoreCase(
			in));
	}

}
