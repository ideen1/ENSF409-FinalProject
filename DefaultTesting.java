
package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;

public class TestClassName {
    
    public TestClassName() {
    }
    
    @Test
    public void testNumberOne() {
        
        int[] birthDate = new int[]{2011,1,1};
        
        ClassName object= new ClassName("Pongo");
        
        System.out.println("calculateAge");
        int expResult = 11;
        int result = object.calculateAge();
        assertEquals("Was incorrect: ", expResult, result);
    }

	
}