/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019-2020, Computer Science, Stellenbosch University.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package za.ac.sun.cs.coastal.messages;

/**
 * Convenience class for collections of two (or more) objects. As it happens,
 * the primary use is to publish statistic about a run of COASTAL. In this case,
 * the field names "key" and "value" are appropriate. However, the class can
 * also be used where the objects in the collection have a different
 * relationship.
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
