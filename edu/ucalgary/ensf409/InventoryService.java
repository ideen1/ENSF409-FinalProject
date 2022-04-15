/** InventoryService.java 
 * Java Class file for ENSF409 Final Project - Winter 2022 - Group 5
 * Represents a service class which will be used to calculate the most 
 * efficient combination of food items from the inventory for each of 
 * the requested hampers.
 * Copyright © 2022 I.B., T.D., M.M.
 * @author Ideen, Tanish, Mary
 * @version 1.11 
 * @since 1.0
 */

package edu.ucalgary.ensf409;

import java.util.ArrayList;
import java.util.HashMap;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

public class InventoryService {
	// private static Request request = HamperApp.currentRequest;
	private static Inventory inventory = HamperApp.inventory;
	private static HashMap<String, Boolean> missingCategory = new HashMap<String, Boolean>();
	private static ArrayList<int[]> pwrSet = new ArrayList<int[]>();
	private static int nextSetSize = 1;

	/// We need to calculate nutrition values for each set <-- Needs to be done


	/** inventoryCheckAlgorithm
	 * Checks if all the hampers in the request can be fulfilled by the food items from 
	 * the inventory
	 * @return A boolean value representing whether or not the hampers could be fulfilled
	 */
	public static boolean inventoryCheckAlgorithm() {
		boolean allFulfilled = true;

		for (Hamper hamper : HamperApp.currentRequest.getHampers()) {
			// Perform check for each hamper
			findOptimalFromSet(hamper);
			try {
				if (!hamper.canBeFulfilled()) {
					throw new InventoryNotAvailableException();
					
				}
			} catch (InventoryNotAvailableException e) {
				GUIViewController.genericError("InventoryNotAvailableException");
				allFulfilled = false;
				break;
			}
			Inventory.finalizeTemps();
			nextSetSize = 1;
			pwrSet.clear();
		}
		// If hampers are fulfilled then fill them and delete proper items
		// Then create order file
		if (allFulfilled){
			fillHampers();
			HamperApp.currentRequest.createOrderFile();
			deleteFoodItems();
			pwrSet.clear();
			nextSetSize = 1;
			return true;
		}
		pwrSet.clear();
		nextSetSize = 1;
		Inventory.resetUsage();
		return false;
	}
	/**
	 *@return void 
	 *@param hamper Hamper object to access peoples nutrition values
	 *This methods finds the most optimal set from the power that waste the least amount of food fo the hamper
	 */
	private static void findOptimalFromSet(Hamper hamper){
		pwrSet.size();

		while (nextPowerSet()){
			
			int i = 0; 
			for (int[] set : pwrSet){ // Should be made into a loop that uses i
				boolean noConflicts = true;
					for (int item : set){
						if (Inventory.getFood(item).getUsageStatus() == 2){
							noConflicts = false;
						}
					}
					if (noConflicts){
					NutritionValues nutrition = calculateNutrientForSet(i);
					if (enoughNutritionRequirements(hamper.getNutritionValues(), nutrition)){
						double deltaVal = compareNutritionRequirements(hamper.getNutritionValues(), nutrition) ;
						if (deltaVal < hamper.getOptimizationAmount()){
							hamper.setCanBeFulfilled(true);
							hamper.setOptimizationAmount(deltaVal);
							ArrayList<Integer> tempList = new ArrayList<Integer>();
							Inventory.removeTemps();
							for (int item: pwrSet.get(i)){
								tempList.add(item);
								Inventory.getFood(item).setUsageStatus(1);
							}
							hamper.setOptimalSet( tempList );


						}
					}
				}
				
				i++;
			}
			
			
			pwrSet.clear();
		}
		if (hamper.canBeFulfilled()){
			return;
		} else {
			hamper.setCanBeFulfilled(false);
			return;
		}
	}

	/** helper method is called to compare the power set items to the hamper request items to find optimal set 
	 * @return double value is return 
	 * @param hamper object of NutritionValues for hampers
	 * @param set object of NutritionValues for power sets
	 *compares the two nutrition values of hamper and powers sets
	 */
	private static double compareNutritionRequirements(NutritionValues hamper, NutritionValues set){
		double deltaFV =  set.getAmountFV() - hamper.getAmountFV();
		double deltaWG =  set.getAmountWG() - hamper.getAmountWG();
		double deltaProtein =  set.getAmountProtein() - hamper.getAmountProtein();
		double deltaOther =  set.getAmountOther() - hamper.getAmountOther();

		return deltaFV + deltaOther + deltaProtein + deltaWG;
	}

	/**
	 * @return boolen type
	 * @param hamper object of NutritionValues for hampers
	 * @param set object of NutritionValues for power sets 
	 *this helper methoed checks if the power set meets the requirements of the hamper regardless if its optimal or not
	 */
	private static boolean enoughNutritionRequirements(NutritionValues hamper, NutritionValues set){
		// Clear previous shortage since we only care about reported shortage from biggest available set(last one)
		missingCategory.clear();
		
		boolean enough = true;
		if (set.getAmountFV() - hamper.getAmountFV() < 0 ){
			missingCategory.put("Fruit/Veggies", true);
			enough = false;
		};
		if (set.getAmountWG() - hamper.getAmountWG() < 0 ){
			missingCategory.put("Wheat/Grains", true);
			enough = false;
		};
		if (set.getAmountProtein() - hamper.getAmountProtein() < 0 ){
			missingCategory.put("Protein", true);
			enough = false;
		};
		if (set.getAmountOther() - hamper.getAmountOther() < 0 ){
			missingCategory.put("Other", true);
			enough = false;
		};
		return enough;

	}
	/**
	 *@return boolean type 
	 *method checks if the next power set is available or not and return true or false,
	 * if false, the next power set will not be run,
	 * uf true, next power set if run until it reachs the inventory size
	 */
	public static boolean nextPowerSet(){
		if ( nextSetSize - 1 > inventory.getFoodlist().size()){
			return false;
		}
		HamperApp.mainScreen.genericLoader("Processing Combinations... Please Wait");
		int[] data = new int[inventory.getFoodlist().size()];
		
		int i = 0 ;
		for(Integer item: inventory.getFoodlist().keySet()){
			data[i] = item;
			i++;
		}
		for (int[] item : generatePwrSet(data, nextSetSize)){
			pwrSet.add(item);
		}
		HamperApp.mainScreen.genericLoaderHide();
		nextSetSize++;
		return true;
	}
	
	/** 
	 * generatePwrSet
	 * Generates power sets of different food combinaions 
	 * @param inputArray An array of integers containing the id numbers of all food items in the inventory
	 * @param setSizeNum The number of food items to be included in each combination i.e. size of the integer arrays contained in the 
	 * ArrayList to be returned
	 * @return An ArrayList containing integer arrays of the given size that each represent a different combination of food items from 
	 * the inventory with their id numbers
	 */
	private static ArrayList<int[]> generatePwrSet (int[] inputArray, int setSizeNum){
		
		
		// to see if the size is ever bigger than the inputArray.size
		// if (setSizeNum > inputArray.length) {
		// 	GUIViewController.genericError("generatePwrSetError - size out of bound");
		//}

		ArrayList<int[]> combinations = new ArrayList<int[]>();

		int data[]= new int[setSizeNum];
        pwrSetHelper(inputArray, data, 0, inputArray.length - 1, 0, setSizeNum, combinations);
		
		return combinations;
	}
	
	/**
	 * Calculates the sets recursively, helping the previous generatePwrSet function
	 *@return void 
	 *@param inputArray data array going in
	 *@param data Temporary array used by the function
	 *@param start	Start Index of data
	 *@param end ending index of the data
	 *@param index current index position(since we are recursing)
	 *@param r the size of the sets
	 *@param combinations 
	 */
	private static void pwrSetHelper(int[] inputArray, int data[], int start, int end, int index, int r, ArrayList<int[]> combinations) {

        if (index == r)
        {
			int[] tempList = new int[r];
            for (int j=0; j<r; j++){
				tempList[j] = data[j];

			}
			pwrSet.add(tempList);
            return;
        }
 
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = inputArray[i];
            pwrSetHelper(inputArray, data, i+1, end, index+1, r, combinations);
        }
	}

	/**
	 * Goes through each Hamper and tranfers the final optimal set items to the permanently allocated items
	 * @return void 
	 * adds items found from the optimal set to the hamper 
	 */
	private static void fillHampers() {

		for (Hamper hamper :  HamperApp.currentRequest.getHampers()){
			for (int num : hamper.getOptimalSet()){
				hamper.addAllocatedItem(num);
			}
		}
	}

	/**
	 * Deletes a specific food item from the Inventory List and the Database
	 * @return void 
	 * removes items from the inventory after being added to the hamper 
	 */
	private static void deleteFoodItems(){
		for (Hamper hamper :  HamperApp.currentRequest.getHampers()){
			for (int num : hamper.getOptimalSet()){
				HamperApp.inventory.removeFoodItem(num); 
			}
		}
	}

	/**
	 * Calculates the nutritional values for a given set and returns them
	 * @return return a NutritionValue type  
	 * @param set  ID of set to calculate NutritionaValues for
	 */
	private static NutritionValues calculateNutrientForSet(int set){

		NutritionValues nutrition = new NutritionValues(0, 0, 0, 0, 0);
		double amountWG = 0;
		double amountFV = 0;
		double amountProtein = 0;
		double amountOther = 0;
		double totalNeedCalories = 0;

		for (int item : pwrSet.get(set)){
			totalNeedCalories += Math.ceil( HamperApp.inventory.getFood(item).getCalories());
			amountFV += Math.ceil( HamperApp.inventory.getFood(item).getCalories() * HamperApp.inventory.getFood(item).getFruitVeggieContent() / 100);
			amountWG += Math.ceil( HamperApp.inventory.getFood(item).getCalories() * HamperApp.inventory.getFood(item).getGrainContent() / 100);
			amountProtein += Math.ceil( HamperApp.inventory.getFood(item).getCalories() * HamperApp.inventory.getFood(item).getProteinContent() / 100);
			amountOther += Math.ceil( HamperApp.inventory.getFood(item).getCalories() * HamperApp.inventory.getFood(item).getOther() / 100);
		}
		nutrition.setTotalNeedCalories(totalNeedCalories);
		nutrition.setPercentFV( amountFV / totalNeedCalories);
		nutrition.setPercentOther(amountOther / totalNeedCalories);
		nutrition.setPercentProtein(amountProtein / totalNeedCalories);
		nutrition.setPercentWG(amountWG / totalNeedCalories);
		// pwrSetNutrition.put(set, nutrition);
		return nutrition;
		
	}

	/** 
	 * getMissingCategory()
	 * Gets the missingCategory HashMap containing the missing category information
	 * @return A HashMap<String, Boolean> containing the missing category information
	 */
	public static HashMap<String, Boolean> getMissingCategory(){
		return missingCategory;
	}

	/** 
	 * getPwrSet()
	 * Gets the pwrSet - for testing purposes
	 * @return An ArrayList<int[]> that contains all possible combinations of integers in an array of a given size
	 */
	public static ArrayList<int[]> getPwrSet() {
		return pwrSet;
	}

	/**
	 * resetNextSize()
	 * Resets the nextSetSize to 1 - for testing purposes
	 */
	public static void resetNextSize(){
		nextSetSize = 1;
	}
}








