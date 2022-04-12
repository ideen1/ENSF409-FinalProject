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
     * Hamper
     * @param client,numAdultMales,numAdultFemales,numChildUnder8,numChildOver8
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

    public double getOptimizationAmount() {
        return optimizationAmount;
    }

    public void setOptimizationAmount(double optimizationAmount) {
        this.optimizationAmount = optimizationAmount;
    }

    /**
     * calculateNeededNutrients()
     * Calculates total nutrients needed for Hamper based on
     * amount and type of people in people ArrayList
     */
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

    public void recalculateNutrients(){
        totalNeeds = null;
        calculateNeededNutrients();
    }

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
     * @param canBeFulfilled
     */
    public void setCanBeFulfilled(boolean canBeFulfilled) {
        this.canBeFulfilled = canBeFulfilled;
    }

    
    /** 
     * @return int
     */
    public double getTotalNeedWG() {
        return totalNeeds.getAmountWG();
    }

    
    /** 
     * @return int
     */
    public double getTotalNeedFV() {
        return totalNeeds.getAmountFV();
    }

    
    /** 
     * @return int
     */
    public double getTotalNeedProtein() {
        return totalNeeds.getAmountProtein();
    }

    
    /** 
     * @return int
     */
    public double getTotalNeedOther() {
        return totalNeeds.getAmountOther();
    }

    
    /** 
     * @return int
     */
    public double getTotalNeedCalories() {
        return totalNeeds.getTotalNeedCalories();
    }

    public String getClientName(){

        return this.CLIENT;
    }


    public int getNumAdultMales() {
        return numAdultMales;
    }

    public int getNumAdultFemales() {
        return numAdultFemales;
    }

    public int getNumChildUnder8() {
        return numChildUnder8;
    }


    public int getNumChildOver8() {
        return numChildOver8;
    }

    public ArrayList<Integer> getOptimalSet() {
        return optimalSet;
    } 
    public void setOptimalSet(ArrayList<Integer> optimal) {
        this.optimalSet = optimal;
    }


}

