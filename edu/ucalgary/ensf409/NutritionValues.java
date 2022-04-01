/** NutritionValues.java 
 *  Java Class file for ENSF409 Final Project 202 - 
 *  Winter 2022 - Group 5
 *  Copyright © 2022 I.B., T.D., M.G.
 *  @author Ideen
 *  @version 1.0
 *  @since 1.0
 */

package edu.ucalgary.ensf409;

/**
 * NutritionValues
 */
public class NutritionValues {
    private  double percentWG;
    private  double percentFV;
    private  double percentProtein;
    private  double percentOther;
    private  double totalNeedCalories;

    public NutritionValues(String type){
        // TO DO : MUST BE POPULATED BY SQL QUERY
        this.totalNeedCalories = 0;
        this.percentFV = 0;
        this.percentWG = 0;
        this.percentProtein = 0;
        this.percentOther = 0;

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
        return percentWG * totalNeedCalories;
    }

    public double getAmountFV() {
        return percentFV * totalNeedCalories;
    }

    public double getAmountProtein() {
        return percentProtein * totalNeedCalories;
    }

    public double getAmountOther() {
        return percentOther * totalNeedCalories;
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