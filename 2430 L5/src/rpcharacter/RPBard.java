/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpcharacter;

import error.ElementNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.StringTokenizer;
import utils.Die;
import utils.DieRoller;
import rpinteract.RPSkill;
import rpinteract.RPSpell;

/**
 * <p>Character that can perform powerful physical attacks
 * and can posses a number of useful skills that they can
 * use on themselves or other Characters</p>
 * @author Maame Apenteng (0802637)
 * @version Nov/25/2013
 */
public class RPBard extends RPCharacter{
    
    final String characterClass = "BARD";
    final Die hitDie = new Die(8);
    private HashMap<String, RPSkill> skills;
    private HashMap<String, RPSpell> spells;
    /**
     * <p>Constructs a new RPFighter with their name and age initialized</p>
     * @param forename
     * @param surname
     * @param age 
     * @throws IllegalArgumentException
     * @see RPCharacter
     */
    public RPBard(String forename, String surname, int age) throws IllegalArgumentException{
        super(forename, surname, age);
        spells = new HashMap();
        skills = new HashMap();
    }
    
    /**
     * <p>Constructs a new RPFighter with all their instance variables initialized</p>
     * @param forename
     * @param surname
     * @param age
     * @param level
     * @param speed
     * @throws IllegalArgumentException 
     * @see RPCharacter
     */
    public RPBard(String forename, String surname, int age, int level, float speed) throws IllegalArgumentException{
        super(forename, surname, age, level, speed);
    }
    /**
     * <p>Retrieves the character class</p>
     * @return BARD
     */
    public String getCharacterClass(){
        return this.characterClass;
    }
    
    /**
     * 
     * @return list of skills the character has
     */
    public ArrayList<RPSkill> getSkillSet(){
        ArrayList<RPSkill> skillSet = new ArrayList();
        skillSet.addAll(skills.values());
        return skillSet;
    } 
    
    /**
     * Adds a spell to character's spell list
     * @param spell to be added
     * @return <code>true</code> if spell is successfully added, <code>false</code>otherwise
     */
    public ArrayList<RPSpell> getSpellSet(){
        ArrayList<RPSpell> spellSet = new ArrayList();
        spellSet.addAll(spells.values());
        return spellSet;
    }
    
    /**
     * <p>Simulates a character attempting to attack another character</p>
     * @param target the character to be attacked
     * @return String detailing whether character was successful or not
     */
    public String attack(RPCharacter target){
        //determine if attack successfully hit target
        Die attackDie = new Die(20);
        int attackSum = DieRoller.rollNormal(attackDie, 1) + (this.getAbility(this.strength)/4);
        int damage = (this.getAbility(this.strength)/8);
        if(attackSum >= target.getArmorClass()){
            //attack is a success
            //determine how much damage attack causes
            damage += DieRoller.rollNormal(hitDie, this.getLevel());
            return this.getName()+"'s attack succeeded. They caused "+damage+" damage to "+target.getName(); 
        }
        else{
            return this.getName()+"'s attack failed to land a hit.";
        }
    }
    
    /**
     * Checks to see if character has skill
     * @param name of the skill
     * @return RPSkill
     */
    public boolean hasSkill(String name){
        return skills.containsKey(name);
    }
    
    /**
      * Adds skill to skill set
      * @param skill to be given
      * @return <code>true</code> if skill is successfully added to skill set
      */
    public boolean receiveSkill(RPSkill skill){
        if(!hasSkill(skill.getName())){
            RPSkill addSkill = skill.createInstanceOf(this.getLevel());
            skills.put(skill.getName(), addSkill);
            return true;
        }
        return false;
    }
    
    /**
     * Removes a skill from the character's skill set
     * @param skill to be removed
     * @return <code>true</code> if skill is successfully removed, <code>false</code> otherwise
     */
    public boolean loseSkill(RPSkill skill){
        if(hasSkill(skill.getName())){
            skills.remove(skill.getName());
            return true;
        }
        return false;
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
     * Adds a spell to the character's spell list
     * @param spell to be added
     * @return <code>true</code> if spell is successfully added, <code>false</code> otherwise
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
     * <p>Simulates a character using a skill on another character</p>
     * @param target character that the skill will be used on
     * @return String
     */
    public String useSkill(String skillName) throws ElementNotFoundException{
        if(this.skills.containsKey(skillName)){
            return this.skills.get(skillName).perform(this);
        }
        throw new ElementNotFoundException(this.getName()+" does not have this skill and cannot use it.");
    } //end of useSkill method
    
     /**
     * <p>Simulates a character casting a spell on another character</p>
     * @param target character that the spell will be cast on
     * @return String
     */
    public String castSpell(RPCharacter target, String spell) throws ElementNotFoundException{
        if(this.spells.containsKey(spell)){
            return this.spells.get(spell).perform(this, target);
        }
        throw new ElementNotFoundException(this.getName()+" does not know this spell and cannot cast it.");
    } //end of castSpell method

    /**
     * <p>Converts character to a HashMap</p>
     * @return HashMap that represents the character
     */
    @Override
    public HashMap<String, String> toMap() {
        HashMap<String, String> charMap = new HashMap();
        charMap.put(RPCharacter.classKey, characterClass);
        if(!skills.isEmpty()){
            String skillValue = "";
            int i = 0;
            for(String s : skills.keySet()){
                skillValue += s;
                if(i >= 0 && i < skills.keySet().size() - 1){
                    skillValue += ", ";
                }
            }
            charMap.put(RPCharacter.skillKey, skillValue);
        }
        if(!spells.isEmpty()){
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
     * @return RPBard character
     */
    public static RPBard mapToCharacter(HashMap<String, String> map){
        RPBard character = new RPBard(map.get(RPCharacter.forenameKey), map.get(RPCharacter.surnameKey), Integer.valueOf(map.get(RPCharacter.ageKey)));
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
    
    /* Deal with spells and skills */
    
    /**
     * <p>Should be invoked immediately after mapToCharacter or initializeSpells</p>
     * @param skillSet
     * @param map 
     */
    public void initializeSkills(ArrayList<RPSkill> skillSet, HashMap<String, String> map){
        if(map.containsKey(RPCharacter.skillKey)){
            String skills = map.get(RPCharacter.skillKey);
            StringTokenizer skillNames = new StringTokenizer(skills);
            while(skillNames.hasMoreTokens()){
                boolean found = false;
                String name = skillNames.nextToken(",");
                for(int i = 0; i < skillSet.size(); i++){
                    if(name.equalsIgnoreCase(skillSet.get(i).getName())){
                        this.receiveSkill(skillSet.get(i));
                        found = true;
                        break;
                    } //end of if statement
                } //end of for loop
                if(!found){
                    throw new ElementNotFoundException("Sorry, could not find " + name + ".\n"+ this.getName() + " could not be given this skill.");
                }   
            } //end of while loop
        }//end of outer if statement
    }//end of initializeSkills method
    
     /**
     * <p>Should be invoked immediately after mapToCharacter or initializeSkills</p>
     * @param spellSet
     * @param map 
     */
    public void initializeSpells(ArrayList<RPSpell> spellSet, HashMap<String, String> map){
        if(map.containsKey(RPCharacter.spellKey)){
            String spells = map.get(RPCharacter.spellKey);
            StringTokenizer spellNames = new StringTokenizer(spells);
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
