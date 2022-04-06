package edu.ucalgary.ensf409;

import java.awt.EventQueue;
/**
 * HamperApp
 */
public class HamperApp {
    public static Request currentRequest;
    public static Inventory inventory;
    public static void main(String[] args) {

        // START Pre GUI Initialization Checks
        // Test DB Connection
        if (!DBConnection.testDBConnection()){
            GUIViewController.genericError("Could not connect to Database");
        }
        // END Pre GUI Initialization Checks


        EventQueue.invokeLater(() -> {
            // Create App Window Frame
            GUIViewController mainScreen = new GUIViewController();  
            // Set Frame to visible
            mainScreen.setVisible(true);   
            // Load Home by Default
            mainScreen.GUILoadHome();

        });
    }
}