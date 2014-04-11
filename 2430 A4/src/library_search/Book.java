
package library_search;
import java.util.ArrayList;

/**
 * <p>Class that represents a book</p>
 * @author Maame Apenteng (0802637)
 * @version Nov/30/2013
 */
public class Book extends Reference{
  
    /**
     * the name of the publisher of the book
     */
    private final String publish;
    
    /**
     * the set of authors in the book;
     */
    private final ArrayList<String> authors;
    
    /**
     * 
     * <p>Creates a new book object that initializes the mandatory values
     * of the Book object and sets all others to some default value</p>
     * <p><b>Note:</b> the author array is null in this case</p>
     * @param newCallNum a string that represents the call number
     * @param newTitle a string that represents the title
     * @param newYear a string that represents the publisher or organization
     * @throws IllegalArgumentException if any of the parameters are null or the empty string
     * or if the year is less than 1000 or greater than 9999
     */
    public Book(String newCallNum, String newTitle, int newYear) throws IllegalArgumentException{
        super(newCallNum, newTitle, newYear);
        this.authors = null;
        this.publish = "";
    }//end of mandatory instructor
    /**
     * <p>Creates a Book object with all instance variables initialized.</p>
     * @param newCallNum the call number of the book
     * @param newTitle the title of the book
     * @param publish the publisher of the book
     * @param authors an ArrayList containing the authors
     * @param newYear an integer representing the year the book was published
     * @throws IllegalArgumentException if the call number or title string is empty or null or
     * if the year integer is less than 1000 or greater than 9999
     */
    public Book(String newCallNum, String newTitle, String publish, ArrayList<String> authors, int newYear) throws IllegalArgumentException {
        super(newCallNum, newTitle, newYear);
        this.authors = authors;
        this.publish = publish;
    }//end of constructor with authors and publisher
   
    /**
     * <p>Gets the publisher of the book</p>
     * <p><b>Note:</b> You might want to call hasPublisher
     * before using this method</p>
     * @return String representing publisher
     */
    public String getPublish(){
        return this.publish;
    }
    /**
     * <p>Gets the authors of the book</p>
     * <p><b>Note:</b> You might want to call hasAuthors
     * before using this method</p>
     * @return an array list representing the authors
     */
    public ArrayList<String> getAuthors() {
        return this.authors;
    }
    
    /**
     * <p>Checks to see if book has information about its authors</p>
     * @return <code>false</code> if authors is null or empty, <code>true</code> otherwise
     */
    public boolean hasAuthors(){
        if(authors == null){
            return false;
        }
        else{
            return !authors.isEmpty();
        }
    }
    
    /**
     * <p>Checks to see if book has information about its publisher</p>
     * @return <code>false</code> if publish is null or empty, <code>true</code> otherwise
     */
    public boolean hasPublisher(){
        if(publish == null){
            return false;
        }
        else{
            return !publish.isEmpty();
        }
    }
    
    /**
     * <p>the string that represents the object is formatted
     * like so:<p>
     * <p>CALL NUMBER</p>
     * <p>TITLE</p>
     * <p>AUTHOR(s)... if the book contains information about them</p>
     * <p>PUBLISHER... if the book contains information about the publisher</p>
     * <p>YEAR</p>
     * @return a string representing the book
     */
    @Override
    public String toString(){
       String book = "\n";
       book += this.getCallNum();
       book += "\n" + this.getTitle();
       if(this.hasAuthors()){
           book += "\n";
           for(int i = 0; i < authors.size(); i++){
               book += authors.get(i);
               if(i < (authors.size() - 1)){
                   book += ", ";
               }
           }
       }
       if(this.hasPublisher()){
           book += "\n" + publish;
       }
       book += "\n" + this.getYear();
       
       return book;
    }

    /**
     * <p>Checks to see if to Books are equivalent</p>
     * @param obj object to be compared to
     * @return <code>true</code> if the two books have identical information for all their fields, <code>false</code> otherwise
     */ 
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!this.publish.equals(other.publish)) {
            return false;
        }
        if (!this.authors.equals(other.authors)) {
            return false;
        }
        return super.equals(obj);
    }

}
