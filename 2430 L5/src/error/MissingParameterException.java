/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

/**
 *
 * @author Maame Apenteng 
 */
public class MissingParameterException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>MissingParameterException</code> without detail message.
     */
    public MissingParameterException() {
        super("Mandatory field missing!");
    }

    /**
     * Constructs an instance of
     * <code>MissingParameterException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public MissingParameterException(String msg) {
        super(msg);
    }
}
