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
package za.ac.sun.cs.coastal.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.diver.SymbolicValueFactory.SymbolicValue;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.IntegerVariable;
import za.ac.sun.cs.coastal.solver.Operation;
import za.ac.sun.cs.coastal.solver.RealConstant;
import za.ac.sun.cs.coastal.solver.RealVariable;
import za.ac.sun.cs.coastal.taint.TaintValueFactory.TaintValue;

/**
 * A model of some operations of {@link java.util.Map}. This implementation was
 * designed to specifically track taint. It allows for symbolic keys, and only
 * keeps track of tainted values for non-symbolic keys. It is able to construct
 * a taint condition for non-tainted values that may be retrieved from the map.
 */
public class TaintMap {

	private final Map<Object, java.lang.Character> mapType = new HashMap<Object, java.lang.Character>();

	private final Map<Object, LinkedHashMap<Object, SymbolicValue>> map = new HashMap<Object, LinkedHashMap<Object, SymbolicValue>>();

	private COASTAL coastal;

	public TaintMap(COASTAL coastal, Configuration options) {
		this.coastal = coastal;
	}

	public boolean put__Ljava_1lang_1Object_2Ljava_1lang_1Object_2__Ljava_1lang_1Object_2(SymbolicState state) {
		SymbolicValue mapValue = state.pop();
		SymbolicValue mapKey = state.pop();
		Object mapName = state.pop();

		if (!this.map.containsKey(mapName)) {
			setMapType(mapName, mapValue.toExpression());
			this.map.put(mapName, new LinkedHashMap<Object, SymbolicValue>());
		}
		// Set initial taint when placed inside the map
		((TaintValue) mapValue).setInitialTaint(((TaintValue) mapValue).getConditionalTaint());
		this.map.get(mapName).put(mapKey, mapValue);
		// If an existing key is passed then the previous value gets returned. If a new
		// pair is passed, then NULL is returned.
		state.push(IntegerConstant.ZERO32);
		return true;
	}

	public boolean get__Ljava_1lang_1Object_2__Ljava_1lang_1Object_2(SymbolicState state) {
		SymbolicValue mapKey = state.pop();
		Object mapName = state.pop();
		TaintValue sv;
		if (this.map.get(mapName) != null && this.map.get(mapName).get(mapKey) != null) {
			sv = (TaintValue) this.map.get(mapName).get(mapKey);
		} else {
			sv = (TaintValue) state.getSymbolicValueFactory()
					.createSymbolicValue(getVariableExpression(mapName, state.getNewVariableName()));
		}
		Expression currentPath = null;

		if (state.getExecution().getPath() != null) {
			currentPath = state.getExecution().getPath().getPathCondition();
		}
		Expression taintCondition = buildConditionalTaint(mapName, mapKey, currentPath);
		sv.setTaintCondition(taintCondition);
		sv.setCurrentPath(currentPath);
		state.push(sv);
		return true;
	}

	/**
	 * Builds a condition for taint, based on concrete keys and symbolic keys. Uses
	 * a LinkedHashMap to keep track of the 'put' order.
	 * 
	 * @param mapName
	 * @param mapKey
	 * @return
	 */
	private Expression buildConditionalTaint(Object mapName, SymbolicValue mapKey, Expression currentPath) {
		// Default case - no taint condition if map does not exist
		if (this.map.get(mapName) == null) {
			return Operation.FALSE;
		}
		// Get keys, and reverse iterator
		Set<Object> keys = this.map.get(mapName).keySet();
		LinkedList<Object> lst = new LinkedList<Object>(keys);
		Iterator<Object> iterator = lst.descendingIterator();
		Expression not = Operation.TRUE;
		Expression conditional = Operation.FALSE;
		if (currentPath == null) {
			currentPath = Operation.TRUE;
		}
		Expression getKey = unbox(mapKey.toExpression());
		while (iterator.hasNext()) {
			Object current = iterator.next();

			SymbolicValue sv = this.map.get(mapName).get(current);
			// current is the Key, mapKey is the given item. SV is tainted or not.
			// If the key is a constant, unbox it for expression building
			Expression currentKey = unbox(((SymbolicValue) current).toExpression());

			System.out.println("KEY: " + getKey + "| CURRENT: " + currentKey + "| VAL: "
					+ ((TaintValue) sv).toExpression() + " -> " + ((TaintValue) sv).getConditionalTaint());
			System.out.println("NOT: " + not);
			conditional = Operation.or(conditional, Operation.and(Operation.and(Operation.eq(currentKey, getKey), not),
					((TaintValue) sv).getInitialTaint()));
			not = Operation.and(not, Operation.ne(getKey, currentKey));
			System.out.println("CONDITIONAL: " + conditional);
		}
		conditional = Operation.and(conditional, currentPath);
		return conditional;
	}

	/**
	 * Basic unboxing of constants for expression building
	 * 
	 * @param expr
	 * @return either the same expression if it is not constant, or the unboxed
	 *         value
	 */
	private Expression unbox(Expression expr) {
		Expression ex = expr;
		if (expr.toString().contains("_")) {
			char type = expr.toString().charAt(1);
			long value = Long.parseLong(expr.toString().split("_")[1]);
			switch (type) {
			case 'Z':
				return new IntegerConstant(value, 32);
			case 'B':
				return new IntegerConstant(value, 8);
			case 'C':
				return new IntegerConstant(value, 16);
			case 'S':
				return new IntegerConstant(value, 16);
			case 'I':
				return new IntegerConstant(value, 32);
			case 'J':
				return new IntegerConstant(value, 64);
			case 'F':
				return new RealConstant(value, 32);
			case 'D':
				return new RealConstant(value, 64);
			default:
				throw new RuntimeException("Not implemented for unboxing");
			}
		}
		return ex;
	}

	private void setMapType(Object mapName, Expression expr) {
		if (expr instanceof IntegerVariable) {
			IntegerVariable iv = (IntegerVariable) expr;
			switch (iv.getBitSize()) {
			case 8: // byte
				mapType.put(mapName, 'B');
				break;
			case 16: // char or short
				if ((char) this.coastal.getDefaultMinValue(char.class) == iv.getLowerBound()
						&& (char) this.coastal.getDefaultMaxValue(char.class) == iv.getUpperBound()) {
					mapType.put(mapName, 'C');
				} else {
					mapType.put(mapName, 'S');
				}
				break;
			case 32: // boolean or int or object
				if ((int) this.coastal.getDefaultMinValue(int.class) == iv.getLowerBound()
						&& (int) this.coastal.getDefaultMaxValue(int.class) == iv.getUpperBound()) {
					mapType.put(mapName, 'I');
				} else if (iv.getLowerBound() == 0 && iv.getUpperBound() == 1) {
					mapType.put(mapName, 'Z');
				} else {
					mapType.put(mapName, 'L');
				}
				break;
			case 64: // long
				mapType.put(mapName, 'J');
				break;
			default:
				throw new RuntimeException("Size error for IntegerVariable in put method of TaintMap");
			}
		} else if (expr instanceof RealVariable) {
			int size = expr.getBitSize();
			if (size == 32) {
				mapType.put(mapName, 'F');
			} else if (size == 64) {
				mapType.put(mapName, 'D');
			} else {
				throw new RuntimeException("Size error for RealVariable in put method of TaintMap");
			}
		} else {
			throw new RuntimeException("Constant value was added to the taint map, causing this");
		}
	}

	// returns expression given mapName, so we get type right
	private Expression getVariableExpression(Object mapName, java.lang.String varName) {
		char type = mapType.get(mapName);
		switch (type) {
		case 'Z':
			long min = 0L;
			long max = 1L;
			return new IntegerVariable(varName, 32, min, max);
		case 'B':
			min = (byte) coastal.getDefaultMinValue(byte.class);
			max = (byte) coastal.getDefaultMaxValue(byte.class);
			return new IntegerVariable(varName, 8, min, max);
		case 'C':
			min = (char) coastal.getDefaultMinValue(char.class);
			max = (char) coastal.getDefaultMaxValue(char.class);
			return new IntegerVariable(varName, 16, min, max);
		case 'S':
			min = (short) coastal.getDefaultMinValue(short.class);
			max = (short) coastal.getDefaultMaxValue(short.class);
			return new IntegerVariable(varName, 16, min, max);
		case 'I':
			min = (int) coastal.getDefaultMinValue(int.class);
			max = (int) coastal.getDefaultMaxValue(int.class);
			return new IntegerVariable(varName, 32, min, max);
		case 'J':
			min = (long) coastal.getDefaultMinValue(long.class);
			max = (long) coastal.getDefaultMaxValue(long.class);
			return new IntegerVariable(varName, 64, min, max);
		case 'F':
			float minf = (float) coastal.getDefaultMinValue(float.class);
			float maxf = (float) coastal.getDefaultMaxValue(float.class);
			return new RealVariable(varName, 32, minf, maxf);
		case 'D':
			double mind = (double) coastal.getDefaultMinValue(double.class);
			double maxd = (double) coastal.getDefaultMaxValue(double.class);
			return new RealVariable(varName, 64, mind, maxd);
		case 'L':
			min = 0;
			max = Integer.MAX_VALUE;
			return new IntegerVariable(varName, 32, min, max);
		default:
			throw new RuntimeException("Not implemented for getting variable expression");
		}
	}

}
