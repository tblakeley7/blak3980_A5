package blak3980;

//---------------------------------------------------------------
/**
 * This exception prints default error messages based upon the value of the
 * error passed from the <code>Rational</code> constructor and methods. Note
 * that the output is sent to <code>System.err</code> rather than
 * <code>System.out</code>, though that can be altered as desire.
 * 
 * @author Tyler Blakeley
 * 090603980	Blak3980@mylaurier.ca
 */
@SuppressWarnings("serial")
public class RationalException extends Exception {

    // ---------------------------------------------------------------
    public static enum Flags {
	INVALID_FORMAT("Cannot be converted to a rational number.");

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
    public RationalException(String variable, Flags flag) {
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