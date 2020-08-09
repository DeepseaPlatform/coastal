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
	
	protected final String methodName;
	
	protected final int invokingInstruction;
	
	protected final Stack<SymbolicValue> stack = new Stack<>();

	protected final Map<Integer, SymbolicValue> locals = new HashMap<>();

	public SymbolicFrame(int methodNumber, String methodName, int invokingInstruction) {
		this.methodNumber = methodNumber;
		this.methodName = methodName;
		this.invokingInstruction = invokingInstruction;
	}

	public int getFrameId() {
		return frameId;
	}

	public int getMethodNumber() {
		return methodNumber;
	}

	public String getMethodName() {
		return methodName;
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
		return stack.get(stack.size() - 1 - index);
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
