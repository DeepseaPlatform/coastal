package apachecli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// import static org.apache.commons.lang3.ArrayUtils.indexOf;

/**
 * The class GnuParser provides an implementation of the
 * {@link Parser#flatten(Options, char[][], boolean) flatten} method.
 *
 * @author John Keyes (john at integralsource.com)
 * @version $Revision: 680644 $, $Date: 2008-07-29 01:13:48 -0700 (Tue, 29 Jul 2008) $
 */
public class GnuParser extends Parser {
	
	
	//----------------------------------------------------------------
	// CODE FROM https://github.com/apache/commons-lang/blob/master
	//         /src/main/java/org/apache/commons/lang3/ArrayUtils.java
	//----------------------------------------------------------------
	public static final int INDEX_NOT_FOUND = -1;
	
	public static int indexOf(final char[] array, final char valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(final char[] array, final char valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
	//----------------------------------------------------------------
    // END OF
	// CODE FROM https://github.com/apache/commons-lang/blob/master
	//         /src/main/java/org/apache/commons/lang3/ArrayUtils.java
	//----------------------------------------------------------------
	
	
	/**
	 * This flatten method does so using the following rules:
	 * <ol>
	 * <li>If an {@link Option} exists for the first character of
	 * the <code>arguments</code> entry <b>AND</b> an {@link Option}
	 * does not exist for the whole <code>argument</code> then
	 * add the first character as an option to the processed tokens
	 * list e.g. "-D" and add the rest of the entry to the also.</li>
	 * <li>Otherwise just add the token to the processed tokens list.</li>
	 * </ol>
	 *
	 * @param options         The Options to parse the arguments by.
	 * @param arguments       The arguments that have to be flattened.
	 * @param stopAtNonOption specifies whether to stop flattening when
	 *                        a non option has been encountered
	 * @return a String array of the flattened arguments
	 */
	protected char[][] flatten(Options options, char[][] arguments, boolean stopAtNonOption) {
		List tokens = new ArrayList();

		boolean eatTheRest = false;

		for (int i = 0; i < arguments.length; i++) {
			char[] arg = arguments[i];

			if ("--".equals(arg)) {
				eatTheRest = true;
				tokens.add("--");
			} else if ("-".equals(arg)) {
				tokens.add("-");
			} else if (arg[0] == '-') {
				char[] opt = Util.stripLeadingHyphens(arg);
				char _opt = opt[0];
				if (options.hasOption(_opt)) {
					tokens.add(arg);
				} else {
					if (indexOf(opt, '=') != -1 && options.hasOption(_opt)) {
						// the format is --foo=value or -foo=value
						tokens.add(Arrays.copyOfRange(arg, 0, indexOf(arg, '='))); // --foo
						tokens.add(Arrays.copyOfRange(arg, indexOf(arg, '=') + 1, arg.length)); // value
					} else if (options.hasOption(arg[0])) {
						// the format is a special properties option (-Dproperty=value)
//                        tokens.add(arg.substring(0, 2)); // -D
						tokens.add(Arrays.copyOfRange(arg, 0, 2)); // -D
//                        tokens.add(arg.substring(2)); // property=value
						tokens.add(Arrays.copyOfRange(arg, 2, arg.length)); // property=value
					} else {
						eatTheRest = stopAtNonOption;
						tokens.add(arg);
					}
				}
			} else {
				tokens.add(arg);
			}

			if (eatTheRest) {
				for (i++; i < arguments.length; i++) {
					tokens.add(arguments[i]);
				}
			}
		}

//        return (String[]) tokens.toArray(new String[tokens.size()]);
		return (char[][]) tokens.toArray(new char[tokens.size()][]);
	}
}
