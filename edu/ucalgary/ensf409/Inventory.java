package edu.ucalgary.ensf409;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Inventory {
    static private HashMap<Integer,FoodItem> foodList = new HashMap<Integer, FoodItem>();
    
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
        
        removeFoodItem(removeFood.getID());

    }
    public static void removeFoodItem(int ID){
        // delete from list
        Inventory.foodList.remove(ID);

        // Delete from SQL
        DBConnection dbc = new DBConnection();
        dbc.initializeConnection();
        dbc.preparedQuery("DELETE FROM AVAILABLE_FOOD WHERE ItemID = ?", String.valueOf(ID));

    }
    public static FoodItem getFood(int ID){
        return Inventory.foodList.get(ID);
    }
}
