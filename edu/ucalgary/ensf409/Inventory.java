package edu.ucalgary.ensf409;

import java.util.HashMap;

public class Inventory {
    static private HashMap<Integer,FoodItem> foodList = new HashMap<Integer, FoodItem>();

    public static HashMap<Integer,FoodItem> getFoodlist(){
        return Inventory.foodList;
    }

    public static void setFoodList(HashMap<Integer,FoodItem> list){
        Inventory.foodList = list;
    }

    public void addFoodItem(FoodItem addFood){
        this.foodList.put(addFood.getID(), addFood);

    }
    public void removeFoodItem(FoodItem removeFood){
        this.foodList.remove(removeFood.getID());
    }
    public void removeFoodItem(int ID){
        this.foodList.remove(ID);
    }
    public void getFood(int ID){
        this.foodList.get(ID);

    }
}
