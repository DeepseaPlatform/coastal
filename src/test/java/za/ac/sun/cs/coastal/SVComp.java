package za.ac.sun.cs.coastal;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SVComp {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				/************************
				 * JPF-REGRESSION TESTS *
				 ************************/
//				{ "jpf-regression", "ExLazy_true", true }, { "jpf-regression", "ExLazy_false", false },
//				{ "jpf-regression", "ExGenSymExe_true", true }, { "jpf-regression", "ExGenSymExe_false", false },
//				{ "jpf-regression", "ExSymExe1_true", true }, { "jpf-regression", "ExSymExe1_false", false },
//				{ "jpf-regression", "ExSymExe2_true", true }, { "jpf-regression", "ExSymExe2_false", false },
//				{ "jpf-regression", "ExSymExe3_true", true }, { "jpf-regression", "ExSymExe3_false", false },
//				{ "jpf-regression", "ExSymExe4_true", true }, { "jpf-regression", "ExSymExe4_false", false },
//				{ "jpf-regression", "ExSymExe5_true", true }, { "jpf-regression", "ExSymExe5_false", false },
//				{ "jpf-regression", "ExSymExe6_true", true }, // { "jpf-regression", "ExSymExe6_false", false }, //
//																// Divide by zero
//				{ "jpf-regression", "ExSymExe7_true", true }, { "jpf-regression", "ExSymExe7_false", false },
//				{ "jpf-regression", "ExSymExe8_true", true }, { "jpf-regression", "ExSymExe8_false", false },
//				{ "jpf-regression", "ExSymExe9_true", true }, { "jpf-regression", "ExSymExe9_false", false },
////				{ "jpf-regression", "ExSymExe12_true", true }, { "jpf-regression", "ExSymExe12_false", false }, // nondetShort
//				{ "jpf-regression", "ExSymExe13_true", true }, { "jpf-regression", "ExSymExe13_false", false },
////				{ "jpf-regression", "ExSymExe14_true", true }, { "jpf-regression", "ExSymExe14_false", false }, // nondetShort
////				{ "jpf-regression", "ExSymExe15_true", true }, { "jpf-regression", "ExSymExe15_false", false }, // nondetShort
//				{ "jpf-regression", "ExSymExe16_true", true }, { "jpf-regression", "ExSymExe16_false", false },
//				{ "jpf-regression", "ExSymExe17_true", true }, { "jpf-regression", "ExSymExe17_false", false },
//				{ "jpf-regression", "ExSymExe18_true", true }, { "jpf-regression", "ExSymExe18_false", false },
//				{ "jpf-regression", "ExSymExe25_true", true }, { "jpf-regression", "ExSymExe25_false", false },
//				{ "jpf-regression", "ExSymExe27_true", true }, { "jpf-regression", "ExSymExe27_false", false },
//				{ "jpf-regression", "ExSymExe28_true", true }, { "jpf-regression", "ExSymExe28_false", false },
//				{ "jpf-regression", "ExSymExeArrays_true", true }, { "jpf-regression", "ExSymExeArrays_false", false },
//				{ "jpf-regression", "ExSymExeBool_true", true }, { "jpf-regression", "ExSymExeBool_false", false },
//				{ "jpf-regression", "ExSymExeSuzette_true", true },
//				{ "jpf-regression", "ExSymExeSuzette_false", false },
//				{ "jpf-regression", "ExSymExeTestAssignments_true", true },
//				{ "jpf-regression", "ExSymExeTestAssignments_false", false },
//				{ "jpf-regression", "ExSymExeTestClassFields_true", true },
//				{ "jpf-regression", "ExSymExeTestClassFields_false", false },

				// Not working
//				{ "jpf-regression", "ExSymExeGetStatic_true", true }, // NoClassDefFoundError
//				{ "jpf-regression", "ExSymExeGetStatic_false", false }, // NoClassDefFoundError 

				/******************
				 * MINEPUMP TESTS *
				 ******************/
//				{ "MinePump", "spec1-5_product10", false }, { "MinePump", "spec1-5_product11", false },
//				{ "MinePump", "spec1-5_product12", false }, { "MinePump", "spec1-5_product13", false },
//				{ "MinePump", "spec1-5_product14", false }, { "MinePump", "spec1-5_product15", false },
//				{ "MinePump", "spec1-5_product16", false }, { "MinePump", "spec1-5_product17", false },
//				{ "MinePump", "spec1-5_product18", false }, { "MinePump", "spec1-5_product19", false },
//				{ "MinePump", "spec1-5_product1", false }, { "MinePump", "spec1-5_product20", false },
//				{ "MinePump", "spec1-5_product21", false }, { "MinePump", "spec1-5_product22", false },
//				{ "MinePump", "spec1-5_product23", false }, { "MinePump", "spec1-5_product24", false },
//				{ "MinePump", "spec1-5_product25", false }, { "MinePump", "spec1-5_product26", false },
//				{ "MinePump", "spec1-5_product27", false }, { "MinePump", "spec1-5_product28", false },
//				{ "MinePump", "spec1-5_product29", false }, { "MinePump", "spec1-5_product2", false },
//				{ "MinePump", "spec1-5_product30", false }, { "MinePump", "spec1-5_product31", false },
//				{ "MinePump", "spec1-5_product32", false }, { "MinePump", "spec1-5_product33", false },
//				{ "MinePump", "spec1-5_product34", false }, { "MinePump", "spec1-5_product35", false },
//				{ "MinePump", "spec1-5_product36", false }, { "MinePump", "spec1-5_product37", false },
//				{ "MinePump", "spec1-5_product38", false }, { "MinePump", "spec1-5_product39", false },
//				{ "MinePump", "spec1-5_product3", false }, { "MinePump", "spec1-5_product40", false },
//				{ "MinePump", "spec1-5_product41", false }, { "MinePump", "spec1-5_product42", false },
//				{ "MinePump", "spec1-5_product43", false }, { "MinePump", "spec1-5_product44", false },
//				{ "MinePump", "spec1-5_product45", false }, { "MinePump", "spec1-5_product46", false },
//				{ "MinePump", "spec1-5_product47", false }, { "MinePump", "spec1-5_product48", false },
//				{ "MinePump", "spec1-5_product49", false }, { "MinePump", "spec1-5_product4", false },
//				{ "MinePump", "spec1-5_product50", false }, { "MinePump", "spec1-5_product51", false },
//				{ "MinePump", "spec1-5_product52", false }, { "MinePump", "spec1-5_product53", false },
//				{ "MinePump", "spec1-5_product54", false }, { "MinePump", "spec1-5_product55", false },
//				{ "MinePump", "spec1-5_product56", false }, { "MinePump", "spec1-5_product5", false },
//				{ "MinePump", "spec1-5_product6", false }, { "MinePump", "spec1-5_product7", false },
//				{ "MinePump", "spec1-5_product8", false }, { "MinePump", "spec1-5_product9", false },
//				{ "MinePump", "spec1-5_product57", true }, { "MinePump", "spec1-5_product58", true },
//				{ "MinePump", "spec1-5_product59", true }, { "MinePump", "spec1-5_product60", true },
//				{ "MinePump", "spec1-5_product61", true }, { "MinePump", "spec1-5_product62", true },
//				{ "MinePump", "spec1-5_product63", true }, { "MinePump", "spec1-5_product64", true },

				/*************************
				 * JBMC-REGRESSION TESTS *
				 *************************/
//			{ "jbmc-regression", "ArithmeticException1", false },
//			{ "jbmc-regression", "ArithmeticException6", false },
//			{ "jbmc-regression", "ArrayIndexOutOfBoundsException1", false }, // Variable Array Length
//			{ "jbmc-regression", "ArrayIndexOutOfBoundsException2", false }, // Variable Array Length
//			{ "jbmc-regression", "ArrayIndexOutOfBoundsException3", false }, // Variable Array Length
//			{ "jbmc-regression", "assert2", false },
//			{ "jbmc-regression", "assert3", false },
//			{ "jbmc-regression", "assert4", false },
//			{ "jbmc-regression", "athrow1", false },
//			{ "jbmc-regression", "BufferedReaderReadLine", false }, // nondetString
//			{ "jbmc-regression", "bug-test-gen-095", false }, // nondetString
//			{ "jbmc-regression", "CharSequenceBug", false }, // nondetString
//			{ "jbmc-regression", "ClassCastException1", false }, // CHECKCAST unimplemented
//			{ "jbmc-regression", "ClassCastException3", false }, // CHECKCAST unimplemented
//			{ "jbmc-regression", "exceptions10", false }, // unknown issue
//			{ "jbmc-regression", "exceptions11", false },
//			{ "jbmc-regression", "exceptions12", false }, // unknown issue
//			{ "jbmc-regression", "exceptions13", false }, // unknown issue
//			{ "jbmc-regression", "exceptions16", false },
//			{ "jbmc-regression", "exceptions1", false }, // unknown issue
//			{ "jbmc-regression", "exceptions2", false }, // unknown issue
//			{ "jbmc-regression", "exceptions3", false }, // unknown issue
//			{ "jbmc-regression", "exceptions6", false }, // unknown issue
//			{ "jbmc-regression", "exceptions7", false }, // unknown issue
//			{ "jbmc-regression", "exceptions8", false }, // unknown issue
//			{ "jbmc-regression", "interface1", false }, // UNIMPLEMENTED INSTRUCTION: <13> INVOKEINTERFACE A f ()V (opcode: 185)
//			{ "jbmc-regression", "java_append_char", false }, // nondetBoolean
//			{ "jbmc-regression", "NegativeArraySizeException1", false },
//			{ "jbmc-regression", "NegativeArraySizeException2", false },
//			{ "jbmc-regression", "NullPointerException2", false },
//			{ "jbmc-regression", "NullPointerException3", false },
//			{ "jbmc-regression", "NullPointerException4", false },
//			{ "jbmc-regression", "RegexMatches02", false }, // nondetString
//			{ "jbmc-regression", "RegexSubstitution02", false }, // nondetString
//			{ "jbmc-regression", "return1", false },
//			{ "jbmc-regression", "return2", false },
//			{ "jbmc-regression", "StaticCharMethods02", false }, // nondetString
//			{ "jbmc-regression", "StaticCharMethods03", false }, // nondetString
//			{ "jbmc-regression", "StaticCharMethods04", false }, // nondetChar
//			{ "jbmc-regression", "StaticCharMethods05", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderAppend02", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderCapLen02", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderCapLen03", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderCapLen04", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderChars02", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderChars03", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderChars04", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderChars05", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderChars06", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderConstructors02", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderInsertDelete02", false }, // nondetString
//			{ "jbmc-regression", "StringBuilderInsertDelete03", false }, // nondetString
//			{ "jbmc-regression", "StringCompare02", false }, // nondetString
//			{ "jbmc-regression", "StringCompare03", false }, // nondetString
//			{ "jbmc-regression", "StringCompare04", false }, // nondetString
//			{ "jbmc-regression", "StringCompare05", false }, // nondetString
//			{ "jbmc-regression", "StringConcatenation02", false }, // nondetString
//			{ "jbmc-regression", "StringConcatenation03", false }, // nondetString
//			{ "jbmc-regression", "StringConcatenation04", false }, // nondetString
//			{ "jbmc-regression", "StringConstructors02", false }, // nondetString
//			{ "jbmc-regression", "StringConstructors03", false }, // nondetString
//			{ "jbmc-regression", "StringConstructors04", false }, // nondetString
//			{ "jbmc-regression", "StringConstructors05", false }, // nondetString
//			{ "jbmc-regression", "StringContains01", false }, // nondetString
//			{ "jbmc-regression", "StringContains02", false }, // nondetString
//			{ "jbmc-regression", "StringIndexMethods02", false }, // nondetString
//			{ "jbmc-regression", "StringIndexMethods03", false }, // nondetString
//			{ "jbmc-regression", "StringIndexMethods04", false }, // nondetString
//			{ "jbmc-regression", "StringIndexMethods05", false }, // nondetString
//			{ "jbmc-regression", "StringMiscellaneous02", false }, // nondetString
//			{ "jbmc-regression", "StringMiscellaneous03", false }, // nondetString
//			{ "jbmc-regression", "StringStartEnd02", false }, // nondetString
//			{ "jbmc-regression", "StringStartEnd03", false }, // nondetString
//			{ "jbmc-regression", "StringValueOf02", false }, // nondetString
//			{ "jbmc-regression", "StringValueOf03", false }, // nondetString
//			{ "jbmc-regression", "StringValueOf04", false }, // nondetBoolean
//			{ "jbmc-regression", "StringValueOf05", false }, // nondetString
//			{ "jbmc-regression", "StringValueOf06", false },
//			{ "jbmc-regression", "StringValueOf07", false }, // nondetLong
//			{ "jbmc-regression", "StringValueOf08", false }, // nondetString
//			{ "jbmc-regression", "StringValueOf09", false }, // nondetString
//			{ "jbmc-regression", "StringValueOf10", false }, // nondetString
//			{ "jbmc-regression", "SubString02", false }, // nondetString
//			{ "jbmc-regression", "SubString03", false }, // nondetString
//			{ "jbmc-regression", "TokenTest02", false }, // nondetString
//			{ "jbmc-regression", "Validate02", false }, // nondetString
//			{ "jbmc-regression", "virtual2", false },
//			{ "jbmc-regression", "aastore_aaload1", true }, // Object Arrays
//			{ "jbmc-regression", "ArithmeticException5", true },
//			{ "jbmc-regression", "array1", true }, // Variable Length Arrays
//			{ "jbmc-regression", "array2", true },
//			{ "jbmc-regression", "arraylength1", true }, // Variable Array Length
//			{ "jbmc-regression", "arrayread1", true }, // Object Arrays
//			{ "jbmc-regression", "assert1", true },
//			{ "jbmc-regression", "assert5", true },
//			{ "jbmc-regression", "assert6", true },
//			{ "jbmc-regression", "astore_aload1", true }, // I2B, I2C and Object Arrays
//			{ "jbmc-regression", "basic1", true },
//			{ "jbmc-regression", "bitwise1", true }, // nondetChar
//			{ "jbmc-regression", "boolean1", true }, // nondetBoolean
//			{ "jbmc-regression", "boolean2", true }, // nondetBoolean
//			{ "jbmc-regression", "bug-test-gen-119-2", true },
//			{ "jbmc-regression", "bug-test-gen-119", true }, // nondetBoolean
//			{ "jbmc-regression", "calc", true }, // nondetString
//			{ "jbmc-regression", "cast1", true }, // I2B, CHECKCAST
//			{ "jbmc-regression", "catch1", true }, // INSTANCEOF
//			{ "jbmc-regression", "char1", true }, // nondetString
//			{ "jbmc-regression", "charArray", true }, // nondetString
//			{ "jbmc-regression", "CharSequenceToString", true }, // nondetString
//			{ "jbmc-regression", "ClassCastException2", true }, // unknown issue
//			{ "jbmc-regression", "Class_method1", true },
//			{ "jbmc-regression", "classtest1", true },
//			{ "jbmc-regression", "const1", true },
//			{ "jbmc-regression", "constructor1", true },
//			{ "jbmc-regression", "enum1", true }, // ANEWARRAY
//			{ "jbmc-regression", "exceptions14", true }, // unknown issue
//			{ "jbmc-regression", "exceptions15", true }, // unknown issue
//			{ "jbmc-regression", "exceptions18", true },
//			{ "jbmc-regression", "exceptions4", true }, // unknown issue
//			{ "jbmc-regression", "exceptions5", true }, // unnknown issue
//			{ "jbmc-regression", "exceptions9", true },
//			{ "jbmc-regression", "fcmpx_dcmpx1", true },
//			{ "jbmc-regression", "iarith1", true },
//			{ "jbmc-regression", "iarith2", true },
//			{ "jbmc-regression", "if_acmp1", true },
//			{ "jbmc-regression", "if_expr1", true },
//			{ "jbmc-regression", "if_icmp1", true },
//			{ "jbmc-regression", "ifxx1", true },
//			{ "jbmc-regression", "Inheritance1", true },
//			{ "jbmc-regression", "instanceof1", true },
//			{ "jbmc-regression", "instanceof2", true },
//			{ "jbmc-regression", "instanceof3", true },
//			{ "jbmc-regression", "instanceof4", true },
//			{ "jbmc-regression", "instanceof5", true },
//			{ "jbmc-regression", "instanceof6", true },
//			{ "jbmc-regression", "instanceof7", true },
//			{ "jbmc-regression", "instanceof8", true },
//			{ "jbmc-regression", "lazyloading4", true },
//			{ "jbmc-regression", "list1", true },
//			{ "jbmc-regression", "long1", true },
//			{ "jbmc-regression", "lookupswitch1", true },
//			{ "jbmc-regression", "multinewarray", true },
//			{ "jbmc-regression", "NullPointerException1", true },
//			{ "jbmc-regression", "overloading1", true },
//			{ "jbmc-regression", "package1", true },
//			{ "jbmc-regression", "putfield_getfield1", true },
//			{ "jbmc-regression", "putstatic_getstatic1", true },
//			{ "jbmc-regression", "recursion2", true },
//			{ "jbmc-regression", "RegexMatches01", true },
//			{ "jbmc-regression", "RegexSubstitution01", true },
//			{ "jbmc-regression", "RegexSubstitution03", true },
//			{ "jbmc-regression", "StaticCharMethods01", true },
//			{ "jbmc-regression", "StaticCharMethods06", true },
//			{ "jbmc-regression", "store_load1", true },
//			{ "jbmc-regression", "StringBuilderAppend01", true },
//			{ "jbmc-regression", "StringBuilderCapLen01", true },
//			{ "jbmc-regression", "StringBuilderChars01", true },
//			{ "jbmc-regression", "StringBuilderConstructors01", true },
//			{ "jbmc-regression", "StringBuilderInsertDelete01", true },
//			{ "jbmc-regression", "StringCompare01", true },
//			{ "jbmc-regression", "StringConcatenation01", true },
//			{ "jbmc-regression", "StringConstructors01", true },
//			{ "jbmc-regression", "StringIndexMethods01", true },
//			{ "jbmc-regression", "StringMiscellaneous01", true },
//			{ "jbmc-regression", "StringMiscellaneous04", true },
//			{ "jbmc-regression", "StringStartEnd01", true },
//			{ "jbmc-regression", "StringValueOf01", true },
//			{ "jbmc-regression", "SubString01", true },
//			{ "jbmc-regression", "swap1", true },
//			{ "jbmc-regression", "synchronized", true },
//			{ "jbmc-regression", "tableswitch1", true },
//			{ "jbmc-regression", "TokenTest01", true },
//			{ "jbmc-regression", "uninitialised1", true },
//			{ "jbmc-regression", "Validate01", true },
//			{ "jbmc-regression", "virtual1", true },
//			{ "jbmc-regression", "virtual4", true },
//			{ "jbmc-regression", "virtual_function_unwinding", true },

				/***************************
				 * JAYHORN-RECURSIVE TESTS *
				 ***************************/

		});
	}

	private String testDir;

	private String testSubDir;

	private boolean testExpectedOutcome;

	public SVComp(String dir, String subDir, boolean expectedOutcome) {
		this.testDir = dir;
		this.testSubDir = subDir;
		this.testExpectedOutcome = expectedOutcome;
	}

	@Test
	public void testSVComp() {
		final Logger log = LogManager.getLogger("COASTAL");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/jars/" + testDir + ".xml" }, "<coastal><target><jar directory=\"tmp/" + testSubDir
						+ "\">tests/jars/" + testDir + ".jar</jar></target></coastal>");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertNotEquals(reporter.getBool("AssertController.assert-failed"), testExpectedOutcome);
	}
}
