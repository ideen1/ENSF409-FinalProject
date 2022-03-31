package edu.ucalgary.ensf409;

public class Inventory {
    static private FoodItem[] foodList;

    public FoodItem[] getFoodlist(){
        return this.foodList;
    }

    public void setFoodList(FoodItem[] list){
        this.foodList = list;
    }
}
