package za.ac.sun.cs.coastal;

import java.util.Map;

import org.objectweb.asm.Type;

import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.instrument.HeavyMethodAdapter;
import za.ac.sun.cs.coastal.instrument.LightMethodAdapter;
import za.ac.sun.cs.coastal.surfer.TraceState;
import za.ac.sun.cs.coastal.symbolic.VM;

/**
 * Triggers describe the methods and their symbolic parameters that switch on
 * symbolic execution.
 * 
 * One important aspect of triggers is that they used to handle the type
 * information for parameters. Over time, the locus of control for types has
 * moved to other places, but for historical reasons, this class contains the
 * information for adding new types:
 * 
 * <ol>
 * 
 * <li>Add an appropriate <code>if</code>-statement to
 * {@link #parseType(String)}.</li>
 * 
 * <li>Add the appropriate code to {@link #toString()}.</li>
 * 
 * <li>Add instrumentation to "pick up" values for the type to
 * {@link HeavyMethodAdapter#visitParameter(String, int)}.</li>
 * 
 * <li>Add instrumentation to "pick up" values for the type to
 * {@link LightMethodAdapter#visitParameter(String, int)}.</li>
 * 
 * <li>Add a <code>getConcreteXXX(...)</code> method to {@link VM}.</li>
 * 
 * <li>Add a <code>getConcreteXXX(...)</code> method to
 * {@link SymbolicState}.</li>
 * 
 * <li>Add a <code>getConcreteXXX(...)</code> method to {@link TraceState}.</li>
 * 
 * <li>Add appropriate code to {@link COASTAL#parseConfigBounds()}.</li>
 * 
 * </ol>
 */
public final class Trigger {
	
	/**
	 * The name of the method (e.g., {@code "routine"}).
	 */
	private final String methodName;

	/**
	 * The fully qualified name of the class (e.g.,
	 * {@code "example.progs.Program"}).
	 */
	private final String className;


	/**
	 * The fully qualified name of the method (e.g.,
	 * {@code "example.progs.Program.routine"}).
	 */
	private final String fullName;

	/**
	 * The names of the symbolic parameters. If a parameter is non-symbolic, its
	 * entry in this array is {@code null}.
	 */
	private final String[] paramNames;

	/**
	 * The types of the parameters.
	 */
	private final Class<?>[] paramTypes;

	/**
	 * The Java signature for this trigger (excluding the return type).
	 */
	private final String signature;

	/**
	 * A string representation of the trigger.
	 */
	private String stringRepr = null;

	/**
	 * Construct a new trigger.
	 * 
	 * @param methodName
	 *            the fully qualified name of the trigger
	 * @param paramNames
	 *            the trigger parameter names
	 * @param paramTypes
	 *            the trigger parameter types
	 */
	private Trigger(String methodName, String packageName, String[] paramNames, Class<?>[] paramTypes) {
		this.methodName = methodName;
		this.className = packageName;
		this.fullName = packageName + "." + methodName;
		assert paramNames.length == paramTypes.length;
		this.paramNames = paramNames;
		this.paramTypes = paramTypes;
		StringBuilder sb = new StringBuilder().append('(');
		for (Class<?> c : paramTypes) {
			sb.append(Type.getDescriptor(c));
		}
		signature = sb.append(')').toString();
	}

	/**
	 * Check if this trigger corresponds to the given method name and signature.
	 * This is used to avoid adding duplicate triggers.
	 * 
	 * @param methodName
	 *            the fully qualified name of the method
	 * @param signature
	 *            the method signature (return type excluded)
	 * @return {@code true} if and only the name and signature are the same as
	 *         this triggers
	 */
	public boolean match(String methodName, String signature) {
		return methodName.equals(this.fullName) && signature.startsWith(this.signature);
	}

	/**
	 * Return the name of the trigger method.
	 * 
	 * @return the name of the method
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Return the name of the trigger class.
	 * 
	 * @return the name of the class
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Return the number of parameters.
	 * 
	 * @return the number of parameters
	 */
	public int getParamCount() {
		return paramNames.length;
	}

	/**
	 * Return the name of the given parameter. This will be {@code null} if the
	 * parameter is not symbolic.
	 * 
	 * @param index
	 *            the number of parameter, starting from 0
	 * @return the name of the parameter, or {@code null} if the parameter is
	 *         not symbolic
	 */
	public String getParamName(int index) {
		return paramNames[index];
	}

	/**
	 * Return the array that lists all the parameter types.
	 * 
	 * @return the parameter types
	 */
	public Class<?>[] getParamTypes() {
		return paramTypes;
	}
	
	/**
	 * Return the type of the given parameter. This will be a valid type even if
	 * the parameter is not symbolic.
	 * 
	 * @param index
	 *            the number of parameter, starting from 0
	 * @return the type of the parameter
	 */
	public Class<?> getParamType(int index) {
		return paramTypes[index];
	}

	/**
	 * Compute (if necessary) and return the string representation of a trigger.
	 * 
	 * @return a string description of the trigger
	 * 
	 * @see java.lang.Object#toString()
	 */
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
				} else if (paramTypes[i] == byte.class) {
					sb.append("byte");
				} else if (paramTypes[i] == short.class) {
					sb.append("short");
				} else if (paramTypes[i] == char.class) {
					sb.append("char");
				} else if (paramTypes[i] == int.class) {
					sb.append("int");
				} else if (paramTypes[i] == long.class) {
					sb.append("long");
				} else if (paramTypes[i] == float.class) {
					sb.append("float");
				} else if (paramTypes[i] == double.class) {
					sb.append("double");
				} else if (paramTypes[i] == String.class) {
					sb.append("string");
				} else if (paramTypes[i] == boolean[].class) {
					sb.append("boolean[]");
				} else if (paramTypes[i] == byte[].class) {
					sb.append("byte[]");
				} else if (paramTypes[i] == short[].class) {
					sb.append("short[]");
				} else if (paramTypes[i] == char[].class) {
					sb.append("char[]");
				} else if (paramTypes[i] == int[].class) {
					sb.append("int[]");
				} else if (paramTypes[i] == long[].class) {
					sb.append("long[]");
				} else if (paramTypes[i] == float[].class) {
					sb.append("float[]");
				} else if (paramTypes[i] == double[].class) {
					sb.append("double[]");
				} else if (paramTypes[i] == String[].class) {
					sb.append("string[]");
				} else {
					sb.append('*');
				}
				// MISSING: check for overridden length arrays int[5]? int[4]?...
			}
			stringRepr = sb.append(')').toString();
		}
		return stringRepr;
	}

	/**
	 * Create a new trigger from a string specified in the COASTAL
	 * configuration. The string should consist of the name of the method, an
	 * open parenthesis ({@code (}), a comma-separated list of zero or more
	 * parameters, and a closing parenthesis ({@code )}). Each parameter
	 * consists of a name, followed by a colon ({@code :}), and a type
	 * description. If the method name is not fully qualified, the class name
	 * parameter is used.
	 * 
	 * @param desc
	 *            the description of the trigger method
	 * @param className
	 *            optional name of the entry point class
	 * @param parameters
	 *            a mapping that relates parameter names to Java classes
	 * @return the new trigger
	 */
	public static Trigger createTrigger(String desc, String className, Map<String, Class<?>> parameters) {
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
//		if (symbolicCount == 0) {
//			Banner bn = new Banner('@');
//			bn.println("COASTAL PROBLEM\n");
//			bn.println("IGNORED NON-SYMBOLIC TRIGGER \"" + desc + "\"");
//			bn.display(System.out);
//			return null;
//		}
		String tm = "", tc = "";
		int dot = m.lastIndexOf('.');
		if (dot != -1) {
			tm = m.substring(dot + 1);
			tc = m.substring(0, dot);
		} else {
			tm = m;
			tc = className;
		}
		return new Trigger(tm, tc, pn, pt);
	}

	/**
	 * Create a new trigger that represents the entry point for the analysis
	 * run. The string should consist of the name of the method, an open
	 * parenthesis ({@code (}), a comma-separated list of zero or more types,
	 * and a closing parenthesis ({@code )}). If the method name is not fully
	 * qualified, the class name parameter is used.
	 * 
	 * @since 0.0.3
	 * 
	 * @param desc
	 *            the description of the entry point
	 * @param className
	 *            optional name of the entry point class
	 * @return the new trigger
	 */
	public static Trigger createTrigger(String desc, String className) {
		int s = desc.indexOf('('), e = desc.indexOf(')', s);
		assert s != -1;
		assert e != -1;
		String m = desc.substring(0, s).trim();
		String ps = desc.substring(s + 1, e).trim();
		int n = 0;
		String[] pn;
		Class<?>[] pt;
		if (ps.length() > 0) {
			String[] pz = ps.split(",");
			n = pz.length;
			pn = new String[n];
			pt = new Class<?>[n];
			for (int i = 0; i < n; i++) {
				String p = pz[i].trim();
				pn[i] = null;
				pt[i] = parseType(p.trim());
			}
		} else {
			pn = new String[0];
			pt = new Class<?>[0];
		}
		String tm = "", tc = "";
		int dot = m.lastIndexOf('.');
		if (dot != -1) {
			tm = m.substring(dot + 1);
			tc = m.substring(0, dot);
		} else {
			tm = m;
			tc = className;
		}
		return new Trigger(tm, tc, pn, pt);
	}

	/**
	 * Convert a string description of a type to a Java class instance. If the
	 * string is not recognized, {@code Object.class} is returned. The following
	 * types are currently recognized:
	 * 
	 * <ul>
	 * <li>{@code boolean}</li>
	 * <li>{@code byte}</li>
	 * <li>{@code short}</li>
	 * <li>{@code char}</li>
	 * <li>{@code int}</li>
	 * <li>{@code long}</li>
	 * <li>{@code float}</li>
	 * <li>{@code double}</li>
	 * <li>{@code String}</li>
	 * <li>and any one-dimensional arrays of the above.</li>
	 * </ul>
	 * 
	 * @param type
	 *            a string description of a type
	 * @return the Java class instance for the type
	 */
	private static Class<?> parseType(String type) {
		int i = type.indexOf('[');
		if (i > -1) {
			String arrayType = type.substring(0, i);
			if (arrayType.equals("boolean")) {
				return boolean[].class;
			} else if (arrayType.equals("byte")) {
				return byte[].class;
			} else if (arrayType.equals("short")) {
				return short[].class;
			} else if (arrayType.equals("char")) {
				return char[].class;
			} else if (arrayType.equals("int")) {
				return int[].class;
			} else if (arrayType.equals("long")) {
				return long[].class;
			} else if (arrayType.equals("float")) {
				return float[].class;
			} else if (arrayType.equals("double")) {
				return double[].class;
			} else if (arrayType.equals("String")) {
				return String[].class;
			} else {
				return Object[].class;
			}
		} else if (type.equals("boolean")) {
			return boolean.class;
		} else if (type.equals("byte")) {
			return byte.class;
		} else if (type.equals("short")) {
			return short.class;
		} else if (type.equals("char")) {
			return char.class;
		} else if (type.equals("int")) {
			return int.class;
		} else if (type.equals("long")) {
			return long.class;
		} else if (type.equals("float")) {
			return float.class;
		} else if (type.equals("double")) {
			return double.class;
		} else if (type.equals("String")) {
			return String.class;
		} else {
			return Object.class;
		}
	}

}
