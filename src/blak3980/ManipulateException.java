package blak3980;

//---------------------------------------------------------------
/**
 * This exception prints default error messages based upon the value of the
 * error passed from the <code>Manipulate</code> constructor and methods.
 * 
 * @author Tyler Blakeley
 * 090603980	Blak3980@mylaurier.ca
 */
@SuppressWarnings("serial")
public class ManipulateException extends Exception {

    // ---------------------------------------------------------------
    /**
     * The enumerated error flags and matching strings.
     */
    public static enum Flags {
	INVALID_EVALUATION("Incorrect evaluation points description."), INVALID_COMMAND(
		"Not a valid command."), INVALID_COUNT(
		"Count of points must be positive integer."), INVALID_ENDPOINTS(
		"Start value must be less than finish value."), INVALID_VALUE(
		"Cannot be converted to a number.");

	private final String message;

	// Constructor assigns message strings to flags.
	Flags(final String message) {
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
    public ManipulateException(final String variable, final Flags flag) {
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