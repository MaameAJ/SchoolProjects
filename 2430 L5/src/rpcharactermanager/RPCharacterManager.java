package rpcharactermanager;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import utils.*;
import rpcharacter.*;
import rpinteract.*;
import error.MissingParameterException;
import error.DuplicateException;
import error.ElementNotFoundException;

/**
 * <p>The back end code of the RPCharacterInterface</p>
 * @author Maame Apenteng (0802637)
 * @version Nov/25/2013
 */
public class RPCharacterManager {
        
    /**
     *<p>where all the character are stored</p>
     */
    private ArrayList<RPCharacter> characterList;
    private ArrayList<RPWeapon> weaponSet;
    private ArrayList<RPSkill> skillSet;
    private ArrayList<RPSpell> spellSet;
    private FileIO charInfo;
    
    //Constructor
    /**
     * Constructs a new RPCharacterManager with all the instance  variables initialized
     * @param filename 
     */
    public RPCharacterManager(String filename) {
        charInfo = new FileIO(filename);
        characterList = new ArrayList();
        weaponSet = new ArrayList();
        spellSet = new ArrayList();
        skillSet = new ArrayList();
    }
    
    //Accessors

    /**
     * 
     * @return the list of characters
     */
    public ArrayList<RPCharacter> getCharacterList() {
        return characterList;
    }

    /**
     * 
     * @return the list of weapons
     */
    public ArrayList<RPWeapon> getWeaponSet() {
        return weaponSet;
    }

    /**
     * 
     * @return the list of skills
     */
    public ArrayList<RPSkill> getSkillSet() {
        return skillSet;
    }

    /**
     * 
     * @return the list of spells
     */
    public ArrayList<RPSpell> getSpellSet() {
        return spellSet;
    }
    
    
   /**
    * Adds a character to the characterList
    * @param e character to be edited
    * @return <code>true</code> if character is successfully added, <code>false</code> otherwise
    * @throws MissingParameterException if character is null
    * @throws DuplicateException if a character with the same name already exists in the characterList
    */
    protected boolean create(RPCharacter e) throws MissingParameterException, DuplicateException{
        if(e == null){
            throw new MissingParameterException("This is not a character.");
        }
        for(int i = 0; i < characterList.size(); i++){
            if(e.isIdentical(characterList.get(i))){
                throw new DuplicateException("\nI'm sorry but a character with this name already exists.");
            } 
        } //end of loop to check for existing character
        return characterList.add(e);
    }
    
    protected boolean create(String name, String keyAbility, int enhancement) throws DuplicateException{
        if(weaponSet == null){
            weaponSet = new ArrayList();
        }
        RPWeapon newWeapon = new RPWeapon(name, keyAbility, enhancement);
        for(RPWeapon w : weaponSet){
            if(w.isIdentical(newWeapon)){
               throw new DuplicateException("Sorry, this weapon already exists.\n");
            }
        }
        return weaponSet.add(newWeapon);
    } //end of create method for weapon
    
    protected boolean create(String name, String description, String keyAbility, String mainAction, String checkFailed, String checkSucceeded, String finalOutcome) throws DuplicateException{
        if(skillSet == null){
            skillSet = new ArrayList();
        }
        RPSkill newSkill = new RPSkill(name, description, keyAbility, mainAction, checkFailed,checkSucceeded, finalOutcome);
        for(RPSkill s : skillSet){
            if(s.isIdentical(newSkill)){
                throw new DuplicateException("Sorry, this item already exists.");
            }
        }
        return skillSet.add(newSkill);
        
    } //end of RPSkill create
    
    protected boolean create(String counterAbility, String name, String description, String keyAbility, String mainAction, String checkFailed, String checkSucceeded, String finalOutcome) throws DuplicateException{
        if(spellSet == null){
            spellSet = new ArrayList();
        }
        RPSpell newSpell = new RPSpell(counterAbility, name, description, keyAbility, mainAction, checkFailed,checkSucceeded, finalOutcome);
        for(RPSkill s : spellSet){
            if(s.isIdentical(newSpell)){
                throw new DuplicateException("Sorry, this item already exists.");
            }
        }
        return spellSet.add(newSpell);
    } //end of RPSpell create
    
    /**
     * Edits a character
     * @param toEdit character to be edited
     * @param surname character's new last name
     * @param firstName character's new first name
     * @param age character's new age
     * @param level character's new level
     * @param speed character's new speed
     * @param abilities character's new abilities
     * @return String detailing the success of the edit
     * @throws ElementNotFoundException if character is not found inside the characterList
     */
    protected String edit(RPCharacter toEdit, String surname, String firstName, int age, int level, float speed, HashMap<String, String> abilities) throws ElementNotFoundException{
        int index = characterList.indexOf(toEdit);
        
        if(index < 0){
            throw new ElementNotFoundException("Sorry. This character is not in the database and therefore cannot be edited. \nThis character must be created.");
        }
        else{
            characterList.remove(index);
        }
        
        if(!firstName.isEmpty()){
            toEdit.setFirstName(firstName);
        }

        if(!surname.isEmpty()){
            toEdit.setLastName(surname);
        }

      
        toEdit.setAge(age);
        toEdit.setLevel(level);
        toEdit.setSpeed(speed);
        
        for(String e : abilities.keySet()){
            toEdit.setAbility(e, Integer.parseInt(abilities.get(e)));
        }
        
        characterList.add(index, toEdit);
        return "Character successully edited!";
    } //end of edit method
    
    /**
     * Gives character a skill from the skill list
     * @param toEdit character to be given the skill
     * @param name of the skill to be given
     * @return <code>true</code> if skill is successfully received, <code>false</code> otherwise
     * @throws ElementNotFoundException if character or skill are not in respective ArrayLists
     * @throws IllegalArgumentException if character is not an RPFighter or RPBard
     */
    protected boolean giveSkill(RPCharacter toEdit, String name) throws ElementNotFoundException{
        boolean exists = false;
        RPSkill toGive = null;
        for(int i = 0; i < skillSet.size(); i++){
            if(skillSet.get(i).getName().equalsIgnoreCase(name)){
                exists = true;
                toGive = skillSet.get(i);
                break;
            }//end of if statement
        }//end of for loop
        if(!exists){
            throw new ElementNotFoundException("Sorry. This skill is not in the database and therefore cannot be given.");
        }
        if(toEdit instanceof RPBard){
            RPBard enhance = (RPBard) toEdit;
            enhance.receiveSkill(toGive);
        }
        else if(toEdit instanceof RPFighter){
            RPFighter enhance = (RPFighter) toEdit;
            enhance.receiveSkill(toGive);
        }
        else{
            return !exists;
        }
        
        return exists;
    }
    
    /**
     * Teaches a character a spell from the spell list
     * @param toEdit character to be taught the spell
     * @param name of the spell to be taught
     * @return <code>true</code> if spell is successfully learned, <code>false</code> otherwise
     * @throws ElementNotFoundException if character or spell are not in respective ArrayLists
     * @throws IllegalArgumentException if character is not an RPWizard or RPBard
     */
    protected String teachSpell(RPCharacter toEdit, String name) throws ElementNotFoundException, IllegalArgumentException{
        boolean exists = false;
        RPSpell toGive = null;
        for(int i = 0; i < spellSet.size(); i++){
            if(spellSet.get(i).getName().equalsIgnoreCase(name)){
                exists = true;
                toGive = spellSet.get(i);
                break;
            }//end of if statement
        }//end of for loop
        if(!exists){
            throw new ElementNotFoundException("Sorry. This spell is not in the database and therefore cannot be taught.");
        }
        if(toEdit instanceof RPBard){
            RPBard enhance = (RPBard) toEdit;
            if(enhance.learnSpell(toGive)){
                return toEdit.getName()+ "has successfully learned "+ toGive.getName();
            }
        }
        else if(toEdit instanceof RPWizard){
            RPWizard enhance = (RPWizard) toEdit;
            if(enhance.learnSpell(toGive)){
                return toEdit.getName()+ "has successfully learned "+ toGive.getName();
            }
        }
        else{
            throw new IllegalArgumentException("This character cannot learn spells.");
        }
        return toEdit.getName() + " was unable to learn "+ toGive.getName();
    }
    
    /**
     * Equips a character with a weapon from the weapon list
     * @param toEdit character to be given the weapon
     * @param name of the weapon to be equipped
     * @return <code>true</code> if weapon is successfully equipped, <code>false</code> otherwise
     * @throws ElementNotFoundException if character or weapon are not in respective ArrayLists
     * @throws IllegalArgumentException if character is not an RPFighter
     */
    protected boolean giveWeapon(RPCharacter toEdit, String name) throws ElementNotFoundException, IllegalArgumentException{
        boolean exists = false, equip = true;
        int index = characterList.indexOf(toEdit);
        if(index < 0){
            throw new ElementNotFoundException("Sorry, I cannot find this character.");
        }
        else{
            characterList.remove(index);
        }
        RPWeapon toGive = null;
        for(int i = 0; i < weaponSet.size(); i++){
            if(weaponSet.get(i).getName().equalsIgnoreCase(name)){
                exists = true;
                toGive = weaponSet.get(i);
                break;
            }//end of if statement
        }//end of for loop
        if(name.equalsIgnoreCase("none")){
            equip = false;
        }
        else if(!exists){
            throw new ElementNotFoundException("Sorry. This weapon is not in the database and therefore cannot be equipped.");
        }
        if(toEdit instanceof RPFighter){
            RPFighter enhance = (RPFighter) toEdit;
            if(equip){
                enhance.equipWeapon(toGive);
            }
            else{
                enhance.unequipWeapon();
            }
            characterList.add(index, toEdit);
        }
        else{
            throw new IllegalArgumentException("This character cannot cannot be equipped weapons.");
        }
        
        return exists;
    } 
    
    /**
     * <p>Prompts user for keywords and prints all characters that match any given keyword</p>
     */
    protected String search(String keywords){
        String currentToken, results;
        StringTokenizer searchQuery;
        ArrayList<RPCharacter> matches = new ArrayList();
        RPCharacter currentChar;
        boolean found;
        
        searchQuery = new StringTokenizer(keywords);
        while(searchQuery.hasMoreTokens()){
            currentToken = searchQuery.nextToken();
            for(int i = 0; i < characterList.size(); i++){
                currentChar = characterList.get(i);
                if(currentToken.equalsIgnoreCase(currentChar.getFirstName()) || currentToken.equalsIgnoreCase(currentChar.getLastName())){
                    found = false;
                    for(int j = 0;j < matches.size(); j++){
                        if(matches.get(j).equals(currentChar)){
                            found = true;
                            break;
                        }//end of if statement
                    }//end of for loop
                    if(!found){
                        matches.add(currentChar);
                    }//end of inner if statment
                } //end of name match if statement
            } //end of for loop
        } //end of while loop
        results = "Your keywords matched "+ matches.size()+" results.\n";
        if(!matches.isEmpty()){
            results += print(matches);
        } //end of if statement
        
        return results;
    } //end of search method
    
    /**
     * Finds a character based on name
     * @param name of the character to be found
     * @return RPCharacter if character is found, null otherwise
     */
    protected RPCharacter find(String name){
        for(int i = 0; i < characterList.size(); i++){
            if(characterList.get(i).getName().equals(name)){
                return characterList.get(i);
            }
        }
        return null;
    } //end of search method
    
    /**
     * <p>Generates and prints out the ability scores for a character</p>
     */
    protected static String generate(RPCharacter toGenerate) throws MissingParameterException{
        if(toGenerate == null){
            throw new MissingParameterException("Invalid character.");
        }
        for(String key : toGenerate.getAbilities().keySet()){
            toGenerate.setAbility(key, DieRoller.generateAbilityScoreStandard());
        }
        String returnValue = "\n"+toGenerate.getFirstName()+"'s abilities are now at the following values:";
        returnValue += "\nCharisma: " + toGenerate.getAbility(RPCharacter.charisma);
        returnValue += "\nConstitution: " + toGenerate.getAbility(RPCharacter.constitution);
        returnValue += "\nDexterity: " + toGenerate.getAbility(RPCharacter.dexterity);
        returnValue += "\nIntelligence: " + toGenerate.getAbility(RPCharacter.intelligence);
        returnValue += "\nStrength: " + toGenerate.getAbility(RPCharacter.strength);
        returnValue += "\nWisdom: " + toGenerate.getAbility(RPCharacter.wisdom);
        
        return returnValue;
    } //end of generate method
    
    /**
     * Deletes a character from the database
     * @param name of the character to be deleted
     * @return String detailing whether deletion was successfully
     */
    protected String deleteCharacter(String name){
        RPCharacter toDel = find(name);
        if(toDel != null && characterList.remove(toDel)){
            return toDel.getName()+" has been successfully deleted.";
        }
        return "I couldn't find "+name+"! Maybe they've already been deleted?";
    }
    
    /**
     * Deletes a skill from the ArrayList
     * @param name the name of the skill to be deleted
     * @param everywhere if <code>true</code> then deletes the spell and all characters that have the skill, lose it
     * @return String detailing whether deletion was a success or not
     */
    protected String deleteSkill(String name, boolean everywhere){
        ArrayList<String> affected;
        RPSkill toDel = null;
        for(int i = 0; i < skillSet.size(); i++){
            if(skillSet.get(i).getName().equals(name)){
                toDel = skillSet.get(i);
            }
        }
        if(toDel != null){
            affected = new ArrayList();
            if(everywhere){
                for(RPCharacter c : characterList){
                    if(c instanceof RPBard){
                        RPBard bard = (RPBard) c;
                        if(bard.loseSkill(toDel)){
                            affected.add(bard.getName());
                        } //end of if skill deleted successfully
                    }
                    else if(c instanceof RPFighter){
                        RPFighter fight = (RPFighter) c;
                        if(fight.loseSkill(toDel)){
                            affected.add(fight.getName());
                        } //end of if skill deleted successfully
                    }
                } //end of for-each loop characters
            }
            if(skillSet.remove(toDel)){
                String output = toDel.getName()+" has been successfully deleted.";
                if(everywhere){
                    output += "\nThe following characters were affected ";
                    for(int i = 0; i < affected.size(); i++){
                        output += affected.get(i);
                        if(i != affected.size() - 1){
                            output += ", ";
                        } //if not the last element
                    } //end of for loop
                    return output += ".";
                } //end of if delete from everywhere
            }
        }
        return "I couldn't find "+name+"! Maybe it's already been deleted?";
    }
    
    /**
     * Deletes a spell from the ArrayList
     * @param name the name of the spell to be deleted
     * @param everywhere if <code>true</code> then deletes the spell and all characters that know the spell, forget it
     * @return String detailing whether deletion was a success or not
     */
    protected String deleteSpell(String name, boolean everywhere){
        ArrayList<String> affected;
        RPSpell toDel = null;
        for(int i = 0; i < spellSet.size(); i++){
            if(spellSet.get(i).getName().equals(name)){
                toDel = spellSet.get(i);
            }
        }
        if(toDel != null){
            affected = new ArrayList();
            if(everywhere){
                for(RPCharacter c : characterList){
                    if(c instanceof RPBard){
                        RPBard bard = (RPBard) c;
                        if(bard.forgetSpell(toDel)){
                            affected.add(bard.getName());
                        } //end of if skill deleted successfully
                    }
                    else if(c instanceof RPWizard){
                        RPWizard wiz = (RPWizard) c;
                        if(wiz.forgetSpell(toDel)){
                            affected.add(wiz.getName());
                        } //end of if skill deleted successfully
                    }
                } //end of for-each loop characters
            }
            if(spellSet.remove(toDel)){
                String output = toDel.getName()+" has been successfully deleted.";
                if(everywhere){
                    output += "\nThe following characters were affected ";
                    for(int i = 0; i < affected.size(); i++){
                        output += affected.get(i);
                        if(i != affected.size() - 1){
                            output += ", ";
                        } //if not the last element
                    } //end of for loop
                    return output += ".";
                } //end of if delete from everywhere
            }
        }
        return "I couldn't find "+name+"! Maybe it's already been deleted?";
    }
    
    /**
     * Deletes a weapon
     * @param name the name of the weapon to be deleted
     * @param everywhere if <code>true</code> deletes weapon and une-quips it from all characters that are equipped with it
     * @return String detailing if deletion was successfully or not
     */
    protected String deleteWeapon(String name, boolean everywhere){
        ArrayList<String> affected;
        RPWeapon toDel = null;
        for(int i = 0; i < weaponSet.size(); i++){
            if(weaponSet.get(i).getName().equals(name)){
                toDel = weaponSet.get(i);
            }
        }
        if(toDel != null){
            affected = new ArrayList();
            if(everywhere){
                for(RPCharacter c : characterList){
                    if(c instanceof RPFighter){
                        RPFighter fight = (RPFighter) c;
                        if(fight.ownWeapon(toDel.getName())){
                            fight.unequipWeapon();
                            affected.add(fight.getName());
                        } //end of if skill deleted successfully
                    }
                } //end of for-each loop characters
            }
            if(weaponSet.remove(toDel)){
                String output = toDel.getName()+" has been successfully deleted.";
                if(everywhere){
                    output += "\nThe following characters were affected ";
                    if(affected.isEmpty()){
                        output += " none";
                    }
                    for(int i = 0; i < affected.size(); i++){
                        output += affected.get(i);
                        if(i != affected.size() - 1){
                            output += ", ";
                        } //if not the last element
                    } //end of for loop
                    return output += ".";
                } //end of if delete from everywhere
            }
        }
        return "I couldn't find "+name+"! Maybe they've already been deleted?";
    }
    

    /* print methods */
    /**
     * <p>Prints out all the characters in the database</p>
     */
    private static String print(ArrayList<RPCharacter> characterList){
        if(characterList.isEmpty()){
            return "\nThere are no characters to print.";
        }
        String output = "\n";
        for(int i = 0; i < characterList.size(); i++){
            output += characterList.get(i)+"\n";
        }
        return output;
    } //end of print method
    
    protected String printAll(){
        return print(characterList);
    }
    
    
    /* simulation methods */
    /**
     * Selects two random character
     * @return selected character
     * @throws MissingParameterException if there aren't enough character
     */
    protected RPCharacter[] selectRandom() throws MissingParameterException{
        RPCharacter [] pair = new RPCharacter[2];
        RPCharacter charA;
        RPCharacter charB;
        int getA, getB;
        boolean needDiff;
        if(characterList.size() < 2){
            throw new MissingParameterException("Sorry, not enough characters to complete the simulation.");
        }
        do{
            getA = (int)Math.round(Math.random()*(characterList.size() - 1));
            getB = (int)Math.round(Math.random()*(characterList.size() - 1));
            if(getA == getB){
                needDiff = true;
            }
            else{
                needDiff = false;
            }
        }while(needDiff);
        
        pair[0] = characterList.get(getA);
        pair[1] = characterList.get(getB);
        
        return pair;
    }
    
    /**
     * Runs a simulation
     * @param charA first character selected
     * @param charB second character selected
     * @return String detailing simulation information
     */
    protected String startSim(RPCharacter charA, RPCharacter charB){
        int direction;
        String walkWay;
        String output = charA.getName()+" and "+charB.getName()+" have been selected.\n";
        for(int i = 0; i < 2; i++){
            direction = (int)Math.random()*7 + 1;
            switch(direction){
                case 1:
                    walkWay = "north";
                    break;
                case 2:
                    walkWay = "northeast";
                    break;
                case 3:
                    walkWay = "east";
                    break;
                case 4:
                    walkWay = "southeast";
                    break;
                case 5:
                    walkWay = "south";
                    break;
                case 6:
                    walkWay = "southwest";
                    break;
                case 7:
                    walkWay = "west";
                    break;
                default:
                    walkWay = "north-northeast";
                    break;
            }
            if(i == 0){
                output += charA.walk(walkWay) +"\n";
            }
            else{
                output += charB.run(walkWay) + "\n";
            }
        }
        return output;
    }
    
    /**
     * Runs a simulation
     * @param fightA owner of the skill
     * @param skill skill to be used
     * @param charB target of the attack
     * @return 
     */
    protected String simulate(RPFighter fightA, String skill, RPCharacter charB){
        String output = fightA.attack(charB) + "\n";
        if(skill != null && !skill.isEmpty()){
            output += fightA.useSkill(skill) + "\n";
        }
        return output;
    }
    
    /**
     * Runs a simulation
     * @param bardA owner of skill and spell
     * @param skill skill to be used
     * @param spell spell to be cast
     * @param charB target of spell
     * @return String detailing simulation information
     */
    protected String simulate(RPBard bardA, String skill, String spell, RPCharacter charB){
        String output = bardA.attack(charB) + "\n";
        if(skill != null && !skill.isEmpty()){
            output += bardA.useSkill(skill);
        }
        if(spell != null && !spell.isEmpty()){
            output += bardA.castSpell(charB, spell);
        }
        return output;
    }
    
    /**
     * Runs a simulation
     * @param wizA owner of the spell
     * @param spell spell to be cast
     * @param charB target of the spell
     * @return String detailing simulation information
     */
    protected String simulate(RPWizard wizA, String spell, RPCharacter charB){
        if(spell == null || spell.isEmpty()){
            return wizA.getName() + " cannot do anything!\n";
        }
        else{
            return wizA.castSpell(charB, spell) + "\n";
        }
    }
    
    /**
     * Loads data
     * @return goodbye message
     */
    protected String finish(){
        saveAll();
        return "\nGoodbye then! Have a nice day!";
    }
    
    /**
     * Saves all data to file
     */
    protected void saveAll(){
            PrintWriter save = charInfo.getWriteFile();
            HashMap charMap;
            if(weaponSet != null){
                for(RPWeapon weapon : weaponSet){
                    save.println("[weapon]");
                    charMap = weapon.toMap();
                    charInfo.saveItem(charMap);
                }
            }
            if(skillSet != null){
                for(RPSkill skill : skillSet){
                    save.println("[skill]");
                    charMap = skill.toMap();
                    charInfo.saveItem(charMap);
                }
            }
            if(spellSet != null){
                for(RPSpell spell : spellSet){
                    save.println("[spell]");
                    charMap = spell.toMap();
                    charInfo.saveItem(charMap);
                }
            }
            if(characterList != null){
                for(RPCharacter RPChar : characterList){
                     save.println("[character]");
                     if(RPChar instanceof RPBard){
                         RPBard currentChar = (RPBard)RPChar;
                         charMap = currentChar.toMap();
                         charInfo.saveItem(charMap);
                     }
                     else if(RPChar instanceof RPWizard){
                         RPWizard currentChar = (RPWizard)RPChar;
                         charMap = currentChar.toMap();
                         charInfo.saveItem(charMap);
                     }
                     else if(RPChar instanceof RPFighter){
                         RPFighter currentChar = (RPFighter)RPChar;
                         charMap = currentChar.toMap();
                         charInfo.saveItem(charMap);
                     }
                } //end of RPCharacter for loop
            }
            save.close();
    }
    
    /**
     * Loads in data from file
     */
    protected void readAll(){
        Scanner read = this.charInfo.getReadFile();
        String currentLine;
        HashMap charInfo;
        if(weaponSet == null){
            weaponSet = new ArrayList();
        }
        if(skillSet == null){
            skillSet = new ArrayList();
        }
        if(spellSet == null){
            spellSet = new ArrayList();
        }
        readLoop:
        while(read.hasNextLine()){
           currentLine = read.nextLine();
            if(currentLine.equals("[weapon]")){
                RPWeapon currentWeapon;
                charInfo = this.charInfo.readItem();
                currentWeapon = RPWeapon.mapToWeapon(charInfo);
                weaponSet.add(currentWeapon);
            }
            else if(currentLine.equals("[skill]")){
                RPSkill current;
                charInfo = this.charInfo.readItem();
                current = RPSkill.mapToSkill(charInfo);
                skillSet.add(current);
            }
            else if(currentLine.equals("[spell]")){
                RPSpell current;
                charInfo = this.charInfo.readItem();
                current = RPSpell.mapToSpell(charInfo);
                spellSet.add(current);
            }
            else if(currentLine.equals("[character]")){
                RPCharacter currentChar;
                charInfo = this.charInfo.readItem();
                if(charInfo.get(RPCharacter.classKey).toString().equalsIgnoreCase("Bard")){
                    currentChar = RPBard.mapToCharacter(charInfo);
                    RPBard current = (RPBard) currentChar;
                    current.initializeSkills(skillSet, charInfo);
                    current.initializeSpells(spellSet, charInfo);
                    characterList.add(current);
                }
                else if(charInfo.get(RPCharacter.classKey).toString().equalsIgnoreCase("Wizard")){
                    currentChar = RPWizard.mapToCharacter(charInfo);  
                    RPWizard current = (RPWizard) currentChar;
                    current.initializeSpells(spellSet, charInfo);
                    characterList.add(current);
                }
                else if(charInfo.get(RPCharacter.classKey).toString().equalsIgnoreCase("Fighter")){
                    currentChar = RPFighter.mapToCharacter(charInfo);
                    RPFighter current = (RPFighter) currentChar;
                    current.initializeWeapon(weaponSet, charInfo);
                    current.initializeSkills(skillSet, charInfo);
                    characterList.add(currentChar);
                 }
            }//end of if statement  
        } //end of while loop
        read.close();
    } //end of readAll method
    
} //end of RPCharacter manager class
