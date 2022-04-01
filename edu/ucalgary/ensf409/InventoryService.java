// InventoryService.java

package edu.ucalgary.ensf409;

import java.util.HashMap;

public class InventoryService{
	private Hamper[] hampersToCheck;
	private static Inventory inventory;
	private HashMap<String, Boolean> missingCategory = new HashMap<String, Boolean>();
	
	public void inventoryCheckAlgorithm() {
		
		/*
		 * fillHamper();
		 * if missingCategory is empty
		 * 		for all food items stored in each Hamper's allocatedItems // OR // all the FoodItem whose used field is true
		 * 			Inventory.deleteFoodItem(usedItem)
		 * 		create an order form (.txt)
		 * else 
		 * 		reset the tmpUsed field of all FoodItem's in the Inventory to "false"
	 * 			throw the custom exception InventoryNotAvailableException, with a message indicating which food category 
	 * 			is short (missingCategory content)
		 */
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








