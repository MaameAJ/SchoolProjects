package library_search;

/**
 * <p>Class that represents a journal</p>
 * @author Maame Apenteng (0802637)
 * @version Nov/30/2013
 */
public class Journal extends Reference{
    /**
     * The organization that publishes the journal - this variable can be empty
     */
    private final String org;
    
    /**
     * 
     * <p>Creates a new journal object that initializes the mandatory values
     * of the Journal object and sets all others to some default value</p>
     * <p><b>Note:</b> the author array is null in this case</p>
     * @param newCallNum a string that represents the call number
     * @param newTitle a string that represents the title
     * @param newYear a string that represents the organization that publishes the journal
     * @throws IllegalArgumentException if any of the parameters are null or the empty string
     * or if the year is less than 1000 or greater than 9999
     */
    public Journal(String newCallNum, String newTitle, int newYear) throws IllegalArgumentException{
        super(newCallNum, newTitle, newYear);
        this.org = "";
    }//end of mandatory instructor
    
    /**
     * <p>Creates a Journal object with all instance variables initialized.</p>
     * @param newCallNum the call number of the journal
     * @param newTitle the title of the journal
     * @param org the organization that publishes the journal
     * @param newYear an integer representing the year the journal was published
     * @throws IllegalArgumentException if the call number or title is an empty string or null pointer
     * or if the year is less than 1000 or greater than 9999
     */
    public Journal(String newCallNum, String newTitle, String org, int newYear) throws IllegalArgumentException{
        super(newCallNum, newTitle, newYear);
        this.org = org;
    } //end of constructor with organization
  
    /**
     * <p>Gets the organization that writes and/or publishes the journal</p>
     * <p><b>Note:</b> You might want to call the getOrg method before using
     * this method.</p>
     * @return String representing organization that publishes the journal
     */
    public String getOrg(){
        return this.org;
    }
   
    /**
     * <p>Checks to see if journal contains information about the organization
     * that writes/publishes it</p>
     * @return <code>false</code> if org is null or empty, <code>true</code> otherwise
     */
    public boolean hasOrg(){
        if(org == null){
            return false;
        }
        else{
            return !org.isEmpty();
        }
    } //end of hasOrg method
    /**
     * <p>the string that represents the object is formatted
     * like so:<p>
     * <p>CALL NUMBER</p>
     * <p>TITLE</p>
     * <p>ORGANIZATION - if the journal contains information about it</p>
     * <p>YEAR</p>
     * @return a string representing the journal
     */
    @Override
    public String toString(){
       String journal = "\n";
       journal += this.getCallNum() + "\n" + this.getTitle();
       if(this.hasOrg()){
           journal += "\n" + org;
       }
       journal += "\n" + this.getYear();
       
       return journal;
    } //end of toString method

    /**
     * <p>Checks to see if to Journals are equivalent</p>
     * @param obj Journal to be compared to
     * @return <code>true</code> if the Journals contain identical information for all the fields, <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Journal other = (Journal) obj;
        if (!this.org.equals(other.org)) {
            return false;
        }
        return super.equals(obj);
    } //end of equals method

} //end of Journal class


