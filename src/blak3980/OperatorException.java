package blak3980;

//---------------------------------------------------------------
/**
 * This exception prints default error messages based upon the value of the
 * error passed from the <code>Operator</code> constructor and methods.
 * 
 * @author Tyler Blakeley
 * 090603980	Blak3980@mylaurier.ca
 */
@SuppressWarnings("serial")
public class OperatorException extends Exception {

    // ---------------------------------------------------------------
    /**
     * The enumerated error flags and matching strings.
     */
    public static enum Flags {
	DIVISION_BY_ZERO("Cannot perform division by zero."), NOT_AN_OPERATOR(
		"Not an operator."), CANNOT_PERFORM(
		"Cannot execute this operator."), NO_RIGHT_PARENTHESIS(
		"Missing right parenthesis.");

	private final String message;

	// Constructor assigns message strings to flags.
	Flags(String message) {
	    this.message = message;
	}

	// Returns the message string that matches the current error flag.
	public String getMessage() {
	    return this.message;
	}
    }

    /**
     * The current error flag - must be one of the class' defined enumerated
     * flags.
     */
    Flags flag = null;

    /**
     * The string value that caused the exception to be thrown.
     */
    String variable = null;

    // ---------------------------------------------------------------
    /**
     * @param variable
     *            The string value that caused the exception.
     * @param flag
     *            <code>Flags</code> enumerated constant signalling specific
     *            exception.
     */
    public OperatorException(final String variable, final Flags flag) {
	this.flag = flag;
	this.variable = variable;
    }

    // ---------------------------------------------------------------
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String getMessage() {
	return "  Error: " + this.variable + ": " + this.flag.getMessage();
    }

    // ---------------------------------------------------------------
}
