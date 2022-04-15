/** Person.java 
 *  Java Class file for ENSF409 Final Project - Winter 2022 - Group 5
 *  Represents a Person used in Hamper.
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

public class Person {

    private final PersonType TYPE;
    /**
     * Person() constructor
     * @param type The type of person
     */
    public Person(PersonType type){
        this.TYPE = type;
    }

    /**
     * getPersonType()
     * Gets the type of person
     * @return A PersonType object that represents the type of person
     */
    public PersonType getPersonType() {
        return TYPE;
    }

    /**
     * getNutrition()
     * Gets the nutrition needs of the specified type of person
     * @return NutritionValues object that contains the nutritional needs information of the person type
     */
    public NutritionValues getNutrition() {
        return this.TYPE.getNutrition();
    }

}
