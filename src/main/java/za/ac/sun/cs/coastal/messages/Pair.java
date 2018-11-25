package za.ac.sun.cs.coastal.messages;

import java.util.AbstractMap;

/**
 * Convenience class for pairs of objects. As it happens, the primary use is to
 * publish statistic about a run of COASTAL. In this case, the field names "key"
 * and "value" are appropriate. However, the class can also be used where the
 * objects in the pair have a different relationship.
 */
public class Pair extends AbstractMap.SimpleImmutableEntry<Object, Object> {

	/**
	 * Generated serial number.
	 */
	private static final long serialVersionUID = -6910313600578889269L;

	/**
	 * Obligatory constructor.
	 * 
	 * @param key
	 *            first object in pair
	 * @param value
	 *            second object in pair
	 */
	public Pair(Object key, Object value) {
		super(key, value);
	}

}
