/** TanishTests.java 
 *  Java Class file for ENSF409 Final Project 202 - 
 *  Winter 2022 - Group 5
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Tanish
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.*;


public class TanishTests {
    
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
}
