/** PersonType.java 
 *  Java Enum file for ENSF409 Final Project - Winter 2022 - Group 5 
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

/**
* Possible PersonTypes
*/
public enum PersonType {
    /**
    * Stores a NutritionValue object for the Person Type "CHILDUNDER8"
    */
    CHILDUNDER8{
        private final NutritionValues NUTRITION = new NutritionValues("CHILDUNDER8");
        public  NutritionValues getNutrition(){return this.NUTRITION;}
    },
    /**
    * Stores a NutritionValue object for the Person Type "CHILDOVER8"
    */
    CHILDOVER8{
        private final NutritionValues NUTRITION = new NutritionValues("CHILDOVER8");
        public  NutritionValues getNutrition(){return this.NUTRITION;}
    },
    /**
    * Stores a NutritionValue object for the Person Type "ADULTFEMALE"
    */
    ADULTFEMALE{
        private final NutritionValues NUTRITION = new NutritionValues("ADULTFEMALE");
        public  NutritionValues getNutrition(){return this.NUTRITION;}
    },
    /**
    * Stores a NutritionValue object for the Person Type "ADULTMALE"
    */
    ADULTMALE{
        private final NutritionValues NUTRITION = new NutritionValues("ADULTMALE");
        public  NutritionValues getNutrition(){return this.NUTRITION;}
    };
    /**
    * Returns the enum's nutrition values object
    * @return NutritionValues
    */
    public abstract NutritionValues getNutrition();
    
}
