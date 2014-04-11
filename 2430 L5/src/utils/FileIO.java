
package utils;
import rpcharacter.*;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.HashMap;

/**
 * <p>Loads and saves RPCharacters from and to file respectively</p>
 * @author Maame Apenteng
 * @version Nov/05/2013
 */
public class FileIO {
    private String filename;
    private Scanner readFile;
    private PrintWriter writeFile;
    
    /**
     * <p>Constructs a new FileIO object</p>
     * @param filename the file to be saved to and read from
     */
    public FileIO(String filename) {
        this.filename = filename;   
    }

    public Scanner getReadFile() {
        try{
            readFile = new Scanner(new FileInputStream(filename));
        }
        catch(FileNotFoundException e){
            System.out.println("Error: cannot find "+filename);
            System.exit(0);
        }
        return readFile;
    }

    public PrintWriter getWriteFile() {
        try{
            writeFile = new PrintWriter(new FileOutputStream(filename));
        }
        catch(FileNotFoundException e){
            System.out.println("Error: cannot find "+filename);
            System.exit(0);
        }
        return writeFile;
    }
    
    /**
     * <p>Saves an item to file</p>
     * <p><b>Note:</b> This function is a helper method for saveAll and does not close the stream</p>
     * @param item HashMap to be saved to file
     */
    public void saveItem(HashMap<String, String> item){
        for (String key : item.keySet()){
                writeFile.println(key+"="+item.get(key));
        }
        writeFile.println();
    }
    
    /**
     * <p>Reads in an item from file and converts it to a HashMap</p>
     * @return HashMap representing the item
     */
    public HashMap<String, String> readItem(){
        String currentLine = readFile.nextLine(), key, value;
        StringTokenizer readLine;
        HashMap<String, String> item = new HashMap();
        while(!currentLine.isEmpty()){
            readLine = new StringTokenizer(currentLine);
            if(!readLine.hasMoreTokens()){
                continue;
             }
            key = readLine.nextToken("=").trim();
            if(!readLine.hasMoreTokens()){
                continue;
            }
            else{
                value = readLine.nextToken().trim();
            }
            item.put(key, value);
            currentLine = readFile.nextLine();
        }
        return item;
    }
    
    /**
     * <p>Exists to test the methods of the FileIO class</p>
     * @param args 
     */
    public static void main(String[] args) {
       FileIO test = new FileIO("testCharacters.txt");
       ArrayList<RPCharacter> testRead = new ArrayList();
       ArrayList<RPCharacter> testWrite = new ArrayList();
       PrintWriter saveAll;
       System.out.println("Testing:");
       
        Scanner read = test.getReadFile();
        String currentLine;
        RPCharacter currentChar = null;
        HashMap charInfo;
        readLoop:
        while(read.hasNextLine()){
           currentLine = read.nextLine();
            if(currentLine.equals("[character]")){
                charInfo = test.readItem();
                if(charInfo.get(RPCharacter.classKey).toString().equalsIgnoreCase("Bard")){
                    currentChar = RPBard.mapToCharacter(charInfo);
                    testRead.add(currentChar);
                }
                else if(charInfo.get(RPCharacter.classKey).toString().equalsIgnoreCase("Wizard")){
                    currentChar = RPWizard.mapToCharacter(charInfo);                   
                    testRead.add(currentChar);
                }
                else if(charInfo.get(RPCharacter.classKey).toString().equalsIgnoreCase("Fighter")){
                    currentChar = RPFighter.mapToCharacter(charInfo);
                    testRead.add(currentChar);
                 }
            }//end of if statement
        } //end of while loop for entire file
        read.close();
       
       if(testRead == null || testRead.isEmpty()){
           System.out.println("Array was not properly loaded.");
           System.exit(0);
       }
       for(int i = 0; i < testRead.size(); i++){
           System.out.println(testRead.get(i));
       }
       
       saveAll = test.getWriteFile();
       for(RPCharacter e : testRead){
           HashMap toMap = e.toMap();
           test.saveItem(toMap);
       }
       saveAll.close();
       
        read = test.getReadFile();
        readLoop:
        while(read.hasNextLine()){
           currentLine = read.nextLine();
            if(currentLine.equals("[character]")){
                charInfo = test.readItem();
                if(charInfo.get(RPCharacter.classKey).toString().equalsIgnoreCase("Bard")){
                    currentChar = RPBard.mapToCharacter(charInfo);
                    testWrite.add(currentChar);
                }
                else if(charInfo.get(RPCharacter.classKey).toString().equalsIgnoreCase("Wizard")){
                    currentChar = RPWizard.mapToCharacter(charInfo);                   
                    testWrite.add(currentChar);
                }
                else if(charInfo.get(RPCharacter.classKey).toString().equalsIgnoreCase("Fighter")){
                    currentChar = RPFighter.mapToCharacter(charInfo);
                    testWrite.add(currentChar);
                 }
            }//end of if statement
        } //end of while loop for entire file
        read.close();
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
