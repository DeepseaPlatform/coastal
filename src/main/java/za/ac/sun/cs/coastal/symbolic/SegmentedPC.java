package za.ac.sun.cs.coastal.symbolic;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

/**
 * Class to store a path condition as a linked list of triples.
 * 
 * Each triple contains (1) an active conjunct that represents a branch
 * condition, (2) a passive conjunct that represents additional constraints on
 * the variables, and (3) a signal that indicates whether the branch was taken
 * ("<code>0</code>") or not ("<code>1</code>").
 *
 * For efficiency, this data structure also stores (1) the accumulated path
 * condition which is the conjunction of all path condition conjuncts of this
 * instance and its parent instances, and (2) the signature which is the
 * concatenation of this instance's signal and those of all its parents.
 */
public class SegmentedPC {

	private final SegmentedPC parent;

	private final Expression activeConjunct;

	private final Expression passiveConjunct;

	private final boolean isFalse;

	private final Expression pathCondition;

	private final String signature;

	private final int depth;

	private String stringRep = null, stringRep0 = null;

	public SegmentedPC(SegmentedPC parent, Expression activeConjunct, Expression passiveConjunct, boolean isFalse) {
		assert activeConjunct != null;
		assert activeConjunct != Operation.TRUE;
		this.parent = parent;
		this.activeConjunct = activeConjunct;
		this.passiveConjunct = passiveConjunct;
		this.isFalse = isFalse;
		Expression a = activeConjunct;
		if (isFalse) {
			this.signature = ((parent == null) ? "" : parent.getSignature()) + "0";
			a = negate(a);
		} else {
			this.signature = ((parent == null) ? "" : parent.getSignature()) + "1";
		}
		if (parent == null) {
			if ((passiveConjunct != Operation.TRUE) && (passiveConjunct != null)) {
				this.pathCondition = new Operation(Operator.AND, a, passiveConjunct); 
			} else {
				this.pathCondition = a; 
			}
		} else {
			if ((passiveConjunct != Operation.TRUE) && (passiveConjunct != null)) {
				this.pathCondition = new Operation(Operator.AND, a, new Operation(Operator.AND, passiveConjunct, parent.getPathCondition())); 
			} else {
				this.pathCondition = new Operation(Operator.AND, a, parent.getPathCondition());
			}
		}
		this.depth = (parent == null) ? 1 : (1 + parent.getDepth());
	}

	public SegmentedPC getParent() {
		return parent;
	}

	public Expression getActiveConjunct() {
		return activeConjunct;
	}

	public Expression getPassiveConjunct() {
		return passiveConjunct;
	}

	public boolean isLastConjunctFalse() {
		return isFalse;
	}

	public Expression getPathCondition() {
		return pathCondition;
	}
	
	public String getSignature() {
		return signature;
	}

	public int getDepth() {
		return depth;
	}
	
	public SegmentedPC negate() {
		return new SegmentedPC(parent, activeConjunct, passiveConjunct, !isFalse);
	}

	public static Expression negate(Expression expression) {
		if (expression instanceof Operation) {
			Operation operation = (Operation) expression;
			switch (operation.getOperator()) {
			case NOT:
				return operation.getOperand(0);
			case EQ:
				return new Operation(Operator.NE, operation.getOperand(0), operation.getOperand(1));
			case NE:
				return new Operation(Operator.EQ, operation.getOperand(0), operation.getOperand(1));
			case LT:
				return new Operation(Operator.GE, operation.getOperand(0), operation.getOperand(1));
			case LE:
				return new Operation(Operator.GT, operation.getOperand(0), operation.getOperand(1));
			case GT:
				return new Operation(Operator.LE, operation.getOperand(0), operation.getOperand(1));
			case GE:
				return new Operation(Operator.LT, operation.getOperand(0), operation.getOperand(1));
			default:
				break;
			}
		}
		return new Operation(Operator.NOT, expression);
	}

	@Override
	public String toString() {
		if (stringRep == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(toString0());
			stringRep = sb.toString();
		}
		return stringRep;
	}

	public String toString0() {
		if (stringRep0 == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(isLastConjunctFalse() ? "FALSE" : "TRUE ");
			sb.append("  ").append(String.format("%-30s", getActiveConjunct().toString()));
			Expression e = getPassiveConjunct();
			sb.append("  ").append(String.format("%s", ((e == null) ? "NULL" : e.toString())));
			SegmentedPC p = getParent();
			if (p != null) {
				sb.append('\n').append(p.toString0());
			}
			stringRep0 = sb.toString();
		}
		return stringRep0;
	}

//	public static String constraintBeautify(String subject) {
//		subject = replace("[0-9]+[!=]=[a-zA-Z]+" + SymbolicState.CHAR_SEPARATOR + "[0-9]+", m -> rewriteCharConstraint(m.group()), subject);
//		subject = replace("[a-zA-Z]+" + SymbolicState.CHAR_SEPARATOR + "[0-9]+[!=]=[0-9]+", m -> rewriteCharConstraint(m.group()), subject);
//		return subject;
//	}
//
//	public static String modelBeautify(String subject) {
//		return replace("[a-zA-Z]+" + SymbolicState.CHAR_SEPARATOR + "[0-9]+=[0-9]+", m -> rewriteCharModel(m.group()), subject);
//	}
//	
//	public static String replace(String regex, Function<MatchResult, String> callback, CharSequence subject) {
//		Matcher m = Pattern.compile(regex).matcher(subject);
//		StringBuffer sb = new StringBuffer();
//		while (m.find()) {
//			m.appendReplacement(sb, callback.apply(m.toMatchResult()));
//		}
//		m.appendTail(sb);
//		return sb.toString();
//	}
//
//	public static String rewriteCharConstraint(String replace) {
//		int index = Math.max(replace.indexOf("=="), replace.indexOf("!="));
//		if (index == -1) {
//			return replace;
//		}
//		if (Character.isDigit(replace.charAt(0))) {
//			StringBuilder b = new StringBuilder();
//			appendChar(b, Integer.parseInt(replace.substring(0, index)));
//			return b.append(replace.substring(index)).toString().replaceAll(SymbolicState.CHAR_SEPARATOR, "#");
//		} else {
//			StringBuilder b = new StringBuilder();
//			b.append(replace.substring(0, index + 2));
//			appendChar(b, Integer.parseInt(replace.substring(index + 2)));
//			return b.toString().replaceAll(SymbolicState.CHAR_SEPARATOR, "#");
//		}
//	}
//	
//	public static String rewriteCharModel(String replace) {
//		int index = replace.indexOf('=');
//		if (index == -1) {
//			return replace.replaceAll(SymbolicState.CHAR_SEPARATOR, "#");
//		}
//		StringBuilder b = new StringBuilder().append(replace.substring(0, index + 1));
//		appendChar(b, Integer.parseInt(replace.substring(index + 1)));
//		return b.toString().replaceAll(SymbolicState.CHAR_SEPARATOR, "#");
//	}

	public static void appendChar(StringBuilder stringBuilder, int ascii) {
		stringBuilder.append('\'');
		if (ascii == '\'') {
			stringBuilder.append("\\\\'");
		} else if (ascii == '\\') {
			stringBuilder.append("\\\\\\\\");
		} else if ((ascii >= ' ') && (ascii < 127)) {
			stringBuilder.append((char) ascii);
		} else if (ascii == 0) {
			stringBuilder.append("\\\\0");
		} else if (ascii == 8) {
			stringBuilder.append("\\\\b");
		} else if (ascii == 9) {
			stringBuilder.append("\\\\t");
		} else if (ascii == 10) {
			stringBuilder.append("\\\\n");
		} else if (ascii == 12) {
			stringBuilder.append("\\\\f");
		} else if (ascii == 13) {
			stringBuilder.append("\\\\r");
		} else {
			stringBuilder.append("\\\\u").append(String.format("%04x", ascii));
		}
		stringBuilder.append('\'');
	}
	
}
