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
package za.ac.sun.cs.coastal.taint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.diver.SymbolicValueFactory;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.solver.Constant;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.Operation;
import za.ac.sun.cs.coastal.solver.Solver;
import za.ac.sun.cs.coastal.solver.Variable;

/**
 * Factory for tainted values.
 */
public class TaintValueFactory implements SymbolicValueFactory {

	/**
	 * Shortcut to the broker instance.
	 */
	protected Broker broker;

	/**
	 * Handling of untracked methods; {@link #KILL} means that all untracked methods
	 * remove taint, {@link #TAINT} means that all untracked methods introduce
	 * taint, and {@link #CUSTOM} means that the user must specify which methods
	 * introduce and kill taint.
	 */
	private static enum ApproximationLevel {
		KILL, CUSTOM, TAINT,
	}

	/**
	 * Current handling of untracked methods.
	 */
	protected ApproximationLevel approximationLevel = ApproximationLevel.CUSTOM;

	/**
	 * Names of tainted symbolic variables.
	 */
	protected final List<String> taintParameters = new ArrayList<String>();

	/**
	 * Methods that introduce taint.
	 */
	protected final List<String> taintSources = new ArrayList<String>();

	/**
	 * Methods that remove taint.
	 */
	protected final List<String> taintSanitisers = new ArrayList<String>();

	/**
	 * Methods that leak taint.
	 */
	protected final List<String> taintSinks = new ArrayList<String>();

	/**
	 * Constraint solver.
	 */
	protected final Solver solver;

	/**
	 * Construct new tainted value factory.
	 * 
	 * @param coastal
	 *                instance of COASTAL that started this run
	 */
	public TaintValueFactory(COASTAL coastal) {
		broker = coastal.getBroker();
		solver = Solver.getSolver(coastal);
	}

	/**
	 * Construct new tainted value factory.
	 * 
	 * @param coastal
	 *                instance of COASTAL that started this run
	 * @param options
	 *                configuration options
	 */
	public TaintValueFactory(COASTAL coastal, Configuration options) {
		broker = coastal.getBroker();
		String approx = options.getString("approximation-level", "custom");
		if ("kill".equalsIgnoreCase(approx)) {
			approximationLevel = ApproximationLevel.KILL;
		} else if ("taint".equalsIgnoreCase(approx)) {
			approximationLevel = ApproximationLevel.TAINT;
		}
		taintParameters.addAll(getList(options.getString("taint-parameters")));
		taintSources.addAll(getList(options.getString("taint-sources")));
		taintSanitisers.addAll(getList(options.getString("taint-sanitisers")));
		taintSinks.addAll(getList(options.getString("taint-sinks")));
		solver = Solver.getSolver(coastal);
	}

	/**
	 * Split a string of comma-separated items in to a list of strings.
	 *
	 * @param str
	 *            comma-separate items in a string
	 * @return a list of string items
	 */
	private List<String> getList(String str) {
		if (str == null) {
			return null;
		}
		List<String> ret = new ArrayList<String>();
		for (String s : Arrays.asList(str.split(","))) {
			ret.add(s.trim());
		}
		return ret;
	}

	/**
	 * Create an untainted symbolic value given an expression.
	 *
	 * @param expression
	 *                   expression to encapsulate
	 * @return new symbolic value
	 * 
	 * @see za.ac.sun.cs.coastal.diver.SymbolicValueFactory#createSymbolicValue(za.ac.sun.cs.coastal.solver.Expression)
	 */
	@Override
	public SymbolicValue createSymbolicValue(Expression expression) {
		if (expression instanceof Variable) {
			String varName = ((Variable) expression).getName();
			if (taintParameters.contains(varName)) {
				// Remove taint from list, so we do not introduce taint later on
				broker.publishThread("taint-parameter", new Tuple(varName));
				this.taintParameters.remove(varName);
				return new TaintValue(expression, Operation.TRUE);
			}
		}
		return new TaintValue(expression, Operation.FALSE);
	}

	/**
	 * Propagates taint flow
	 * 
	 * @param expression
	 *                   the expression to be wrapped
	 * @param taintFlow
	 *                   the taint condition
	 * @return
	 */
	public SymbolicValue createSymbolicValue(Expression expression, Expression taintFlow) {
		return new TaintValue(expression, taintFlow);
	}

	/*
	 * @Override public SymbolicValue createSymbolicValue(SymbolicValue first,
	 * Expression last, int index) { // This method is only ever called my a new
	 * expression creation, we can check if // it is overwriting a tainted value if
	 * (this.solver.solve(((TaintValue) first).getConditionalTaint()) != null) {
	 * broker.publishThread("taint-overwritten", new
	 * Tuple(first.toExpression().toString(), last.toString(), index)); } return
	 * null; }
	 */

	/**
	 * Handles untracked void method calls
	 * 
	 * @param pathCondition
	 *                           The current path condition in the execution path
	 * @param classAndMethodName
	 *                           The method call
	 * @param arguments
	 *                           The parameters used in the method call
	 */
	/*
	 * @Override public void handleVoidType(Expression pathCondition, String
	 * classAndMethodName, ArrayList<SymbolicValue> arguments) { if (taintSinks !=
	 * null && taintSinks.contains(classAndMethodName)) { // All of these tainted
	 * arguments potentially leaked Expression leakCondition = Operation.FALSE; for
	 * (SymbolicValue sv : arguments) { leakCondition = Operation.or(leakCondition,
	 * ((TaintValue) sv).getConditionalTaint()); } if (pathCondition != null) {
	 * leakCondition = Operation.and(leakCondition, pathCondition); }
	 * 
	 * Input input = this.solver.solve(leakCondition); // There is a leak if input
	 * is not null since there are satisfiable inputs if (input != null) {
	 * broker.publishThread("taint-leaked", new Tuple(input.toMapString(),
	 * classAndMethodName, leakCondition.toString().length())); } } }
	 */

	/**
	 * Handles untracked return type method calls
	 * 
	 * @param pathCondition
	 *                           The path condition of the current execution path
	 * @param expr
	 *                           The return value expression
	 * @param classAndMethodName
	 *                           The method call
	 * @param arguments
	 *                           The parameters used in the method call
	 */
	/*
	 * @Override public SymbolicValue getReturnType(Expression pathCondition,
	 * Expression expr, String classAndMethodName, ArrayList<SymbolicValue>
	 * arguments) { boolean isTainted = false; boolean explicitlyTainted = false; //
	 * Assume method not tainted Expression leakCondition = Operation.FALSE;
	 * 
	 * // Zero check if SINK state!!! if (taintSinks != null &&
	 * taintSinks.contains(classAndMethodName)) { // It could be a sink state, and
	 * it could continue with flow
	 * 
	 * // All of these tainted arguments potentially leaked leakCondition =
	 * Operation.FALSE;
	 * 
	 * for (SymbolicValue sv : arguments) { leakCondition =
	 * Operation.or(leakCondition, ((TaintValue) sv).getConditionalTaint()); } if
	 * (pathCondition != null) { leakCondition = Operation.and(leakCondition,
	 * pathCondition); }
	 * 
	 * Input input = this.solver.solve(leakCondition); if (input != null) {
	 * broker.publishThread("taint-leaked", new Tuple(input.toMapString(),
	 * classAndMethodName)); } // We let the rest continue, in case the sink call is
	 * just part of the program // execution }
	 * 
	 * if (this.approximationLevel == 2) { // Over-approximation, taint is
	 * introduced regardless of method call broker.publishThread("taint-introduced",
	 * new Tuple(((Variable) expr).getName(), classAndMethodName)); TaintValue tv =
	 * (TaintValue) createSymbolicValue(expr, Operation.TRUE); return tv; }
	 * 
	 * // First check if the classAndMethodName is a sanitiser or source. This takes
	 * // precedence in the options! if (taintSources != null &&
	 * taintSources.contains(classAndMethodName)) { explicitlyTainted = true;
	 * isTainted = true; } else if (taintSanitisers != null &&
	 * taintSanitisers.contains(classAndMethodName) || this.approximationLevel == 0)
	 * { // Under-approx, taint is killed if approxLevel is 0 explicitlyTainted =
	 * true; isTainted = false; broker.publishThread("taint-sanitised", new
	 * Tuple(((Variable) expr).getName(), classAndMethodName)); } // Secondly, check
	 * if AT LEAST ONE of the tainted arguments is given as a // parameter. We //
	 * will assume this affects the result. for (SymbolicValue sv : arguments) { if
	 * (((TaintValue) sv).getConditionalTaint() != Operation.FALSE) { leakCondition
	 * = Operation.or(leakCondition, ((TaintValue) sv).getConditionalTaint()); } }
	 * // Thirdly, add or remove taint to the newVariable if (explicitlyTainted &&
	 * isTainted) { // This adds new taint // Check if conditional Taint
	 * Introduction using input! broker.publishThread("taint-introduced", new
	 * Tuple(((Variable) expr).getName(), classAndMethodName)); // TaintValue tv =
	 * (TaintValue) createSymbolicValue(expr, getNewTaintSet()); TaintValue tv =
	 * (TaintValue) createSymbolicValue(expr, Operation.TRUE); return tv; } else if
	 * (!explicitlyTainted && leakCondition != Operation.FALSE) { if (leakCondition
	 * == Operation.TRUE) { broker.publishThread("taint-propagation", new
	 * Tuple(((Variable) expr).getName(), classAndMethodName)); } else {
	 * broker.publishThread("potential-taint-propagation", new Tuple(((Variable)
	 * expr).getName(), classAndMethodName)); } return createSymbolicValue(expr,
	 * leakCondition); } else { // No taint return createSymbolicValue(expr,
	 * Operation.FALSE); } }
	 */

	// ======================================================================
	//
	// VALUE OBJECT
	//
	// ======================================================================

	/**
	 * Specialized class that includes information about a symbolic value's tainted
	 * status; specifically, the symbolic expression that tells if a value is
	 * tainted.
	 */
	public class TaintValue implements SymbolicValue {

		protected Expression currentPath = null;

		/**
		 * The (potentially) tainted symbolic value.
		 */
		protected final Expression expression;

		/**
		 * The initial condition under which the value is tainted.
		 */
		private Expression initialTaint = null;

		/**
		 * The current condition under which the value is tainted.
		 */
		private Expression taintCondition = null;

		/**
		 * Create an untainted value.
		 * 
		 * @param expression
		 *                   the symbolic value
		 */
		public TaintValue(Expression expression) {
			this.expression = expression;
		}

		/**
		 * Create a conditionally tainted value.
		 * 
		 * @param expression
		 *                       the symbolic value
		 * @param taintCondition
		 *                       the condition under which taint applies
		 */
		public TaintValue(Expression expression, Expression taintCondition) {
			this.expression = expression;
			this.taintCondition = taintCondition;
		}

		/**
		 * Return the expression associated with this symbolic value.
		 *
		 * @return associated expression
		 * @see za.ac.sun.cs.coastal.diver.SymbolicValueFactory.SymbolicValue#toExpression()
		 */
		@Override
		public Expression toExpression() {
			return expression;
		}

		@Override
		public boolean isConstant() {
			return expression instanceof Constant;
		}

		/**
		 * Return the constant integer value associated with this symbolic value, if
		 * any.
		 *
		 * @return associated integer constant
		 * @see za.ac.sun.cs.coastal.diver.SymbolicValueFactory.SymbolicValue#toValue()
		 */
		@Override
		public long toValue() {
			if (expression instanceof IntegerConstant) {
				return ((IntegerConstant) expression).getValue();
			} else {
				return 0;
			}
		}

		/*
		 * public void setTaintCondition(Expression taintCondition) {
		 * this.taintCondition = taintCondition; }
		 */

		/*
		 * public void setInitialTaint(Expression initialTaint) { this.initialTaint =
		 * initialTaint; }
		 */

		/**
		 * Return the initial condition under which this value was tainted.
		 *
		 * @return initial taint condition
		 */
		public Expression getInitialTaint() {
			if (this.initialTaint == null) {
				return Operation.FALSE;
			}
			return this.initialTaint;
		}

		public Expression getConditionalTaint() {
			if (this.taintCondition == null) {
				return Operation.FALSE;
			}
			return this.taintCondition;
		}

		public Expression getCurrentPath() {
			if (this.currentPath == null) {
				return Operation.TRUE;
			}
			return this.currentPath;
		}

		/*
		 * public void setCurrentPath(Expression currentPath) { this.currentPath =
		 * currentPath; }
		 */

		// ----------------------------------------------------------------------
		//
		// COMPARISONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue eq(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.eq(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue ne(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.ne(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue lt(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.lt(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue le(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.le(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue gt(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.gt(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue ge(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.ge(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		// ----------------------------------------------------------------------
		//
		// ARITHMETIC OPERATIONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue add(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.add(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue sub(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.sub(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue mul(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.mul(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue div(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.div(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue rem(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.rem(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue fneg() {
			return createSymbolicValue(Operation.fneg(this.toExpression()), this.getConditionalTaint());
		}

		// ----------------------------------------------------------------------
		//
		// BIT OPERATIONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue lshr(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.lshr(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue ashr(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.ashr(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue shl(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.shl(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue bitor(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.bitor(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue bitand(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.bitand(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue bitxor(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.bitxor(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		// ----------------------------------------------------------------------
		//
		// MORE COMPLEX COMPARISONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue lcmp(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.lcmp(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue fcmpl(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.fcmpl(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue fcmpg(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.fcmpg(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue dcmpl(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.dcmpl(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		@Override
		public SymbolicValue dcmpg(SymbolicValue value) {
			TaintValue t = (TaintValue) value;
			return createSymbolicValue(Operation.dcmpg(this.toExpression(), t.toExpression()),
					Operation.or(this.getConditionalTaint(), t.getConditionalTaint()));
		}

		// ----------------------------------------------------------------------
		//
		// CONVERSIONS
		//
		// ----------------------------------------------------------------------

		@Override
		public SymbolicValue b2i() {
			return createSymbolicValue(Operation.b2i(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue d2f() {
			return createSymbolicValue(Operation.d2f(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue d2i() {
			return createSymbolicValue(Operation.d2i(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue d2l() {
			return createSymbolicValue(Operation.d2l(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue f2d() {
			return createSymbolicValue(Operation.f2d(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue f2i() {
			return createSymbolicValue(Operation.f2i(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue f2l() {
			return createSymbolicValue(Operation.f2l(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue i2b() {
			return createSymbolicValue(Operation.i2b(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue i2c() {
			return createSymbolicValue(Operation.i2c(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue i2d() {
			return createSymbolicValue(Operation.i2d(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue i2f() {
			return createSymbolicValue(Operation.i2f(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue i2l() {
			return createSymbolicValue(Operation.i2l(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue i2s() {
			return createSymbolicValue(Operation.i2s(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue l2d() {
			return createSymbolicValue(Operation.i2d(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue l2f() {
			return createSymbolicValue(Operation.i2f(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue l2i() {
			return createSymbolicValue(Operation.l2i(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public SymbolicValue s2i() {
			return createSymbolicValue(Operation.s2i(this.toExpression()), this.getConditionalTaint());
		}

		@Override
		public String toString() {
			return "( " + this.expression.toString() + " :: " + this.taintCondition + ")";
		}

		public void setInitialTaint(Expression conditionalTaint) {
			// TODO Auto-generated method stub
		}

		public void setTaintCondition(Expression taintCondition2) {
			// TODO Auto-generated method stub
		}

		public void setCurrentPath(Expression currentPath2) {
			// TODO Auto-generated method stub
		}

	}

}
