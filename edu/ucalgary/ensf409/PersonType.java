/** PersonType.java 
 *  Java Enum file for ENSF409 Final Project 202 - 
 *  Winter 2022 - Group 5
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

public enum PersonType {
    CHILDUNDER8{
        private final NutritionValues NUTRITION = new NutritionValues("CHILDUNDER8");
        public  NutritionValues getNutrition(){return this.nutrition;}
    },
    CHILDOVER8{
        private final NutritionValues NUTRITION = new NutritionValues("CHILDOVER8");
        public  NutritionValues getNutrition(){return this.nutrition;}
    },
    ADULTFEMALE{
        private final NutritionValues NUTRITION = new NutritionValues("ADULTFEMALE");
        public  NutritionValues getNutrition(){return this.nutrition;}
    },
    ADULTMALE{
        private final NutritionValues NUTRITION = new NutritionValues("ADULTMALE");
        public  NutritionValues getNutrition(){return this.nutrition;}
    };
    public abstract NutritionValues getNutrition();
    
}
