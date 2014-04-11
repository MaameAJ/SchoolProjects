/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

/**
 *
 * @author Owner
 */
public class InvalidNameException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>InvalidNameException</code> without detail message.
     */
    public InvalidNameException() {
        super("Error: Invalid name. Name cannot contain characters other than alphabetical letters, hyphens and apostrophes.");
    }

    /**
     * Constructs an instance of
     * <code>InvalidNameException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidNameException(String msg) {
        super(msg);
    }
    
    /**
     * Checks to see if string is valid
     * @param name string to validated
     * @return <code>true</code> if string contains only alphanumeric characters and hyphens, <code>false</code> otherwise
     */
    public static boolean isValid(String name){
        for(int i = 0; i < name.length(); i++){
            if(!isValidChar(name.charAt(i))){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks to see if character is valid
     * @param letter string to validated
     * @return <code>true</code> if character is an alphanumeric character or a hyphen, <code>false</code> otherwise
     */
    private static boolean isValidChar(char letter){
        boolean valid;
        switch(letter){
            case 'a':
            case 'A':
            case 'b':
            case 'B':
            case 'C':
            case 'c':
            case 'd':
            case 'D':
            case 'e':
            case 'E':
            case 'f':
            case 'F':
            case 'g':
            case 'G':
            case 'h':
            case 'H':
            case 'i':
            case 'I':
            case 'j':
            case 'J':
            case 'k':
            case 'K':
            case 'l':
            case 'L':
            case 'm':
            case 'M':
            case 'n':
            case 'N':
            case 'o':
            case 'O':
            case 'p':
            case 'P':
            case 'q':
            case 'Q':
            case 'r':
            case 'R':
            case 's':
            case 'S':
            case 't':
            case 'u':
            case 'U':
            case 'v':
            case 'V':
            case 'w':
            case 'W':
            case 'x':
            case 'X':
            case 'y':
            case 'Y':
            case 'z':
            case 'Z':
            case '-':
            case '\'':
                valid = true;
                break;
            default:
                valid = false;
        } //end of switch statement
        return valid;
    } //end of isValidChar function
}
