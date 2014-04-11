/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpinteract;
import error.InvalidNameException;
import java.util.HashMap;
/**
 * <p>Weapon that can only be wielded by the RPFighter class</p>
 * @author Maame Apenteng (0802637)
 * @version Nov/12/2013
 */
public class RPWeapon {
    private String name ; // Unique name of the weapon
    private String keyAbility ; // One of the six abilities that is enhanced by this weapon
    private int enhancement ; // Added to the corresponding Fighter 's ability (min : 1, max: 7)
    public final static String nameKey = "name";
    public final static String keyAbilityKey = "KA";
    public final static String enhancementKey = "ENH";
    
    /**
     * Constructs a new weapon
     * @param name unique name of weapon - can only contain alphabetic letters, apostrophes and hyphens
     * @param keyAbility One of the six abilities that is enhanced by this weapon
     * @param enhancement Added to the corresponding Fighter 's ability (min : 1, max: 7)
     * @throws InvalidNameException 
     */
    public RPWeapon(String name, String keyAbility, int enhancement) throws InvalidNameException{
        if(!InvalidNameException.isValid(name)){
            throw new InvalidNameException();
        }
        this.name = name;
        this.keyAbility = keyAbility;
        
        if(enhancement >= 1 && enhancement <= 7){
            this.enhancement = enhancement;
        }
        else{
            throw new IllegalArgumentException("Error: Enhancement must be between 1 and 7");
        }
    } //end of RPWeapon constructor
    
    /**
     * <p>Gets the name of the weapon</p>
     * @return the name of the weapon
     */
    public String getName(){
        return name;
    }

    /**
     * <p>Gets the ability that is enhanced by the weapon</p>
     * @return the ability affected by this weapon
     */
    public String getKeyAbility() {
        return keyAbility;
    }

    /**
     * <p>Gets the amount that the ability is enhanced by</p>
     * @return the enhancement
     */
    public int getEnhancement() {
        return enhancement;
    }
    
    public HashMap<String,String> toMap(){
        HashMap<String,String> map = new HashMap();
        map.put(RPWeapon.nameKey, name);
        map.put(RPWeapon.keyAbilityKey, keyAbility);
        map.put(RPWeapon.enhancementKey, Integer.toString(enhancement));
        return map;
    }
    
    public static RPWeapon mapToWeapon(HashMap<String,String> map){
        RPWeapon weapon = new RPWeapon(map.get(RPWeapon.nameKey), map.get(RPWeapon.keyAbilityKey), Integer.valueOf(map.get(RPWeapon.enhancementKey)));
        return weapon;
    }
    
    public boolean isIdentical(RPWeapon other){
        if(this.name.equalsIgnoreCase(other.name)){
            return true;
        }
        return false;
    }
}
