package edu.ucalgary.ensf409;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Inventory {
    static private HashMap<Integer,FoodItem> foodList = new HashMap<Integer, FoodItem>();
    
    static {
        // CODE to load food items from inventory
        try {
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
                foodList.put(Integer.valueOf(results.getString("ItemID")) , newItem);
                
            } 

        } catch (SQLException e){
            GUIViewController.genericError("Error retrieving Food Inventory from Database");
        }

    }
    public static HashMap<Integer,FoodItem> getFoodlist(){
        return Inventory.foodList;
    }

    public static void setFoodList(HashMap<Integer,FoodItem> list){
        Inventory.foodList = list;
    }

    public static void addFoodItem(FoodItem addFood){
        Inventory.foodList.put(addFood.getID(), addFood);
    }
    public static void removeFoodItem(FoodItem removeFood){
        // DELETE FROM SQL AS WELL
        Inventory.foodList.remove(removeFood.getID());
    }
    public static void removeFoodItem(int ID){
        // DELETE FROM SQL AS WELL
        Inventory.foodList.remove(ID);
    }
    public static FoodItem getFood(int ID){
        return Inventory.foodList.get(ID);
    }
}
