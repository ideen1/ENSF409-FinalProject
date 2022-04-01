package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;


public class TanishTests {
    
    @Test 
    public void testAddHamper() {
        int expectedlistsize = Request.getHampers().size() + 1;

        Request.addHamper();
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

    //testing iventory class 
    @Test 
    public void testSetFoodlist(){
        
    }

    }