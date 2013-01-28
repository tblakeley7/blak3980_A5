package blak3980;

import java.util.Hashtable;
import java.util.Map;

//---------------------------------------------------------------
/**
 * An <code>Operator</code> object consists of a character and an arbitrary
 * precedence value for a binary integer operator. These operators are
 * <code>+ - / * % ( )</code>. Given a pair of values this operator can be
 * applied to these values.
 * 
 * @author Tyler Blakeley
 * 090603980	Blak3980@mylaurier.ca
 */
public class Operator {
    /**
     * The list of valid operator characters.
     */
    public final static String OPERATORS = "+-/*%()";

    /**
     * A <code>Map</code> containing operator symbols and their matching
     * precedences. This allows a quick look-up of an operator precedence.
     */
    private final static Map<String, Integer> precedents = Operator
	    .definePrecedents();

    // ---------------------------------------------------------------
    /**
     * Assigns a precedence value to an operator symbol. This allows the
     * precedence of a symbol to by found by looking at the precedence map using
     * the symbol as a key.
     * 
     * @return the mapping between a symbol and its precedence.
     */
    private final static Map<String, Integer> definePrecedents() {
	final Map<String, Integer> map = new Hashtable<String, Integer>();
	map.put("(", new Integer(0));
	map.put(")", new Integer(0));
	map.put("+", new Integer(1));
	map.put("-", new Integer(1));
	map.put("*", new Integer(2));
	map.put("/", new Integer(2));
	map.put("%", new Integer(2));
	return map;
    }

    // ---------------------------------------------------------------
    /**
     * Determines whether or not the input string is an operator.
     * 
     * @param s
     *            The string to compare against the operator pattern.
     * @return <code>true</code> if the input string is an operator,
     *         <code>false</code> otherwise.
     */
    public static boolean isOperator(final String s) {
	return OPERATORS.contains(s);
    }

    /**
     * The arbitrary precedence value for an operator.
     */
    private int precedence = 0;

    /**
     * The character representation of the operator.
     */
    private String symbol = null;

    // ---------------------------------------------------------------
    /**
     * Constructs a new operator based upon the character passed in.
     * 
     * @param operator
     *            a String whose first character(s) is an operator character
     * @throws OperatorException
     *             when the string parameter does not begin with an operator
     */
    public Operator(final String operator) throws OperatorException {
	try {
	    this.symbol = operator;
	    this.setPrecedence();
	} catch (final Exception e) {
	    throw new OperatorException(operator,
		    OperatorException.Flags.NOT_AN_OPERATOR);
	}
    }

    // ---------------------------------------------------------------
    /**
     * @param operator
     *            The operator to compare to this operator object.
     * @return <code>true</code> if <code>operator</code> is not null and if the
     *         strings that make up the operators' symbols are the same,
     *         <code>false</code> otherwise.
     */
    public boolean equals(final Operator operator) {
	boolean result = false;

	if (operator != null) {
	    result = this.symbol.equals(operator.toString());
	}
	return result;
    }

    // ---------------------------------------------------------------
    /**
     * <p>
     * Applies a binary operator to two <code>Poly</code> parameters.
     * </p>
     * <p>
     * (A similar function could be written for any appropriate class.)
     * </p>
     * 
     * @param x
     *            the first <code>Poly</code> for the binary operator
     * @param y
     *            the second <code>Poly</code> for the binary operator
     * @return the result of applying the operator to x and y
     * @throws OperatorException
     */
    public Poly perform(final Poly x, final Poly y) throws OperatorException {
	Poly result = null;

	if (this.symbol.equals("+")) {
	    result = x.add(y);
	} else if (this.symbol.equals("-")) {
	    result = x.sub(y);
	} else if (this.symbol.equals("*")) {
	    result = x.mult(y);
	} else if (this.symbol.equals("(")) {
	    throw new OperatorException(this.toString(),
		    OperatorException.Flags.NO_RIGHT_PARENTHESIS);
	} else {
	    throw new OperatorException(this.toString(),
		    OperatorException.Flags.CANNOT_PERFORM);
	}
	return result;
    }

    // ---------------------------------------------------------------
    /**
     * Returns whether the current operator precedes another operator.
     * 
     * @param operator
     *            the operator to compare precedence against.
     * @return <code>true</code> if the current operator precedes the second
     *         operator, <code>false</code> otherwise.
     */
    public boolean precedes(final Operator operator) {
	return this.precedence < operator.precedence;
    }

    // ---------------------------------------------------------------
    /**
     * Sets the precedence for an operator based upon its symbol character. The
     * precedence is acquired from the precedence map.
     */
    private void setPrecedence() {
	this.precedence = Operator.precedents.get(this.symbol);
    }

    // ---------------------------------------------------------------
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return this.symbol;
    }

    // ---------------------------------------------------------------
}
