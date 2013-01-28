package blak3980;

//---------------------------------------------------------------
/**
 * A utilities class containing functions to:
 * 
 * <ul>
 * <li>Calculate the GCD of two integers</li>
 * </ul>
 * 
 * (Note that any of the given versions of the GCD are acceptable.)
 * 
 * @author Tyler Blakeley
 * 090603980	Blak3980@mylaurier.ca
 */
public class Tools {

    // ---------------------------------------------------------------
    /**
     * Uses iteration to calculate the GCD.
     * 
     * @param num
     * @param den
     * @return the greatest common divisor of two integers.
     */
    public static double gcd(double num, double den) {
	double t = 0;

	if (num < den) {
	    t = num;
	    num = den;
	    den = t;
	}
	while (den != 0) {
	    t = num % den;
	    num = den;
	    den = t;
	}
	return (num);
    }

    // ---------------------------------------------------------------
    /**
     * Uses recursion (Euclid's algorithm) to calculate the GCD.
     * 
     * @param a
     * @param b
     * @return the greatest common divisor of two integers. Based on the fact
     *         that the gcd from p and q is the same as the gcd from p and p % q
     *         in case p is larger then q
     */
    public static int gcd_r(int a, int b) {
	int denom = 0;

	if (b == 0) {
	    denom = a;
	} else {
	    denom = gcd_r(b, a % b);
	}
	return denom;
    }

    // ---------------------------------------------------------------
    /**
     * Uses an iterative form of Euclid's algorithm.
     * 
     * @param a
     * @param b
     * @return the greatest common divisor of two integers.
     */
    public static int gcd2(int a, int b) {
	int x = 0;
	int y = 0;

	while (a % b != 0) {
	    x = b;
	    y = a % b;
	    a = x;
	    b = y;
	}
	return b;
    }

    // ---------------------------------------------------------------
}
