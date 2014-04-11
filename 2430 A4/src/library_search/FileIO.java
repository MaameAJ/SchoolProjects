
package library_search;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * <p>Loads and saves RPCharacters from and to file respectively</p>
 * @author Maame Apenteng
 * @version Nov/29/2013
 */
public class FileIO {
    private Scanner readFile;
    private PrintWriter writeFile;
    private File saveFile;
    private File loadFile;
    
    /**
     * <p>Constructs a new FileIO object</p>
     * <p><b>Note:</b> the saveFile is emptied as soon as the constructor is called</p>
     * @param loadFile the file to be read from
     * @param saveFile the file to be saved to
     */
    public FileIO(String loadFile, String saveFile) throws FileNotFoundException, IOException{
        this(new File(loadFile), new File(saveFile));
    }
    
    public FileIO(File loadFile, File saveFile) throws IOException, FileNotFoundException{
        this.saveFile = saveFile;
        if(!this.saveFile.exists()){
            this.saveFile.createNewFile();
        }
        else if((!this.saveFile.isFile()) || (!this.saveFile.canWrite())){
            throw new FileNotFoundException("Error: cannot use "+saveFile);
        }
        
       this.loadFile = loadFile;
        if(!this.loadFile.exists()){
            throw new FileNotFoundException("Error: cannot find "+loadFile);
        }
        else if((!this.loadFile.isFile()) || (!this.loadFile.canRead())){
            throw new FileNotFoundException("Error: cannot use "+loadFile);
        }
    }
    
    /**
     * <p>Reads all the References from file and saves them to an array list</p>
     * @return an array list containing all the References
     */
    public ArrayList<Reference> readAll(){
        
        ArrayList<Reference> fromFile = new ArrayList();
        StringTokenizer readLine;
        String currentLine, key;
        String type, callNum = "", title = "", pub_org = "";
        int year = 0;
        ArrayList<String> authors = new ArrayList();
        boolean book = false;
        Reference currentRef;
        
        try{
            readFile = new Scanner(new FileInputStream(loadFile));
        }
        catch(FileNotFoundException e){
            System.out.println("Error: cannot find "+loadFile.getName());
            System.exit(0);
        }
        
        readLoop:
        while(readFile.hasNextLine()){
            currentLine = readFile.nextLine();
            //System.out.println("Inside a reference:");
            while(!currentLine.isEmpty()){
                readLine = new StringTokenizer(currentLine);
                type = readLine.nextToken("=").trim();
                key = readLine.nextToken().trim();
                //System.out.println(type + " = " + key);
                if(type.equalsIgnoreCase("type")){
                    if(key.equalsIgnoreCase("book")){
                        book = true;
                    }
                    else if(key.equalsIgnoreCase("journal")){
                        book = false;
                    }
                    else{
                        System.out.printf("Error: Something went wrong.");
                        //throw error? or exit????
                    }
                }
                if(type.equalsIgnoreCase("callnumber")){
                    callNum = key;
                }
                else if(type.equalsIgnoreCase("authors")){
                    authors = new ArrayList();
                    StringTokenizer authorInfo = new StringTokenizer(key);
                    while(authorInfo.hasMoreTokens()){
                        authors.add(authorInfo.nextToken(",").trim());
                    }//end of inner while loop
                }
                else if(type.equalsIgnoreCase("title")){
                    title = key;
                }
                else if(type.equalsIgnoreCase("publisher") || type.equalsIgnoreCase("organization")){
                    pub_org = key;
                }
                else if(type.equalsIgnoreCase("year")){
                    year = Integer.valueOf(key);
                }
                currentLine = readFile.nextLine();
            } //end of inner while loop
            
            if(book){
                if(!authors.isEmpty() || !pub_org.isEmpty()){
                    currentRef = new Book(callNum, title, pub_org, authors, year);
                }
                else{
                    currentRef = new Book(callNum, title, year);
                }
            }
            else{
                if(pub_org.isEmpty()){
                    currentRef = new Journal(callNum, title, year);
                }
                else{
                    currentRef = new Journal(callNum, title, pub_org, year);
                }
            }
            if(!fromFile.contains(currentRef)){
                fromFile.add(currentRef);
            }
        } //end of outer while loop     
        readFile.close();
        //System.out.println(fromFile.size()+" should be "+added);
        return fromFile;
    }
    
    /**
     * <p>Saves all the References to file</p>
     * <p><b>Note:</b> This function overwrites the file each time</p>
     * @param toSave the arrayList to be saved to
     */
    public void saveAll(ArrayList<Reference> toSave){
        
        try{
            writeFile = new PrintWriter(new FileOutputStream(saveFile));
        }
        catch(FileNotFoundException e){
            System.out.println("Error: cannot find "+saveFile.getName());
            System.exit(0);
        }
        
        Reference currentRef;
        for(int i = 0; i < toSave.size(); i++){
            currentRef = toSave.get(i);
            if(currentRef instanceof Book){
                Book saveBook = (Book)currentRef;
                saveReference(saveBook);
            }
            else if(currentRef instanceof Journal){
                Journal saveJrnl = (Journal)currentRef;
                saveReference(saveJrnl);
            }
        }
        writeFile.close();
        
    }
    
    /**
     * <p>Saves a RPWizard character to file</p>
     * <p><b>Note:</b> Since this method is meant to be used with saveAll, it does not close the stream</p>
     * @param currentChar 
     */
    private void saveReference (Book currentBook){
        writeFile.println("type = book");
        writeFile.println("callnumber = "+currentBook.getCallNum());
        writeFile.println("title = "+currentBook.getTitle());
        if(currentBook.hasAuthors()){
            writeFile.print("authors = ");
            ArrayList authors = currentBook.getAuthors();
            for(int i = 0; i < authors.size(); i++){
                writeFile.print(authors.get(i));
                if(i >= 0 && i < authors.size()-1){
                    writeFile.print(", ");
                }
            }
            writeFile.println();
        }
        if(currentBook.hasPublisher()){
            writeFile.println("publisher = "+currentBook.getPublish());
        }
        writeFile.println("year = "+currentBook.getYear());
        writeFile.println();
    }
    
     /**
     * <p>Saves a RPFighter character to file</p>
     * <p><b>Note:</b> Since this method is meant to be used with saveAll, it does not close the stream</p>
     * @param currentChar 
     */
    private void saveReference(Journal currentJrnl){
        writeFile.println("type = journal");
        writeFile.println("callnumber = "+currentJrnl.getCallNum());
        writeFile.println("title = "+currentJrnl.getTitle());
        if(currentJrnl.hasOrg()){
            writeFile.println("organization = "+currentJrnl.getOrg());
        }
        writeFile.println("year = "+currentJrnl.getYear());
        writeFile.println();
    }
    
    
    /**
     * <p>Exists to test the methods of the FileIO class</p>
     * @param args 
     */
    public static void main(String[] args) {
       FileIO test = null;
       try{
        test = new FileIO("testLoad.txt", "testSave.txt");
       }
       catch(FileNotFoundException e){
           System.out.println(e.getMessage());
           System.exit(0);
       }
       catch(IOException e){
           System.out.println(e.getMessage());
           System.exit(0);
       }
       
       ArrayList<Reference> testRead = test.readAll();
       ArrayList<Reference> testWrite;
       System.out.println("Testing:");
       if(testRead == null || testRead.isEmpty()){
           System.out.println("Array was not properly loaded.");
           System.exit(0);
       }
       for(int i = 0; i < testRead.size(); i++){
           System.out.println(testRead.get(i));
       }
       test.saveAll(testRead);
       testWrite = test.readAll();
       if(testWrite == null || testRead.size() != testWrite.size()){
           System.out.println("Array was not properly saved.");
           System.exit(0);
       }
       else{
           for(int i = 0; i < testWrite.size(); i++){
               if(!testRead.get(i).equals(testWrite.get(i))){
                   System.out.println("Array was not properly saved.");
                   System.exit(0);
               }
           }
       }
       System.out.println("Test was sucessful!");
    } //end of main method
}

