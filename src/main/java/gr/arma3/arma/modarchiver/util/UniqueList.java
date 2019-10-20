package gr.arma3.arma.modarchiver.util;


import java.util.List;
import java.util.ListIterator;

/**
 *
 */
public interface UniqueList<E> extends List<E> {

	@Override
	void add(int index, E element);

	@Override
	boolean add(E e);

	/**
	 * Same as <code>{@link #ensureUniqueness(UniqueComparator)}</code> with
	 * a null parameter.
	 *
	 * @return This.
	 */
	default UniqueList<E> ensureUniqueness() {
		return this.ensureUniqueness(null);
	}

	/**
	 * Checks all elements against the given comparator and removes those that
	 * fail. In all removals, the element encountered first will be the one
	 * retained.
	 *
	 * @param comparator Runs provided comparator on this list.
	 * @return This.
	 */
	default UniqueList<E> ensureUniqueness(UniqueComparator<E> comparator) {
		final ListIterator<E> it = this.listIterator();
		comparator = comparator == null
			? new DefaultComparator<>()
			: comparator;

		while (it.hasNext()) {
			final E current = it.next();
			final E next = it.hasNext() ? it.next() : null;

			if (next == null || comparator.isUnique(current, next)) {
				break;
			}

			it.remove(); //remove 'next'
		}

		return this;
	}
}
