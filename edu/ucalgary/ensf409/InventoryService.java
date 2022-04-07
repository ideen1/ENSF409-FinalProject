// InventoryService.java

package edu.ucalgary.ensf409;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryService{
	private static Hamper[] hampersToCheck;
	private static Inventory inventory = HamperApp.inventory;
	private static HashMap<String, Boolean> missingCategory = new HashMap<String, Boolean>();

	private static ArrayList<int[]> pwrSet = new ArrayList<int []>();
	private static ArrayList<Integer> tmpUsed = new ArrayList<Integer>();
	private static int nextSetSize = 2;
	
	public void inventoryCheckAlgorithm() {
		
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
	
	public static void nextPowerSet(){
		int[] data = new int[inventory.getFoodlist().size()];
		
		int i = 0 ;
		for(Integer item: inventory.getFoodlist().keySet()){
			data[i] = item;
			i++;
		}
		for (int[] item : generatePwrSet(data, nextSetSize)){
			pwrSet.add(item);
		}
		nextSetSize++;
	}
	
	public static ArrayList<int[]> generatePwrSet (int[] inputArray, int setSizeNum){
		
		ArrayList<int[]> combinations = new ArrayList<int[]>();

		int data[]= new int[setSizeNum];
        pwrSetHelper(inputArray, data, 0, data.length-1, 0, setSizeNum, combinations);
		return combinations;
	}
	
	private static void pwrSetHelper(int[] inputArray, int data[], int start, int end, int index, int r, ArrayList<int[]> combinations) {

        if (index == r)
        {
			int[] tempList = new int[r];
            for (int j=0; j<r; j++){
				tempList[j] = data[j];
				// System.out.print(data[j]+" ");
			}
			combinations.add(tempList);
            // System.out.println("");
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
	private void fillHampers() {
		// used in a loop, helper method for inventoryCheckAlgorithm
		// selects food items from the inventory and updates the tmpUsed in foodItems
		// updates missingCategory if missing anything for a hamper
	}
			

	
}
//
//request = new Request();
//hampers = request.getHampers();
//check = new InventoryService(hampers);








