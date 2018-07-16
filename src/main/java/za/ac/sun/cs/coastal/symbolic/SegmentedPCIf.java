package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.green.expr.Expression;

public class SegmentedPCIf extends SegmentedPC {

	private final Expression expression;

	private final boolean value;

	private final Expression activeConjunct;

	private final String signature;

	private SegmentedPC negated = null;

	public SegmentedPCIf(SegmentedPC parent, Expression expression, Expression passiveConjunct, boolean value) {
		super(parent, passiveConjunct);
		this.expression = expression;
		this.value = value;
		this.activeConjunct = this.value ? this.expression : negate(this.expression);
		if (this.value) {
			SegmentedPC p = getParent();
			this.signature = (p == null) ? "1" : (p.getSignature() + "1");
		} else {
			SegmentedPC p = getParent();
			this.signature = (p == null) ? "0" : (p.getSignature() + "0");
		}
	}

	@Override
	public Expression getExpression() {
		return expression;
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public Expression getActiveConjunct() {
		return activeConjunct;
	}

	@Override
	public String getSignature() {
		return signature;
	}

	@Override
	public int getNrOfOutcomes() {
		return 2;
	}

	@Override
	public int getOutcomeIndex() {
		return value ? 1 : 0;
	}

	@Override
	public String getOutcome(int index) {
		if (index == 0) {
			return "F";
		} else {
			return "T";
		}
	}
	
	@Override
	public SegmentedPC getChild(int index, SegmentedPC parent) {
		if (getValue() == (index == 1)) {
			return this;
		} else {
			return new SegmentedPCIf(parent, getExpression(), getPassiveConjunct(), index == 1);
		}
	}

	public SegmentedPC negate() {
		if (negated== null) {
			negated = new SegmentedPCIf(getParent(), getExpression(), getPassiveConjunct(), !getValue());
		}
		return negated;
	}

	@Override
	public String toString0() {
		Expression e = getPassiveConjunct();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-7s ", getValue() ? "TRUE" : "FALSE"));
		sb.append(String.format("%-30s ", getExpression().toString()));
		sb.append(String.format("%s", (e == null) ? "NULL" : e.toString()));
		SegmentedPC p = getParent();
		if (p != null) {
			sb.append('\n').append(p.toString());
		}
		return sb.toString();
	}

	//	public static String constraintBeautify(String subject) {
	//		subject = replace("[0-9]+[!=]=[a-zA-Z]+" + SymbolicState.CHAR_SEPARATOR + "[0-9]+", m -> rewriteCharConstraint(m.group()), subject);
	//		subject = replace("[a-zA-Z]+" + SymbolicState.CHAR_SEPARATOR + "[0-9]+[!=]=[0-9]+", m -> rewriteCharConstraint(m.group()), subject);
	//		return subject;
	//	}

	//	public static String modelBeautify(String subject) {
	//		return replace("[a-zA-Z]+" + SymbolicState.CHAR_SEPARATOR + "[0-9]+=[0-9]+", m -> rewriteCharModel(m.group()), subject);
	//	}

	//	public static String replace(String regex, Function<MatchResult, String> callback, CharSequence subject) {
	//		Matcher m = Pattern.compile(regex).matcher(subject);
	//		StringBuffer sb = new StringBuffer();
	//		while (m.find()) {
	//			m.appendReplacement(sb, callback.apply(m.toMatchResult()));
	//		}
	//		m.appendTail(sb);
	//		return sb.toString();
	//	}

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

	//	public static String rewriteCharModel(String replace) {
	//		int index = replace.indexOf('=');
	//		if (index == -1) {
	//			return replace.replaceAll(SymbolicState.CHAR_SEPARATOR, "#");
	//		}
	//		StringBuilder b = new StringBuilder().append(replace.substring(0, index + 1));
	//		appendChar(b, Integer.parseInt(replace.substring(index + 1)));
	//		return b.toString().replaceAll(SymbolicState.CHAR_SEPARATOR, "#");
	//	}

	//	public static void appendChar(StringBuilder stringBuilder, int ascii) {
	//		stringBuilder.append('\'');
	//		if (ascii == '\'') {
	//			stringBuilder.append("\\\\'");
	//		} else if (ascii == '\\') {
	//			stringBuilder.append("\\\\\\\\");
	//		} else if ((ascii >= ' ') && (ascii < 127)) {
	//			stringBuilder.append((char) ascii);
	//		} else if (ascii == 0) {
	//			stringBuilder.append("\\\\0");
	//		} else if (ascii == 8) {
	//			stringBuilder.append("\\\\b");
	//		} else if (ascii == 9) {
	//			stringBuilder.append("\\\\t");
	//		} else if (ascii == 10) {
	//			stringBuilder.append("\\\\n");
	//		} else if (ascii == 12) {
	//			stringBuilder.append("\\\\f");
	//		} else if (ascii == 13) {
	//			stringBuilder.append("\\\\r");
	//		} else {
	//			stringBuilder.append("\\\\u").append(String.format("%04x", ascii));
	//		}
	//		stringBuilder.append('\'');
	//	}

}
