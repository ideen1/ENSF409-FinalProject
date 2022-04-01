// InventoryService.java

package edu.ucalgary.ensf409;

import java.util.HashMap;

public class InventoryService{
	private Hamper[] hampersToCheck;
	private static Inventory inventory;
	private HashMap<String, Boolean> missingCategory;
	
	public void inventoryCheckAlgorithm() {
	
	}
	
	// Helper methods
	private void fillHamper() {
		// used in a loop, helper method for inventoryCheckAlgorithm
		// selects food items from the inventory and updates the tmpUsed in foodItems
		// updates missingCategory if missing anything for a hamper
	}
	private boolean checkHamperAvail() {
		// checks each Hamper's canBeFulfilled part
		
		return true; //temporary filler
	}
	private void deleteTmpUsedItems() {
		// updates inventory depending on whether or not all hampers can be filled
	}
			
	// Constructor
	public InventoryService(Hamper[] hampers, Inventory inventory) {}
	
}
//
//request = new Request();
//hampers = request.getHampers();
//check = new InventoryService(hampers);








