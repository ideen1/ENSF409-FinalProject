package edu.ucalgary.ensf409;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    public static final String DBURL = "";
    public static final String USERNAME = "";
    public static final String PASSWORD = "";    
    
    private Connection dbConnect;
    private ResultSet results;
    
    /*
    public DBConnection(String url, String user, String pw){
        // Database URL
        this.DBURL = url;
        //  Database credentials
        this.USERNAME = user;
        this.PASSWORD = pw;
    }*/


//Must create a connection to the database, no arguments, no return value    
    /**
     * Starts a connection to the MySQL Database 
     * and stores it in dbConnect.
     * 
     * @param DBURL,USERNAME,PASSWORD passed in as arguments,
     * 
     */
    public void initializeConnection(){

    try {
        this.dbConnect = DriverManager.getConnection(this.DBURL, this.USERNAME, this.PASSWORD);
        } 
    catch (SQLException e) {
        GUIViewController.genericError("Could not initialize Connection");
        }
    }

    public void prepareStatement(String query, String... args ){

        try {
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            
            int i = 1;
            for (String arg : args){
                myStmt.setString(i, arg);
                i++;
            }
            myStmt.executeUpdate();
            
            results.close();
            myStmt.close();
            }   
        catch (SQLException e) {
            GUIViewController.genericError("Error processing prepared statement");
            }
        }

        public ResultSet customQuery(String query){

            try {
                Statement myStmt = dbConnect.createStatement();
                ResultSet results = myStmt.executeQuery(query);
                return results;
                
                }   
            catch (SQLException e) {
                GUIViewController.genericError("Error processing custom query");
                return null;
                }
            }

    
}
