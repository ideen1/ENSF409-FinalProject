/** NutritionValues.java 
 *  Java Class file for ENSF409 Final Project - Winter 2022 - Group 5
 *  Represents Nutritional Value information for different person types.
 *  Copyright © 2022 I.B., T.D., M.M.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * NutritionValues
 */
public class NutritionValues {
    private double percentWG;
    private double percentFV;
    private double percentProtein;
    private double percentOther;
    private double totalNeedCalories;

    /**
     * NutritionValues() constructor
     * @param percentWG The whole grain nutritional need in percent
     * @param percentFV The fruit and vegetable nutritional need in percent
     * @param percentProtein The protein nutritional need in percent
     * @param percentOther The other nutritional need in percent
     * @param totalNeedCalories The total caloric need
     */
    public NutritionValues(double percentWG, double percentFV, double percentProtein, double percentOther,
            double totalNeedCalories) {
        this.percentWG = percentWG;
        this.percentFV = percentFV;
        this.percentProtein = percentProtein;
        this.percentOther = percentOther;
        this.totalNeedCalories = totalNeedCalories;
    }

    /**
     * NutritionValues() constructor
     * @param type The person type for which the nutrition values are being set
     */
    public NutritionValues(String type){
        
        try {
            DBConnection dbc = new DBConnection();
            dbc.initializeConnection();
            ResultSet results = null;

            if (type.equals("ADULTMALE")){
                results = dbc.customQuery("SELECT * FROM DAILY_CLIENT_NEEDS WHERE Client = 'Adult Male'");
            } else if (type.equals("ADULTFEMALE")) {
                results = dbc.customQuery("SELECT * FROM DAILY_CLIENT_NEEDS WHERE Client = 'Adult Female'");
            } else if (type.equals("CHILDOVER8")){
                results = dbc.customQuery("SELECT * FROM DAILY_CLIENT_NEEDS WHERE Client = 'Child over 8'");
            } else if (type.equals("CHILDUNDER8")){
                results = dbc.customQuery("SELECT * FROM DAILY_CLIENT_NEEDS WHERE Client = 'Child under 8'");
            } else {
                GUIViewController.genericError("INTERNAL - Invalid PersonType");
            }

            
            if (results.next()){
                this.totalNeedCalories = Double.valueOf(results.getString("Calories")) ;
                this.percentFV = Double.valueOf(results.getString("FruitVeggies"));
                this.percentWG = Double.valueOf(results.getString("WholeGrains"));
                this.percentProtein = Double.valueOf(results.getString("Protein"));
                this.percentOther = Double.valueOf(results.getString("Other"));
                
            } else{
                GUIViewController.genericError("INTERNAL - No Table Data for PersonType");
            }

        } catch (SQLException e){
            GUIViewController.genericError("Error retrieving Daily Client Needs from SQL");
        }
        
        

    }

    /**
     * getPercentWG
     * Returns Percent of Wheat Grain for PersonType
     * @param 1,2,3
     * @return A number (double) representing the Whole Grain need for the person type in percentage
     * @since 1.0
     */
    public double getPercentWG() {
        return percentWG;
    }

    /**
     * getPercentFV
     * @return Percent of Wheat Grain for PersonType
     * @since 1.0
     */
    public double getPercentFV() {
        return percentFV;
    }

    /**
     * getPercentProtein
     * @return percent of Protein for PersonType
     * @since 1.0
     */
    public double getPercentProtein() {
        return percentProtein;
    }

    /**
     * getPercentOther
     * @return percent of Other for PersonType
     * @since 1.0
     */
    public double getPercentOther() {
        return percentOther;
    }

    /**
     * getTotalNeedCalories()
     * @return A number (double) representing the caloric needs for PersonType
     * @since 1.0
     */
    public double getTotalNeedCalories() {
        return totalNeedCalories;
    }

    /**
     * getTotalNeedAmountWG()
     * @return A number (double) representing the percentage of whole grain nutritional needs for PersonType
     * @since 1.0
     */
    public double getAmountWG() {
        return Math.ceil(percentWG * totalNeedCalories / 100);
    }

    /**
     * getTotalNeedAmountFV()
     * @return A number (double) representing the percentage of fruit and vegetable nutritional needs for PersonType
     * @since 1.0
     */
    public double getAmountFV() {
        return Math.ceil(percentFV * totalNeedCalories / 100);
    }

    /**
     * getTotalNeedAmountProtein()
     * @return A number (double) representing the percentage of protein nutritional needs for PersonType
     * @since 1.0
     */
    public double getAmountProtein() {
        return Math.ceil(percentProtein * totalNeedCalories / 100);
    }

    /**
     * getTotalNeedAmountOther()
     * @return A number (double) representing the percentage of other nutritional needs for PersonType
     * @since 1.0
     */
    public double getAmountOther() {
        return Math.ceil(percentOther * totalNeedCalories / 100);
    }

    /**
     * setPercentWG()
     * Sets the value of whole grain nutritional need in percentage
     * @param percentWG A number (double) representing the whole grain nutritional needs for PersonType in percentage
     * @since 1.0
     */
    public void setPercentWG(double percentWG) {
        this.percentWG = percentWG;
    }

    /**
     * setPercentFV()
     * Sets the value of fruit and vegetable nutritional need in percentage
     * @param percentFV A number (double) representing the fruit and vegetable nutritional needs for PersonType in percentage
     * @since 1.0
     */
    public void setPercentFV(double percentFV) {
        this.percentFV = percentFV;
    }

    /**
     * setPercentProtein()
     * Sets the value of protein nutritional need in percentage
     * @param percentProtein A number (double) representing the protein nutritional needs for PersonType in percentage
     * @since 1.0
     */
    public void setPercentProtein(double percentProtein) {
        this.percentProtein = percentProtein;
    }

    /**
     * setPercentOther()
     * Sets the value of other nutritional need in percentage
     * @param percentOther A number (double) representing the other nutritional needs for PersonType in percentage
     * @since 1.0
     */
    public void setPercentOther(double percentOther) {
        this.percentOther = percentOther;
    }

    /**
     * setTotalNeedCalories()
     * Sets the value of total caloric need
     * @param percentWG A number (double) representing the total caloric needs for PersonType
     * @since 1.0
     */
    public void setTotalNeedCalories(double totalNeedCalories) {
        this.totalNeedCalories = totalNeedCalories;
    }

    
}