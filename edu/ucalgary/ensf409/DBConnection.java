/** DBConnection.java
 *  Java Class file for ENSF409 Final Project - Winter 2022 - Group 5
 *  Represents connection to the database
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen, Tanish, Mary 
 *  @version 1.6
 *  @since 1.4
 */

 package edu.ucalgary.ensf409;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    public static final String DBURL = "jdbc:mysql://10.0.0.11/FOOD_INVENTORY";
    public static final String USERNAME = "student";
    public static final String PASSWORD = "ensf";    
    
    private Connection dbConnect;
    private ResultSet results;
    
    /**
     * initializeConnection()
     * Starts a connection to the MySQL Database 
     * and stores it in dbConnect.
     * @return void
     * @param DBURL,USERNAME,PASSWORD passed in as arguments,
     * 
     */
    public void initializeConnection(){

    try {
        this.dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
    } 
    catch (SQLException e) {
        GUIViewController.genericError("Could not initialize Connection");
        System.out.println("HAMPER APP - SQL DEBUG FOR TA's: ");
        System.out.println(e.toString());
    }
    }

    /**
     * preparedQuery()
     * Runs a prepared query statement against the connected database
	 * @return void 
	 * @param query A string representing a prepared SQL statement
     * @param args A variable length arguments of that will 
     * replace the ?'s in the prepared statement
	 */
    public void preparedQuery(String query, String... args ){

        try {
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            
            int i = 1;
            for (String arg : args){
                myStmt.setString(i, arg);
                i++;
            }
            myStmt.executeUpdate();
            
            myStmt.close();
            }   
        catch (SQLException e) {
            GUIViewController.genericError("Error processing prepared statement");
            System.out.println("HAMPER APP - SQL DEBUG FOR TA's: ");
            System.out.println(e.toString());
            }
        }

    /**
     * customQuery()
     * Runs a query passed in from the argument
     * @param query A string that represents an SQL query
     * @return A ResultSet that represents the result returned form the database
     */
    public ResultSet customQuery(String query){

        try {
            Statement myStmt = dbConnect.createStatement();
            ResultSet results = myStmt.executeQuery(query);
            return results;
            
        } catch (SQLException e) {
            GUIViewController.genericError("Error processing custom query");
            System.out.println("HAMPER APP - SQL DEBUG FOR TA's: ");
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * testDBConnection()
     * Checks whehter or not the database connection was successful
     * @return A boolean value indicating whether or not the database connection was successful. 
     */
    public static boolean testDBConnection(){
            try {
                Connection dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
                return true;
            } 
            catch (SQLException e) {
                System.out.println("HAMPER APP - SQL DEBUG FOR TA's: ");
                System.out.println(e.toString());
                return false;
            }
        }    

    
}
