package edu.ucalgary.ensf409;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import org.junit.*;

public class TestingMary {
	
	/* What do I want to test?????
	 * 
	 * Need a Hamper[] and an Inventory objects to construct an object of InventoryService class with
	 * 
	 * The constructor should set the hampersToCheck and inventory properly
	 * 
	 * fillHamper() should properly update the tmpUsed field in appropriate FoodItem obj, the canBeFulfilled 
	 * and the allocatedItems fields in each Hamper, and the missingCategory filed if needed.
	 * 
	 * inventoryCheckAlgorithm() will use fillHamper() helper method in a loop, use checkHamperAvail(), and depending
	 * the content of the missingCategory:
	 * 	if empty:
	 * 			update the inventory by deleting the FoodItem's that have "true" tmpUsed field
	 * 			create an order form (.txt)
	 * 	if !empty:
	 * 			reset the tmpUsed field of all FoodItem's in the Inventory to "false"
	 * 			throw the custom exception InventoryNotAvailableException, with a message indicating which food category 
	 * 			is short (missingCategory content)
	 * 
	 * 
	 * 
	 */
	
	private final Hamper HAMPER1 = new Hamper("client1",2,1,1,1);
	private final Hamper HAMPER2 = new Hamper("client2", 1,2,1,1);
	
	private final Hamper HAMPER3 = new Hamper("client3-cannotBeFilled", 3,3,3,3);
	private final Hamper HAMPER4 = new Hamper("client4-canBeFilled", 0,0,1,1);
	
	private final Hamper[] HAMPERS1 = {HAMPER1, HAMPER2};
	private final Hamper[] HAMPERS2 = {HAMPER2, HAMPER2};
	
	private final INVENTORY = new Inventory();
	
	
	
}
