
package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;

public class IdeenTests {
    
    // Person Tests:
    @Test
    public void testPersonConstructor() {
        
        Person person = new Person(PersonType.ADULTMALE);
        assertTrue("The person constructor did not initialize an Object", person != null);
    }


    @Test
    public void testPersonNutrition() {
        Person person = new Person(PersonType.ADULTFEMALE);
        NutritionValues nutrition = person.getNutrition();

        person.getNutrition().setTotalNeedCalories(2000);
        person.getNutrition().setPercentFV(0.1);
        
        assertTrue("NutritionValues Object was not returned from person", nutrition != null);
        assertEquals(String.valueOf(200.0) , String.valueOf(nutrition.getAmountFV()));
    }

    // Hamper Tests:

    @Test
    public void testHamperConstructorWith0People() {
        
        Hamper hamper1 = new Hamper("test", 0, 0, 0, 0);
        
        assertTrue("Hamper object was not created when there are 0 people", hamper1 != null);
    }


    @Test
    public void testHamperConstructorWithOnly1Child() {
        
        Hamper hamper1 = new Hamper("test", 0, 0, 0, 1);
        
        assertTrue("Hamper object was not created with only 1 child", hamper1 != null);
    }

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


	
}
