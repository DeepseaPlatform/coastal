package za.ac.sun.cs.coastal.diver;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import za.ac.sun.cs.coastal.solver.Expression;

public class SymbolicFrame {

	protected final int methodNumber;
	
	protected final int invokingInstruction;
	
	protected final Stack<Expression> stack = new Stack<>();

	protected final Map<Integer, Expression> locals = new HashMap<>();

	public SymbolicFrame(int methodNumber, int invokingInstruction) {
		this.methodNumber = methodNumber;
		this.invokingInstruction = invokingInstruction;
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
	
	public Expression pop() {
		return stack.pop();
	}

	public Expression peek() {
		return stack.peek();
	}

	public Expression peek(int index) {
		return stack.get(index);
	}

	public void push(Expression value) {
		stack.push(value);
	}

	public int size() {
		return stack.size();
	}

	public Expression getLocal(int index) {
		return locals.get(index);
	}

	public void setLocal(int index, Expression value) {
		locals.put(index, value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(locals.toString()).append('\n').append(stack.toString());
		return sb.toString();
	}

}
