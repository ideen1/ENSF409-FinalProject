/** Person.java 
 *  Java Class file for ENSF409 Final Project 202 - 
 *  Winter 2022 - Group 5
 *  Copyright © 2022 I.B., T.D., M.G.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;
import java.util.*;

public class Person {
    enum PersonType {
        CHILDUNDER8{
            public int getCalories(){
                return ;
            }
            public int getProteins(){
                return ;
            }
            public int getFruitVeggies(){
                return ;
            }
            public int getWholeGrains(){
                return ;
            }
            public int getOther(){
                return ;
            }
        },
        CHILDOVER8{
            public int getCalories(){
                return ;
            }
            public int getProteins(){
                return ;
            }
            public int getFruitVeggies(){
                return ;
            }
            public int getWholeGrains(){
                return ;
            }
            public int getOther(){
                return ;
            }

        },
        ADULTFEMALE{
            public int getCalories(){
                return ;
            }
            public int getProteins(){
                return ;
            }
            public int getFruitVeggies(){
                return ;
            }
            public int getWholeGrains(){
                return ;
            }
            public int getOther(){
                return ;
            }

        },
        ADULTMALE{
            public int getCalories(){
                return ;
            }
            public int getProteins(){
                return ;
            }
            public int getFruitVeggies(){
                return ;
            }
            public int getWholeGrains(){
                return ;
            }
            public int getOther(){
                return ;
            }

        };
        public abstract int getCalories();
        public abstract int getProteins();
        public abstract int getFruitVeggies();
        public abstract int getWholeGrains();
        public abstract int getOther();
    }

    private final PersonType type;

    public Person(String type){
        this.type = convertToPersonType(type);
    }

    private PersonType convertToPersonType(String type){
        return PersonType.valueOf(type);
    }
}
