/** FoodItem.java 
 *  Java Class file for ENSF409 Final Project 2022 - Winter 2022 - Group 5
 *  Represents a food item stored in inventory. 
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen, Tanish, Mary 
 *  @version 1.6
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

public class FoodItem{
	private final int ID;
	private final String NAME;
	private final double FVC;
	private final double GC;
	private final double PC;
	private final double OTHER;
	private final double CAL;
	private int usageStatus = 0; // 0: Not Used, 1: Used by current Hamper, 2: Used for good
	
	// Getters

	/** 
	 * getID()
	 * Gets the food item's id number
	 * @return An integer representing the food item's id number in the inventory
	 */
	public int getID() { return this.ID; }

	/** 
	 * getUsageStatus()
	 * Returns the Usage status flag of a food item
	 * @return An integer representing the food item's status flag
	 */
	public int getUsageStatus() {
		return usageStatus;
	}

	/** 
	 * setUsageStatus()
	 * Sets the Usage status flag of a food item
	 * @return void
	 * @param usageStatus An int value to be set as the food item's new usage status flag
	 */
	public void setUsageStatus(int usageStatus) {
		this.usageStatus = usageStatus;
	}

	/** 
	 * getName()
	 * Gets the food item's description
	 * @return A string represeting the name and quantity of the food item
	 */
	public String getName() { return this.NAME; }
	
	/** 
	 * getFruitVeggieContent()
	 * Gets the food item's Fruit and Vegetable content
	 * @return A number (double) representing the food item's fruit and vegetable nutritional content
	 */
	public double getFruitVeggieContent() { return this.FVC; }
	
	/** 
	 * getGrainContent()
	 * Gets the food item's Grain content
	 * @return A number (double) representing the food item's grain nutritional content
	 */
	public double getGrainContent() { return this.GC; }
	
	/** 
	 * getProteinContent()
	 * Gets the food item's Protein content
	 * @return A number (double) representing the food item's protein nutritional content
	 */
	public double getProteinContent() { return PC; }
	
	/** 
	 * getOther()
	 * Gets the food item's Other content
	 * @return A number (double) representing the food item's "other" nutritional content
	 */
	public double getOther() { return this.OTHER; }

	/** 
	 * getCalories()
	 * Gets the food item's calories
	 * @return A number (double) representing the food item's calories
	 */
	public double getCalories() { return this.CAL; }
	
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
	public FoodItem(int id, String name, double fruitVeggieContent, double grainContent, double proteinContent, 
			double other, double calories) {
		this.ID = id;
		this.NAME = name;
		this.FVC = fruitVeggieContent;
		this.GC = grainContent;
		this.PC = proteinContent;
		this.OTHER = other;
		this.CAL = calories;
	}

	
}
