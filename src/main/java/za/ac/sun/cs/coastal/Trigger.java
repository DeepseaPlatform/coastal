package za.ac.sun.cs.coastal;

import java.util.Map;

import org.objectweb.asm.Type;

public final class Trigger {

	public static Trigger createTrigger(Map<String, Class<?>> parameters, String desc) {
		int s = desc.indexOf('('), e = desc.indexOf(')', s);
		assert s != -1;
		assert e != -1;
		String m = desc.substring(0, s).trim();
		String ps = desc.substring(s + 1, e).trim();
		int n = 0;
		String[] pn;
		Class<?>[] pt;
		int symbolicCount = 0;
		if (ps.length() > 0) {
			String[] pz = ps.split(",");
			n = pz.length;
			pn = new String[n];
			pt = new Class<?>[n];
			for (int i = 0; i < n; i++) {
				String p = pz[i].trim();
				int c = p.indexOf(':');
				if (c == -1) {
					pn[i] = null;
					pt[i] = parseType(p.substring(c + 1).trim());
				} else {
					symbolicCount++;
					pn[i] = p.substring(0, c).trim();
					pt[i] = parseType(p.substring(c + 1).trim());
					if (parameters.containsKey(pn[i]) && (parameters.get(pn[i]) != pt[i])) {
						Banner bn = new Banner('@');
						bn.println("COASTAL PROBLEM\n");
						bn.println("IGNORED TRIGGER WITH DUPLICATES \"" + desc + "\"");
						bn.display(System.out);
						return null;
					}
					parameters.put(pn[i], pt[i]);
				}
			}
		} else {
			pn = new String[0];
			pt = new Class<?>[0];
		}
		if (symbolicCount == 0) {
			Banner bn = new Banner('@');
			bn.println("COASTAL PROBLEM\n");
			bn.println("IGNORED NON-SYMBOLIC TRIGGER \"" + desc + "\"");
			bn.display(System.out);
			return null;
		}
		return new Trigger(m, pn, pt);
	}

	// To implement a new type:
	// (1) Add it here
	// (2) Add a case to "toString()" method below
	// (3) Add it to HeavyMethodAdapter.visitParameter(...)
	// (4) Add it to LightMethodAdapter.visitParameter(...)
	// (5) Add a "getConcreteXXX(...)" method to SymbolicState.java
	// (6) Add a "getConcreteXXX(...)" method to VM.java
	// (7) Add it to COASTAL.parseConfigBounds()
	private static Class<?> parseType(String type) {
		int i = type.indexOf('[');
		if (i > -1) {
			String arrayType = type.substring(0, i);
			if (arrayType.equals("int")) {
				return int[].class;
			} else if (arrayType.equals("char")) {
				return char[].class;
			} else if (arrayType.equals("byte")) {
				return byte[].class;
			}else if (arrayType.equals("String")) {
				return String[].class;
			} else {
				return Object[].class;
			}
		} else if (type.equals("int")) {
			return int.class;
		} else if (type.equals("byte")) {
			return byte.class;
		} else if (type.equals("char")) {
			return char.class;
		} else if (type.equals("String")) {
			return String.class;
		} else {
			return Object.class;
		}
	}

	private final String methodName;

	private final String[] paramNames;

	private final Class<?>[] paramTypes;

	private final String signature;

	private String stringRepr = null;

	private Trigger(String methodName, String[] paramNames, Class<?>[] paramTypes) {
		this.methodName = methodName;
		assert paramNames.length == paramTypes.length;
		this.paramNames = paramNames;
		this.paramTypes = paramTypes;
		StringBuilder sb = new StringBuilder().append('(');
		for (Class<?> c : paramTypes) {
			sb.append(Type.getDescriptor(c));
		}
		signature = sb.append(')').toString();
	}

	public boolean match(String methodName, String signature) {
		return methodName.equals(this.methodName) && signature.startsWith(this.signature);
	}

	public int getParamCount() {
		return paramNames.length;
	}

	public String getParamName(int index) {
		return paramNames[index];
	}

	public Class<?> getParamType(int index) {
		return paramTypes[index];
	}

	@Override
	public String toString() {
		if (stringRepr == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(methodName).append('(');
			for (int i = 0; i < paramNames.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}
				if (paramNames[i] != null) {
					sb.append(paramNames[i]).append(": ");
				}
				if (paramTypes[i] == null) {
					sb.append('?');
				} else if (paramTypes[i] == boolean.class) {
					sb.append("boolean");
				} else if (paramTypes[i] == int.class) {
					sb.append("int");
				} else if (paramTypes[i] == byte.class) {
					sb.append("byte");
				} else if (paramTypes[i] == char.class) {
					sb.append("char");
				} else if (paramTypes[i] == String.class) {
					sb.append("string");
				} else if (paramTypes[i] == int[].class) {
					sb.append("int[]");
				} else if (paramTypes[i] == char[].class) {
					sb.append("char[]");
				} else if (paramTypes[i] == byte[].class) {
					sb.append("byte[]");
				} else {
					sb.append('*');
				}
				// MISSING: check for overridden length arrays int[5]? int[4]?...
			}
			stringRepr = sb.append(')').toString();
		}
		return stringRepr;
	}

}
