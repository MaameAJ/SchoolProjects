/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpcharacter;

import error.ElementNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import utils.Die;
import rpinteract.RPSpell;
/**
 * <p>Character that can cast powerful on itself or other characters</p>
 * @author Maame Apenteng (0802637)
 * @version Nov/25/2013
 */
public class RPWizard extends RPCharacter{
    
    final String characterClass = "WIZARD";
    final Die hitDie = new Die(6);
    private HashMap<String, RPSpell> spells;
    /**
     * <p>Constructs a new RPWizard with their name and age initialized</p>
     * @param forename
     * @param surname
     * @param age 
     * @throws IllegalArgumentException
     * @see RPCharacter
     */
    public RPWizard(String forename, String surname, int age) throws IllegalArgumentException{
        super(forename, surname, age);
        spells = new HashMap();
    }
    
    /**
     * 
     * @param forename
     * @param surname
     * @param age
     * @param level
     * @param speed
     * @throws IllegalArgumentException 
     * @see RPCharacter
     */
    public RPWizard(String forename, String surname, int age, int level, float speed) throws IllegalArgumentException{
        super(forename, surname, age, level, speed);
        spells = new HashMap();
    }
    
    /**
     * <p>Retrieves the character class</p>
     * @return WIZARD
     */
    public String getCharacterClass(){
        return this.characterClass;
    }
    
    /**
     * 
     * @return list of spells the character knows
     */
    public ArrayList<RPSpell> getSpellSet(){
        ArrayList<RPSpell> spellSet = new ArrayList();
        spellSet.addAll(spells.values());
        return spellSet;
    }
    
    /**
     * Checks to see if character knows the spell
     * @param name spell to be checked
     * @return <code>true</code> if spell is known, <code>false</code> otherwise
     */
    public boolean knowsSpell(String name){
        return spells.containsKey(name);
    }
    
    /**
     * Adds a spell to character's spell list
     * @param spell to be added
     * @return <code>true</code> if spell is successfully added, <code>false</code>otherwise
     */
    public boolean learnSpell(RPSpell spell){
        if(!knowsSpell(spell.getName())){
            RPSpell addSpell = spell.createInstanceOf(this.getLevel());
            spells.put(spell.getName(), addSpell);
            return true;
        }
        return false;
    }
    
     /**
     * Removes a spell from the character's spell list
     * @param spell to be removed
     * @return <code>true</code> if spell is successfully removed, <code>false</code> otherwise
     */
    public boolean forgetSpell(RPSpell spell){
        if(knowsSpell(spell.getName())){
           spells.remove(spell.getName());
           return true;
        }
        return false;
    }
    
    /**
     * <p>Simulates a character casting a spell on another character</p>
     * @param target character that the spell will be cast on
     * @return String
     */
    public String castSpell(RPCharacter target, String spell) throws ElementNotFoundException{
        if(this.spells.containsKey(spell)){
            return this.spells.get(spell).perform(this, target);
        }
        throw new ElementNotFoundException(this.getName() + " does not know this spell and cannot cast it.");
    }
    
    /**
     * <p>Converts character to a HashMap</p>
     * @return HashMap that represents the character
     */
    @Override
    public HashMap<String, String> toMap() {
        HashMap<String, String> charMap = new HashMap();
        charMap.put(RPCharacter.classKey, characterClass);
        if(spells != null && !spells.isEmpty()){
            String spellValue = "";
            int i = 0;
            for(String s : spells.keySet()){
                spellValue += s;
                if(i >= 0 && i < spells.keySet().size() - 1){
                    spellValue += ", ";
                }
            }
            charMap.put(RPCharacter.spellKey, spellValue);
        }
        charMap.putAll(super.toMap());
        return charMap; //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * <p>Converts a HashMap to a character<p>
     * @param map HashMap to be converted
     * @return RPWizard character
     */
    public static RPWizard mapToCharacter(HashMap<String, String> map){
        RPWizard character = new RPWizard(map.get(RPCharacter.forenameKey), map.get(RPCharacter.surnameKey), Integer.valueOf(map.get(RPCharacter.ageKey)));
        character.setLevel(Integer.valueOf(map.get(RPCharacter.levelKey)));
        character.setSpeed(Float.valueOf(map.get(RPCharacter.speedKey)));
        character.setAbility(RPCharacter.strength, Integer.valueOf(map.get(RPCharacter.strength)));
        character.setAbility(RPCharacter.charisma, Integer.valueOf(map.get(RPCharacter.charisma)));
        character.setAbility(RPCharacter.intelligence, Integer.valueOf(map.get(RPCharacter.intelligence)));
        character.setAbility(RPCharacter.dexterity, Integer.valueOf(map.get(RPCharacter.dexterity)));
        character.setAbility(RPCharacter.constitution, Integer.valueOf(map.get(RPCharacter.constitution)));
        character.setAbility(RPCharacter.wisdom, Integer.valueOf(map.get(RPCharacter.wisdom)));
        return character;
    }
    
    /**
     * <p>Should be invoked immediately after mapToCharacter</p>
     * @param spellSet
     * @param map 
     */
     public void initializeSpells(ArrayList<RPSpell> spellSet, HashMap<String, String> map) throws ElementNotFoundException{
        if(map.containsKey(RPCharacter.spellKey)){
            String spell = map.get(RPCharacter.spellKey);
            StringTokenizer spellNames = new StringTokenizer(spell);
            while(spellNames.hasMoreTokens()){
                boolean found = false;
                String name = spellNames.nextToken(",");
                for(int i = 0; i < spellSet.size(); i++){
                    if(name.equalsIgnoreCase(spellSet.get(i).getName())){
                        this.learnSpell(spellSet.get(i));
                        found = true;
                        break;
                    } //end of if statement
                } //end of for loop
                if(!found){
                    throw new ElementNotFoundException("Sorry, could not find " + name + ".\n"+ this.getName() + " could not be taught this spell.");
                }
            } //end of while loop
        }//end of outer if statement
    }//end of initializeSkills method
}
