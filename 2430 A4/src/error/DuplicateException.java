/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

/**
 *
 * @author Owner
 */
public class DuplicateException extends RuntimeException{
     /**
     * Creates a new instance of
     * <code>DuplicateException</code> with default message.
     */
    public DuplicateException() {
        super("This item already exists.");
    }

    /**
     * Constructs an instance of
     * <code>DuplicateException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicateException(String msg) {
        super(msg);
    }
    
}
