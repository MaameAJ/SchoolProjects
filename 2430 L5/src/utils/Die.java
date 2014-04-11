/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Scanner;

/**
 * The Die class can be used to create a die object with a given 
 * maximum number of faces.
 * 
 * @author Fei Song
 * @version Oct. 5, 2011
 */
public class Die {
    
    public static final int COMMON_MAX_FACES = 6;
    
    private int maxFaces;     // maximum number of faces
    private int faceValue;    // current face value
    
    /** 
     * Construct a new die with a given maximum number of faces.
     * 
     * @param maxFaces maximum number of faces  
     */
    public Die(int maxFaces) {
        if( valid(maxFaces) ) {
            this.maxFaces = maxFaces;
            roll();
        } else {
            System.out.println("Invalide value for max faces");
            System.exit(0);
        }
    } //end of Die constructor w/ given max faces
    
    /**
     * Construct a die with a common maximum number of faces.
     */
    public Die() {
        maxFaces = COMMON_MAX_FACES;
        roll();
    } //end of Die default constructor
    
    /**
     * Test if a given maximum number of faces is valid.
     * @param maxFaces maximum number of faces.
     * @return true if valid and false, otherwise.
     */
    public static boolean valid(int maxFaces) {
        return maxFaces > 0;
    } //end of valid method
    
    /**
     * Test if two dies have the same content.
     * @param other a different die object.
     * @return true if they are the same and false otherwise.
     */
    public boolean equals(Die other) {
        if( other == null)
            return false;
        else
            return maxFaces == other.maxFaces &&
                   faceValue == other.faceValue;
    } //end of equals method
    
    /**
     * Displays the content of a die object
     * @return a string value.
     */
    @Override
    public String toString() {
        return "max faces = " + maxFaces + ", face value = "
                + faceValue;
    } //end of toString
    
    /**
     * Roll a die to generate a random face value.
     */
    public final void roll() {
        faceValue = (int)(Math.random() * maxFaces + 1);
    } //end of roll method
    
    /**
     * Get the current face value.
     * @return an integer face value.
     */
    public int getFaceValue() {
        return faceValue;
    } //end of getFaceValue method
    
    /**
     * Get the current maximum faces.
     * @return an integer face value.
     */
    public int getMaxFaces() {
        return maxFaces;
    } //end of getMaxFaces method
    
    /**
     * 
     */
    public boolean setMaxFaces(int maxFaces) {
        if( valid(maxFaces) ) {
            this.maxFaces = maxFaces;
            return true;
        } else {
            System.out.println("Invalid value for max faces");
            return false;
        }
    } //end of setMaxFaces method
    
    /** 
     * Test the die class with some examples.
     */
    public static void main(String[] args) {
       
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter a maximum number of faces > ");
        try {
           int maxFaces = keyboard.nextInt();
           Die die1 = new Die(maxFaces);
           System.out.println(die1.toString());
           die1.roll();
           System.out.println("new face value = " + die1.getFaceValue());
        } catch( Exception e) {
           System.out.println(e.getMessage());
           System.exit(0);
        } 
    } //end of main method
} //end of Die Class
