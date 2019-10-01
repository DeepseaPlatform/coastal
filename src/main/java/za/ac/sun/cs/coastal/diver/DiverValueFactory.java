/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.diver;

import za.ac.sun.cs.coastal.solver.Constant;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.Operation;

/**
 * Generic interface for all symbolic values.
 */
public class DiverValueFactory implements SymbolicValueFactory {

	/**
	 * Create a symbolic value given an expression.
	 *
	 * @param expression
	 *                   expression to encapsulate
	 * @return new symbolic value
	 * 
	 * @see za.ac.sun.cs.coastal.diver.SymbolicValueFactory#createSymbolicValue(za.ac.sun.cs.coastal.solver.Expression)
	 */
	@Override
	public SymbolicValue createSymbolicValue(Expression expression) {
		return new DiverValue(expression);
	}

	// ======================================================================
	//
	// VALUE OBJECT
	//
	// ======================================================================

	public class DiverValue implements SymbolicValue {

		protected final Expression expression;

		public DiverValue(Expression expression) {
			assert expression != null;
			this.expression = expression;
		}

		@Override
		public Expression toExpression() {
			return expression;
		}

		@Override
		public boolean isConstant() {
			return expression instanceof Constant;
		}

		@Override
		public long toValue() {
			if (expression instanceof IntegerConstant) {
				return ((IntegerConstant) expression).getValue();
			} else {
				return 0;
			}
		}

		// ----------------------------------------------------------------------
		//
		// COMPARISONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue eq(SymbolicValue value) {
			return createSymbolicValue(Operation.eq(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue ne(SymbolicValue value) {
			return createSymbolicValue(Operation.ne(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue lt(SymbolicValue value) {
			return createSymbolicValue(Operation.lt(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue le(SymbolicValue value) {
			return createSymbolicValue(Operation.le(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue gt(SymbolicValue value) {
			return createSymbolicValue(Operation.gt(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue ge(SymbolicValue value) {
			return createSymbolicValue(Operation.ge(expression, value.toExpression()));
		}

		// ----------------------------------------------------------------------
		//
		// ARITHMETIC OPERATIONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue add(SymbolicValue value) {
			return createSymbolicValue(Operation.add(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue sub(SymbolicValue value) {
			return createSymbolicValue(Operation.sub(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue mul(SymbolicValue value) {
			return createSymbolicValue(Operation.mul(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue div(SymbolicValue value) {
			return createSymbolicValue(Operation.div(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue rem(SymbolicValue value) {
			return createSymbolicValue(Operation.rem(expression, value.toExpression()));
		}

		// ----------------------------------------------------------------------
		//
		// BIT OPERATIONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue lshr(SymbolicValue value) {
			return createSymbolicValue(Operation.lshr(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue ashr(SymbolicValue value) {
			return createSymbolicValue(Operation.ashr(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue shl(SymbolicValue value) {
			return createSymbolicValue(Operation.shl(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue bitor(SymbolicValue value) {
			return createSymbolicValue(Operation.bitor(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue bitand(SymbolicValue value) {
			return createSymbolicValue(Operation.bitand(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue bitxor(SymbolicValue value) {
			return createSymbolicValue(Operation.bitxor(expression, value.toExpression()));
		}

		// ----------------------------------------------------------------------
		//
		// MORE COMPLEX COMPARISONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue lcmp(SymbolicValue value) {
			return createSymbolicValue(Operation.lcmp(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue fcmpl(SymbolicValue value) {
			return createSymbolicValue(Operation.fcmpl(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue fcmpg(SymbolicValue value) {
			return createSymbolicValue(Operation.fcmpg(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue dcmpl(SymbolicValue value) {
			return createSymbolicValue(Operation.dcmpl(expression, value.toExpression()));
		}

		@Override
		public SymbolicValue dcmpg(SymbolicValue value) {
			return createSymbolicValue(Operation.dcmpg(expression, value.toExpression()));
		}

		// ----------------------------------------------------------------------
		//
		// CONVERSIONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue f2d() {
			return createSymbolicValue(Operation.f2d(expression));
		}

		@Override
		public SymbolicValue i2l() {
			return createSymbolicValue(Operation.i2l(expression));
		}

		@Override
		public SymbolicValue i2s() {
			return createSymbolicValue(Operation.i2s(expression));
		}

		@Override
		public SymbolicValue i2b() {
			return createSymbolicValue(Operation.i2b(expression));
		}

		@Override
		public SymbolicValue i2c() {
			return createSymbolicValue(Operation.i2c(expression));
		}

		@Override
		public String toString() {
			return expression.toString();
		}

	}

}
