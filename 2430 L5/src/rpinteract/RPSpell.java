/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpinteract;

import error.InvalidNameException;
import rpcharacter.RPCharacter;
import utils.Die;
import utils.DieRoller;
import java.util.HashMap;

/**
 *
 * @author Maame Apenteng (0802637)
 * @version Nov/12/2013
 */
public class RPSpell extends RPSkill{
    public final static String counterAbilityKey = "CA";
    private String counterAbility; //the ability used to resist key ability
    
    //Constructor
    public RPSpell(String counterAbility, String name, String description, String keyAbility, String mainAction, String checkFailed, String checkSucceeded, String finalOutcome) throws InvalidNameException{
        super(name, description, keyAbility, mainAction, checkFailed, checkSucceeded, finalOutcome);
        this.counterAbility = counterAbility;
    }
    
    //Accessor
    public String getCounterAbility() {
        return counterAbility;
    }
    
    //Mutator
    public void setCounterAbility(String counterAbility) {
        this.counterAbility = counterAbility;
    }
    
    /**
     * <p>Performs a spell check using the owner's relevant ability and the spell's DC.</p>
     * @param owner The Character using the skill
     * @return true if the check succeeded, else false
     */
    public boolean check(RPCharacter owner, RPCharacter target){
        Die attackDie = new Die(20);
        int targetCheck = DieRoller.rollNormal(attackDie, 1) + target.getAbility(counterAbility)/4;
        int ownerCheck = DieRoller.rollNormal(attackDie, 1) + owner.getAbility(this.getKeyAbility())/4;
        
        if(this.check(owner) && ownerCheck >= targetCheck){
            return true;
        }
        else{
            return false;
        }
    } //end of check method
    
    /**
     * <p>Actually performs the spell.</p>
     * @param owner The character using the skill
     * @param target of the spell
     * @return A message indicating the outcomes of the skill action
     */
    public String perform(RPCharacter owner, RPCharacter target){
        String output = this.getMainAction();
        if(check(owner, target)){
            output += "\n" + this.getCheckSucceeded();
        }
        else{
            output+= "\n" + this.getCheckFailed();
        }
        
        return output + "\n" + this.getFinalOutcome();
    } //end of perform method
    
    /**
     * <p>Converts RPSpell into HashMap</p>
     * @return HashMap that represents the RPSkill
     */
    @Override
    public HashMap<String,String> toMap(){
        HashMap<String, String> map = new HashMap();
        map.put(RPSpell.counterAbilityKey, counterAbility);
        map.putAll(super.toMap());
        return map;
    }

   /**
     * Converts HashMap to an RPSpell
     * @param map to be converted
     * @return RPSkill
     */
    public static RPSpell mapToSpell(HashMap<String, String> map){
        String name = map.get(nameKey);
        String checkFailed = map.get(checkFailedKey);
        String checkSucceeded = map.get(checkSucceededKey);
        String description = map.get(descriptionKey);
        String finalOutcome = map.get(finalOutcomeKey);
        String keyAbility = map.get(keyAbilityKey);
        String mainAction = map.get(mainActionKey);
        String counterAbility = map.get(counterAbilityKey);
        return new RPSpell(counterAbility, name, description, keyAbility, mainAction, checkFailed, checkSucceeded, finalOutcome);
    }
    
    /**
     * <p>Used to assign an RPSpell to a Character. 
     * Creates a copy of the character with rank set to the provided value</p>
     * @param characterLevel the value for the rank instance variable to be set to
     * @return a copy of the RPSkill
     */
    @Override
    public RPSpell createInstanceOf(int characterLevel) {
        RPSpell copy = new RPSpell(this.counterAbility, this.getName(), this.getDescription(), this.getKeyAbility(), this.getMainAction(), this.getCheckFailed(), this.getCheckSucceeded(), this.getFinalOutcome());
        copy.setRank(characterLevel);
        copy.setDifficultyClass(characterLevel);
        return copy;
    }


    
    
}
