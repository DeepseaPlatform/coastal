package za.ac.sun.cs.coastal.messages;

/**
 * Convenience class for pairs of objects. As it happens, the primary use is to
 * publish statistic about a run of COASTAL. In this case, the field names "key"
 * and "value" are appropriate. However, the class can also be used where the
 * objects in the pair have a different relationship.
 */
public class Tuple {

	private final Object[] objects;

	public Tuple(Object... objects) {
		this.objects = objects;
	}

	public int getSize() {
		return objects.length;
	}

	public Object get(int index) {
		if ((index >= 0) && (index < objects.length)) {
			return objects[index];
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('(');
		boolean isFirst = true;
		for (Object object : objects) {
			if (isFirst) {
				isFirst = false;
			} else {
				b.append(", ");
			}
			b.append(object);
		}
		b.append(')');
		return b.toString();
	}

	@Override
	public int hashCode() {
		return objects.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other instanceof Tuple) {
			Tuple otherPair = (Tuple) other;
			return otherPair.objects.equals(objects);
		} else {
			return false;
		}
	}

}
