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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;




public class AllTests {
    
      /*
     * Tests FoodItem class's getters
     */
    @Test
    public void testFoodItemGetters(){
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();

        FoodItem cucumber = new FoodItem(1111, "cucumber", 100, 0, 2, 5, 10);

        assertEquals("The getID() method did not return the correct id number of the food item.", 1111, cucumber.getID());
        assertEquals("The getName() method did not return the correct description of the food item.", "cucumber", cucumber.getName());
        assertEquals("The getFruitVeggieContent() method did not return the correct fruit and vegetable content of the food item.", 100, cucumber.getFruitVeggieContent(), 100-cucumber.getFruitVeggieContent());
        assertEquals("The getGrainContent() method did not return the correct grain content of the food item.", 0, cucumber.getGrainContent(), 0-cucumber.getGrainContent());
        assertEquals("The getProteinContent() method did not return the correct protein content of the food item.", 2, cucumber.getProteinContent(), 2-cucumber.getProteinContent());
        assertEquals("The getOtherContent() method did not return the correct other content of the food item.", 5, cucumber.getOther(), 5-cucumber.getOther());
        assertEquals("The getCalories() method did not return the correct calory of the food item.", 10, cucumber.getCalories(), 10-cucumber.getCalories());

    }

    /* 
     * The nextPowerSet() method correctly returns a boolean value indicating whether or not a next powerset is available.
     * The method also correctly updates pwrSet field of InventoryService class.
     * getPwrSet() returns the correct ArrayList<int[]> containing powersets.
     */
    @Test
    public void testNextPowerSet() {
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();

        FoodItem food1 = new FoodItem(1, "Fruit", 20, 0, 0, 0, 5000);
        FoodItem food2 = new FoodItem(2, "Fruit", 15, 0, 0, 0, 4000);
        Inventory.addFoodItem(food1); 
        Inventory.addFoodItem(food2); 
        HamperApp.mainScreen = new GUIViewController();  

        ArrayList<int[]> expected1 = new ArrayList<>();
        int[] arr1 = {1};
        expected1.add(arr1);
        int[] arr2 = {2};
        expected1.add(arr2);

        ArrayList<int[]> expected2 = new ArrayList<>();
        int[] arr3 = {1, 2};
        expected2.add(arr3);
        
        boolean t = InventoryService.nextPowerSet();
        assertTrue("The nextPowerSet() method did not return the correct boolean value. (1)", t);
        ArrayList<int[]> result1 = InventoryService.getPwrSet();
        
        assertEquals("The getPwrSet() method did not return the correct ArrayList<int[]>. (1)(index 0, 0)", result1.get(0)[0], arr1[0]);
        assertEquals("The getPwrSet() method did not return the correct ArrayList<int[]>. (2)(index 1, 0)", result1.get(1)[0], arr2[0]);

        // result1.forEach(arr -> {assertEqual(ar);});
        // assertTrue("The getPwrSet() method did not retun the correct ArrayList<int[]>. (1)", result1.contains(arr1));
        InventoryService.getPwrSet().clear();
        assertTrue("The nextPowerSet() method did not return the correct boolean value. (2)", InventoryService.nextPowerSet());
        ArrayList<int[]> result2 = InventoryService.getPwrSet();
        assertEquals(1, result2.size());
        assertEquals("The getPwrSet() method did not return the correct ArrayList<int[]>. (2)(index 0, 0)", result2.get(0)[0], arr3[0]);
        assertEquals("The getPwrSet() method did not return the correct ArrayList<int[]>. (2)(index 0, 1)", result2.get(0)[1], arr3[1]);
        
        boolean pass = false;
        try {
            result2.get(1);
        } catch (IndexOutOfBoundsException e) {
            pass = true;
        }

        assertTrue("The getPwrSet() method did not return the correct ArrayList<int[]>. (2)(index 1, 0)", pass);
    }


    /**
     * Test whether the algorithm chooses the optimal combination of items when more items actually causes less nutrient waste
     * For simplicity, we only test one of the nutrient categories
     * Fruit1 + Fruit2 = 2000
     * Fruit 3 = 2200
     * It should pick Fruit1+2 for the optimal combination
     */
    @Test
    public void testInventoryServiceWithOptimizedValues(){
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
        Hamper hamper1 = new Hamper("testClient",1,1,0,0);
        HamperApp.currentRequest = new Request("testRequest", LocalDate.now());
        HamperApp.mainScreen = new GUIViewController();  

	    hamper1.getPeople().get(0).getNutrition().setTotalNeedCalories(3000);
        hamper1.getPeople().get(0).getNutrition().setPercentFV(25);
        hamper1.getPeople().get(0).getNutrition().setPercentWG(0);
        hamper1.getPeople().get(0).getNutrition().setPercentProtein(0);
        hamper1.getPeople().get(0).getNutrition().setPercentOther(0);
        hamper1.getPeople().get(1).getNutrition().setTotalNeedCalories(2000);
        hamper1.getPeople().get(1).getNutrition().setPercentFV(25);
        hamper1.getPeople().get(1).getNutrition().setPercentWG(0);
        hamper1.getPeople().get(1).getNutrition().setPercentProtein(0);
        hamper1.getPeople().get(1).getNutrition().setPercentOther(0);
        hamper1.recalculateNutrients();
        HamperApp.currentRequest.addHamper(hamper1);


        FoodItem goodItem = new FoodItem(1, "Fruit1", 100, 0, 0, 0, 1000);       //Creating an example test item
        FoodItem goodItem2 = new FoodItem(2, "Fruit2", 100, 0, 0, 0, 1000);     //Creating a second example test item
        FoodItem ineffcientItem = new FoodItem(3, "Fruit3", 100, 0, 0, 0, 2200);      //Creating a third example test item

        // Food Items Fruit1 and Fruit2 should be picked since they create less waste.

        Inventory.addFoodItem(goodItem);                                        //Adding test item to Foodlist in Inventory
        Inventory.addFoodItem(goodItem2);                                       //Adding second test item to Foodlist in Inventory
        Inventory.addFoodItem(ineffcientItem);                                       //Adding third test item to Foodlist in Inventory

        InventoryService.inventoryCheckAlgorithm();

        Integer[] expectedIDs = {1,2};
        assertArrayEquals(expectedIDs, HamperApp.currentRequest.getHampers().get(0).getAllocatedItems().toArray());
        Inventory.getFoodlist().clear();
    }
    
    /* 
     * Tests getMissingCategory()
     * A reuqest is created with a hamper and a FoodItem is created with sufficient caloric content
     * to fill the hamper but insufficient fruit and vegetable content.
     * getMissingCategory() method is expected to return a HashMap that contains a key
     * "Fruit/Veggies" and a boolean value true to indicate the insufficient category.
     */
    @Test
    public void testGetMissingCategory() {
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();

        Hamper hamper1 = new Hamper("testClient",1,1,0,0);
        HamperApp.currentRequest = new Request("testRequest", LocalDate.now());
        HamperApp.mainScreen = new GUIViewController();  

	    hamper1.getPeople().get(0).getNutrition().setTotalNeedCalories(3000);
        hamper1.getPeople().get(0).getNutrition().setPercentFV(25);
        hamper1.getPeople().get(0).getNutrition().setPercentWG(0);
        hamper1.getPeople().get(0).getNutrition().setPercentProtein(0);
        hamper1.getPeople().get(0).getNutrition().setPercentOther(0);
        hamper1.getPeople().get(1).getNutrition().setTotalNeedCalories(2000);
        hamper1.getPeople().get(1).getNutrition().setPercentFV(25);
        hamper1.getPeople().get(1).getNutrition().setPercentWG(0);
        hamper1.getPeople().get(1).getNutrition().setPercentProtein(0);
        hamper1.getPeople().get(1).getNutrition().setPercentOther(0);
        hamper1.recalculateNutrients();
        HamperApp.currentRequest.addHamper(hamper1);

        FoodItem insuffcientItem = new FoodItem(1, "Fruit", 20, 0, 0, 0, 5000);
        Inventory.addFoodItem(insuffcientItem);  

        InventoryService.inventoryCheckAlgorithm();

        boolean result = InventoryService.getMissingCategory().get("Fruit/Veggies");
        assertTrue("The getMissingCategory() method did not return the correct missing category information.", result);
    }

    //Testing addHamper method 
    @Test 
    public void testAddHamper() {
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();

        Request request = new Request("test", LocalDate.now());

        int expectedlistsize = request.getHampers().size() + 1;             //Check the size if the current Hamper list and adds 1
        
        request.addHamper("Test client", 1, 1, 1, 1);
        int actuallistsize = request.getHampers().size();                   //Gets actual hampers list size after adding new client
        
        assertEquals("The hamper array list did not get updated",expectedlistsize, actuallistsize);
    }

    //Testing the createOrderFile method
    @Test 
    public void testCreateOrderFile(){
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
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
    public void testGenerateOrderForm() throws FileNotFoundException{
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();

        HamperApp.mainScreen = new GUIViewController();  
        String expectedOrderString = "Group 5 Food Bank\nHamper OrderForm\n\nName: TestName\nDate: 2022-04-12\n\nOriginal Request\nHamper 1(Client1): 1 Adult Male, 1 Adult Female, 1 Child under 8, 1 Child over 8\n\nHamper 1(Client1) Items:\n1\teggs\n2\tbread\n3\tapples\n4\tmilk\n5\tbananas\n6\tbeans\n\n";
        String Date = "2022-04-12";
        LocalDate fixedDate = LocalDate.parse(Date);


        HamperApp.currentRequest = new Request("TestName",fixedDate);
        Hamper hamperToTest = new Hamper("Client1", 1, 1, 1, 1);
        hamperToTest.getPeople().get(0).getNutrition().setTotalNeedCalories(2000);
        hamperToTest.getPeople().get(0).getNutrition().setPercentFV(25);
        hamperToTest.getPeople().get(0).getNutrition().setPercentWG(25);
        hamperToTest.getPeople().get(0).getNutrition().setPercentProtein(25);
        hamperToTest.getPeople().get(0).getNutrition().setPercentOther(25);
        hamperToTest.getPeople().get(1).getNutrition().setTotalNeedCalories(2000);
        hamperToTest.getPeople().get(1).getNutrition().setPercentFV(25);
        hamperToTest.getPeople().get(1).getNutrition().setPercentWG(25);
        hamperToTest.getPeople().get(1).getNutrition().setPercentProtein(25);
        hamperToTest.getPeople().get(1).getNutrition().setPercentOther(25);
        hamperToTest.getPeople().get(2).getNutrition().setTotalNeedCalories(2000);
        hamperToTest.getPeople().get(2).getNutrition().setPercentFV(25);
        hamperToTest.getPeople().get(2).getNutrition().setPercentWG(25);
        hamperToTest.getPeople().get(2).getNutrition().setPercentProtein(25);
        hamperToTest.getPeople().get(2).getNutrition().setPercentOther(25);
        hamperToTest.getPeople().get(3).getNutrition().setTotalNeedCalories(2000);
        hamperToTest.getPeople().get(3).getNutrition().setPercentFV(25);
        hamperToTest.getPeople().get(3).getNutrition().setPercentWG(25);
        hamperToTest.getPeople().get(3).getNutrition().setPercentProtein(25);
        hamperToTest.getPeople().get(3).getNutrition().setPercentOther(25);
        hamperToTest.recalculateNutrients();
        HamperApp.currentRequest.addHamper(hamperToTest);

        
        FoodItem testItem = new FoodItem(1, "eggs", 0, 0, 100, 0,1000);       //Creating an example test item
        FoodItem testItem2 = new FoodItem(2, "bread", 0, 100, 0, 0,2000);       //Creating a second example test item
        FoodItem testItem3 = new FoodItem(3, "apples", 100, 0, 0, 0,1000);   //Creating a third example test item
        FoodItem testItem4 = new FoodItem(4, "milk", 0, 0, 0, 100,2000);        //Creating a fourth example test item
        FoodItem testItem5 = new FoodItem(5, "bananas", 100, 0, 0, 0,1300);     //Creating a fifth example test item
        FoodItem testItem6 = new FoodItem(6, "beans", 0, 0, 100, 0,1000);       //Creating a sixth example test item

        HashMap<Integer,FoodItem> foodList = new HashMap<Integer,FoodItem>();   //Creating a food list to provide setter
        Inventory.addFoodItem(testItem);                               //Adding test item to food list
        Inventory.addFoodItem(testItem2);                            //Adding second test item to food list
        Inventory.addFoodItem(testItem3);                               //Adding third test item to food list
        Inventory.addFoodItem(testItem4);                           //Adding fourth test item to food list
        Inventory.addFoodItem(testItem5);                               //Adding fifth test item to food list
        Inventory.addFoodItem(testItem6);                            //Adding sixth test item to food list


        InventoryService.inventoryCheckAlgorithm();                             // runs algorithm and finds optimal hamper

        File file = new File("TestName.txt");                             //import order.txt into java
        Scanner scan = new Scanner(file);                                       //scanner for order.txt file
        String actualOrderString = "";                                          //String to store contents from order.txt

        while(scan.hasNextLine()){                                              //goes through order.txt file line by line
            actualOrderString = actualOrderString.concat(scan.nextLine()+"\n"); //adds content from each line into a string
        }


        assertEquals("The actual string did not match the expected string",expectedOrderString, actualOrderString);
    }

    //Testing same clients with different requests 
    @Test
    public void TesttwoHampersWithSameClient(){
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();

        Request request = new Request("test", LocalDate.now());
        request.addHamper("client 1", 1, 1, 1, 1);                              //Adding a client to Hamper
        request.addHamper("client 1", 2, 2, 2, 2);                              //Adding same client to Hamper with a new request

        assertFalse("Second hamper with the same client name is a copy of the first hamper", request.getHampers().get(0) ==request.getHampers().get(1));
        assertTrue("The second client request didn't get made", request.getHampers().get(1) != null);
    }

    // Test Creating a new hamper with negative person amounts
    @Test 
    public void testCreateHampersWithNegativePeople(){
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
        Request request = new Request("Test", LocalDate.now());


        boolean returnVal = request.addHamper("Test", -1, 0, 0, -10); //Sending negative number of people into hamper



        assertTrue("Create Hamper wth negative amounts of people did not throw an exception", returnVal);
    }

    /*                                              Testing iventory class                                                      */ 

    //Testing setter and getter if a FoodItem list is already provided 
    @Test 
    public void testSetFoodListAndGetFoodList(){
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();

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
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();

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
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
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

    // Person Tests:
    /**
     * Test Basic Person Constructor to ensure that object is initilized
     */
    @Test
    public void testPersonConstructor() {
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
        Person person = new Person(PersonType.ADULTMALE);
        assertTrue("The person constructor did not initialize an Object", person != null);
    }

    /**
     * Test Basic Person nutritionValues to ensure that values are getting appropriate updates
     */
    @Test
    public void testPersonNutrition() {
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        Person person = new Person(PersonType.ADULTFEMALE);
        NutritionValues nutrition = person.getNutrition();

        person.getNutrition().setTotalNeedCalories(2000);
        person.getNutrition().setPercentFV(10);
        
        assertTrue("NutritionValues Object was not returned from person", nutrition != null);
        assertEquals(String.valueOf(200.0) , String.valueOf(nutrition.getAmountFV()));
    }

    /**
     * Test Creation of Hamper when there are 0 people
     */
    @Test
    public void testHamperConstructorWith0People() {
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
        Hamper hamper1 = new Hamper("test", 0, 0, 0, 0);
        
        assertTrue("Hamper object was not created when there are 0 people", hamper1 != null);
    }

    /**
     * Test Creation of Hamper with 0 Adults and Only 1 child 
     */
    @Test
    public void testHamperConstructorWithOnly1Child() {
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
        Hamper hamper1 = new Hamper("test", 0, 0, 0, 1);
        
        assertTrue("Hamper object was not created with only 1 child", hamper1 != null);
    }

    /**
     * Test recalculate nutrition values function on Hamper
     */
    @Test
    public void testHamperRecalculationFunction() {
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
        Hamper hamper2 = new Hamper("test", 1, 0, 0, 0);
        // Set total calories
        hamper2.getPeople().get(0).getNutrition().setTotalNeedCalories(12500);
        assertNotEquals(12500, hamper2.getTotalNeedCalories());

        // Recalculate Nutrient Amounts for hamper
        hamper2.recalculateNutrients();
        assertEquals(12500, hamper2.getTotalNeedCalories(), 12500-hamper2.getTotalNeedCalories());

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
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
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
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
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
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();

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
        Inventory.getFoodlist().clear();
        InventoryService.getPwrSet().clear();
        InventoryService.resetNextSize();
        
        NutritionValues nutrients = new NutritionValues("ADULTMALE");
        nutrients.setTotalNeedCalories(1000);
        nutrients.setPercentOther(5);
        nutrients.setPercentFV(0);
        
        
        assertEquals(50, nutrients.getPercentOther(), 50 - nutrients.getPercentOther());
        assertEquals(0.0, nutrients.getPercentFV(), 0.00 - nutrients.getPercentFV());
        assertEquals(50, nutrients.getAmountOther(), 50 - nutrients.getAmountOther());

    }

    
}
