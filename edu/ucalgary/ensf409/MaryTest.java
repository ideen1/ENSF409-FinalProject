package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;

import java.beans.Transient;

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
     * Tests getMissingCategory()
     * 
     * */
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

        FoodItem insuffcientItem = new FoodItem(1, "Fruit", 40, 0, 0, 0, 5200);

        Inventory.addFoodItem(insuffcientItem);  

        InventoryService.inventoryCheckAlgorithm();

        boolean result = InventoryService.getMissingCategory().get("Fruit/Veggies");
        assertTrue("The getMissingCategory() method did not return the correct missing category information.", result);
    }
}
