package antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {

	private static void parse(String inputString) {
		ANTLRInputStream input = new ANTLRInputStream(inputString);
        ExprLexer lexer = new ExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
        ParseTree t = parser.expr();

        boolean accepted = t.getSourceInterval().a == 0 && t.getSourceInterval().b == inputString.length() - 1;

        System.out.println("Accepted: "+accepted);
        System.out.println(t.toStringTree(parser));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputString = "2+2";
		parse(inputString);
        
	}

}
