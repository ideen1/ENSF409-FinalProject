/** HamperApp.java 
 * Java Class file for ENSF409 Final Project - Winter 2022 - Group 5
 * Contains the main() to run the program.
 * Copyright © 2022 I.B., T.D., M.M.
 * @author Ideen
 * @version 1.7 
 * @since 1.1
 */

package edu.ucalgary.ensf409;

import java.awt.EventQueue;

/**
 * HamperApp
 */
public class HamperApp {
    public static Request currentRequest;
    public static Inventory inventory;
    public static GUIViewController mainScreen;
    
    /**
    * The main entry point to the program
    * @return void
    * @param args
    */
    public static void main(String[] args) {

        // START Pre GUI Initialization Checks
        // Test DB Connection
        
        if (!DBConnection.testDBConnection()){
            GUIViewController.genericError("Could not connect to Database");
        }
        // END Pre GUI Initialization Checks
        EventQueue.invokeLater(() -> {
            // Create App Window Frame
            mainScreen = new GUIViewController();  
            Inventory.loadInventory(); 
            // Set Frame to visible
            mainScreen.setVisible(true);   
            // Load Home by Default
            mainScreen.GUILoadHome();
            
        });
        

    }
    




    

}