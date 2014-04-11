/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpcharacter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import utils.Die;
import utils.DieRoller;
import rpinteract.RPWeapon;
import rpinteract.RPSkill;
import error.ElementNotFoundException;
/**
 * <p>Character that can perform powerful physical attacks
 * and can posses a number of useful skills that they can
 * use on themselves or other Characters</p>
 * @author Maame Apenteng (0802637)
 * @version Nov/25/2013
 */
public class RPFighter extends RPCharacter{
    
    final String characterClass = "FIGHTER";
    final Die hitDie = new Die(10);
    private RPWeapon weapon;
    private HashMap<String, RPSkill> skills;
    /**
     * <p>Constructs a new RPFighter with their name and age initialized</p>
     * @param forename
     * @param surname
     * @param age 
     * @throws IllegalArgumentException
     * @see RPCharacter
     */
    public RPFighter(String forename, String surname, int age) throws IllegalArgumentException{
        super(forename, surname, age);
        skills = new HashMap();
        weapon = null;
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
    public RPFighter(String forename, String surname, int age, int level, float speed) throws IllegalArgumentException{
        super(forename, surname, age, level, speed);
        skills = new HashMap();
        weapon = null;
    }
    
    /**
     * <p>Retrieves the character class</p>
     * @return FIGHTER
     */
    public String getCharacterClass(){
        return this.characterClass;
    }
    
    /**
     * <p>Simulates a character attempting to attack another character</p>
     * @param target the character to be attacked
     * @return String detailing whether character was successful or not
     */
    public String attack(RPCharacter target){
        //determine if attack successfully hit target
        Die attackDie = new Die(20);
        int attackSum = DieRoller.rollNormal(attackDie, 1) + (this.getAbility(strength)/4);
        int damage = (this.getAbility(strength)/8);
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
     * <p>Simulates a character using a skill on another character</p>
     * @param target character that the skill will be used on
     * @return String
     */
    public String useSkill(String skillName) throws ElementNotFoundException{
        if(this.skills.containsKey(skillName)){
            return this.skills.get(skillName).perform(this);
        }
        throw new ElementNotFoundException(this.getName()+" does not have this skill and cannot use it.");
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
     * <p>Makes the fighter wield the specified weapon (replaces
     * any existing weapon).
     * @param weapon Weapon to be wielded
     */
    public void equipWeapon(RPWeapon weapon){
        int currentScore = this.getAbility(weapon.getKeyAbility());
        this.weapon = weapon;
        this.setAbility(weapon.getKeyAbility(), currentScore + weapon.getEnhancement());
    }
    
    /**
     * <p>Discard the weapon currently wielded by the fighter.</p>
     */
    public void unequipWeapon(){
        if(weapon == null){
            return;
        }
        int currentScore = this.getAbility(weapon.getKeyAbility());
        this.setAbility(weapon.getKeyAbility(), currentScore - weapon.getEnhancement());
        this.weapon = null;
    }
    
    /**
     * <p>Gets the weapon currently wielded by the fighter</p>
     * @return name of the weapon
     */
    public String getWeapon(){
        if(weapon != null){
            return this.getName() + " is equipped with " + weapon.getName();
        }
        else{
            return this.getName() + " is not equipped with any weapon.";
        }
    }
    
    /**
     * Checks to see if character owns this weapon
     * @param name
     * @return <code>true</code> if character owns this weapon, <code>false</code>otherwise
     */
    public boolean ownWeapon(String name){
        if(weapon != null){
            return this.getName().equals(name);
        }
        return false;
    }
    
    /**
     * Checks to see if character is equipped with a weapon
     * @return <code>true</code>if character has a weapon, <code>false</code>otherwise
     */
    public boolean isArmed(){
        if(weapon == null){
            return false;
        }
        return true;
    }
    
    /**
     * <p>Converts character to a HashMap</p>
     * @return HashMap that represents the character
     */
    @Override
    public HashMap<String, String> toMap() {
        
        HashMap<String, String> charMap = new HashMap();
        charMap.put(RPCharacter.classKey, characterClass);
        if(weapon != null){
            charMap.put(RPCharacter.weaponKey, weapon.getName());
        }
        if(skills != null && !skills.isEmpty()){
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
        charMap.putAll(super.toMap());
        return charMap; //To change body of generated methods, choose Tools | Templates.
    }
    
   /**
     * <p>Converts a HashMap to a character<p>
     * @param map HashMap to be converted
     * @return RPFighter character
     */
    public static RPFighter mapToCharacter(HashMap<String, String> map){
        RPFighter character = new RPFighter(map.get(RPCharacter.forenameKey), map.get(RPCharacter.surnameKey), Integer.valueOf(map.get(RPCharacter.ageKey)));
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
    
    /*Deal with weapon and skills that need to be equipped*/
    
    /**
     * <p>Should be invoked immediately after mapToCharacter or initializeSkills</p>
     * @param weaponList the list of weapons available
     * @param character the HashMap of the character from file
     */
    public void initializeWeapon (ArrayList<RPWeapon> weaponList, HashMap<String, String> character) throws ElementNotFoundException{
        if(character.containsKey(RPCharacter.weaponKey)){
            boolean found = false;
            String weaponName = character.get(RPCharacter.weaponKey);
            for(int i = 0; i < weaponList.size(); i++){
                if(weaponList.get(i).getName().equalsIgnoreCase(weaponName)){
                    this.equipWeapon(weaponList.get(i));
                    found = true;
                    break;
                } //end of if statement
            } //end of for loop
            if(!found){
                throw new ElementNotFoundException("Sorry, could not find "+ character.get(RPCharacter.weaponKey)+ " in the list of weapons provided. /nWeapon cannot be equipped to "+ this.getName()+ ".");
                
            }
        } //end of outer if statement
        
    } //end of initializeWeapon menthod
    
    /**
     * <p>Should be invoked immediately after mapToCharacter or initializeWeapon</p>
     * @param skillSet
     * @param map 
     */
    public void initializeSkills(ArrayList<RPSkill> skillSet, HashMap<String, String> map) throws ElementNotFoundException{
        boolean found;
        if(map.containsKey(RPCharacter.skillKey)){
            String skill = map.get(RPCharacter.skillKey);
            StringTokenizer skillNames = new StringTokenizer(skill);
            while(skillNames.hasMoreTokens()){
                found = false;
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
        }
    }//end of initializeSkills method
}
