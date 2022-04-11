/** NutritionValues.java 
 *  Java Class file for ENSF409 Final Project - 
 *  Winter 2022 - Group 5
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

    

    public NutritionValues(double percentWG, double percentFV, double percentProtein, double percentOther,
            double totalNeedCalories) {
        this.percentWG = percentWG;
        this.percentFV = percentFV;
        this.percentProtein = percentProtein;
        this.percentOther = percentOther;
        this.totalNeedCalories = totalNeedCalories;
    }

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
     * @return
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
     * getTotalNeedCalories
     * @return percent of Protein for PersonType
     * @since 1.0
     */
    public double getTotalNeedCalories() {
        return totalNeedCalories;
    }

    public double getAmountWG() {
        return percentWG * totalNeedCalories / 100;
    }

    public double getAmountFV() {
        return percentFV * totalNeedCalories / 100;
    }

    public double getAmountProtein() {
        return percentProtein * totalNeedCalories / 100;
    }

    public double getAmountOther() {
        return percentOther * totalNeedCalories / 100;
    }

    public void setPercentWG(double percentWG) {
        this.percentWG = percentWG;
    }

    public void setPercentFV(double percentFV) {
        this.percentFV = percentFV;
    }

    public void setPercentProtein(double percentProtein) {
        this.percentProtein = percentProtein;
    }

    public void setPercentOther(double percentOther) {
        this.percentOther = percentOther;
    }

    public void setTotalNeedCalories(double totalNeedCalories) {
        this.totalNeedCalories = totalNeedCalories;
    }

    
}