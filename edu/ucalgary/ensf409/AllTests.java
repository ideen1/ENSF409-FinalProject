/** AllTests.java 
 *  Java Class file for ENSF409 Final Project 202 - 
 *  Winter 2022 - Group 5
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen, Tanish, Mary
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

import org.junit.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.Assert.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;


public class TanishTests {
    // Person Tests:
    /**
     * Test Basic Person Constructor to ensure that object is initilized
     */
    @Test
    public void testPersonConstructor() {
        
        Person person = new Person(PersonType.ADULTMALE);
        assertTrue("The person constructor did not initialize an Object", person != null);
    }

    /**
     * Test Basic Person nutritionValues to ensure that values are getting appropriate updates
     */
    @Test
    public void testPersonNutrition() {
        Person person = new Person(PersonType.ADULTFEMALE);
        NutritionValues nutrition = person.getNutrition();

        person.getNutrition().setTotalNeedCalories(2000);
        person.getNutrition().setPercentFV(0.1);
        
        assertTrue("NutritionValues Object was not returned from person", nutrition != null);
        assertEquals(String.valueOf(200.0) , String.valueOf(nutrition.getAmountFV()));
    }

    /**
     * Test Creation of Hamper when there are 0 people
     */
    @Test
    public void testHamperConstructorWith0People() {
        
        Hamper hamper1 = new Hamper("test", 0, 0, 0, 0);
        
        assertTrue("Hamper object was not created when there are 0 people", hamper1 != null);
    }

    /**
     * Test Creation of Hamper with 0 Adults and Only 1 child 
     */
    @Test
    public void testHamperConstructorWithOnly1Child() {
        
        Hamper hamper1 = new Hamper("test", 0, 0, 0, 1);
        
        assertTrue("Hamper object was not created with only 1 child", hamper1 != null);
    }

    /**
     * Test recalculate nutrition values function on Hamper
     */
    @Test
    public void testHamperRecalculationFunction() {
        
        Hamper hamper2 = new Hamper("test", 1, 0, 0, 0);
        // Set total calories
        //hamper1.getPeople().get(0).getNutrition().setTotalNeedCalories(2500);
        assertNotEquals(2500, hamper2.getTotalNeedCalories());

        // Recalculate Nutrient Amounts for hamper
        hamper2.recalculateNutrients();
        assertEquals(2500, hamper2.getTotalNeedCalories(), 2500-hamper2.getTotalNeedCalories());

        hamper2.getPeople().get(0).getNutrition().setTotalNeedCalories(10000);
        hamper2.recalculateNutrients();
        assertEquals(10000, hamper2.getTotalNeedCalories(), 10000-hamper2.getTotalNeedCalories());
        
    }

    /**
     * Test that the correct nurtrition values are returned for each family memeber type 
     * when there are multiple family memebers.
     */
    @Test
    public void testHamperCalculateNutrientsForFamily() {
        
        Hamper hamper1 = new Hamper("test", 1, 1, 2, 2);
        // Set total calories
        hamper1.getPeople().get(0).getNutrition().setTotalNeedCalories(3000);
        hamper1.getPeople().get(1).getNutrition().setTotalNeedCalories(2500);
        hamper1.getPeople().get(2).getNutrition().setTotalNeedCalories(1500);
        hamper1.getPeople().get(3).getNutrition().setTotalNeedCalories(1500);
        hamper1.getPeople().get(4).getNutrition().setTotalNeedCalories(1000);
        hamper1.getPeople().get(5).getNutrition().setTotalNeedCalories(1000);
        

        // Set Whole Grains percent
        hamper1.getPeople().get(0).getNutrition().setPercentWG(0.1);
        hamper1.getPeople().get(1).getNutrition().setPercentWG(0.1);
        hamper1.getPeople().get(2).getNutrition().setPercentWG(0.1);
        hamper1.getPeople().get(3).getNutrition().setPercentWG(0.1);
        hamper1.getPeople().get(4).getNutrition().setPercentWG(0.1);
        hamper1.getPeople().get(5).getNutrition().setPercentWG(0.1);

        hamper1.recalculateNutrients();
        
        assertEquals(10500, hamper1.getTotalNeedCalories(), 10500-hamper1.getTotalNeedCalories());
        assertEquals(1050.0, hamper1.getTotalNeedWG(), 1050 - hamper1.getTotalNeedWG());
    }

    /**
     * Ensure allocated items are returned appropriately 
     */
    @Test
    public void testHamperAddAllocatedItem() {
        
        Hamper hamper3 = new Hamper("test", 1, 1, 1, 1);
        hamper3.addAllocatedItem(1);
        hamper3.addAllocatedItem(102);
        hamper3.addAllocatedItem(-5);

        Integer expectVal1 = 1;
        Integer expectVal2 = 102;
        Integer expectVal3 = -5;
        
        assertEquals(3, hamper3.getAllocatedItems().size());
        assertEquals(expectVal1, hamper3.getAllocatedItems().get(0));
        assertEquals(expectVal2, hamper3.getAllocatedItems().get(1));
        assertEquals(expectVal3, hamper3.getAllocatedItems().get(2));
    }


    /**
     * Test Normal Constructor for Nutrition Values
     */

    @Test
    public void testNutritionValuesConstructorWithInvalidType() {
        boolean exceptionThrown = false;
        
        try{
            NutritionValues nutrients = new NutritionValues("TEENMALE");
        } catch(Exception e){
            exceptionThrown = true;
        }
        

        assertTrue("The nutrition class was created with invalid type TEENMALE", exceptionThrown);
    }

    /**
     * Test that valid nutrition values are returned when they are modified through setters
     */
    @Test
    public void testNutritionValuesSetValues() {
        
        NutritionValues nutrients = new NutritionValues("ADULTMALE");
        nutrients.setTotalNeedCalories(1000);
        nutrients.setPercentOther(0.05);
        
        
        assertEquals(0.05, nutrients.getPercentOther(), 0.05 - nutrients.getPercentOther());
        assertEquals(0.0, nutrients.getPercentFV(), 0.00 - nutrients.getPercentFV());
        assertEquals(50, nutrients.getAmountOther(), 50 - nutrients.getAmountOther());

    }
/*                                      Request and Inventory Tests                                         */
    
    //Testing addHamper method 
    @Test 
    public void testAddHamper() {

        Request request = new Request("test", LocalDate.now());

        int expectedlistsize = request.getHampers().size() + 1;             //Check the size if the current Hamper list and adds 1
        
        request.addHamper("Test client", 1, 1, 1, 1);
        int actuallistsize = request.getHampers().size();                   //Gets actual hampers list size after adding new client
        
        assertEquals("The hamper array list did not get updated",expectedlistsize, actuallistsize);
    }

    //Testing the createOrderFile method
    @Test 
    public void testCreateOrderFile(){
        
        boolean orderGenarated = true;
        Request request = new Request("test", LocalDate.now());

        try {
            request.createOrderFile();

        } catch (Exception e) {

            orderGenarated = false;
        }

        assertTrue("Order file could not be created because of exception", orderGenarated);
    }

    // Testing the generated .txt file and its contents with expected string
    @Test 
    public void testGenerateOrderForm(){

        Request request = new Request("test", LocalDate.now());
        String expectedOrderString = "Some example order string we expect to get from order form";

        request.genarateOrderForm();
        StringBuilder actualOrderString = new StringBuilder();
        //need to implement more
        // convert txt created by genarateOrderForm method to a string and compare that to expectedOrderString

        assertEquals("The actual string did not match the expected string",expectedOrderString, actualOrderString);
    }

    //Testing same clients with different requests 
    @Test
    public void TesttwoHampersWithSameClient(){
        Request request = new Request("test", LocalDate.now());
        request.addHamper("client 1", 1, 1, 1, 1);                              //Adding a client to Hamper
        request.addHamper("client 1", 2, 2, 2, 2);                              //Adding same client to Hamper with a new request

        assertFalse("Second hamper with the same client name is a copy of the first hamper", request.getHampers().get(0) ==request.getHampers().get(1));
        assertTrue("The second client request didn't get made", request.getHampers().get(1) != null);
    }

    // Test Creating a new hamper with negative person amounts
    @Test 
    public void testCreateHampersWithNegativePeople(){
        
        
        boolean exceptionThrown = false;

        try {
            Request.addHamper("Test", -1, 0, 0, -10);                           //Sending negative number of people into hamper

        } catch (Exception e) {

            exceptionThrown = true;
        }

        assertTrue("Create Hamper wth negative amounts of people did not throw an exception", exceptionThrown);
    }

    /*                                              Testing iventory class                                                      */ 

    //Testing setter and getter if a FoodItem list is already provided 
    @Test 
    public void testSetFoodListAndGetFoodList(){
        FoodItem testItem = new FoodItem(1, "eggs", 30, 10, 20, 45,1000);       //Creating an example test item
        FoodItem testItem2 = new FoodItem(2, "bread", 34, 12, 27, 42,1023);     //Creating a second example test item

        HashMap<Integer,FoodItem> foodList = new HashMap<Integer,FoodItem>();   //Creating a food list to provide setter
        foodList.put(testItem.getID(),testItem);                                //Adding test item to food list
        foodList.put(testItem2.getID(),testItem2);                              //Adding second test item to food list
        Inventory.setFoodList(foodList);                                        //Sending the food list to inventory
        
        HashMap<Integer,FoodItem> incomingList = Inventory.getFoodlist();       //Gets food list from inventory

        assertEquals("The actual item name did not match the expected item name", incomingList.get(1).getName(),testItem.getName());

    }

    //Testing the addFoodItem method and testing for items with duplicate ID that get added to list
    @Test
    public void testAddDuplicateFoodItemID(){
        FoodItem testItem = new FoodItem(1, "eggs", 30, 10, 20, 45,1000);       //Creating an example test item
        FoodItem testItem2 = new FoodItem(2, "bread", 34, 12, 27, 42,1023);     //Creating a second example test item
        FoodItem testItem3 = new FoodItem(1, "milk", 20, 60, 50, 70,2000);      //Creating a third example test item
        

        Inventory.addFoodItem(testItem);                                        //Adding test item to Foodlist
        Inventory.addFoodItem(testItem2);                                       //Adding second test item to Foodlist
        Inventory.addFoodItem(testItem3);                                       //Adding third test item to Foodlist

        assertFalse("Duplicate Food Item did not replace food item with same ID number ",testItem == Inventory.getFood(1));
    }

    //Testing the removeFoodItem methods from inventory class
    @Test
    public void testRemoveFoodItem(){
        FoodItem testItem = new FoodItem(1, "eggs", 30, 10, 20, 45,1000);       //Creating an example test item
        FoodItem testItem2 = new FoodItem(2, "bread", 34, 12, 27, 42,1023);     //Creating a second example test item
        FoodItem testItem3 = new FoodItem(3, "milk", 20, 60, 50, 70,2000);      //Creating a third example test item

        Inventory.addFoodItem(testItem);                                        //Adding test item to Foodlist
        Inventory.addFoodItem(testItem2);                                       //Adding second test item to Foodlist
        Inventory.addFoodItem(testItem3);                                       //Adding third test item to Foodlist

        Inventory.removeFoodItem(testItem2);                                    //Removing item using FoodItem Argument
        Inventory.removeFoodItem(1);                                            //Removing item using test item ID

        assertTrue("Item was not removed from the Food List using FoodItem as argument", Inventory.getFood(2) == null);
        assertTrue("Item was not removed from the Food List using food item ID", Inventory.getFood(1) == null);
    }
/*                                              Inventory service and FootItem Tests                                                        */
 private Hamper hamper1 = new Hamper("client1",1,0,0,0);
	private Hamper hamper2 = new Hamper("client2", 0,0,0,1);

	hamper1.getPeople().get(0).getNutrition().setTotalNeedCalories(3000);

    hamper2.getPeople().get(0).getNutrition().setTotalNeedCalories(1000);
	
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

	// inventory.removeFoodItem(testItem2);                                    //Removing item using FoodItem Argument
    // inventory.removeFoodItem(1);                                            //Removing item using test item ID

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
