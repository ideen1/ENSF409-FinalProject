// InventoryService.java

package edu.ucalgary.ensf409;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryService{
	private static Request request;
	private static Inventory inventory = HamperApp.inventory;
	private static HashMap<String, Boolean> missingCategory = new HashMap<String, Boolean>();

	private static ArrayList<int[]> pwrSet = new ArrayList<int []>();
	private static HashMap<Integer, NutritionValues> pwrSetNutrition = new HashMap<Integer, NutritionValues>();
	private static HashMap<Integer,Integer> tmpUsed = new HashMap<Integer,Integer>(); // 1:temp used, 2: permanent used
	private static int nextSetSize = 2;

	/// We need to calculate nutrition values for each set <-- Needs to be done
	public static void inventoryCheckAlgorithm() {
		boolean allFulfilled = true;
		for (Hamper hamper :  request.getHampers()){
			// Perform check for each hamper
			findOptimalFromSet(hamper);
			try {
				if (!hamper.canBeFulfilled()){
					throw new InventoryNotAvailableException();
					
				}
			} catch(InventoryNotAvailableException e){
				GUIViewController.genericError("InventoryNotAvailableException");
				allFulfilled = false;
				break;
			}
			
		}
		if (allFulfilled){
			fillHampers();
		}


		/*
		 * fillHamper();
		 * if missingCategory is empty
		 * 		for all food items stored in each Hamper's allocatedItems // OR // all the FoodItem whose used field is true
		 * 			Inventory.deleteFoodItem(usedItem)
		 * 		create an order form (.txt)
		 * else 
		 * 		reset the tmpUsed field of all FoodItem's in the Inventory to "false"
	 	 * 		throw the custom exception InventoryNotAvailableException, with a message indicating which food category 
	     * 		is short (missingCategory content)
		 */

		 
	}

	private static void findOptimalFromSet(Hamper hamper){
		int i = 0;
		while (nextPowerSet()){
			for (int[] set : pwrSet){
				if (tmpUsed.get(i) != 1 && tmpUsed.get(i) != 2){
					if (enoughNutritionRequirements(hamper.getNutritionValues(), pwrSetNutrition.get(i))){
						double deltaVal = compareNutritionRequirements(hamper.getNutritionValues(), pwrSetNutrition.get(i)) ;
						if (deltaVal < hamper.getOptimizationAmount()){
							hamper.setCanBeFulfilled(true);
							hamper.setOptimizationAmount(deltaVal);
							hamper.setOptimalSet(i);
							tmpUsed.put(i, 1);
						}
					}
				}
				i++;
			}
			
			if (hamper.canBeFulfilled()){
				return;
			}
		}
		hamper.setCanBeFulfilled(false);
		
	}

	private static double compareNutritionRequirements(NutritionValues hamper, NutritionValues set){
		double deltaFV =  hamper.getAmountFV() - set.getAmountFV();
		double deltaWG =  hamper.getAmountWG() - set.getAmountWG();
		double deltaProtein =  hamper.getAmountProtein() - set.getAmountProtein();
		double deltaOther =  hamper.getAmountOther() - set.getAmountOther();

		return deltaFV + deltaOther + deltaProtein + deltaWG;

	}

	private static boolean enoughNutritionRequirements(NutritionValues hamper, NutritionValues set){
		if (set.getAmountFV() - hamper.getAmountFV() < 0 ){
			return false;
		};
		if (set.getAmountWG() - hamper.getAmountWG() < 0 ){
			return false;
		};
		if (set.getAmountProtein() - hamper.getAmountProtein() < 0 ){
			return false;
		};
		if (set.getAmountOther() - hamper.getAmountOther() < 0 ){
			return false;
		};
		return true;

	}
	
	/**
	 * Power sets ...
	 */
	public static boolean nextPowerSet(){
		if ( nextSetSize > inventory.getFoodlist().size()){
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
	
	public static ArrayList<int[]> generatePwrSet (int[] inputArray, int setSizeNum){
		
		ArrayList<int[]> combinations = new ArrayList<int[]>();

		int data[]= new int[setSizeNum];
        pwrSetHelper(inputArray, data, 0, 20, 0, setSizeNum, combinations);
		
		return combinations;
	}
	
	private static void pwrSetHelper(int[] inputArray, int data[], int start, int end, int index, int r, ArrayList<int[]> combinations) {
		//System.out.println(combinations.size());
        if (index == r)
        {
			int[] tempList = new int[r];
            for (int j=0; j<r; j++){
				tempList[j] = data[j];
				 //System.out.print(data[j]+" ");
			}
			combinations.add(tempList);
             //System.out.println("");
            return;
        }
 
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = inputArray[i];
            pwrSetHelper(inputArray, data, i+1, end, index+1, r, combinations);
        }
	}
	
	// GUIViewController.genericError("");

	// Helper methods
	private static void fillHampers() {

		for (Hamper hamper :  request.getHampers()){
			for (int num : pwrSet.get(hamper.getOptimalSet())){
				hamper.addAllocatedItem(num);
				removeTempUsed(num);
				HamperApp.inventory.removeFoodItem(num); 
			}
		}


		// used in a loop, helper method for inventoryCheckAlgorithm
		// selects food items from the inventory and updates the tmpUsed in foodItems
		// updates missingCategory if missing anything for a hamper
		// Loop through hampers
		// for each hamper, add food items from its optimal set, 
		// delete those items from sql and hashmap
		// delete all power sets that contain food values value

	}
	private static void removeTempUsed(int num){
		// DELETE FROM TMPUSED when tmp used = 1; set to 2
		for (Integer nums : tmpUsed.values()){
			
			hamper.addAllocatedItem(num);
			
		}
	}
			
}
//
//request = new Request();
//hampers = request.getHampers();
//check = new InventoryService(hampers);








