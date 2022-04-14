package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.ArrayList;

public class MaryTest {
    
    /*
     * Tests FoodItem class's getters
     */
    @Test
    public void testFoodItemGetters(){
        FoodItem cucumber = new FoodItem(1111, "cucumber", 100, 0, 2, 5, 10);

        assertEquals("The getID() method did not return the correct id number of the food item.", 1111, cucumber.getID());
        assertEquals("The getName() method did not return the correct description of the food item.", "cucumber", cucumber.getName());
        assertEquals("The getFruitVeggieContent() method did not return the correct fruit and vegetable content of the food item.", 100, cucumber.getFruitVeggieContent(), 100-cucumber.getFruitVeggieContent());
        assertEquals("The getGrainContent() method did not return the correct grain content of the food item.", 0, cucumber.getGrainContent(), 0-cucumber.getGrainContent());
        assertEquals("The getProteinContent() method did not return the correct protein content of the food item.", 2, cucumber.getProteinContent(), 2-cucumber.getProteinContent());
        assertEquals("The getOtherContent() method did not return the correct other content of the food item.", 5, cucumber.getOther(), 5-cucumber.getOther());
        assertEquals("The getCalories() method did not return the correct calory of the food item.", 2, cucumber.getCalories(), 2-cucumber.getCalories());

    }

    /* 
     * The nextPowerSet() method correctly returns a boolean value indicating whether or not a next powerset is available.
     * The method also correctly updates pwrSet field of InventoryService class.
     * getPwrSet() returns the correct ArrayList<int[]> containing powersets.
     */
    @Test
    public void testNextPowerSet() {
        // InventoryService test = new InventoryService();
        FoodItem food1 = new FoodItem(1, "Fruit", 20, 0, 0, 0, 5000);
        FoodItem food2 = new FoodItem(2, "Fruit", 15, 0, 0, 0, 4000);
        Inventory.addFoodItem(food1); 
        Inventory.addFoodItem(food2); 

        ArrayList<int[]> expected1 = new ArrayList<>();
        int[] arr1 = {1};
        expected1.add(arr1);
        int[] arr2 = {2};
        expected1.add(arr2);

        ArrayList<int[]> expected2 = new ArrayList<>();
        int[] arr3 = {1, 2};
        expected2.add(arr3);
    
        assertTrue("The nextPowerSet() method did not return the correct boolean value. (1)", InventoryService.nextPowerSet());
        ArrayList<int[]> result1 = InventoryService.getPwrSet();
        assertEquals("The getPwrSet() method did not retun the correct ArrayList<int[]>. (1)", expected1, result1);
        
        assertTrue("The nextPowerSet() method did not return the correct boolean value. (2)", InventoryService.nextPowerSet());
        ArrayList<int[]> result2 = InventoryService.getPwrSet();
        assertEquals("The getPwrSet() method did not retun the correct ArrayList<int[]>. (2)", expected2, result2);
        assertFalse("The nextPowerSet() method did not return the correct boolean value. (3)", InventoryService.nextPowerSet());

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
}
