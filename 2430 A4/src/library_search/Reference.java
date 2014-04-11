
package library_search;

/**
 * <p>Represents an object in a library</p>
 * @author Maame Apenteng
 * @version Nov/30/2013
 */
public class Reference {
    /**
     * The call number of the Reference - this variable cannot be empty
     */
    private final String callNum;
    
     /**
     * The title of the Reference - this variable cannot be empty
     */
    private final String title;
    
    /**
     * the year the Reference was published - this variable cannot be empty
     */
    private int year;

    /**
     * 
     * <p>Creates a new Reference object that initializes the mandatory values
     * of the Reference object and sets all others to some default value</p>
     * <p><b>Note:</b> the author array is null in this case</p>
     * @param callNum a string that represents the call number
     * @param title a string that represents the title
     * @param year a string that represents the publisher or organization
     * @throws IllegalArgumentException if any of the parameters are null or the empty string
     * or if the year is less than 1000 or greater than 9999
     */
    public Reference(String callNum, String title, int year) throws IllegalArgumentException {
        if(callNum == null || callNum.isEmpty() || callNum.equals(" ")){
            throw new IllegalArgumentException("Invalid Call Number");
        }
        else if(title == null || title.isEmpty() || title.equals(" ")){
            throw new IllegalArgumentException("Invalid Title");
        }
        else if(year < 1000 || year > 9999){
            throw new IllegalArgumentException("Invalid year");
        }
        else{
            this.callNum = callNum;
            this.title = title;
            this.year = year;
        }
    } //end of Reference constructor

    /**
     * <p>Gets the call number of the Reference</p>
     * @return String representing the call number
     */
    public String getCallNum() {
        return callNum;
    }

    /**
     * <p>Gets the title of the Reference</p>
     * @return String representing title
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>Gets the year the Reference was published</p>
     * @return integer representing year
     */
    public int getYear() {
        return year;
    }
    
    
    /**
     * <p>the string that represents the object is formatted
     * like so:<p>
     * <p>CALL NUMBER</p>
     * <p>TITLE</p>
     * <p>YEAR</p>
     * @return a string representing the Reference
     */
    @Override
    public String toString() {
        return "\n" + callNum + "\n" + title + "\n" + year;
    }

    /**
     * <p>Checks to see if this Reference is equal to another object
     * @param obj the object to be compared to
     * @return <code>true<code> if all the fields of the obj is identical to this Reference's fields
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reference other = (Reference) obj;
        if (!this.callNum.equals(other.callNum)) {
            return false;
        }
        if (!this.title.equals(other.title)) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }
    
    /**
     * <p>Checks to see if the two references are considered identical</p>
     * @param other
     * @return <code>true</code> if the two References have to same call number and year
     */
    public boolean isDuplicate(Reference other){
        if(!callNum.equalsIgnoreCase(other.callNum)){
            return false;
        }
        if(year != other.year){
            return false;
        }
        return true;
    }

}
