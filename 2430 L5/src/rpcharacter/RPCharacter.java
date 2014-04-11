
package rpcharacter;
import error.InvalidNameException;
import error.MissingParameterException;
import java.util.HashMap;
/**
 * <p>Representation of a character that can be used in a role-playing game.</p>
 * 
 * @author Maame Apenteng (0802637)
 * @version Nov/25/2013
 */
public class RPCharacter {

    /*
     *
     * -------------------------
     * HASHMAP KEY CONSTANTS
     * -------------------------
     */
        
    /**
     * <p>the constant used in the hashmap of the character for the key class</p>
     */
    final public static String classKey = "class";
    
    /**
     * <p>the constant used in the hashmap of the character for the key first name</p>
     */
    public static final String forenameKey = "firstName";
    
    /**
     * <p>the constant used in the hashmap of the character for the key last name</p>
     */
    public static final String surnameKey = "lastName";
    
    /**
     * <p>the constant used in the hashmap of the character for the key age</p>
     */
    public static final String ageKey = "age";
    
    /**
     * <p>the constant used in the hashmap of the character for the key level</p>
     */
    public final static String levelKey = "level";
    
    /**
     * <p>the constant used in the hashmap of the character for the key speed</p>
     */
    public final static String speedKey = "speed";
    
     // Credits - The following ability descriptions were taken straight from the Pathfinder Core Rulebook
    /**
     * <p>the constant used as the key for the character's muscle and physical power.</p>
     */
    public final static String strength = "STR";
    
    /**
     * <p>the constant used as the key for the character's agility, reflexes and balance.</p>
     */
    public final static String dexterity = "DEX";
    /**
     * <p>the constant used as the key for the character's stamina, endurance and vitality.</p>
     */
    public final static String constitution = "CON";
    
    /**
     * <p>the constant used as the key for the character's intellect, brains, mind and knowledge.</p>
     */
    public final static String intelligence = "INT";
    
    /**
     * <p>the constant used as the key for the character's spirit, wit, psyche and sense.</p>
     */
    public final static String wisdom = "WIS";
    
    /**
     * <p>the constant used as the key for the character's presence, charm and social skills.</p>
     */
    public final static String charisma = "CHA";
    
    public final static String weaponKey = "weapon";
    public final static String skillKey = "skill";
    public final static String spellKey = "spell";
    
    /*
     * -------------------------
     * INSTANCE/MEMBER VARIABLES
     * -------------------------
     */

    
    /**
     * <p>First name of the character.</p>
     */
    private String firstName;
    
    /**
     * <p>Last name of the character.</p>
     */
    private String lastName;

    /**
     * <p>Age of the character (must be at least 5 years old).</p>
     */
    private int age;
    
    /**
     * 
     */
    private HashMap<String, String> abilities;
   
    
    /**
     * <p>the general level of power and skill of the character
     * (between 1 and 18)</p>
     */
    private int level;

    
    /**
     * <p>Character's base speed; how much distance (in feet) the
     * character covers in one move/minute</p>
     */
    private float speed;
    
    
    /**
     * <p>Represents how hard it is to hit 
     * the character upon being attacked</p>
     */
    private int armorClass;
    /*
     * ------------
     * CONSTRUCTORS
     * ------------
     */
    /**
     * <p>Constructor that creates a character with given first name, last name and age.
     * Note that all attributes are defaulted to 0. </p>
     * @param firstName cannot contain characters other than alphabetical letters, hyphens and apostrophes
     * @param lastName cannot contain characters other than alphabetical letters, hyphens and apostrophes
     * @param age cannot be less than 5
     * @throws IllegalArgumentException if if any of the parameters violate the above conditions
     */
    public RPCharacter(String firstName, String lastName, int age) throws IllegalArgumentException, InvalidNameException, MissingParameterException {
        if(firstName == null || lastName == null || firstName.isEmpty() || lastName.isEmpty()){
            throw new MissingParameterException("First name and last name fields cannot be empty");
        }
        
        if(!InvalidNameException.isValid(firstName)){
            throw new InvalidNameException();
        }
        if(!InvalidNameException.isValid(lastName)){
            throw new InvalidNameException();
        }
        
        this.firstName = firstName;
        this.lastName = lastName;
        
        if(age >= 5){
            this.age = age;
        }
        else{
            throw new IllegalArgumentException("Invalid age: age must be greater than 5.");
        }
        abilities = new HashMap();
        abilities.put(wisdom, Integer.toString(0));
        abilities.put(strength, Integer.toString(0));
        abilities.put(charisma, Integer.toString(0));
        abilities.put(dexterity, Integer.toString(0));
        abilities.put(intelligence, Integer.toString(0));
        abilities.put(constitution, Integer.toString(0));
    } //end of construct with parameters
    
    /**
     * <p>Constructor that sets all the instance variables</p>
     * @param firstName cannot contain characters other than alphabetical letters, hyphens and apostrophes
     * @param lastName cannot contain characters other than alphabetical letters, hyphens and apostrophes
     * @param age cannot be less than 5
     * @param level must be between 1 and 18 inclusive
     * @param speed
     * @throws IllegalArgumentException if any of the parameters violate the above conditions
     */
    public RPCharacter(String firstName, String lastName, int age, int level, float speed) throws IllegalArgumentException, InvalidNameException, MissingParameterException{
        this(firstName, lastName, age);
        
        if(level < 1 || level > 18){
            throw new IllegalArgumentException("Invalid level: level must be between 1 and 18 inclusive.");
        }
        else{
            this.level = level;
        }
        this.speed = speed;
    }
    
    /*
     * --------
     * MUTATORS
     * --------
     */
    
    /**
     * <p>Sets the first name of the character<p>
     * <p><b>Note:</b> first name can only contain alphabetical characters, hyphens and apostrophes<p>
     * @param firstName
     * @return <code>true</code> if first name is successfully set, <code>false</code> otherwise
     */
    public boolean setFirstName(String firstName) {
        if(!InvalidNameException.isValid(firstName)){
            System.out.println("Sorry this is not a valid first name.");
            System.out.println("First names are only allowed alphabetical characters, hyphens and apostrophes");
            return false;
        }
        this.firstName = firstName;
        return true;
    }

    /**
     * <p>Sets the last name of the character<p>
     * <p><b>Note:</b> last name can only contain alphabetical characters, hyphens and apostrophes<p>
     * @param lastName
     * @return <code>true</code> if name is successfully set, <code>false</code>otherwise
     */
    public boolean setLastName(String lastName) {  
        if(!InvalidNameException.isValid(lastName)){
            System.out.println("Sorry this is not a valid last name.");
            System.out.println("Last names are only allowed alphabetical characters, hyphens and apostrophes");
            return false;
        }
        this.lastName = lastName;
        return true;
    }

    /**
     * <p>Sets the age of the character</p>
     * <p><b>Note:<b> age cannot be greater than 5</p>
     * @param age
     * @return <code>true</code> if age is successfully set, <code>false</code> otherwise
     */
    public boolean setAge(int age) {
        if(age >= 5){
            this.age = age;
            return true;
        }
        else{
            System.out.println("Sorry this is not a valid age.");
            System.out.println("Characters must be at least 5 years old.");
            return false;
        }
    }
    
    
    /**
     * <p>Sets the Strength ability score for the character.</p>
     * 
     * @param ability the key for the character's ability
     * @param score the value for the ability
     * @return <code>true</code> if the new value was assigned; <code>false</code> if the assignment failed
     */
    public boolean setAbility (String ability, int score) {
        if(!abilities.containsKey(ability)){
            return false;
        }
        abilities.put(ability, Integer.toString(score));
        if(ability.equalsIgnoreCase(dexterity)){
            armorClass = 10 + (score/4);
        }
        return true;
    } 
 

    /**
     * <p>Sets the level of the character</p>
     * <p><b>Note:</b> Level is between 1 and 18</p>
     * @param level value for the character level
     * @return <code>true</code> if the new value was assigned; <code>false</code> otherwise
     */
     
    public boolean setLevel(int level) {
        if(level < 1 || level > 18){
            System.out.println("I'm sorry, that's not a valid character level.");
            return false;
        }
        else{
            this.level = level;
            return true;
        }
    }

    /**
     * <p>Sets the base speed of the character</p>
     * @param speed how much distance in feet the character covers in one move/minute
     * @return <code>true</code> if the new value was assigned; <code>false</code> otherwise
     */
    public boolean setSpeed(float speed) {
        this.speed = speed;
        return true;
    }
    
    
    /*
     * ---------
     * ACCESSORS
     * ---------
     */
    
    /**
     * <p>Retrieves the first name of the character.</p>
     * @return First name of the character
     */
    public String getFirstName () {
        return this.firstName;
    }
    
    /**
     * <p>Retrieves the last name of the character.</p>
     * @return Last name of the character
     */
    public String getLastName () {
        return this.lastName;
    }

    /**
     * <p>Retrieves the age of the character</p>
     * @return age of the character
     */
    public int getAge() {
        return age;
    }

    /**
     * <p>Retrieves the strength of the character</p>
     * @return the character's strength score
     */
    public int getAbility(String ability) {
        return Integer.valueOf(abilities.get(ability));
    }

    /**
     * <p>Retrieves the HashMap of the abilities</p>
     * @return HashMap of the characters' abilities
     */
    public HashMap<String, String> getAbilities() {
        return abilities;
    }
    
    /**
     * <p>Retrieves the level of the character</p>
     * @return the character's level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * <p>Retrieves the speed of the character</p>
     * @return how much feet the character covers in one move/minute
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * <p>Retrieves the Armor Class of the character</p>
     * @return an integer that represents how hard it is to hit the character
     */
    public int getArmorClass() {
        return this.armorClass;
    }
    
    
    
    /*
     * -------------
     * OTHER METHODS
     * -------------
     */
    
    /**
     * <p>Causes the character to walk in specified direction</p>
     * @param direction the direction the character is to walk
     * @return String stating how far the character has walked
     */
    public String walk(String direction){
        return this.firstName+" "+this.lastName+" has walked "+this.speed+" feet.\n";
    }
    
    /**
     <p>Causes the character to run in specified direction</p>
     * @param direction the direction the character is to run
     * @return String stating how far the character has run and covered
     */
    public String run(String direction){
        return this.firstName+" "+this.lastName+" has run and covered "+(this.speed*3)+" feet.\n";
    }
   
    /**
     * 
     * @return a string representing the character
     */
    @Override
    public String toString() {
        return "\nCharacter Name: "+this.getName()+"\n\tAge: "+ age +"\n\tLevel:"+ level + "\n\tSpeed: "+speed+"\n\tSix Abilities:\n\t\tStrength: " + this.getAbility(strength) + "\n\t\tDexterity: " + this.getAbility(dexterity) + "\n\t\tConstitution: " + this.getAbility(constitution) + "\n\t\tIntelligence: " + this.getAbility(intelligence) + "\n\t\tWisdom: " + this.getAbility(wisdom) + "\n\t\tCharisma: " + this.getAbility(charisma);
    } //end of toString method
    
    /**
     * Retrieves the full name of the character
     * @return FirstName LastName
     */
    public String getName(){
        return this.firstName + " " + this.lastName;
    }
    
    /**
     * <p>Characters are considered equivalent if they have the same last and first name</p>
     * @param otherObject
     * @return <code>true</code> if the characters have the same values for all instance variables <code>false</code>otherwise
     */
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null) {
            return false;
        }
        else if(getClass() != otherObject.getClass()){
            return false;
        }
        else{
            RPCharacter other = (RPCharacter) otherObject;
            if(!this.isIdentical(other)){
                return false;
            }
            if(this.age != other.age){
                return false;
            }
            if(this.level != other.level){
                return false;
            }
            if(this.speed != other.speed){
                return false;
            }
            if(!this.abilities.equals(other.abilities)){
                return false;
            }
        }
        return true;
    } //end of equals method
    
    /**
     * <p>Checks to see if two characters are identical
     * @param other
     * @return <code>true</code> if the characters have the same first and last name, <code>false</code>otherwise
     */
    public boolean isIdentical(RPCharacter other){
        if(other == null){
            return false;
        }
        if (!this.firstName.equalsIgnoreCase(other.firstName)) {
            return false;
        }
        if (!this.lastName.equalsIgnoreCase(other.lastName)) {
            return false;
        }
        return true;
    }
    
       
    /**
     * <p>Converts a HashMap to a character<p>
     * @return RPWizard character
     */
    public HashMap<String, String> toMap(){
        HashMap<String, String> charMap = new HashMap();
        charMap.put(forenameKey, firstName);
        charMap.put(surnameKey, lastName);
        charMap.put(ageKey, Integer.toString(age));
        charMap.putAll(abilities);
        charMap.put(levelKey, Integer.toString(level));
        charMap.put(speedKey, Float.toString(speed));
        return charMap;
    }
    
   /**
     * <p>Converts a HashMap to a character<p>
     * @param map HashMap to be converted
     * @return RPCharacter
     */
    public static RPCharacter mapToCharacter(HashMap<String, String> map){
        RPCharacter character = new RPCharacter(map.get(RPCharacter.forenameKey), map.get(RPCharacter.surnameKey), Integer.valueOf(map.get(RPCharacter.ageKey)));
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
    
} //end of RPCharacter class

