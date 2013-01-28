package blak3980;

import java.util.StringTokenizer;

//---------------------------------------------------------------
/**
 * This class defines polynomial objects. It contains a array of
 * <code>int</code> objects, each of which represents a polynomial monomial.
 * Thus coefficients can be explicitly addressed through their exponent. All
 * elements of the <code>coeffs</code> array must have a <code>int</code> object
 * defined - thus there is no need to check for <code>null</code> array elements
 * when processing the array.
 * 
 * @author Tyler Blakeley
 * 090603980	Blak3980@mylaurier.ca
 */
public class Poly {

	// ---------------------------------------------------------------
	/**
	 * A static method used by the <code>Poly( String )</code> constructor to
	 * execute an operation against the top two elements of the constructor's
	 * stack of <code>Poly</code> objects.
	 * 
	 * @param operatorStack
	 *            The stack of outstanding operators.
	 * @param operandStack
	 *            The stack of outstanding polynomials.
	 * @throws OperatorException
	 *             An operator error.
	 */
	private static void operate(final GenericStack<Operator> operatorStack,
			final GenericStack<Poly> operandStack) throws OperatorException {
		final Operator operator = operatorStack.pop();
		final Poly p2 = operandStack.pop();
		final Poly p1 = operandStack.pop();
		operandStack.push(operator.perform(p1, p2));
	}

	// An array of int objects.
	private double[] coeffs = null;

	// The largest degree of the polynomial.
	private int degree = 0;
	public String variable = "";

	// ---------------------------------------------------------------
	/**
	 * Creates a new <code>Poly</code> object of degree <code>degree</code>. All
	 * elements of the <code>coeffs</code> array contain a <code>int</code>
	 * object with a coefficient of 0. The index of each element matches the
	 * exponent of its <code>int</code> object. The number of elements in the
	 * <code>coeffs</code> array is one greater than the degree of the
	 * polynomial, in order to store the exponents from 0 to <code>degree</code>
	 * .
	 * 
	 * @param degree
	 *            the <code>Poly</code> degree.
	 */
	public Poly(final int degree) {
		this.degree = degree;
		this.coeffs = new double[degree + 1];
		this.initializeCoeffs();
	}

	// ---------------------------------------------------------------
	/**
	 * Creates a new <code>Poly</code> with content identical to another
	 * <code>Poly</code> object.
	 * 
	 * @param that
	 *            The <code>Poly</code> whose contents are to be copied into
	 *            this <code>Poly</code>.
	 */
	public Poly(final Poly that) {
		this.degree = that.degree;
		this.coeffs = new double[this.degree + 1];
		variable = that.variable;

		for (int i = 0; i <= this.degree; i++) {
			this.coeffs[i] = that.coeffs[i];
		}
	}

	// ---------------------------------------------------------------
	/**
	 * Creates a <code>Poly</code> based upon a string representation of a
	 * polynomial. Not all terms need be explicitly represented in the input
	 * string.
	 * 
	 * @param str
	 *            The string representation of a polynomial. See <a
	 *            href="http://bohr.wlu.ca/cp213/asgns/A3.pdf">Assignment 3</a>
	 * @throws PolyException
	 *             A polynomial definition error.
	 * @throws OperatorException
	 *             An operator error.
	 */
	public Poly(final String str, String var) throws PolyException,
			OperatorException {
		// The stack of polynomials to process.
		variable = var;
		final GenericStack<Poly> operandStack = new GenericStack<Poly>();
		// The stack of operators to process against the polynomial stack.
		final GenericStack<Operator> operatorStack = new GenericStack<Operator>();
		// The delimiters used by the tokenizer.
		final String delimiters = var + "+-*()";
		final StringTokenizer input = new StringTokenizer(str, delimiters, true);
		String token = null;

		// Check for a leading negative sign. Assumes the next token is an
		// operand.
		if (str.startsWith("-")) {
			// Token must look like a negative number.
			try {
				// Read the first operand as a negative number.
				token = input.nextToken() + input.nextToken();
				final Poly p = new Poly(0);
				final double coefficient = Integer.parseInt(token);
				p.setCoefficient(0, coefficient);
				operandStack.push(p);
			} catch (NumberFormatException e) {
				throw new PolyException(token,
						PolyException.Flags.INVALID_TOKEN);
			}
		}

		// Process the rest of the tokens.
		while (input.hasMoreTokens()) {
			token = input.nextToken();

			if (token.equals(var)) {
				// Create a new polynomial with a single x term.
				final Poly p = new Poly(1);
				p.setCoefficient(1, 1);
				operandStack.push(p);
			} else if (Operator.isOperator(token)) {
				// token is an operator of some kind.

				if (token.equals("(")) {
					// Push the operator onto the operator stack.
					operatorStack.push(new Operator(token));
				} else if (token.equals(")")) {

					try {
						// Process all operators up to the next "(".
						while (!operatorStack.peek().toString().equals("(")) {
							// Calculate answer and push back onto operand
							// stack.
							Poly.operate(operatorStack, operandStack);
						}
						// Discard the opening "(".
						operatorStack.pop();
					} catch (final Exception e) {
						throw new PolyException(str,
								PolyException.Flags.NO_LEFT_PARENTHESIS);
					}
				} else {
					// Process all other operators.
					final Operator operator = new Operator(token);

					while (!operatorStack.isEmpty()
							&& operator.precedes(operatorStack.peek())) {
						Poly.operate(operatorStack, operandStack);
					}
					// pushing new operator on the stack
					operatorStack.push(operator);
				}
			} else {
				// Must be an operand. If not, there is an error.
				try {
					final double coefficient = Integer.parseInt(token);
					final Poly p = new Poly(0);
					p.setCoefficient(0, coefficient);
					operandStack.push(p);
				} catch (NumberFormatException e) {
					throw new PolyException(token,
							PolyException.Flags.INVALID_TOKEN);
				}
			}
		}

		while (!operatorStack.isEmpty()) {
			try {
				// Process the data left on the stacks.
				Poly.operate(operatorStack, operandStack);
			} catch (final Exception e) {
				throw new PolyException(str,
						PolyException.Flags.UNBALANCED_OPERATOR);
			}
		}
		// Copy the final polynomial on the stack into this polynomial's data.
		final Poly p = operandStack.pop();
		this.copy(p);
		// Operand stack should be empty at this point.
		if (!operandStack.isEmpty()) {
			throw new PolyException(str, PolyException.Flags.UNBALANCED_OPERAND);
		}
	}

	public Poly(final String str) throws PolyException,
			OperatorException {
		// The stack of polynomials to process.
		final GenericStack<Poly> operandStack = new GenericStack<Poly>();
		// The stack of operators to process against the polynomial stack.
		final GenericStack<Operator> operatorStack = new GenericStack<Operator>();
		// The delimiters used by the tokenizer.
		final String delimiters =  "x+-*()";
		final StringTokenizer input = new StringTokenizer(str, delimiters, true);
		String token = null;

		// Check for a leading negative sign. Assumes the next token is an
		// operand.
		if (str.startsWith("-")) {
			// Token must look like a negative number.
			try {
				// Read the first operand as a negative number.
				token = input.nextToken() + input.nextToken();
				final Poly p = new Poly(0);
				final double coefficient = Integer.parseInt(token);
				p.setCoefficient(0, coefficient);
				operandStack.push(p);
			} catch (NumberFormatException e) {
				throw new PolyException(token,
						PolyException.Flags.INVALID_TOKEN);
			}
		}

		// Process the rest of the tokens.
		while (input.hasMoreTokens()) {
			token = input.nextToken();

			if (token.equals("x")) {
				// Create a new polynomial with a single x term.
				final Poly p = new Poly(1);
				p.setCoefficient(1, 1);
				operandStack.push(p);
			} else if (Operator.isOperator(token)) {
				// token is an operator of some kind.

				if (token.equals("(")) {
					// Push the operator onto the operator stack.
					operatorStack.push(new Operator(token));
				} else if (token.equals(")")) {

					try {
						// Process all operators up to the next "(".
						while (!operatorStack.peek().toString().equals("(")) {
							// Calculate answer and push back onto operand
							// stack.
							Poly.operate(operatorStack, operandStack);
						}
						// Discard the opening "(".
						operatorStack.pop();
					} catch (final Exception e) {
						throw new PolyException(str,
								PolyException.Flags.NO_LEFT_PARENTHESIS);
					}
				} else {
					// Process all other operators.
					final Operator operator = new Operator(token);

					while (!operatorStack.isEmpty()
							&& operator.precedes(operatorStack.peek())) {
						Poly.operate(operatorStack, operandStack);
					}
					// pushing new operator on the stack
					operatorStack.push(operator);
				}
			} else {
				// Must be an operand. If not, there is an error.
				try {
					final double coefficient = Integer.parseInt(token);
					final Poly p = new Poly(0);
					p.setCoefficient(0, coefficient);
					operandStack.push(p);
				} catch (NumberFormatException e) {
					throw new PolyException(token,
							PolyException.Flags.INVALID_TOKEN);
				}
			}
		}

		while (!operatorStack.isEmpty()) {
			try {
				// Process the data left on the stacks.
				Poly.operate(operatorStack, operandStack);
			} catch (final Exception e) {
				throw new PolyException(str,
						PolyException.Flags.UNBALANCED_OPERATOR);
			}
		}
		// Copy the final polynomial on the stack into this polynomial's data.
		final Poly p = operandStack.pop();
		this.copy(p);
		// Operand stack should be empty at this point.
		if (!operandStack.isEmpty()) {
			throw new PolyException(str, PolyException.Flags.UNBALANCED_OPERAND);
		}
	}

	// ---------------------------------------------------------------
	/**
	 * Adds the coefficients of two polynomials together and returns a new
	 * <code>Poly</code> containing the result of this addition.
	 * 
	 * @param that
	 *            The <code>Poly</code> to add to this <code>Poly</code>.
	 * @return The new <code>Poly</code> that results from adding
	 *         <code>that</code> <code>Poly</code> to this <code>Poly</code>.
	 */
	public Poly add(final Poly that) {
		Poly result = null;
		int minDegree = 0;

		if (this.degree >= that.degree) {
			// Create a new Poly based up this (larger) polynomial.
			result = new Poly(this);
			minDegree = that.degree;
		} else {
			// Create a new Poly based up that (larger) polynomial.
			result = new Poly(that);
			minDegree = this.degree;
		}
		// Add the coeffs of the two polynomials up to the degree of the
		// smallest polynomial.
		for (int i = 0; i <= minDegree; i++) {
			result.coeffs[i] = this.coeffs[i] + that.coeffs[i];
		}
		result.redegree();
		return result;
	}

	// ---------------------------------------------------------------
	/**
	 * @param that
	 */
	public void copy(Poly that) {
		this.degree = that.degree;
		this.coeffs = new double[this.degree + 1];

		for (int i = 0; i <= this.degree; i++) {
			this.coeffs[i] = that.coeffs[i];
		}
	}

	// ---------------------------------------------------------------
	/**
	 * Returns a differentiated version of this <code>Poly</code>. The new
	 * <code>Poly</code> has a degree one smaller than that of the source
	 * <code>Poly</code>.
	 * 
	 * @return a differentiated version of this <code>Poly</code>.
	 */
	public Poly diff() {
		// The new Poly is one degree smaller than the source Poly. The 0 term
		// is dropped.
		final int degree = this.degree - 1;
		// The minimum allowed degree is 0.
		final Poly poly = new Poly(degree > 0 ? degree : 0);

		for (int i = this.degree; i > 0; i--) {
			poly.coeffs[i - 1] = this.coeffs[i] * i;
		}
		return poly;
	}

	// ---------------------------------------------------------------
	/**
	 * Returns whether this <code>Poly</code> object and another
	 * <code>Poly</code> object are the same object, or contain equivalent
	 * coeffs.
	 * 
	 * @param that
	 *            The <code>Poly</code> to compare this <code>Poly</code> to.
	 * @return <code>true</code> if this <code>Poly</code> and <code>that</code>
	 *         <code>Poly</code> are the same object or contain the equivalent
	 *         coeffs, <code>false</code> otherwise.
	 */
	public boolean equals(final Poly that) {
		boolean isEquals = false;

		if (this == that) {
			// Compare the actual objects.
			isEquals = true;
		} else if (that != null) {
			// Compare the objects' coeffs.

			if (this.degree == that.degree) {
				// Objects cannot be the same if their degrees do not match.
				int i = 0;
				isEquals = true;

				while (isEquals && i <= this.degree) {
					// Stop process when a term does not match or all coeffs are
					// compared.
					isEquals = this.coeffs[i] == that.coeffs[i];
					i++;
				}
			}
		}
		return isEquals;
	}

	// ---------------------------------------------------------------
	/**
	 * Evaluates a polynomial given an <i>x</i> value and returns the result of
	 * that evaluation. Uses the Horner rule to evaluate the polynomial using
	 * only the term coefficients, rather than calling the <code>int</code>
	 * <code>evalAt</code> method for each <code>int</code>. Uses
	 * <code>int</code> values.
	 * 
	 * @param x
	 *            The value to substitute for <i>x</i> in the polynomial.
	 * @return The result of substituting the value <code>x</code> for <i>x</i>
	 *         in the polynomial.
	 */
	public Rational evalAt(final Rational x) {
		Rational result = new Rational(0);

		for (int i = this.degree; i >= 0; i--) {
			result = result.multiply(x).add(new Rational(this.coeffs[i]));
		}
		return result;
	}

	// ---------------------------------------------------------------
	/**
	 * Evaluates a polynomial given an <i>x</i> value and returns the result of
	 * that evaluation. Uses the Horner rule to evaluate the polynomial using
	 * only the term coefficients, rather than calling the <code>int</code>
	 * <code>evalAt</code> method for each <code>int</code>. Uses
	 * <code>int</code> values.
	 * 
	 * @param x
	 *            The value to substitute for <i>x</i> in the polynomial.
	 * @return The result of substituting the value <code>x</code> for <i>x</i>
	 *         in the polynomial.
	 */
	public double evalAt(final int x) {
		double result = 0;

		for (int i = this.degree; i >= 0; i--) {
			result = result * x + this.coeffs[i];
		}
		return result;
	}

	// ---------------------------------------------------------------
	/**
	 * Returns the coefficient for the term of degree.
	 * 
	 * @param degree
	 *            The degree to return the coefficient of.
	 * @return the coefficient for degree.
	 */
	public double getCoefficient(int degree) {
		return this.coeffs[degree];
	}

	// ---------------------------------------------------------------
	/**
	 * Returns the degree of the polynomial.
	 * 
	 * @return the degree of the polynomial.
	 */
	public int getDegree() {
		return this.degree;
	}

	// ---------------------------------------------------------------
	/**
	 * Helper method to initialize all coefficients to 0.
	 */
	private void initializeCoeffs() {
		for (int i = 0; i <= this.degree; i++) {
			this.coeffs[i] = 0;
		}
	}

	// ---------------------------------------------------------------
	/**
	 * Prints all of the integer roots of the polynomial in order from negative
	 * to positive roots.
	 */
	public void iroots() {

		if (this.degree == 0) {
			// An empty polynomial has no roots.
			System.out.println("no roots");
		} else if (this.coeffs.length == 0) {
			// The zero polynomial.
			System.out.println("Infinitely many roots");
		} else {
			// Find the first non-zero coefficient. (This may include the
			// coefficient at degree 0.)
			int i = 0;

			while (i < this.degree && this.coeffs[i] == 0) {
				i++;
			}
			final double coefficient = this.coeffs[i];
			final double upperValue = Math.abs(coefficient);
			final double lowerValue = -1 * Math.abs(coefficient);

			// Check for roots in the range and print any found.
			for (i = (int) lowerValue; i <= upperValue; i++) {
				// Evaluate the polynomial for 0 and for values that evenly
				// divide the coefficient. (Values that do not evenly divide
				// the coefficient cannot be roots.)
				if (i == 0 || coefficient % i == 0) {
					if (this.evalAt(i) == 0) {
						System.out.println(i);
					}
				}
			}
		}
	}

	// ---------------------------------------------------------------
	/**
	 * Multiplies this <code>Poly</code> with another <code>Poly</code> and
	 * returns the result in a new <code>Poly</code>.
	 * 
	 * @param that
	 *            The other <code>Poly</code> to multiply this <code>Poly</code>
	 *            with.
	 * @return The result of multiplying this <code>Poly</code> with
	 *         <code>that</code> <code>Poly</code>.
	 */
	public Poly mult(final Poly that) {
		final int degrees = this.degree + that.degree;
		final Poly result = new Poly(degrees);

		// Multiply each int of this Poly by each term of that Poly. Sum the
		// results.
		for (int i = 0; i <= this.degree; i++) {
			for (int j = 0; j <= that.degree; j++) {
				result.coeffs[i + j] += this.coeffs[i] * that.coeffs[j];
			}
		}
		return result;
	}

	// ---------------------------------------------------------------
	/**
	 * A helper method to rid the polynomial of any 0 coefficient coeffs above
	 * the last non-zero coefficient coeffs. (Zero coefficient coeffs may appear
	 * due to addition or subtraction.) Note that the existance of the
	 * <code>Poly( int )</code> constructor makes this method either required,
	 * or less useful, depending on your point of view.
	 */
	private void redegree() {
		int i = this.degree;

		// Find the largest non-zero degree.
		while (i > 0 && this.coeffs[i] == 0) {
			i--;
		}
		// Are there any high-order zero coeffs?
		if (i < this.degree) {
			double[] coeffs = new double[i + 1];
			this.degree = i;

			// Copy the non-zero coeffs to the shorter array.
			while (i >= 0) {
				coeffs[i] = this.coeffs[i];
				i--;
			}
			this.coeffs = coeffs;
		}
	}

	// ---------------------------------------------------------------
	/**
	 * Sets the coefficient for the term degree.
	 * 
	 * @param degree
	 *            The degree to set a coefficient for.
	 * @param coeff
	 *            The value of the coefficient.
	 */
	public void setCoefficient(int degree, double coeff) {
		this.coeffs[degree] = coeff;
	}

	// ---------------------------------------------------------------
	/**
	 * Subtracts the coeffs of one polynomial from another and returns a new
	 * <code>Poly</code> representing the result of this subtraction.
	 * 
	 * @param that
	 *            The <code>Poly</code> to subtract from this <code>Poly</code>.
	 * @return The new <code>Poly</code> that results from subtracting
	 *         <code>that</code> <code>Poly</code> from this <code>Poly</code>.
	 */
	public Poly sub(final Poly that) {
		Poly result = null;
		int minDegree = 0;

		// Base the new Poly on the largest of the two input Poly objects.
		if (this.degree >= that.degree) {
			result = new Poly(this);
			minDegree = that.degree;
		} else {
			result = new Poly(that);
			minDegree = this.degree;
			// Negate the higher coeffs.
			for (int i = that.degree; i >= minDegree; i--) {
				result.coeffs[i] *= -1;
			}
		}
		// Subtract the remaining elements of that polynomial from this
		// polynomial.
		for (int i = 0; i <= minDegree; i++) {
			result.coeffs[i] = this.coeffs[i] - that.coeffs[i];
		}
		result.redegree();
		return result;
	}

	// ---------------------------------------------------------------
	@Override
	public String toString() {
		String result = "";

		// Add all the int strings to the final result.
		for (int exponent = this.degree; exponent >= 0; exponent--) {
			String str = "";

			double coefficient = this.coeffs[exponent];

			if (coefficient != 0) {

				if (coefficient > 1 && exponent > 1) {
					// Don't add a coefficient of 1 if there is an exponent > 1.
					str = " + " + coefficient + variable + "^" + exponent;
				}
				// Add a positive or negative sign as appropriate.
				if (coefficient < 0) {
					str = " -";
				} else {
					str = " + ";
				}
				final double c = Math.abs(coefficient);

				if (c != 1 || c == 1 && exponent == 0) {
					// Add a lone coefficient if necessary.
					str += c;
				}
				// Add the exponent, if necessary.
				if (exponent > 1) {

					str = str + variable + "^" + exponent;

				} else if (exponent == 1) {

					str = str + variable;

				}
			}
			result += str;
		}

		if (result.startsWith(" + ")) {
			// Remove any plus sign prefix.
			result = result.substring(3);
		}
		// Remove leading and trailing whitespace.
		result = result.trim();

		if (result.equals("")) {
			// Must be a zero polynomial.
			result = "0";
		}
		return result;
	}

	// ---------------------------------------------------------------
	/**
	 * Returns a string containing all coeffs in this <code>Poly</code>,
	 * whatever their value. Use for debugging. Uses the
	 * <code>toStringFull</code> method of <code>int</code>.
	 * 
	 * @return The string version of this <code>Poly</code>.
	 * */
	public String toStringFull() {
		String result = "";

		for (int i = this.degree; i >= 0; i--) {
			result += this.coeffs[i] + variable + "^" + i + " ";
		}
		return result;
	}

	// ---------------------------------------------------------------
}
