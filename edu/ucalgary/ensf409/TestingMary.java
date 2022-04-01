package edu.ucalgary.ensf409;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import org.junit.*;

public class TestingMary {
	private final Hamper HAMPER1 = new Hamper("client1",1,1,1,1);
	private final Hamper HAMPER2 = new Hamper("client2", 1,1,0,2);
	
	private final Hamper HAMPER3 = new Hamper("client3-cannotBeFilled", 3,3,3,3);
	private final Hamper HAMPER4 = new Hamper("client4-canBeFilled", 0,0,1,1);
	
	private final Hamper[] HAMPERS_GOOD = {HAMPER1, HAMPER2};
	private final Hamper[] HAMPERS_BAD = {HAMPER2, HAMPER2};
	
	// private final INVENTORY = new Inventory();
	// OR Assume the Inventory class is initialized from the SQL database

	/* What do I want to test?????
	
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
	 */
//
	/*
	 * InventoryService(Hamper[]) is called with a list of hampers that can all be filled.
	 * inventoryCheckAlgorithm() calls fillHampers() which then updates tmpUsed field in appropriate FoodItem objects to 
	 * "true", the canBeFulfilled field in each Hamper to "true", and adds appropriate FoodItem objects to the 
	 * allocatedItems field of each hamper.
	 */
	@Test
	public void testFillHampersCorrectlyFillsHampers(){

	}

	/*
	 * InventoryService(Hamper[]) is called with a list of hampers where the first hamper cannot be filled.
	 * inventoryCheckAlgorithm() calls fillHampers() which then updates tmpUsed field in appropriate FoodItem objects to 
	 * "true", the canBeFulfilled field in the first Hamper to "false", adds the missing food category to the 
	 * missingCategory array, and continues to check the second hamper in the hampersToCheck array. 
	 */
	@Test
	public void testFillHampersCorrectlyUpdatesMissingCategory(){

	}

	/*
	 * InventoryService(Hamper[]) is called with a list of hampers that can all be filled.
	 * inventoryCheckAlgorithm() calls fillHampers() which then updates tmpUsed field in appropriate FoodItem objects to 
	 * "true", the canBeFulfilled field in each Hamper to "true", and adds appropriate FoodItem objects to the 
	 * allocatedItems field of each hamper.
	 * inventoryCheckAlgorithm() checks that the missingCategory array is empty and creates a text file, "Order.txt"
	 * which contains the order information.
	 */
	@Test
	public void testCreateOrderFormCorrectlyCreatesOrderForm(){}

	/*
	 * InventoryService(Hamper[]) is called with a list of hampers where the first hamper cannot be filled.
	 * inventoryCheckAlgorithm() calls fillHampers() which then updates tmpUsed field in appropriate FoodItem objects to 
	 * "true", the canBeFulfilled field in the first Hamper to "false", adds the missing food category to the 
	 * missingCategory array, and continues to check the second hamper in the hampersToCheck array. 
	 * inventoryCheckAlgorithm() checks that the missingCategory array is not empty, resets the tmpUsed field of all
	 * FoodItems in the inventory to "false" and throws the custom InventoryNotAvailableException with a message 
	 * indicating which food categories are missing.
	 */ 
	@Test
	public void inventoryCheckAlgorithmCorrectlyThrowsException(){}

}