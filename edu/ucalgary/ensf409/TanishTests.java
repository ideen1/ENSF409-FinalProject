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

import java.io.File;
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

        String expectedOrderString = "Group 5 Food Bank\nHamper OrderForm\n\nName: TestName\nDate: 2022-4-12\n\nOriginal Request\nHamper 1(Client1): 1 Adult Male, %d Adult Female, %d Child uner 8, %d Child over 8"+
        "\nHamper 1(Client1) Items:\n1\teggs\n2\tbread\n3\tapples\n4\tmilk\n5\tbananas\n6\tbeans\n";
        LocalDate fixedDate = 2022-4-12;


        Request request = new Request("TestName",fixedDate);
        request.addHamper("Client1", 1, 1, 1, 1);
        
        FoodItem testItem = new FoodItem(1, "eggs", 0, 0, 100, 0,2100);       //Creating an example test item
        FoodItem testItem2 = new FoodItem(2, "bread", 0, 100, 0, 0,1500);       //Creating a second example test item
        FoodItem testItem3 = new FoodItem(3, "apples", 100, 0, 0, 0,1000);   //Creating a third example test item
        FoodItem testItem4 = new FoodItem(4, "milk", 0, 0, 0, 100,1700);        //Creating a fourth example test item
        FoodItem testItem5 = new FoodItem(5, "bananas", 100, 0, 0, 0,1300);     //Creating a fifth example test item
        FoodItem testItem6 = new FoodItem(6, "beans", 0, 0, 100, 0,1900);       //Creating a sixth example test item

        HashMap<Integer,FoodItem> foodList = new HashMap<Integer,FoodItem>();   //Creating a food list to provide setter
        foodList.put(testItem.getID(),testItem);                                //Adding test item to food list
        foodList.put(testItem2.getID(),testItem2);                              //Adding second test item to food list
        foodList.put(testItem3.getID(),testItem);                               //Adding third test item to food list
        foodList.put(testItem4.getID(),testItem2);                              //Adding fourth test item to food list
        foodList.put(testItem5.getID(),testItem);                               //Adding fifth test item to food list
        foodList.put(testItem6.getID(),testItem2);                              //Adding sixth test item to food list
        Inventory.setFoodList(foodList);                                        //sets custom food list
        
        InventoryService.inventoryCheckAlgorithm();                             // runs algorithm and finds optimal hamper
        request.createOrderFile();                                              // creates a order.txt file

        File file = new File("TestName.txt");                             //import order.txt into java
        Scanner scan = new Scanner(file);                                       //scanner for order.txt file
        String actualOrderString = "";                                          //String to store contents from order.txt

        while(scan.hasNextLine()){                                              //goes through order.txt file line by line
            actualOrderString = actualOrderString.concat(scan.nextLine()+"\n"); //adds content from each line into a string
        }
        actualOrderString = actualOrderString.trim();                           //trims extra white spaces

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
        
        Request request = new Request("Test", LocalDate.now());


        boolean returnVal = request.addHamper("Test", -1, 0, 0, -10); //Sending negative number of people into hamper



        assertTrue("Create Hamper wth negative amounts of people did not throw an exception", returnVal);
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
