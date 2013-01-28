package blak3980;

//---------------------------------------------------------------
/**
 * This exception prints default error messages based upon the value of the
 * error passed from the <code>Evaluator</code> constructor and methods. Note
 * that the output is sent to <code>System.err</code> rather than
 * <code>System.out</code>, though that can be altered as desire.
 * 
  * @author Tyler Blakeley
 * 090603980	Blak3980@mylaurier.ca
 */
@SuppressWarnings("serial")
public class PolyException extends Exception {

    // ---------------------------------------------------------------
    public static enum Flags {
	NO_LEFT_PARENTHESIS("Missing left parenthesis."), UNBALANCED_OPERATOR(
		"Missing operand."), UNBALANCED_OPERAND(
		"Missing operator for operand."), BAD_VARIABLE(
		"Variable in values list does not match variable in expression."), INVALID_TOKEN(
		"Token is not a valid operator, variable, or operand.");

	private final String message;

	Flags(String message) {
	    this.message = message;
	}

	public String getMessage() {
	    return this.message;
	}
    }

    Flags flag = null;

    String variable = null;

    // ---------------------------------------------------------------
    /**
     * @param variable
     *            The variable value that caused the exception.
     * @param flag
     *            <code>VariableFlags</code> constant signalling specific
     *            exception.
     */
    public PolyException(String variable, Flags flag) {
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
	return "  Error in \"" + this.variable + "\": "
		+ this.flag.getMessage();
    }

    // ---------------------------------------------------------------
}