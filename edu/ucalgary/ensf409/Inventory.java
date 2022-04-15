/** Inventory.java 
 *  Java Class file for ENSF409 Final Project - Winter 2022 - Group 5
 *  Represents an Inventory which is created on the Application's launch
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen, Tanish, Mary 
 *  @version 1.6
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Inventory {
    public static boolean testMode = false;                                                     //checks if testing file if being used or not
    static private HashMap<Integer,FoodItem> foodList = new HashMap<Integer, FoodItem>();       //hasm map of inventory which contains food id and fooditem itself
    
    /**
     * loadInventory()
     * Updates foodlist with information from the database
     * @return void
     */
    public static void loadInventory() {
        HamperApp.mainScreen.genericLoader("Loading Inventory");
        // CODE to load food items from inventory
        try {
            // DEBUGGING
            double amountFV = 0;
            double amountOther = 0;
            double amountWG = 0;
            double amountProtein = 0;

            DBConnection dbc = new DBConnection();
            dbc.initializeConnection();
            ResultSet results = dbc.customQuery("SELECT * FROM AVAILABLE_FOOD");;

            while (results.next()){
                FoodItem newItem = new FoodItem(
                    Integer.valueOf(results.getString("ItemID")), 
                    results.getString("Name"), 
                    Double.valueOf(results.getString("FVContent")), 
                    Double.valueOf(results.getString("GrainContent")), 
                    Double.valueOf(results.getString("ProContent")), 
                    Double.valueOf(results.getString("Other")), 
                    Double.valueOf(results.getString("Calories"))
                    );
                    // DEBUG
                    amountFV += newItem.getCalories() * newItem.getFruitVeggieContent()/100;
                    amountWG += newItem.getCalories() * newItem.getGrainContent()/100;
                    amountProtein+= newItem.getCalories() * newItem.getProteinContent()/100;
                    amountOther += newItem.getCalories() * newItem.getOther()/100;

                foodList.put(Integer.valueOf(results.getString("ItemID")) , newItem);
                
            } 
            System.out.println("Grain: " + amountWG);
            System.out.println("Fruit: "+amountFV);
            System.out.println("Protein: "+amountProtein);
            System.out.println("Other: "+amountOther);
            

        } catch (SQLException e){
            GUIViewController.genericError("Error retrieving Food Inventory from Database");
        }
        HamperApp.mainScreen.genericLoaderHide();
    }

    /**
     * getFoodlist()
     * Gets the list of food items stored in foodList
     * @return A HashMap contaning food items as values and their ids as keys
     */
    public static HashMap<Integer,FoodItem> getFoodlist(){
        return Inventory.foodList;
    }

    /**
     * setFoodList()
     * Sets foodList with a list of food items
     * @param list A HashMap containing food items as values and their ids as the keys
     * @return void
     */
    public static void setFoodList(HashMap<Integer,FoodItem> list){
        Inventory.foodList = list;
    }

    /**
     * addFoodItem()
     * @param addFood The food item to be added
     * @return void
     */
    public static void addFoodItem(FoodItem addFood){
        Inventory.foodList.put(addFood.getID(), addFood);
    }

    /** 
     * removeFoodItem()
     * Removes the specified food item from the database 
     * @param removeFood The food item to be removed
     * @return void
     */ 
    public static void removeFoodItem(FoodItem removeFood){
        removeFoodItem(removeFood.getID());

    }

    /** 
     * removeFoodItem()
     * Removes food item from the database using the food item ID
     * @param ID The food item's id in the inventory
     * @return void
     */ 
    public static void removeFoodItem(int ID){
        // delete from list
        Inventory.foodList.remove(ID);

        if (testMode == false){
            // Delete from SQL
            DBConnection dbc = new DBConnection();
            dbc.initializeConnection();
            dbc.preparedQuery("DELETE FROM AVAILABLE_FOOD WHERE ItemID = ?", String.valueOf(ID));
        }
    }

    /** 
     * getFood()
     * Gets the food item using specific food item ID
     * @param ID The food item's id in the inventory
     * @return The food item with the specified id
     */ 
    public static FoodItem getFood(int ID){
        return Inventory.foodList.get(ID);
    }

    /**
     * removeTemps()
     * Converts temporarily used items to not used so they may be available for other hampers
     * @return void
     */
    public static void removeTemps(){
        for(FoodItem item: foodList.values()){
            if (item.getUsageStatus() == 1){
                item.setUsageStatus(0);
            }
        }
    }

    /**
     * finalizeTemps()
     * Converts temporarily used items to permanent used so they may not be accessed by subsequent hampers
     * @return void
     */
    public static void finalizeTemps(){
        for(FoodItem item: foodList.values()){
            if (item.getUsageStatus() == 1){
                item.setUsageStatus(2);
            }
        }
    }

    /**
     * resetUsage()
     * Removes all usage flags on food items so they are clear for the next order
     * @return void
     */
    public static void resetUsage(){
        for(FoodItem item: foodList.values()){
            item.setUsageStatus(0);
        }
    }
}
