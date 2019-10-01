/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.diver;

import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.symbolic.ValueFactory;

/**
 * Factory for producing and manipulating symbolic values.
 */
public interface SymbolicValueFactory extends ValueFactory {

	/**
	 * Create one symbolic value given an expression.
	 *
	 * @param expression
	 *                   expression to encapsulate
	 * @return new symbolic value
	 */
	SymbolicValue createSymbolicValue(Expression expression);

	// ======================================================================
	//
	// VALUE OBJECT
	//
	// ======================================================================

	/**
	 * Interface requirements for symbolic values.
	 */
	public interface SymbolicValue extends Value {

		/**
		 * Return the expression associated with this symbolic value.
		 *
		 * @return associated expression
		 */
		Expression toExpression();

		/**
		 * Return the constant integer value associated with this symbolic value, if
		 * any.
		 *
		 * @return associated integer constant
		 */
		long toValue();

		/**
		 * Return {@code true} if and only if the value is a constant.
		 *
		 * @return {@code true} if and only if constant
		 */
		boolean isConstant();

		// ----------------------------------------------------------------------
		//
		// COMPARISONS
		//
		// ----------------------------------------------------------------------

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue eq(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue ne(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue lt(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue le(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue gt(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue ge(SymbolicValue value);

		// ----------------------------------------------------------------------
		//
		// ARITHMETIC OPERATIONS
		//
		// ----------------------------------------------------------------------

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue add(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue sub(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue mul(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue div(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue rem(SymbolicValue value);

		// ----------------------------------------------------------------------
		//
		// BIT OPERATIONS
		//
		// ----------------------------------------------------------------------

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue lshr(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue ashr(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue shl(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue bitor(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue bitand(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue bitxor(SymbolicValue value);

		// ----------------------------------------------------------------------
		//
		// MORE COMPLEX COMPARISONS
		//
		// ----------------------------------------------------------------------

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue lcmp(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue fcmpl(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue fcmpg(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue dcmpl(SymbolicValue value);

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue dcmpg(SymbolicValue value);

		// ----------------------------------------------------------------------
		//
		// CONVERSIONS
		//
		// ----------------------------------------------------------------------

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue f2d();

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue i2l();

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue i2s();

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue i2b();

		/**
		 *
		 *
		 * @param value
		 * @return
		 */
		SymbolicValue i2c();

	}

}
