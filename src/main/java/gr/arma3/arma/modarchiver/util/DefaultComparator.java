package gr.arma3.arma.modarchiver.util;

/**
 * Runs t
 *
 * @param <E>
 */
public final class DefaultComparator<E> implements UniqueComparator<E> {

	/**
	 * Check if
	 *
	 * @param first  First parameter.
	 * @param second Second parameter.
	 * @return True if both parameters are unique to each other.
	 */
	@Override
	public boolean isUnique(E first, E second) {
		return first != null && !first.equals(second);
	}
}
