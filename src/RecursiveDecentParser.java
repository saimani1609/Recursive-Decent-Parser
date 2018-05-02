/****<expression> ::= <term> + <expression> | <term> - <expression> | <term>

<term> ::= <factor> * <term> | <factor> / <term> | <factor>
<factor> ::= <digit> | ( <expression> )
<digit> ::= 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9*****/




import java.io.*;

import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

//expression trees
public class RecursiveDecentParser{

// represents a node in an expression tree
abstract public static class ExprNode{
	abstract double value();
	abstract void commands();
	}
public static abstract class TermNode extends ExprNode{
	double n ; 
	TermNode(double val){
	n = val;
	}
	void commands(){
		System.out.println("Push"+n);
	}
}


// expression node representing a binary operator

private static class OperatorNode extends ExprNode {
        char opr;        // The operator.
        ExprNode left;   // The expression for its left operand.
        ExprNode right;  // The expression for its right operand.
        OperatorNode(char opr, ExprNode left, ExprNode right) {

            assert opr == '+' || opr == '-' || opr == '*' || opr == '/';
            assert left != null && right != null;
            this.opr = opr;
            this.left = left;
            this.right = right;
        }
        double value() {
                
            double a = left.value();
            double b = right.value();
            switch (opr) {
            case '+':  return a + b;
            case '-':  return a - b;
            case '*':  return a * b;
            case '/':  return a / b;
            default:   return Double.NaN;  // Bad operator!
            }
        }
	void commands(){
	left.commands();
            right.commands();
            System.out.println("  Operator " + opr);
        }
    }
	
// exceptions
  @SuppressWarnings("serial")
private static class ParseError extends Exception {
        ParseError(String message) {
            super(message);
        }
    } // end nested class ParseError




public static void main(String[] args) {

       
            System.out.println("\n\nEnter an expression, or press enter to end.");
            try {
            String expression = inputListener();
            RecursiveDecentParser rdp = new RecursiveDecentParser();
            System.out.println( "Result: " +(rdp.expressionTree(expression)));
        } catch (Exception e) {
            System.out.println( "Invalid Expression" );
        }
}

    public static String inputListener() {
        Scanner sc = new Scanner(System.in);
        String expression = sc.nextLine();
        System.out.println("Expression Entered: " + expression);
        return expression;
    }


		
       

  //  } // end main()


// read the input expression

private static ExprNode expressionTree() throws ParseError {
    ExprNode exp= new ExprNode();       // The expression tree for the expression.
	//	
    
        exp = termTree();  // Start with the first term.
       
 //       
//<expression> ::= <term> + <expression> | <term> - <expression> | <term>
        while ( exp.peek() == '+' || exp.peek() == '-' ) {
                // Read the next term and combine it with the
                // previous terms into a bigger expression tree.
            char opr = exp.getChar();
            ExprNode nextTerm = termTree();
            exp = new OperatorNode(opr, exp, nextTerm);
         
        }
        return exp;
    } // end expressionTree()

 private static ExprNode termTree() throws ParseError {
        //
        ExprNode term;  // The expression tree representing the term.
        term = factorTree();
        //
//<term> ::= <factor> * <term> | <factor> / <term> | <factor>

        while ( exp.peek() == '*' || exp.peek() == '/' ) {
                // Read the next factor, and combine it with the
                // previous factors into a bigger expression tree.
            char opr = exp.getAnyChar();
            ExprNode nextFactor = factorTree();
            term = new OperatorNode(opr,term,nextFactor);
            
        }
        return term;
    } // end termValue()

// <factor>  ::=  <digit>  |  (  <expression>  )
// <digit>  ::=   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9


  private static ExprNode factorTree() throws ParseError {
        
        char ch = exp.peek();
        if ( Character.isDigit(ch) ) {
                // The factor is a number.  Return a ConstNode.
            double number = exp.getDouble();
            //double number;
			return new TermNode(number);
        }

 else if ( ch == '(' ) {
                // The factor is an expression in parentheses.
                // Return a tree representing that expression.
            exp.getAnyChar();  // Read the "("
            ExprNode exp = expressionTree();
            
            if ( exp.peek() != ')' )
                throw new ParseError("Missing right parenthesis.");
            exp.getAnyChar();  // Read the ")"
            return exp;
        }
        else if ( ch == '\n' )
            throw new ParseError("End-of-line encountered in the middle of an expression.");
        else if ( ch == ')' )
            throw new ParseError("Extra right parenthesis.");
        else if ( ch == '+' || ch == '-' || ch == '*' || ch == '/' )
            throw new ParseError("Misplaced operator.");
        else
            throw new ParseError("Unexpected character \"" + ch + "\" encountered.");
    }  // end factorTree()


} // end RecursiveDecentParser()