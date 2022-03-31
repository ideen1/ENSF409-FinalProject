// FoodItem.java

package edu.ucalgary.ensf409;

public class FoodItem{
	private int id;
	private String name;
	private int fruitVeggiesContent;
	private int grainContent;
	private int proteinContent;
	private int other;
	private int calories;
	
	// Getters
	public int getID() { return this.id; }
	public String getName() { return this.name; }
	public int getFruitVeggieContent() { return this.fruitVeggiesContent; }
	public int getGrainContent() { return this.grainContent; }
	public int getProteinContent() { return proteinContent; }
	public int getOther() { return this.other; }
	public int calories() { return this.calories; }
	
	
	// Constructor
	public FoodItem(int id, String name, int fvc, int gc, int pc, int other, int cal) {
		this.id = id;
		this.name = name;
		this.fruitVeggiesContent = fvc;
		this.grainContent = gc;
		this.proteinContent = pc;
		this.other = other;
		this.calories = cal;
	}
	
}
