/** Person.java 
 *  Java Class file for ENSF409 Final Project 202 - 
 *  Winter 2022 - Group 5
 *  Copyright © 2022 I.B., T.D., M.G.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

public class Person {

    private final PersonType type;

    public Person(PersonType type){
        this.type = type;
    }

    public PersonType getPersonType() {
        return type;
    }

    public NutritionValues getNutrition() {
        return this.type.getNutrition();
    }

}
