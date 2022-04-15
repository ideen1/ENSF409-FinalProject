/** Hamper.java 
 *  Java Class file for ENSF409 Final Project 202 - 
 *  Winter 2022 - Group 5
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Hamper {
    private ArrayList<Person> people = new ArrayList<Person>();
    private NutritionValues totalNeeds;

    private final String CLIENT;

    private ArrayList<Integer> optimalSet = new ArrayList<>() ;
    private double optimizationAmount = Integer.MAX_VALUE;
    private ArrayList<Integer> allocatedItems = new ArrayList<>();
    private boolean canBeFulfilled = false;

    private int numAdultMales = 0;
    private int numAdultFemales = 0;
    private int numChildUnder8 = 0;
    private int numChildOver8 = 0;

    /** 
	 * FoodItem() constructor
	 * Fills the info field of the food item
	 * @param id The food item's id number in the inventory
	 * @param name The food item's description including the name and quantity
	 * @param fruitVeggieContent The food item's fruit and vegetable nutritional content
	 * @param grainContent The food item's grain nutrional content
	 * @param proteinContent The food item's protein nutrional content
	 * @param other The food item's other nutrional content
	 * @param calories The food item's caloric nutrional content
	 */ 
    /**
     * Hamper() constructor
     * @param client The name of the client for whom the hamper is made
     * @param numAdultMales The number of male adults to be considered for the hamper
     * @param numAdultFemales The number of female adults to be considered for the hamper
     * @param numChildUnder8 The number of children under the age of 8 to be considered for the hamper
     * @param numChildOver8 The number of children over the age of 8 to be considered for the hamper
     */
    public Hamper(String clientName, int numAdultMales, int numAdultFemales, int numChildUnder8, int numChildOver8){
        
        // Set Client for Hamper
        this.CLIENT = clientName;
        this.numAdultFemales = numAdultFemales;
        this.numAdultMales = numAdultMales;
        this.numChildOver8 = numChildOver8;
        this.numChildUnder8 = numChildUnder8;

        // Add Adult Males
        for (int i = 0; i < numAdultMales; i++){
            this.people.add(new Person(PersonType.ADULTMALE));
        }
        // Add Adult Females
        for (int i = 0; i < numAdultFemales; i++){
            this.people.add(new Person(PersonType.ADULTFEMALE));
        }
        // Add Child Under 8
        for (int i = 0; i < numChildUnder8; i++){
            this.people.add(new Person(PersonType.CHILDUNDER8));
        }
        // Add Child Over 8
        for (int i = 0; i < numChildOver8; i++){
            this.people.add(new Person(PersonType.CHILDOVER8));
        }

        // Calculate nutrients after people have been entered
        calculateNeededNutrients();
    }

    /**
     * getOptimizationAmount()
     * Gets the optimization amount
     * @return A number (double) that represents the optimization amount for the hamper
     */
    public double getOptimizationAmount() {
        return optimizationAmount;
    }

    /**
     * setOptimizationAmount()
     * Sets the optimiation amount
     * @param optimizationAmount
     */
    public void setOptimizationAmount(double optimizationAmount) {
        this.optimizationAmount = optimizationAmount;
    }

    private void calculateNeededNutrients() {
        double totalNeedWG = 0;
        double totalNeedFV = 0;
        double totalNeedProtein = 0;
        double totalNeedOther = 0;
        double totalNeedCalories = 0;
        for (Person person: people){
            totalNeedFV += person.getNutrition().getAmountFV();
            totalNeedProtein += person.getNutrition().getAmountProtein();
            totalNeedWG += person.getNutrition().getAmountWG();
            totalNeedOther += person.getNutrition().getAmountOther();
            totalNeedCalories += person.getNutrition().getTotalNeedCalories();
        }
        totalNeeds = new NutritionValues(totalNeedWG/totalNeedCalories, totalNeedFV/totalNeedCalories, totalNeedProtein/totalNeedCalories, totalNeedOther/totalNeedCalories, totalNeedCalories);
    }

    /**
     * recalculateNutrients()
     * Calculates total nutrients needed for the hamper based on
     * the amount and type of people included in the hamper 
     */
    public void recalculateNutrients(){
        totalNeeds = null;
        calculateNeededNutrients();
    }

    /**
     * getNutritionValues()
     * Gets the total needed nutrition values of the hamper
     * @return NutritionValues object
     */
    public NutritionValues getNutritionValues(){
        return this.totalNeeds;
    }

    /** 
     * @return ArrayList<Person>
     */
    public ArrayList<Person> getPeople() {
        return people;
    }

    /** 
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getAllocatedItems() {
        return allocatedItems;
    }
    
    /** 
     * @param allocatedItems
     */
    public void addAllocatedItem(Integer allocatedItems) {
        this.allocatedItems.add(allocatedItems);
    }

    /** 
     * @return boolean
     */
    public boolean canBeFulfilled() {
        return canBeFulfilled;
    }
    
    /** 
     * This method updates the canBeFulfilled field 
     * @param canBeFulfilled
     */
    public void setCanBeFulfilled(boolean canBeFulfilled) {
        this.canBeFulfilled = canBeFulfilled;
    }
    
    /** 
     * @return double
     */
    public double getTotalNeedWG() {
        return totalNeeds.getAmountWG();
    }
    
    /** 
     * @return double
     */
    public double getTotalNeedFV() {
        return totalNeeds.getAmountFV();
    }

    /** 
     * @return double
     */
    public double getTotalNeedProtein() {
        return totalNeeds.getAmountProtein();
    }
    
    /** 
     * @return double
     */
    public double getTotalNeedOther() {
        return totalNeeds.getAmountOther();
    }
    
    /** 
     * @return double
     */
    public double getTotalNeedCalories() {
        return totalNeeds.getTotalNeedCalories();
    }

    /** 
     * @return String
     */
    public String getClientName(){

        return this.CLIENT;
    }

    /** 
     * @return int
     */
    public int getNumAdultMales() {
        return numAdultMales;
    }

    /** 
     * @return int
     */
    public int getNumAdultFemales() {
        return numAdultFemales;
    }

    /** 
     * @return int
     */
    public int getNumChildUnder8() {
        return numChildUnder8;
    }

    /** 
     * @return int
     */
    public int getNumChildOver8() {
        return numChildOver8;
    }

    /** 
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getOptimalSet() {
        return optimalSet;
    } 
    
    /** 
     * @param optimal
     */
    public void setOptimalSet(ArrayList<Integer> optimal) {
        this.optimalSet = optimal;
    }
}

