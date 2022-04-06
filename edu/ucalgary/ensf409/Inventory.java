package edu.ucalgary.ensf409;

import java.util.HashMap;

public class Inventory {
    static private HashMap<Integer,FoodItem> foodList = new HashMap<Integer, FoodItem>();
    static {
        // CODE to load food items from inventory
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
        Inventory.foodList.remove(removeFood.getID());
    }
    public static void removeFoodItem(int ID){
        Inventory.foodList.remove(ID);
    }
    public static FoodItem getFood(int ID){
        return Inventory.foodList.get(ID);
    }
}
