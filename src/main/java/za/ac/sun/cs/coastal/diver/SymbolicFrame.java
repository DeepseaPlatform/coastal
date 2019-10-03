/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.diver;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import za.ac.sun.cs.coastal.diver.SymbolicValueFactory.SymbolicValue;

public class SymbolicFrame {

	private static AtomicInteger frameCounter = new AtomicInteger();

	protected final int frameId = frameCounter.incrementAndGet();

	protected final int methodNumber;
	
	protected final int invokingInstruction;
	
	protected final Stack<SymbolicValue> stack = new Stack<>();

	protected final Map<Integer, SymbolicValue> locals = new HashMap<>();

	public SymbolicFrame(int methodNumber, int invokingInstruction) {
		this.methodNumber = methodNumber;
		this.invokingInstruction = invokingInstruction;
	}

	public int getFrameId() {
		return frameId;
	}

	public int getMethodNumber() {
		return methodNumber;
	}

	public int getInvokingInstruction() {
		return invokingInstruction;
	}
	
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	public void clear() {
		stack.clear();
	}
	
	public SymbolicValue pop() {
		assert !isEmpty();
		return stack.pop();
	}

	public SymbolicValue peek() {
		assert !isEmpty();
		return stack.peek();
	}

	public SymbolicValue peek(int index) {
		return stack.get(index);
	}

	public void push(SymbolicValue value) {
		stack.push(value);
	}

	public int size() {
		return stack.size();
	}

	public SymbolicValue getLocal(int index) {
		return locals.get(index);
	}

	public void setLocal(int index, SymbolicValue value) {
		locals.put(index, value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(locals.toString()).append('\n').append(stack.toString());
		return sb.toString();
	}

}
