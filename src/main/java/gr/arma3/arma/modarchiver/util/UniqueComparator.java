package gr.arma3.arma.modarchiver.util;

import lombok.NonNull;

/**
 * Signifies a uniqueness trait which is different than {@link #equals(Object)}.
 * Forms the primary key used by {@link UniqueList} to determine uniqueness.
 *
 * @param <E>
 */
@FunctionalInterface
public interface UniqueComparator<E> {

	/**
	 * Check if
	 *
	 * @param first  First parameter.
	 * @param second Second parameter.
	 * @return True if both parameters are unique to each other.
	 */
	boolean isUnique(@NonNull E first, @NonNull E second);
}
