// InventoryService.java

package edu.ucalgary.ensf409;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryService{
	private Hamper[] hampersToCheck;
	private static Inventory inventory = HamperApp.inventory;
	private HashMap<String, Boolean> missingCategory = new HashMap<String, Boolean>();
	private ArrayList<ArrayList<Integer>> pwrSet = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> tmpUsed = new ArrayList<Integer>();
	private int setSize = 2;

	
	public void inventoryCheckAlgorithm() {
		
		/*
		 * fillHamper();
		 * if missingCategory is empty
		 * 		for all food items stored in each Hamper's allocatedItems // OR // all the FoodItem whose used field is true
		 * 			Inventory.deleteFoodItem(usedItem)
		 * 		create an order form (.txt)
		 * else 
		 * 		reset the tmpUsed field of all FoodItem's in the Inventory to "false"
	 	 * 		throw the custom exception InventoryNotAvailableException, with a message indicating which food category 
	     * 		is short (missingCategory content)
		 */
	}
	public void checkHamperNeeds(){

	}

	public void nextPowerSet(){
		generate(in)
		setSize++;
	}
	public ArrayList<ArrayList<Integer>> generate (int numOfItems, int setSizeNum){
		return null;
	}

	
	// Helper methods
	private void fillHampers() {
		// used in a loop, helper method for inventoryCheckAlgorithm
		// selects food items from the inventory and updates the tmpUsed in foodItems
		// updates missingCategory if missing anything for a hamper
	}
			
	// Constructor
	public InventoryService(Hamper[] hampers) {
		hampersToCheck = hampers;
		inventory = new Inventory();
	}
	
}
//
//request = new Request();
//hampers = request.getHampers();
//check = new InventoryService(hampers);








