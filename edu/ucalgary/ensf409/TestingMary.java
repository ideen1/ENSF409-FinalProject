package edu.ucalgary.ensf409;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import org.junit.*;

public class TestingMary {

	private Hamper HAMPER1 = new Hamper("client1",1,0,0,0);
	private Hamper HAMPER2 = new Hamper("client2", 0,0,0,1);

	HAMPER1.getPeople().get(0).getNutrition().setTotalNeedCalories(3000);

    HAMPER2.getPeople().get(0).getNutrition().setTotalNeedCalories(1000);
	
	private final Hamper HAMPER3 = new Hamper("client3-cannotBeFilled", 2,2,2,2);
	private final Hamper HAMPER4 = new Hamper("client4-canBeFilled", 0,0,1,0);

	HAMPER3.getPeople().get(0).getNutrition().setTotalNeedCalories(3000);
	HAMPER3.getPeople().get(1).getNutrition().setTotalNeedCalories(3000);
    HAMPER3.getPeople().get(2).getNutrition().setTotalNeedCalories(2500);
	HAMPER3.getPeople().get(3).getNutrition().setTotalNeedCalories(2500);
    HAMPER3.getPeople().get(4).getNutrition().setTotalNeedCalories(1500);
	HAMPER3.getPeople().get(5).getNutrition().setTotalNeedCalories(1500);
	HAMPER3.getPeople().get(6).getNutrition().setTotalNeedCalories(1000);
	HAMPER3.getPeople().get(7).getNutrition().setTotalNeedCalories(1000);

	HAMPER4.getPeople().get(0).getNutrition().setTotalNeedCalories(1500);

	private final Hamper[] HAMPERS_GOOD = {HAMPER1, HAMPER2};
	private final Hamper[] HAMPERS_BAD = {HAMPER3, HAMPER4};

	private Inventory inventory = new Inventory();

	private final FoodItem testItem = new FoodItem(1, "eggs", 30, 10, 20, 45,1000);       //Creating an example test item
    private final FoodItem testItem2 = new FoodItem(2, "bread", 34, 12, 27, 42,1023);     //Creating a second example test item
    private final FoodItem testItem3 = new FoodItem(3, "milk", 20, 60, 50, 70,2000);      //Creating a third example test item

    inventory.addFoodItem(testItem);                                        //Adding test item to Foodlist in Inventory
    inventory.addFoodItem(testItem2);                                       //Adding second test item to Foodlist in Inventory
    inventory.addFoodItem(testItem3);                                       //Adding third test item to Foodlist in Inventory

	// inventory.removeFoodItem(testItem2);                                    //Removing item using FoodItem Argument
    // inventory.removeFoodItem(1);                                            //Removing item using test item ID

	/* What do I want to test?????
	hamper1.getPeople().get(0).getNutrition().setTotalNeedCalories(3000);

    hamper2.getPeople().get(0).getNutrition().setTotalNeedCalories(1000);
	
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
	 * getHampers() returns correct ArrayList<Hampers> that is stored as hampersToCheck.
	 * getInventory() returns the correct Inventory that is stored as inventory. 
	 */
	@Test
	public void testGetters(){
		
		ArrayList<Hamper> expectedHampers = new ArrayList<Hamper>();
		expectedHampers.add(HAMPER1);
		expectedHampers.add(HAMPER2);
		

		InventoryService check = new InventoryService(HAMPERS_GOOD);
		Inventory actualInventory = check.getInventory();

		assertEquals("The getHampers() method did not return the correct ArrayList<Hamper>", expectedHampers, check.getHampers());
		
	}
	
	/*
	 * InventoryService(Hamper[]) is called with a list of hampers that can all be filled.
	 * inventoryCheckAlgorithm() calls fillHampers() which then updates tmpUsed field in appropriate FoodItem objects to 
	 * "true", the canBeFulfilled field in each Hamper to "true", and adds appropriate FoodItem objects to the 
	 * allocatedItems field of each hamper.
	 */
	@Test
	public void testFillHampersCorrectlyUpdatesHampersAndFoodItems(){
		InventoryService check = new InventoryService(HAMPERS_GOOD);
		check.inventoryCheckAlgorithm();
	private Hamper hamper3 = new Hamper("client3-cannotBeFilled", 2,2,2,2);
	private Hamper hamper4 = new Hamper("client4-canBeFilled", 0,0,1,0);

	hamper3.getPeople().get(0).getNutrition().setTotalNeedCalories(3000);
	hamper3.getPeople().get(1).getNutrition().setTotalNeedCalories(3000);
    hamper3.getPeople().get(2).getNutrition().setTotalNeedCalories(2500);
	hamper3.getPeople().get(3).getNutrition().setTotalNeedCalories(2500);
    hamper3.getPeople().get(4).getNutrition().setTotalNeedCalories(1500);
	hamper3.getPeople().get(5).getNutrition().setTotalNeedCalories(1500);
	hamper3.getPeople().get(6).getNutrition().setTotalNeedCalories(1000);
	hamper3.getPeople().get(7).getNutrition().setTotalNeedCalories(1000);

	hamper3.getPeople().get(0).getNutrition().setPercentWG(100);

	hamper4.getPeople().get(0).getNutrition().setTotalNeedCalories(1500);

	private Hamper[] hampers_good = {hamper1, hamper2};
	private Hamper[] hampers_bad = {hamper3, hamper4};

	private Inventory inventory = new Inventory();

	private FoodItem testItem = new FoodItem(1, "eggs", 30, 10, 20, 45,2000);       //Creating an example test item
    private FoodItem testItem2 = new FoodItem(2, "bread", 34, 12, 27, 42,1023);     //Creating a second example test item
    private FoodItem testItem3 = new FoodItem(3, "milk", 20, 60, 50, 70,1000);      //Creating a third example test item

    inventory.addFoodItem(testItem);                                        //Adding test item to Foodlist in Inventory
    inventory.addFoodItem(testItem2);                                       //Adding second test item to Foodlist in Inventory
    inventory.addFoodItem(testItem3);                                       //Adding third test item to Foodlist in Inventory

	/*
	 * InventoryService(Hamper[]) is called with a list of hampers that can all be filled.
	 * getHampers() returns correct ArrayList<Hampers> that is stored as hampersToCheck.
	 * getInventory() returns the correct Inventory that is stored as inventory. 
	 */
	@Test
	public void testGetters(){
		
		ArrayList<Hamper> expectedHampers = new ArrayList<Hamper>();
		expectedHampers.add(hamper1);
		expectedHampers.add(hamper2);

		Inventory expectedInventory = new Inventory();
		
		InventoryService check = new InventoryService(hampers_good);
		ArrayList<Hamper> actualHampers = check.getHampers();
		Inventory actualInventory = check.getInventory();

		assertEquals("The getHampers() method did not return the correct ArrayList<Hamper>", expectedHampers, actualHampers);
		assertEquals("The getInventory() method did not return the correct Inventory", expectedInventory, actualInventory);
	}
	
	/*
	 * InventoryService(Hamper[]) is called with a list of hampers that can all be filled.
	 * inventoryCheckAlgorithm() calls fillHampers() which then updates tmpUsed field in appropriate FoodItem objects to 
	 * "true", the canBeFulfilled field in each Hamper to "true", and adds appropriate FoodItem objects to the 
	 * allocatedItems field of each hamper.
	 */
	@Test
	public void testFillHampersCorrectlyUpdatesHampersAndFoodItems(){
		InventoryService check = new InventoryService(hampers_good);
		check.inventoryCheckAlgorithm();

		boolean hamperFilledReturn1 = check.getHampers().get(0).getCanBeFulfilled();
		boolean hamperFilledReturn2 = check.getHampers().get(1).getCanBeFulfilled();

		int returnItemID1 = check.getHampers().get(0).getAllocatedItem(0);
		int returnItemID1 = check.getHampers().get(0).getAllocatedItem(1);
		int returnItemID1 = check.getHampers().get(1).getAllocatedItem(0);

		boolean returnValue1 = check.getInventory().getFoodItem(1).getTmpUsed();
		boolean returnValue2 = check.getInventory().getFoodItem(2).getTmpUsed();
		boolean returnValue3 = check.getInventory().getFoodItem(3).getTmpUsed();

		assertTrue("The Hamper's canBeFulfilled field was not correctly updated (1)", hamperFilledReturn1);
		assertTrue("The Hamper's canBeFulfilled field was not correctly updated (2)", hamperFilledReturn2);

		assertEquals("FoodItem's were not correctly stored in the Hamper's allocatedItems field", 1, rturnItemID1);
		assertEquals("FoodItem's were not correctly stored in the Hamper's allocatedItems field", 2, rturnItemID2);
		assertEquals("FoodItem's were not correctly stored in the Hamper's allocatedItems field", 3, rturnItemID3);		

		assertTrue("The FoodItem's tmpUsed field was not correctly updated (1)", returnValue1);
		assertTrue("The FoodItem's tmpUsed field was not correctly updated (2)", returnValue2);
		assertTrue("The FoodItem's tmpUsed field was not correctly updated (3)", returnValue3);
    
	/*
	 * InventoryService(Hamper[]) is called with a list of hampers where the first hamper cannot be filled.
	 * inventoryCheckAlgorithm() calls fillHampers() which then adds the missing food category to the 
	 * missingCategory array. 
	 * inventoryCheckAlgorithm() correclty throws the custom exception.
	 */
	@Test {expected = InventoryNotAvailableException.class}
	public void testFillHampersCorrectlyUpdatesMissingCategoryAndThrowsException(){
		InventoryService check = new InventoryService(hampers_bad);
		boolean exception = false;
		try {
			check.inventoryCheckAlgorithm();
		}
		catch (InventoryNotAvailableException e){
			exception = true;
			assertTrue("The missingCategory HashMap<String, Boolean> was not correctly updated for WholeGrain ", check.getMissingCategory().get("WholeGrain"));
		}
		assertTrue("The custom exception was not thrown by inventoryCheckAlgroithm", exception);
				
	}

}