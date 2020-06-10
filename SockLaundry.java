package com.socklaundry;

public class SockLaundry {

	//Do not delete or edit this method, you can only modify the block
	public static int getMaximumPairOfSocks(int noOfWashes, int[] cleanPile, int[] dirtyPile) {
		int result = 0;

		if(noOfWashes == 0) {
			result = checkEven(cleanPile);

		}else if(noOfWashes > 0 && noOfWashes <= 50) {
			result = checkEven(cleanPile);			
			
			int temp = checkEven(dirtyPile);

			while(temp > noOfWashes) {
				temp--;			
			}
		
			result = result + temp;
		}else {
			System.out.println("Cant wash more than 50");
		}
		return result;
	}

	/**
	 * You can create various helper methods
	 * */


	public static int checkEven(int[] j) {
		int resultCount = 0;
		
		for(int x= 0; x < j.length; x++) {
			if(j[x]%2 == 0) {
				resultCount++;
			}
		}

		return resultCount;
	}


	public static void main(String... args) {

		int numberMachineCanWash = 4;
		int[] cleanPile = new int[] { 1, 1, 1, 1, 1, 1 ,2};
		int[] dirtyPile = new int[] { 1, 2, 2, 1, 3, 4, 5, 2 };

		int result = getMaximumPairOfSocks(numberMachineCanWash, cleanPile, dirtyPile);
		System.out.println(result);
	}
}
