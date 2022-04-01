package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;


public class TanishTests {
    
    @Test 
    public void testAddHamper() {
        int expectedlistsize = Request.getHampers().size() + 1;

        Request.addHamper("Test client", 1, 1, 1, 1);
        int actuallistsize = Request.getHampers().size();
        
        assertEquals("The hamper array list did not get updated",expectedlistsize, actuallistsize);
        
    }

    @Test 
    public void testCreateOrderFile(){
        
        boolean orderGenarated = true;

        try {
            Request.createOrderFile();

        } catch (Exception e) {

            orderGenarated = false;
        }

        assertTrue("Order file could not be created because of exception", orderGenarated);
    }
    @Test 
    public void testGenerateOrderForm(){

        String expectedOrderString = "Some example order string we expect to get from order form";

        Request.genarateOrderForm();
        StringBuilder actualOrderString = new StringBuilder();
        // convert txt created by genarateOrderForm method to a string and compare that to expectedOrderString

        assertEquals("The actual string did not match the expected string",expectedOrderString, actualOrderString);
    }
    @Test
    public void TesttwoHampersWithSameClient(){
        Request request = new Request("test", LocalDate.now());
        request.addHamper("client 1", 1, 1, 1, 1);
        request.addHamper("client 1", 2, 2, 2, 2);

        assertFalse("Second hamper with the same client name is a copy of the first hamper", request.getHampers().get(0) ==request.getHampers().get(1));
        assertTrue("The second client request didn't get made", request.getHampers().get(1) != null);
    }

    //testing iventory class 
    @Test 
    public void testSetFoodlist(){
        FoodItem testItem = new FoodItem(1, "eggs", 30, 10, 20, 45,1000);
        FoodItem testItem2 = new FoodItem(2, "bread", 34, 12, 27, 42,1023);

        HashMap<Integer,FoodItem> foodList = new HashMap<Integer,FoodItem>();
        foodList.put(testItem.getID(),testItem);
        foodList.put(testItem2.getID(),testItem2);
        Inventory.setFoodList(foodList);
        
        HashMap<Integer,FoodItem> incomingList = Inventory.getFoodlist();

        assertEquals("The actual item name did not match the expected item name", incomingList.get(1).getName(),testItem.getName());

    }

    }