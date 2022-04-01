package edu.ucalgary.ensf409;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import org.junit.*;

public class TestingMary {
	
	/* What do I want to test?????
	 * 
	 * Need a Hamper[] and an Inventory objects to construct an object of InventoryService class with
	 * 
	 * The constructor should set the hampersToCheck and inventory properly
	 * 
	 * fillHamper() should properly update the tmpUsed field in appropriate FoodItem obj, the canBeFulfilled 
	 * and the allocatedItems fields in each Hamper, and the missingCategory filed if needed.
	 * 
	 * inventoryCheckAlgorithm() will use fillHamper() helper method in a loop, use checkHamperAvail(), and depending
	 * the content of the missingCategory:
	 * 	if empty:
	 * 			update the inventory by deleting the FoodItem's that have "true" tmpUsed field
	 * 			create an order form (.txt)
	 * 	if !empty:
	 * 			reset the tmpUsed field of all FoodItem's in the Inventory to "false"
	 * 			throw the custom exception InventoryNotAvailableException, with a message indicating which food category 
	 * 			is short (missingCategory content)
	 * 
	 * 
	 * 
	 */
	
	private final Hamper HAMPER1 = new Hamper("client1",2,1,1,1);
	private final Hamper HAMPER2 = new Hamper("client2", 1,2,1,1);
	
	private final Hamper HAMPER3 = new Hamper("client3-cannotBeFilled", 3,3,3,3);
	private final Hamper HAMPER4 = new Hamper("client4-canBeFilled", 0,0,1,1);
	
	private final Hamper[] HAMPERS1 = {HAMPER1, HAMPER2};
	private final Hamper[] HAMPERS2 = {HAMPER2, HAMPER2};
	
	private final INVENTORY = new Inventory();
	
	
	
    private final String TEST_FILE_NAME = "example.txt";
    private final String[] EXAMPLE_DATA = {
        "@article{barcomb:2020:managing,",
        "   title={Managing Episodic Contributors in Free/ Libre/ Open Source Software Communities},",
        "   author={Barcomb, Ann and Stol, Klaas-Jan and Fitzgerald, Brian and Riehle, Dirk},",
        "   year={2020}",
        "}"
    };

	    
    @Test
	public void testReadFileReturnsFalseBadInput() {
	        // Test data - these values may be changed in actual tests 
	        File noSuchFile = new File("this_file_does_not_exist.txt");

	        // Variables for status
	        boolean returnIsTrue = true;
	        boolean exceptionThrown = false;

	        // Create object, read file
	        BibReader bib = new BibReader(noSuchFile);
	        try {
	            returnIsTrue = bib.readFile();
	        }
	        catch (Exception e) {
	            exceptionThrown = true;
	        }

	        assertFalse("An unexpected exception was thrown when the file couldn't be read", exceptionThrown);
	        assertFalse("readFile() returned true when operation failed due to file not existing", returnIsTrue);
	    }
	    /*
	     * BibReader(File) is called with a file which can be read (in working directory).
	     * readFile() returns true and does not throw an exception.
	     * getContents() returns a String of the contents of the file.
	    */
	    @Test
	    public void testReadFileReturnsTrueGoodInput() {
	        
	        createTestFile();
	        File in = new File(TEST_FILE_NAME);

	        // Variables for status
	        boolean returnIsTrue = true;
	        boolean exceptionThrown = false;
	        
	        // Create object, read file
	        BibReader bib = new BibReader(in);
	        try {
	            returnIsTrue = bib.readFile();
	        }
	        catch (Exception e) {
	            exceptionThrown = true;
	        }

	        assertFalse("An unexpected exception was thrown when reading a valid input file", exceptionThrown);
	        assertTrue("readFile() returned false when operation should have been successful", returnIsTrue);
	    }
	    /*
	     * BibReader(File) is called with a file which can be read (in working directory).
	     * readFile() returns true and does not throw an exception.
	     * getContents() returns a String of the contents of the file.
	    */
	    @Test
	    public void testReadFileFillsContents() {
	        
	        createTestFile();
	        File in = new File(TEST_FILE_NAME);

	        // Variables for status
	        boolean returnIsTrue = true;
	        boolean exceptionThrown = false;
	        
	        StringBuilder tmp = new StringBuilder();;
	        
	        for (int i=0; i<EXAMPLE_DATA.length; i++) {
	            tmp.append(EXAMPLE_DATA[i]+"\n");
	        }
	        String expectedContents = tmp.toString();

	        // Create object, read file
	        BibReader bib = new BibReader(in);
	        try {
	            returnIsTrue = bib.readFile();
	        }
	        catch (Exception e) {
	            exceptionThrown = true;
	        }
	        String actualContents = bib.getContents();

	        assertFalse("An unexpected exception was thrown when reading a valid input file", exceptionThrown);
	        assertTrue("readFile() returned false when operation should have been successful", returnIsTrue);
	        assertEquals("Value of contents did not match what was expected", expectedContents, actualContents);
	    }
	    /*
	     * BibReader(File) is called with a file which can be read (in working directory).
	     * The file is not actually used. setContents(String) is used to set data.
	     * getContents() returns a String of the contents of the data.
	    */
	    @Test
	    public void testSetGetContents() {
	        
	        // Test data - these values may be changed in actual tests 
	        String givenData = "@inproceedings{marasco:2017:flipping,\n" +
	          "  TITLE= {Flipping the foundation: A multi-year flipped classroom study for a large-scale introductory programming course},\n"+
	          "  AUTHOR={ Marasco, Emily Ann and Moshirpour, Mohammad and Moussavi, Mahmood },\n" +
	          "  BOOKTITLE={ 2017 ASEE Annual Conference \\& Exposition },\n" +
	          "  YEAR = {2017}\n}\n";

	        // Create object, parse data
	        BibReader bib = new BibReader(new File("some_file_name.txt"));
	        bib.setContents(givenData);
	        String actualContents = bib.getContents();

	        assertEquals("Value of contents did not match what was expected", givenData, actualContents);
	    }
	    /*
	     * BibReader(File) is used to create an object.
	     * The file is not actually used. setContents(String) is used to set data.
	     * parseContents() should be able to extract year from data.
	     * getAttribute("year") returns the expected value.
	    */
	    @Test
	    public void testParseYear() {
	        // Test data - these values may be changed in actual tests 
	        String expectedValue = "2017";
	        String givenData = "@inproceedings{marasco:2017:flipping,\n" +
	          "  TITLE= {Flipping the foundation: A multi-year flipped classroom study for a large-scale introductory programming course},\n"+
	          "  AUTHOR={ Marasco, Emily Ann and Moshirpour, Mohammad and Moussavi, Mahmood },\n" +
	          "  BOOKTITLE={ 2017 ASEE Annual Conference \\& Exposition },\n" +
	          "  YEAR = {2017}\n}\n";

	        // Create object, parse data
	        BibReader bib = new BibReader(new File("some_file_name.txt"));
	        bib.setContents(givenData);
	        boolean returnValue = bib.parseContents();

	        String actualValue = bib.getAttribute("year"); 
	        assertTrue("parseContents() did not return true when finding 'year'", returnValue);
	        assertEquals("Value of attribute 'year' did not match expected value", expectedValue, actualValue);
	    }


	    /*
	     * BibReader(File) is used to create an object.
	     * The file is not actually used. setContents(String) is used to set data.
	     * parseContents() should be able to extract title from data.
	     * getAttribute("title") returns the expected value.
	    */
	    @Test
	    public void testParseTitle() {
	        // Test data - these values may be changed in actual tests
	        String expectedValue = "Flipping the foundation: A multi-year flipped classroom study for a large-scale introductory programming course";
	        String givenData = "@inproceedings{marasco:2017:flipping,\n" +
	          "  AUTHOR={ Marasco, Emily Ann and Moshirpour, Mohammad and Moussavi, Mahmood },\n" +
	          "  BOOKTITLE={ 2017 ASEE Annual Conference \\& Exposition },\n" +
	          "  title = {Flipping the foundation: A multi-year flipped classroom study for a large-scale introductory programming course   },\n"+
	          "  YEAR = {2017}\n}\n";

	        // Create object, parse data
	        BibReader bib = new BibReader(new File("some_file_name.txt"));
	        bib.setContents(givenData);
	        boolean returnValue = bib.parseContents();

	        String actualValue = bib.getAttribute("title");
	        assertTrue("parseContents() did not return true when finding 'title'", returnValue);
	        assertEquals("Value of attribute 'title' did not match expected value", expectedValue, actualValue);
	    }

	    
	    /*
	     * BibReader(File) is used to create an object.
	     * The file is not actually used. setContents(String) is used to set data.
	     * parseContents() should be able to extract author from data.
	     * getAttribute("author") returns the expected value.
	    */
	    @Test
	    public void testParseAuthor() {
	        // Test data - these values may be changed in actual tests
	        String expectedValue = "Kaufmann, Andreas and Barcomb, Ann and Riehle, Dirk";
	        String givenData = "@inproceedings{kaufmann:2020:supporting,\n" +
	           "   title={Supporting Interview Analysis with Autocoding}, \n" +
	           "   author={Kaufmann, Andreas and Barcomb, Ann and Riehle, Dirk},\n" +
	           "   booktitle={Hawaii International Conference on System Sciences},\n year={2020},\n" +
	           "   month={January},\n" +
	           "   numpages={10},\n" +
	           "   publisher={IEEE}}\n";

	        // Create object, parse data
	        BibReader bib = new BibReader(new File("some_file_name.txt"));
	        bib.setContents(givenData);
	        boolean returnValue = bib.parseContents();

	        String actualValue = bib.getAttribute("author");
	        assertTrue("parseContents() did not return true when finding 'author'", returnValue);
	        assertEquals("Value of attribute 'author' did not match expected value", expectedValue, actualValue);
	    }

	    
	    /*
	     * BibReader(File) is used to create an object.
	     * The file is not actually used. setContents(String) is used to set data.
	     * parseContents() should be able to extract author, title, and year from data.
	     * getAttributes() returns a HashMap which contains the keys author, title, and year.
	     * Each key contains the data which was extracted.
	    */
	    @Test
	    public void testParseContents() {
	        // Test data - these values may be changed in actual tests
	        String[] expectedValues = {
	            "2016",
	            "Using gamification for engagement and learning in electrical and computer engineering classrooms",
	            "Marasco, Emily and Behjat, Laleh and Eggermont, Marjan and Rosehart, William and Moshirpour, Mohammad and Hugo, Ron"
	        };
	        String givenData = "@inproceedings{marasco2016using,\n" +
	          "  title = {Using gamification for engagement and learning in electrical and computer engineering classrooms},\n" +
	          "  author = {Marasco, Emily and Behjat, Laleh and Eggermont, Marjan and Rosehart, William and Moshirpour, Mohammad and Hugo, Ron},\n" +
	          "  pages = {1--4},\n" +
	          "  YEAR = {2016},\n" +
	          "  organization = {IEEE}\n" +
	          "}";

	        // Create object, parse data
	        BibReader bib = new BibReader(new File("some_file_name.txt")); 
	        bib.setContents(givenData);
	        boolean returnValue = bib.parseContents();
	        HashMap<String, String> actualValues = bib.getAttributes();

	        assertTrue("parseContents() did not return true", returnValue);
	        assertEquals("Unable to retrieve year from getAttributes()", expectedValues[0], actualValues.get("year"));
	        assertEquals("Unable to retrieve title from getAttributes()", expectedValues[1], actualValues.get("title"));
	        assertEquals("Unable to retrieve author from getAttributes()", expectedValues[2], actualValues.get("author"));
	    }

	    
	    /*
	     * BibReader(File) is used to create an object.
	     * The file is not actually used. setContents(String) is used to set data.
	     * parseContents() should be able to extract author, title, and year from data.
	     * Only the first instance of author, title, and year are extracted.
	     * getAttribute("author"), getAttribute("title") and getAttribute("year") are used to check the data.
	    */
	    @Test
	    public void testParseDuplicates() {
	        // Test data - these values may be changed in actual tests
	        String[] expectedValues = {
	            "2021",
	            "Importance of data analytics for improving teaching and learning methods",
	            "Moussavi, Mahmood and Amannejad, Yasaman and Moshirpour, Mohammad and Marasco, Emily and Behjat, Laleh"
	        };
	        String givenData = "@incollection{moussavi2020importance,\n"+
	          "  title={Importance of data analytics for improving teaching and learning methods},\n"+
	          "  author={Moussavi, Mahmood and Amannejad, Yasaman and Moshirpour, Mohammad and Marasco, Emily and Behjat, Laleh},\n"+
	          "  booktitle={Data management and analysis},\n"+
	          "  pages={91--101},\n"+
	          "  year={2021},\n"+
	          "  publisher={Springer}\n"+
	          "}\n"+
	          "@inbook{olteanu:2017:towards,\n" +
	          "    author={Olteanu, Alexandru-Liviu and Meyer, Patrick and Barcomb, Ann and Jullien, Nicolas},\n" +
	          "    title={Towards a Protocol for Inferring Preferences Using Majority-rule Sorting Models},\n" +
	          "    bookTitle={Algorithmic Decision Theory: 5th International Conference, ADT 2017, Luxembourg, Luxembourg, October 25--27, 2017, Proceedings},\n"+
	          "    year={2017},\n" +
	          "    publisher={Springer International Publishing},\n" +
	          "    address={Cham},\n" +
	          "    pages={35--49},\n" +
	          "    isbn={978-3-319-67504-6},\n" +
	          "    doi={10.1007/978-3-319-67504-6_3},\n" +
	          "    url={https://doi.org/10.1007/978-3-319-67504-6_3}\n" +
	          "}\n" ;

	        // Create object, parse data
	        BibReader bib = new BibReader(new File("some_file_name.txt"));
	        bib.setContents(givenData);
	        boolean returnValue = bib.parseContents();
	        String[] actualValues = {
	            bib.getAttribute("year"),
	            bib.getAttribute("title"),
	            bib.getAttribute("author")
	        };
	        assertTrue("parseContents() did not return true", returnValue);
	        for (int i=0; i<expectedValues.length; i++) {
	            assertEquals("First value was not identified with parseContents() when data contains duplicates", expectedValues[i], actualValues[i]);
	        }
	    }
	    /*
	     * BibReader(File) is used to create an object.
	     * The file is not actually used.
	     * getAttribute(String) returns an empty string when called with an invalid key.
	    */
	    @Test
	    public void testGetAttributeEmpty() {
	        String expectedValue = "";
	        BibReader bib = new BibReader(new File("some_file_name.txt"));
	        String actualValue = bib.getAttribute("cat");

	        assertEquals("getAttribute() does not return an empty String for unavailable attribute", expectedValue, actualValue);
	    }
	    /*
	     * BibReader(File) is used to create an object.
	     * The file is not actually used. setContents(String) is used to set data.
	     * parseContents() should be able to extract author, title, and year from data.
	     * getAttribute(String) is case-insensitive for the parameter.
	    */
	    @Test
	    public void testGetAttributeIsCaseInsensitive() {
	        // Test data - these values may be changed in actual tests
	        String expectedValues[] = {
	            "2017", 
	            "Towards a Protocol for Inferring Preferences Using Majority-rule Sorting Models",
	            "Olteanu, Alexandru-Liviu and Meyer, Patrick and Barcomb, Ann and Jullien, Nicolas"
	        };
	        String givenData = "@inbook{olteanu:2017:towards,\n" +
	          "    author={Olteanu, Alexandru-Liviu and Meyer, Patrick and Barcomb, Ann and Jullien, Nicolas},\n" +
	          "    title={Towards a Protocol for Inferring Preferences Using Majority-rule Sorting Models},\n" +
	          "    bookTitle={Algorithmic Decision Theory: 5th International Conference, ADT 2017, Luxembourg, Luxembourg, October 25--27, 2017, Proceedings},\n"+
	          "    year={2017},\n" +
	          "    publisher={Springer International Publishing},\n" +
	          "    address={Cham},\n" +
	          "    pages={35--49},\n" +
	          "    isbn={978-3-319-67504-6},\n" +
	          "    doi={10.1007/978-3-319-67504-6_3},\n" +
	          "    url={https://doi.org/10.1007/978-3-319-67504-6_3}\n" +
	          "}\n" ;

	        // Create object, set data, parse data
	        BibReader bib = new BibReader(new File("some_file_name.txt"));
	        bib.setContents(givenData);
	        bib.parseContents();

	        // Get values - check that keys are case insensitive
	        String[] actualValues = {
	            bib.getAttribute("YEAR"),
	            bib.getAttribute("tItlE"),
	            bib.getAttribute("AuTHoR")
	        };
	        for (int i=0; i<expectedValues.length; i++) {
	            assertEquals("getAttribute() is not case-insensitive", expectedValues[i], actualValues[i]);
	        }
	    }

	    
	    /*
	     * Test of complete process.
	     * BibReader(File) is used to create an object.
	     * readFile() is able to read in the file.
	     * parseContents() is able to parse the data.
	     * getAttributes() returns a HashMap which contains the keys author, title, and year.
	     * Each key contains the data which was extracted.
	    */
	    @Test
	    public void testCompleteProcessGetAttributes() {
	        createTestFile();
	        File in = new File(TEST_FILE_NAME);

	        // Variables for status, expected values
	        boolean exceptionThrown = false;
	        String[] expectedValues = {
	           "2020",
	           "Managing Episodic Contributors in Free/ Libre/ Open Source Software Communities",
	           "Barcomb, Ann and Stol, Klaas-Jan and Fitzgerald, Brian and Riehle, Dirk"
	        };

	        // Create object, read file
	        BibReader bib = new BibReader(in);
	        try {
	            bib.readFile();
	        }
	        catch (Exception e) {
	            exceptionThrown = true;
	        }

	        // Get actual values, provided no exception was thrown
	        assertFalse("An unexpected exception was thrown when reading a valid input file", exceptionThrown); 
	        bib.parseContents();
	        HashMap<String, String> actualValues = bib.getAttributes();

	        assertEquals("Unable to retrieve year from getAttributes()", expectedValues[0], actualValues.get("year"));
	        assertEquals("Unable to retrieve title from getAttributes()", expectedValues[1], actualValues.get("title"));
	        assertEquals("Unable to retrieve author from getAttributes()", expectedValues[2], actualValues.get("author"));
	    }

	    
	    
	    
	    /* ******************* HELPER METHODS ***************** */

	    /*
	    * Create a test file to read in (in working directory)
	    */
	    private void createTestFile() {
	        File outFile = new File(TEST_FILE_NAME); 
	        try {
	            BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
	            for (int i=0; i < EXAMPLE_DATA.length; i++) {
	                out.write(EXAMPLE_DATA[i], 0, EXAMPLE_DATA[i].length());
	                out.newLine();
	            }
	            out.close();
	        }
	        catch (Exception e) { } // Nothing meaningful to do with an error
	    }

	    /*
	    * Clean up by removing the test file
	    */
	    @After
	    public void removeTestFile() {
	        File bye = new File(TEST_FILE_NAME);
	        bye.delete();
	    }
	}

}
