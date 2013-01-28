package blak3980;

//---------------------------------------------------------------
/**
 * A simple linked stack structure of <code>GenericNode<T></code> objects. Only
 * the <code>T</code> data contained in the stack is visible through the
 * standard stack methods.
 * 
 * @author Tyler Blakeley
 * Blak3980@mylaurier.ca
 * 090603980
 */
public class GenericStack<T> {
    // The top node of the stack.
    private GenericNode<T> top = null;

    // ---------------------------------------------------------------
    /**
     * Returns whether the stack is empty or not.
     * 
     * @return <code>true</code> if the stack is empty, <code>false</code>
     *         otherwise.
     */
    public boolean isEmpty() {
	return this.top == null;
    }

    // ---------------------------------------------------------------
    /**
     * Returns a reference to the top data element of the stack without removing
     * that data from the stack.
     * 
     * @return The <code>T</code> object at the top of the stack.
     */
    public T peek() {
	return this.top.getElement();
    }

    // ---------------------------------------------------------------
    /**
     * Returns the top data element of the stack and removes that element from
     * the stack. The next node in the stack becomes the new top node.
     * 
     * @return The <code>T</code> object at the top of the stack.
     */
    public T pop() {
	final GenericNode<T> node = this.top;
	this.top = this.top.getNext();
	return node.getElement();
    }

    // ---------------------------------------------------------------
    /**
     * Adds a <code>T</code> object to the top of the stack.
     * 
     * @param data
     *            The <code>T</code> object to add to the top of the stack.
     */
    public void push(final T data) {
	final GenericNode<T> node = new GenericNode<T>(data, this.top);
	this.top = node;
    }

    // ---------------------------------------------------------------
}