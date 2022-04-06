// FoodItem.java

package edu.ucalgary.ensf409;

public class FoodItem{
	private int id;
	private String name;
	private double fruitVeggiesContent;
	private double grainContent;
	private double proteinContent;
	private double other;
	private double calories;
	
	// Getters
	public int getID() { return this.id; }
	public String getName() { return this.name; }
	public double getFruitVeggieContent() { return this.fruitVeggiesContent; }
	public double getGrainContent() { return this.grainContent; }
	public double getProteinContent() { return proteinContent; }
	public double getOther() { return this.other; }
	public double getCalories() { return this.calories; }
	
	
	// Constructor
	public FoodItem(int id, String name, double fvc, double gc, double pc, double other, double cal) {
		this.id = id;
		this.name = name;
		this.fruitVeggiesContent = fvc;
		this.grainContent = gc;
		this.proteinContent = pc;
		this.other = other;
		this.calories = cal;
	}
	
}
