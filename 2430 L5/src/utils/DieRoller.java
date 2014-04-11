/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Scanner;
import java.util.Random;

/**
 * <p>Helper class for simulating various dice-rolling scenarios.</p> 
 * 
 * @author Maame Apenteng (0802637)
 * @version Oct/22/2013
 */
public class DieRoller {  
    /*
     * ------------------------
     * DICE-ROLLING OPERATIONS
     * ------------------------
     */
    
    /**
     * <p>Perform a roll according to the current values for the number of dice 
     * and the number of sides of each die.</p>
     * 
     * @return Array of all the rolled values
     */
    private static int[] roll (Die toRoll, int numDie) {
        int[] rolls = new int[numDie];
        for (int i = 0; i < numDie; i++) {            
            rolls[i] = 1 + new Random(System.nanoTime()).nextInt(toRoll.getFaceValue()); /* Shift by 1 to adjust for the range */
            //System.out.printf("\tLOG (DiceRoller): Roll %d = %d\n", i+1, rolls[i]);                        
        }
        return rolls;
    }
    
    /**
     * <p>Perform a standard roll according to current parameters 
     * and summate the rolled values.</p>
     * 
     * @return Value of the summation of all the rolls
     */
    public static int rollNormal (Die toRoll, int numDie) {        
        int total = 0;
        int[] rolls = roll(toRoll, numDie);
        for (int i = 0; i < rolls.length; i++) {                        
            total = total + rolls[i];            
        }
        return total;
    }
     
    /**
     * <p>Generate a value for use in an ability score using the Standard
     * approach, as stated in the Pathfinder Core Rulebook.</p>
     * @return Value of the rolled ability score
     */
    public static int generateAbilityScoreStandard () {                        
        int total = 0, minRoll = 0;
        int roll_results[];
        Die toRoll = new Die(6);
        
        roll_results = roll(toRoll, 4);
        for(int i = 0; i < roll_results.length; i++){
            if(minRoll == 0){
                minRoll = roll_results[i];
            }
            else if(minRoll < roll_results[i]){
                minRoll = roll_results[i];
            } //end of if/else statement
            total = total + roll_results[i];
        } //end of for loop
        
        total = total - minRoll;
                        
        return total;
    } //end of generateAbilityScoresStandard method
    
    /*
     * -------------
     * OTHER METHODS
     * -------------
     */    
    
    /**
     * <p>Main method is used for testing this class.</p>
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String labelSides = "Choose the number of sides on the die: ";
        String labelNumDice = "Choose the number of dice: ";   
        Scanner keyboard = new Scanner(System.in);
        
    
        int d = 1, n = 1;                        
        System.out.print(labelSides);        
        d = keyboard.nextInt();                        
      
        
        Die diceRoller = new Die(n);
        
        // ----------------
        //System.out.printf("You rolled: %d\n", diceRoller.roll());
        System.out.printf("You rolled: %d\n", generateAbilityScoreStandard());
    }
}