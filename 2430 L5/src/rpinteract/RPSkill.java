/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpinteract;
import utils.DieRoller;
import utils.Die;
import rpcharacter.RPCharacter;
import java.util.HashMap;
import error.InvalidNameException;
/**
 *
 * @author Maame Apenteng (0802637)
 * @version Nov/12/2013
 */
public class RPSkill {
    private String name;
    private String description; //brief description on what the skill does
    private String keyAbility; //one of the six abilities that is relevant to this skill
    private int rank; //power of the skill; cannot be greater than character level
    private int difficultyClass; //DC check of the relevant ability
    //Strings which provide feedback regarding the actions taking place
    private String mainAction; //what the skill actually does
    private String checkFailed; //Output when the skill check fails
    private String checkSucceeded; //Output when the skill check succeeds
    private String finalOutcome; //the final result of using the skill
    //HashMap keys
    public final static String nameKey = "name";
    public final static String descriptionKey = "descript";
    public final static String keyAbilityKey = "KA";
    public final static String mainActionKey = "MA";
    public final static String checkSucceededKey = "SUCCESS";
    public final static String checkFailedKey = "FAIL";
    public final static String finalOutcomeKey = "FO";
    
    //Constructor
    public RPSkill(String name, String description, String keyAbility, String mainAction, String checkFailed, String checkSucceeded, String finalOutcome) throws InvalidNameException{
        if(!InvalidNameException.isValid(name)){
            throw new InvalidNameException();
        }
        this.name = name;
        this.description = description;
        this.keyAbility = keyAbility;
        this.mainAction = mainAction;
        this.checkFailed = checkFailed;
        this.checkSucceeded = checkSucceeded;
        this.finalOutcome = finalOutcome;
        //default value for rank and difficultyClass
        this.difficultyClass = 1;
        this.rank = 1;
    }
    
    //Accessors
    public String getName(){
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getKeyAbility() {
        return keyAbility;
    }

    public int getRank() {
        return rank;
    }

    public int getDifficultyClass() {
        return difficultyClass;
    }

    public String getMainAction() {
        return mainAction;
    }

    public String getCheckFailed() {
        return checkFailed;
    }

    public String getCheckSucceeded() {
        return checkSucceeded;
    }

    public String getFinalOutcome() {
        return finalOutcome;
    }

    //Mutators
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeyAbility(String keyAbility) {
        this.keyAbility = keyAbility;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setDifficultyClass(int maxDifficultyClass) {
        this.difficultyClass = (int)Math.random() * maxDifficultyClass + 1;
    }

    public void setMainAction(String mainAction) {
        this.mainAction = mainAction;
    }

    public void setCheckFailed(String checkFailed) {
        this.checkFailed = checkFailed;
    }

    public void setCheckSucceeded(String checkSucceeded) {
        this.checkSucceeded = checkSucceeded;
    }

    /**
     * @param finalOutcome new final outcome
     */
    public void setFinalOutcome(String finalOutcome) {
        this.finalOutcome = finalOutcome;
    }
    
    //Functionality Methods;
    /**
     * <p>Performs a skill check using the owner's relevant ability and the skill's DC.</p>
     * @param owner The Character using the skill
     * @return true if the check succeeded, else false
     */
    public boolean check(RPCharacter owner){
        Die attackDie = new Die(20);
        int check = DieRoller.rollNormal(attackDie, 1) + owner.getAbility(keyAbility)/4;
        if(check >= difficultyClass){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * <p>Actually performs the skill.</p>
     * @param owner The character using the skill
     * @return A message indicating the outcomes of the skill action
     */
    public String perform(RPCharacter owner){
        String output = mainAction;
        if(check(owner)){
            output += "\n" + checkSucceeded;
        }
        else{
            output+= "\n" + checkFailed;
        }
        
        return output + "\n" + finalOutcome;
    }
    
    /**
     * Checks to see if RPSkill is considered identical
     * @param other RPSkill to be compared to
     * @return <code>true</code> if other has the same name as this RPSkill, <code>false</code> otherwise
     */
    public boolean isIdentical(RPSkill other){
        if(name.equalsIgnoreCase(other.name)){
            return true;
        }
        return false;
    }
    
    /**
     * <p>Converts RPSkill into HashMap</p>
     * @return HashMap that represents the RPSkill
     */
    public HashMap<String,String> toMap(){
        HashMap<String, String> map = new HashMap();
        map.put(RPSkill.nameKey, name);
        map.put(RPSkill.checkFailedKey, checkFailed);
        map.put(RPSkill.checkSucceededKey, checkSucceeded);
        map.put(RPSkill.descriptionKey, description);
        map.put(RPSkill.finalOutcomeKey, finalOutcome);
        map.put(RPSkill.keyAbilityKey, keyAbility);
        map.put(RPSkill.mainActionKey, mainAction);
        return map;
    }
    
    /**
     * Converts HashMap to an RPSkill
     * @param map to be converted
     * @return RPSkill
     */
    public static RPSkill mapToSkill(HashMap<String, String> map){
        String name = map.get(nameKey);
        String checkFailed = map.get(checkFailedKey);
        String checkSucceeded = map.get(checkSucceededKey);
        String description = map.get(descriptionKey);
        String finalOutcome = map.get(finalOutcomeKey);
        String keyAbility = map.get(keyAbilityKey);
        String mainAction = map.get(mainActionKey);
        return new RPSkill(name, description, keyAbility, mainAction, checkFailed, checkSucceeded, finalOutcome);
    }
    
    /**
     * <p>Used to assign an RPSkill to a Character. 
     * Creates a copy of the character with rank set to the provided value</p>
     * @param rank the value for the rank instance variable to be set to
     * @return a copy of the RPSkill
     */
    public RPSkill createInstanceOf(int characterLevel){
        RPSkill copy = new RPSkill(this.name, this.description, this.keyAbility, this.mainAction, this.checkFailed, this.checkSucceeded, this.finalOutcome);        
        copy.setRank(characterLevel);
        copy.setDifficultyClass(characterLevel);
        return copy;
    } //end of createInstanceOf method
    
    
} //end of RPSkill
