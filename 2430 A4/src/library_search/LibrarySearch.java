package library_search;
import error.DuplicateException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
//import java.util.InputMismatchException;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <p>The back end code for LibrarySearchInterface</p>
 * @version Nov/30/2013
 */
public class LibrarySearch {
    
    /**
     * The catalogue of books
     */
    private ArrayList<Reference> library;
    /**
     * the index of the books
     */
    private HashMap<String, ArrayList<Integer>> index = new HashMap();
    
    /**
     * used to read and write to file
     */
    private FileIO info;
    
    /**
     * @param loadFrom name of file to load information from
     * @param saveTo name of file to save information to
     */
    public LibrarySearch(File loadFrom, File saveTo) throws FileNotFoundException, IOException{
        info = new FileIO(loadFrom, saveTo);
        index = new HashMap();
        load();
    }
    
    /**
     * @return library
     */
    public ArrayList<Reference> getLibrary(){
        return library;
    }
    /**
     * <p>Loads information from file</p>
     */
    private void load(){
        library = info.readAll();   //loads in library from file
        //generates index
        for(int i = 0; i < library.size(); i++){
            addToIndex(library.get(i), i);
        } //end of for loop
    }
    
    /**
     * Adds a book to the library
     * @param callNum the call number of the book
     * @param title the title of the book
     * @param year the year the book was published
     * @param authorsInfo the authors of the book | can be empty
     * @param publish the publisher of the book | can be empty
     * @return <code>true</code> if the book is added successfully, <code>false</code> otherwise
     */
    public boolean add(String callNum, String title, int year, String authorsInfo, String publish) throws DuplicateException, IllegalArgumentException{
        Reference newItem;
        ArrayList<String> listAuthors = new ArrayList();
        boolean hasAuthor = false, hasPubOrg = false;
       
        //tokenize authors
        if(!authorsInfo.isEmpty()){
            StringTokenizer authors = new StringTokenizer(authorsInfo);
            while(authors.hasMoreTokens()){
                listAuthors.add(authors.nextToken(";").trim());
            }
            if(!listAuthors.isEmpty()){
                hasAuthor = true;
            }
        }            
                
        if(!publish.isEmpty()){
            hasPubOrg = true;
        }
                
        //creates item
        if(hasAuthor || hasPubOrg){
            newItem = new Book(callNum, title, publish, listAuthors, year);
        }
        else{
            newItem = new Book(callNum, title, year);
        }
        
        //check to see if item is unique
        for(int i = 0; i < library.size(); i++){
            if(library.get(i).isDuplicate(newItem)){
                 throw new DuplicateException("\nSorry, this item already exists in the library.");
            }
        } //end of for loop
        library.add(newItem);
        addToIndex(newItem, library.indexOf(newItem));
        return true;
        
    } //end of add method for books
    
    /**
     * Adds a journal to the library
     * @param callNum the call number of the journal
     * @param title the title of the journal
     * @param year the year the journal was published
     * @param publish the organization that publishes the journal | can be empty
     * @return <code>true</code> if the journal is added successfully, <code>false</code> otherwise
     */
    public boolean add(String callNum, String title, int year, String publish) throws DuplicateException, IllegalArgumentException{
        Reference newItem;
        boolean hasPubOrg = false;           
        //check to see if there is a publisher/organization to add
        if(!publish.isEmpty()){
            hasPubOrg = true;
        }
        //creates the item
        if(hasPubOrg){
            newItem = new Journal(callNum, title, publish, year);
        }
        else{
            newItem = new Journal(callNum, title, year);
        }
        
        //check to see if item is considered unique
        for(int i = 0; i < library.size(); i++){
            if(library.get(i).isDuplicate(newItem)){
                throw new DuplicateException("\nSorry, this item already exists in the library.");
            }
        } //end of for loop
        
        boolean success = library.add(newItem);
        if(success){
            addToIndex(newItem, library.indexOf(newItem));
        }
        return success;
        
    } //end of add method for journals
    
    /**
     * <p>Searches both library for matches using the provided search keys</p>
     */
    public String search(String call_num, int minYear, int maxYear, String keywords){
        ArrayList<Integer> narrowedResults;
        
        if(!keywords.isEmpty()){
            narrowedResults = searchIndex(keywords);
            ArrayList<Reference> matches = new ArrayList();
            for(int i : narrowedResults){
                matches.add(library.get(i));
            }
            return searchList(matches, call_num, minYear, maxYear);
        }
        else{
            return searchList(library, call_num, minYear, maxYear);
        }
    } //end of search method    
    
    /**
     * <p>Edits a reference</p>
     * @param toEdit the reference to be edited
     * @param newVersion the edited version of toEdit
     * @return Strings detailing the success of the edit
     */
    public String edit(Reference toEdit, Reference newVersion){
        if(!library.contains(toEdit)){
            throw new NoSuchElementException("This reference does not exist in the library.");
        }
        else{
            int set = library.indexOf(toEdit);
            library.set(set, newVersion); 
        }
        return "Item successfully edited!";
    }
    
    public String delete(ArrayList<Reference> toDelete){
        String output = "";
        for(Reference r: toDelete){
            String item = r.getCallNum()+"; "+r.getTitle()+"; "+r.getYear();
            if(library.contains(r)){
                library.remove(r);
                output+= "\n"+item+" was succesfully deleted.";
            }
            else{
                output+= "\n"+item+" could not be deleted because it is not contained in the library.";
            }
        }
        return output+="\n";
    }
    
    /**
     * Adds an item to the index
     * @param item the item to be added
     * @param position its index in <code>library</code>
     */
    private void addToIndex(Reference item, int position){
        String title = item.getTitle(), key;
        StringTokenizer makeKeys = new StringTokenizer(title);
        ArrayList<Integer> value;
        while(makeKeys.hasMoreTokens()){
            key = makeKeys.nextToken();
            key = key.toLowerCase();
            if(index.containsKey(key)){
                value = index.get(key);
            }
            else{
                value = new ArrayList();
            }
            value.add(position);
            index.put(key, value);
        } //end of while loop
    } //end of addToIndex
    
    /**
     * <p>Optimizes the search by searching the index for the provided keywords.</p>
     * @param keywords the query to be matched
     * @return the indices of the references that match the given query
     */
    private ArrayList<Integer> searchIndex(String keywords){
        ArrayList<Integer> results = new ArrayList();
        StringTokenizer query = new StringTokenizer(keywords);
        String searchKey;
        int totalQueries = query.countTokens();
        for(int i = 0; i < totalQueries; i++){
            searchKey = query.nextToken();
            searchKey = searchKey.toLowerCase();
            if(index.containsKey(searchKey)){
                ArrayList<Integer> matches = index.get(searchKey);
                if(i == 0){
                    results.addAll(matches);
                }
                else{ //ensures that only results that match all tokens are returned
                    matches.retainAll(results);
                    results.retainAll(matches);
                }
            } //end of if index contains key statement
        }
        
        return results;
    }
    
    /**
     * <p>Searches a given ArrayList of References with the provided search terms. 
     * Also prints out all matches. </p>
     * @param toSearch the ArrayList to be searched
     * @param call_num the call number search key
     * @param minYear the earliest a search result should have been published
     * @param maxYear the latest a search result could be published
     * @return the number of results
     */
    private String searchList(ArrayList<Reference> toSearch, String call_num, int minYear, int maxYear){
        Reference checkItem;
        int found = 0;
        String results = "";
        booksearch: for(int i = 0; i < toSearch.size(); i++){
            checkItem = toSearch.get(i);
            //search for call number matches
            if(!call_num.isEmpty()){
                if(!(checkItem.getCallNum().equalsIgnoreCase(call_num))){
                    continue booksearch;
                }
            }
            //search for year matches
            if(!(checkItem.getYear() >= minYear)){
                continue booksearch;
            }
            if(!(checkItem.getYear() <= maxYear)){
                continue booksearch;
            }
            //search for keyword matches
            
            results += "\n"+checkItem;
            found++;
        }
        return results += "\n\n"+found+" matches found.\n";
    } //end of searchList method
    
    /**
     * Saves information to file
     */
    public void save(){
        info.saveAll(library);       
    } //end of save method
    
}//end of LibrarySearch Class

