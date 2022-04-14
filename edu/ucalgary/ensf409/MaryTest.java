package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.time.LocalDate;

public class MaryTest {
    
    /*
     * Tests FoodItem class's getters
     */
    @Test
    public void testFoodItemGetters(){
        FoodItem cucumber = new FoodItem(1111, "cucumber", 100, 0, 2, 5, 10);

        assertEquals("The getID() method did not return the correct id number of the food item.", 1111, cucumber.getID());
        assertEquals("The getName() method did not return the correct description of the food item.", "cucumber", cucumber.getName());
        assertEquals("The getFruitVeggieContent() method did not return the corrent fruit and vegetable content of the food item.", 100, cucumber.getFruitVeggieContent());
        assertEquals("The getGrainContent() method did not return the corrent grain content of the food item.", 0, cucumber.getGrainContent());
        assertEquals("The getProteinContent() method did not return the correct protein content of the food item.", 2, cucumber.getProteinContent());
        assertEquals("The getOtherContent() method did not return the correct other content of the food item.", 5, cucumber.getOther());
        assertEquals("The getCalories() method did not return the correct calory of the food item.", 2, cucumber.getCalories());

    }

    /* 
     * Tests generatePwrSet()
     * The static method is called with an array of integers and the size of the set
     * as arguments. When the given size is smaller than or equal to the size of the
     * integer array, the method will create an ArrayList of integer arrays containing 
     * different combinations of the given integers. Otherwise ... ?
     * give error when size is 0 or bigger than the size of the given integer array
     */
    @Test
    public void testGeneratePwrSet() {
        int[] input = {0,1,2};
        ArrayList<int[]> result = InventoryService.generatePwrSet(input, 0);
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
