package edu.ucalgary.ensf409;

import java.util.ArrayList;

public class Inventory {
    static private ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();

    public ArrayList<FoodItem> getFoodlist(){
        return this.foodList;
    }

    public void setFoodList(ArrayList<FoodItem> list){
        this.foodList = list;
    }
}
