package blak3980;

//---------------------------------------------------------------
/**
 * The individual node of a linked structure that stores <code>T</code> objects.
 * This is a single forward linked structure node.
 * 
 * @author Tyler Blakeley
 * Blak3980@mylaurier.ca
 * 090603980
 */
public class GenericNode<T> {
    // The T data.
    private T element = null;
    // Link to the next TNode.
    private GenericNode<T> next = null;

    // ---------------------------------------------------------------
    /**
     * Creates a new node with a <code>T</code> data element and a link to the
     * next node in the linked structure.
     * 
     * @param element
     *            The data to store in the node.
     * @param next
     *            The next node in the linked structure. A value of
     *            <code>null</code> marks the end of the linked structure.
     */
    public GenericNode(final T element, final GenericNode<T> next) {
	this.element = element;
	this.next = next;
    }

    // ---------------------------------------------------------------
    /**
     * Returns the node data.
     * 
     * @return The <code>T</code> object that is the data portion of the node.
     */
    public T getElement() {
	return this.element;
    }

    // ---------------------------------------------------------------
    /**
     * Returns the next node in the linked structure.
     * 
     * @return The node that follows this node.
     */
    public GenericNode<T> getNext() {
	return this.next;
    }

    // ---------------------------------------------------------------
    /**
     * Sets the data element of this node to a new <code>T</code> object.
     * 
     * @param element
     *            The new <code>T</code> data.
     */
    public void setElement(final T element) {
	this.element = element;
    }

    // ---------------------------------------------------------------
    /**
     * Sets the link element of this node to a new node.
     * 
     * @param next
     *            The new node link.
     */
    public void setNext(final GenericNode<T> next) {
	this.next = next;
    }

    // ---------------------------------------------------------------
}