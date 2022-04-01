/** Hamper.java 
 *  Java Class file for ENSF409 Final Project 202 - 
 *  Winter 2022 - Group 5
 *  Copyright © 2022 I.B., T.D., M.G.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Hamper {
    private ArrayList<Person> people = new ArrayList<>();

    private String client;

    private ArrayList<Integer> allocatedItems = new ArrayList<>();
    private boolean canBeFulfilled;

    private int totalNeedWG = 0;
    private int totalNeedFV = 0;
    private int totalNeedProtein = 0;
    private int totalNeedOther = 0;
    private int totalNeedCalories = 0;

    /**
     * Hamper
     * @param client,numAdultMales,numAdultFemales,numChildUnder8,numChildOver8
     */

    public Hamper(String client, int numAdultMales, int numAdultFemales, int numChildUnder8, int numChildOver8){
        // Set Client for Hamper
        this.client = client;
        
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
     * calculateNeededNutrients()
     * Calculates total nutrients needed for Hamper based on
     * amount and type of people in people ArrayList
     */
    private void calculateNeededNutrients() {
        for (Person person: people){
            this.totalNeedFV += person.getNutrition().getAmountFV();
            this.totalNeedProtein += person.getNutrition().getAmountProtein();
            this.totalNeedWG += person.getNutrition().getAmountWG();
            this.totalNeedOther += person.getNutrition().getAmountOther();
            this.totalNeedCalories += person.getNutrition().getTotalNeedCalories();
        }
    }

    
    /** 
     * @return Integer[]
     */
    public Integer[] getAllocatedItems() {
        return (Integer[]) allocatedItems.toArray();
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
    public int getTotalNeedWG() {
        return totalNeedWG;
    }

    
    /** 
     * @return int
     */
    public int getTotalNeedFV() {
        return totalNeedFV;
    }

    
    /** 
     * @return int
     */
    public int getTotalNeedProtein() {
        return totalNeedProtein;
    }

    
    /** 
     * @return int
     */
    public int getTotalNeedOther() {
        return totalNeedOther;
    }

    
    /** 
     * @return int
     */
    public int getTotalNeedCalories() {
        return totalNeedCalories;
    }

}
