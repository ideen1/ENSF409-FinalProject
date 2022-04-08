/** Person.java 
 *  Java Class file for ENSF409 Final Project 202 - 
 *  Winter 2022 - Group 5
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

public class Person {

    private final PersonType TYPE;

    public Person(PersonType type){
        this.TYPE = type;
    }

    public PersonType getPersonType() {
        return TYPE;
    }

    public NutritionValues getNutrition() {
        return this.TYPE.getNutrition();
    }

}
