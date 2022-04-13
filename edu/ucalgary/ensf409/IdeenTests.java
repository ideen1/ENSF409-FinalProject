
package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class IdeenTests {
    
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
        person.getNutrition().setPercentFV(10);
        
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
        nutrients.setPercentOther(5);
        nutrients.setPercentFV(0);
        
        
        assertEquals(50, nutrients.getPercentOther(), 50 - nutrients.getPercentOther());
        assertEquals(0.0, nutrients.getPercentFV(), 0.00 - nutrients.getPercentFV());
        assertEquals(50, nutrients.getAmountOther(), 50 - nutrients.getAmountOther());

    }

    @Test
    public void testInventoryServiceWithOptimizedValues(){
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
    }



	
}
