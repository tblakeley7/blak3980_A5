package blak3980;

import java.util.InputMismatchException;
import java.util.Scanner;

// ---------------------------------------------------------------
/**
 * Defines and stores a rational number in the form of a fraction
 * <code>numerator/denominator</code>. Whatever the original numerator and
 * denominator, the values are normalized by using their GCD (Greatest Common
 * Divisor).
 * 
 * @author Tyler Blakeley
 * 090603980	Blak3980@mylaurier.ca
 */
public class Rational {

    // The rational number denominator.
    private double den = 1;
    // The rational number numerator.
    private double num = 0;

    // ---------------------------------------------------------------
    /**
     * Constructor variant where the denominator is 1. (Thus, no need to
     * normalize the number.)
     * 
     * @param coeffs
     *            the numerator
     */
    public Rational(final double coeffs) {
	this.num = coeffs;
    }

    // ---------------------------------------------------------------
    /**
     * Constructor variant where the denominator is not 1.
     * 
     * @param n
     *            the numerator.
     * @param d
     *            the denominator.
     */
    public Rational(final double n, final double d) {
	this.num = n;
	this.den = d;
	this.normalize();
    }

    // ---------------------------------------------------------------
    /**
     * A copy constructor. Creates a new <code>Rational</code> object containing
     * the same attribute values as the parameter.
     * 
     * @param that
     *            the <i>Rational</i> object to copy.
     */
    public Rational(final Rational that) {
	this.num = that.num;
	this.den = that.den;
    }

    // ---------------------------------------------------------------
    /**
     * Constructor variable that parses a string to produce the rational.
     * Assumes a denominator of 1 if the denominator is not specifically given.
     * 
     * @param str
     *            the string representation of the rational number.
     * @throws RationalException
     */
    public Rational(final String str) throws RationalException {
	final Scanner tokenizer = new Scanner(str);

	try {
	    tokenizer.useDelimiter("/");
	    this.num = tokenizer.nextInt();

	    if (tokenizer.hasNextInt()) {
		this.den = tokenizer.nextInt();
		this.normalize();
	    }
	} catch (InputMismatchException e) {
	    throw new RationalException(str,
		    RationalException.Flags.INVALID_FORMAT);
	} finally {
	    tokenizer.close();
	}
    }

    // ---------------------------------------------------------------
    /**
     * Adds a rational number to the current rational object. The new rational
     * number is automatically normalized by the call to the
     * <code>Rational</code> constructor.
     * 
     * @param r
     *            a rational object.
     * @return a new rational object.
     */
    public Rational add(final Rational r) {
	double num = this.num * r.den + this.den * r.num;
	double den = this.den * r.den;
	return new Rational(num, den);
    }

    // ---------------------------------------------------------------
    /**
     * Divides the current rational object by another rational object. The new
     * rational number is automatically normalized by the call to the
     * <code>Rational</code> constructor.
     * 
     * @param r
     *            a rational object.
     * @return a new rational object.
     */
    public Rational divide(final Rational r) {
	double num = this.num * r.den;
	double den = this.den * r.num;
	return new Rational(num, den);
    }

    // ---------------------------------------------------------------
    /**
     * Compares the contents of two rational objects. The rational objects do
     * not need to be normalized. (i.e. their numerators and denominators do not
     * have to be identical, but they must share the same GCD.)
     * 
     * @param r
     *            a rational object.
     * @return true if the rational objects represent the same rational value,
     *         false otherwise.
     */
    public boolean equals(final Rational r) {
	return this.num * r.den - this.den * r.num == 0;
    }

    // ---------------------------------------------------------------
    /**
     * @return the denominator of the rational.
     */
    public double getDen() {
	return this.den;
    }

    // ---------------------------------------------------------------
    /**
     * @return the numerator of the rational.
     */
    public double getNum() {
	return this.num;
    }

    // ---------------------------------------------------------------
    /**
     * Multiplies the current rational object by another. The new rational
     * number is automatically normalized by the call to the
     * <code>Rational</code> constructor.
     * 
     * @param r
     *            a rational object.
     * @return a new rational object.
     */
    public Rational multiply(final Rational r) {
	double num = this.num * r.num;
	double den = this.den * r.den;
	return new Rational(num, den);
    }

    // ---------------------------------------------------------------
    /**
     * Normalizes the numerator and denominator of the current object by finding
     * their greatest common divisor (GCD).
     * 
     */
    private void normalize() {
	final double t = Tools.gcd(this.num, this.den);
	this.num = this.num / t;
	this.den = this.den / t;

	if (this.den < 0) {
	    this.den = -this.den;
	    this.num = -this.num;
	}
    }

    // ---------------------------------------------------------------
    /**
     * Subtracts another rational object from the current one. The new rational
     * number is automatically normalized by the call to the
     * <code>Rational</code> constructor.
     * 
     * @param r
     *            a rational object.
     * @return a new rational object.
     */
    public Rational subtract(final Rational r) {
	double num = this.num * r.den - this.den * r.num;
	double den = this.den * r.den;
	return new Rational(num, den);
    }

    // ---------------------------------------------------------------
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	if (this.den != 1) {
	    return this.num + "/" + this.den;
	} else {
	    return this.num + "";
	}
    }

    // ---------------------------------------------------------------
}
