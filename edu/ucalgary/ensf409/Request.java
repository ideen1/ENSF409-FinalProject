package edu.ucalgary.ensf409;

import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

/** Represents a request which is created every time the user initiates a request using the program.
 * Winter 2022 - Group 5
 * Copyright © 2022 I.B., T.D., M.M.
 * @author Ideen, Tanish, Mary 
 * @version 1.4
 * @since 1.0
*/
public class Request{
	private ArrayList<Hamper> hampers = new ArrayList<Hamper>();
    private LocalDate date;
    private String nameRequest;

    /** Request Constructor
     * Receives and sets the name and date of the request
	 * @param nameRequest The name of the request
	 * @param date The date the request is created
	 */ 
    public Request(String nameRequest, LocalDate date){
        this.nameRequest = nameRequest;
        this.date = date;
    }

    /** addHamper()
     * Checks if the user input is correct for each hamper and if it's correct, adds the hamper to the request
     * @param clientName The name of the client for whom the hamper is being made
     * @param numAdultMales The number of adult males to be included in the hamper
     * @param numAdultFemales The number of adult females to be included in the hamper
     * @param numChildUnder8 The number of children under the age of 8 to be included in the hamper
     * @param numChildOver8 The number of children over the age of 8 to be included in the hamper
	 * @return A boolean value representing whether or not the hamper was successfully added to the request
	 */
    public boolean addHamper(String clientName, int numAdultMales, int numAdultFemales, int numChildUnder8, int numChildOver8){
        if (numAdultFemales < 0 && numAdultMales < 0 && numChildOver8 < 0 && numChildUnder8 < 0){
            GUIViewController.genericError("Amount of people must be 0 or greater.");
            return false;
        }

        Hamper addHamper = new Hamper(clientName, numAdultMales, numAdultFemales, numChildUnder8, numChildOver8);
        hampers.add(addHamper);
        return true;
    }

    /** createOrderFile
     * Creates a text file with the name of the request that lists out the details of the request 
     */
    public void createOrderFile() {

        try {
            String orderName = this.nameRequest;
            PrintWriter orderForm = new PrintWriter(orderName+".txt"); // A .txt file with the name of the request will be created.
            
            // First four lines of text: 
            orderForm.print("Group 5 Food Bank\nHamper OrderForm\n\nName: "
            		+ orderName + "\nDate: " + this.date.toString()+"\n\nOriginal Request\n");
            
            // Writing Original Request:
            for (int i = 0; i < hampers.size(); i++) {
            	Hamper hamp = hampers.get(i);
            	orderForm.printf("Hamper %d(%s): ", (i+1), hamp.getClientName());
            	int male = hamp.getNumAdultMales();
            	int female = hamp.getNumAdultFemales();
            	int under8 = hamp.getNumChildUnder8();
            	int over8 = hamp.getNumChildOver8();
            	
            	if (male != 0) {
            		orderForm.printf("%d Adult Male", male);
            	} 
            	if (female !=0) {
            		if (male != 0) {
            			orderForm.print(", ");
            		}
            		orderForm.printf("%d Adult Female", female);
            	}
            	if (under8 !=0) {
            		if (male != 0 || female != 0) {
            			orderForm.print(", ");
            		}
            		orderForm.printf("%d Child uner 8", under8);
            	}
            	if (over8 !=0) {
            		if (male != 0 || female != 0 || under8 != 0) {
            			orderForm.print(", ");
            		}
            		orderForm.printf("%d Child over 8", over8);
            	}
            	orderForm.println();
            }
            
            // Writing the food list:
            for (int i = 0; i < hampers.size(); i++) {
                Hamper tmp = hampers.get(i);
            	orderForm.printf("\nHamper %d(%s) Items:\n", (i+1), tmp.getClientName());
            	
            	for (int id : tmp.getAllocatedItems()) {
            		orderForm.printf("%d\t%s\n", id, HamperApp.inventory.getFood(id).getName());
            	}
            }
            orderForm.println();
            orderForm.close();
            
        } catch (Exception e) {
            GUIViewController.genericError("Error writing order form to File");
            e.printStackTrace();
        }
    }

    /** gethampers()
     * Gets the hampers included in the request
     * @return An ArrayList that contain all of Hamper objects that are included in the request
     */
    public ArrayList<Hamper> getHampers(){
        return hampers;
    }
}