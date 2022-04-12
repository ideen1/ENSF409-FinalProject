// InventoryService.java

package edu.ucalgary.ensf409;

import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

public class InventoryService {
	//private static Request request = HamperApp.currentRequest;
	private static Inventory inventory = HamperApp.inventory;
	private static HashMap<String, Boolean> missingCategory = new HashMap<String, Boolean>();

	private static ArrayList<int[]> pwrSet = new ArrayList<int []>();
	private static int nextSetSize = 1;

	/// We need to calculate nutrition values for each set <-- Needs to be done
	public static void inventoryCheckAlgorithm() {
		boolean allFulfilled = true;

		for (Hamper hamper : HamperApp.currentRequest.getHampers()){
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
			// Convert best set and all sets with used food items to status 2(used).
			//tmpUsedUpdaterProtocal();
			/*
			nextSetSize = 1;
			pwrSet.clear();
			pwrSetNutrition.clear();
			tmpUsed.clear();
			*/
			nextSetSize = 1;
			pwrSet.clear();
		}
		// If hampers are fiulfilled then fill them and delete proper items
		// Then create order file
		if (allFulfilled){
			fillHampers();
			HamperApp.currentRequest.createOrderFile();
			deleteFoodItems();
			

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
		pwrSet.size();

		int lastGoodSet = -1;
		while (nextPowerSet()){
			
			int i = 0; 
			for (int[] set : pwrSet){ // Should be made into a loop that uses i

					NutritionValues nutrition = calculateNutrientForSet(i);
					if (enoughNutritionRequirements(hamper.getNutritionValues(), nutrition)){
						double deltaVal = compareNutritionRequirements(hamper.getNutritionValues(), nutrition) ;
						if (deltaVal < hamper.getOptimizationAmount()){
							hamper.setCanBeFulfilled(true);
							hamper.setOptimizationAmount(deltaVal);
							ArrayList<Integer> tempList = new ArrayList<Integer>();
							for (int item: pwrSet.get(i)){
								tempList.add(item);
							}
							hamper.setOptimalSet( tempList );

							//tmpUsed.put(lastGoodSet, 0);
							//tmpUsed.put(i, 1);
							lastGoodSet = i;
						}
					}
				
				i++;
			}
			
			
			pwrSet.clear();
			//pwrSetNutrition.clear();
			//tmpUsed.clear();
		
		}
		if (hamper.canBeFulfilled()){
			return;
		} else {
			hamper.setCanBeFulfilled(false);
			return;
		}
		
		
	}

	private static double compareNutritionRequirements(NutritionValues hamper, NutritionValues set){
		double deltaFV =  set.getAmountFV() - hamper.getAmountFV();
		double deltaWG =  set.getAmountWG() - hamper.getAmountWG();
		double deltaProtein =  set.getAmountProtein() - hamper.getAmountProtein();
		double deltaOther =  set.getAmountOther() - hamper.getAmountOther();

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
	
	public static ArrayList<int[]> generatePwrSet (int[] inputArray, int setSizeNum){
		
		ArrayList<int[]> combinations = new ArrayList<int[]>();

		int data[]= new int[setSizeNum];
        pwrSetHelper(inputArray, data, 0, inputArray.length - 1, 0, setSizeNum, combinations);
		
		return combinations;
	}
	
	private static void pwrSetHelper(int[] inputArray, int data[], int start, int end, int index, int r, ArrayList<int[]> combinations) {
		//System.out.println(combinations.size());
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
	


	// Helper methods
	private static void fillHampers() {

		for (Hamper hamper :  HamperApp.currentRequest.getHampers()){
			for (int num : hamper.getOptimalSet()){
				hamper.addAllocatedItem(num);
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

	private static void deleteFoodItems(){
		for (Hamper hamper :  HamperApp.currentRequest.getHampers()){
			for (int num : hamper.getOptimalSet()){
				//HamperApp.inventory.removeFoodItem(num); 
			}
		}
	}



	private static NutritionValues calculateNutrientForSet(int set){

		NutritionValues nutrition = new NutritionValues(0, 0, 0, 0, 0);
		double amountWG = 0;
		double amountFV = 0;
		double amountProtein = 0;
		double amountOther = 0;
		double totalNeedCalories = 0;

		for (int item : pwrSet.get(set) ){
			totalNeedCalories += Math.ceil( HamperApp.inventory.getFood(item).getCalories());
			amountFV += Math.ceil( HamperApp.inventory.getFood(item).getCalories() * HamperApp.inventory.getFood(item).getFruitVeggieContent() / 100);
			amountWG += Math.ceil( HamperApp.inventory.getFood(item).getCalories() * HamperApp.inventory.getFood(item).getGrainContent() / 100);
			amountProtein += Math.ceil( HamperApp.inventory.getFood(item).getCalories() * HamperApp.inventory.getFood(item).getProteinContent() / 100);
			amountOther += Math.ceil( HamperApp.inventory.getFood(item).getCalories() * HamperApp.inventory.getFood(item).getOther() / 100);
		}
		nutrition.setTotalNeedCalories(totalNeedCalories);
		nutrition.setPercentFV(amountFV / totalNeedCalories);
		nutrition.setPercentOther(amountOther / totalNeedCalories);
		nutrition.setPercentProtein(amountProtein / totalNeedCalories);
		nutrition.setPercentWG(amountWG / totalNeedCalories);
		// pwrSetNutrition.put(set, nutrition);
		return nutrition;
		
	}
}


//
//request = new Request();
//hampers = request.getHampers();
//check = new InventoryService(hampers);








