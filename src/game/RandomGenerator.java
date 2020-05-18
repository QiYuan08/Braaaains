/**
 * 
 */
package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**@author Teh Qi Yuan
 * 
 * This is as implementation of the random number generator with an bias towards one side
 * Given an array of item and bias, the class will duplicate the item until the probability of
 * choosing the item is equal to the bias value
 * E.g given an array with item [a,a,b,b] with 50% bias toward a, this class will make the array
 * 		[a,a,a,a,b,b] hence, it will have 50%  bias towards a 
 *
 */
public class RandomGenerator {
		
	private int count(ArrayList<Integer> itemList, Integer item ) {
		
		int count = 0;
		for (int i=0; i < itemList.size(); i++) {
			if (itemList.get(i) == itemList.get(item)) {			
				count +=1;
			}
		}
		
		return count;
	}
	
	/**
	 * An private method to create an ArrayList with duplicate element
	 * based on the bias given
	 * This method calculate the GreatestCommom Divisor(GCD) between the bias
	 * and other value and expand the list accordingly
	 * E.g given duplicate([1,2], 50, 0), this method will return an 
	 * ArrayList containing [1, 2, 2]
	 * 
	 * @param itemList The arrayList to expand
	 * @param bias The percentage of bias in 100 percent range
	 * @return The new arrayList
	 */
	private ArrayList<Integer> duplicate(ArrayList<Integer> itemList, int bias, int biasIndex){
		/* create an new array representing the element in ratio form
		*  E.g convert a list of [1, 2] with bias of 50% toward 2
		*  	   into [100, 150]
		*
		*/
		int finalGCD = 0;
		ArrayList<Integer> finalList = new ArrayList<Integer>();
		
		int[] newList = new int[itemList.size()];
		for (int i=0; i < itemList.size(); i++) {
			newList[i] = 100;
			finalList.add(new Integer(itemList.get(i)));
		}
		newList[biasIndex] =  100 + bias;
		
		// find the GCD for the ratio form array created
		int LastGCD = GreatestCommonDiv(newList[0], newList[1]);

		for(int j=0; j < newList.length - 1; j++) {
			if(newList.length == 2) {
				finalGCD = LastGCD;
				break;
			}
			else {
				finalGCD = GreatestCommonDiv(LastGCD, newList[j + 1]);
			}
			
			LastGCD = finalGCD;
		}
		
		for(int i=0; i < newList.length; i++) {
			int  temp = newList[i];
			newList[i] = temp/finalGCD;
		}
		
		// expand the list according to the GCD
		int size = itemList.size();
		int count = 0;
		for(int k=0; k < size ; k++ ) {
			for (int i=0; i < newList[count]; i++) {
				finalList.add(new Integer(itemList.get(k)));
			}
			count += 1;
		}
		

		
		return finalList;

	}
	
	/**
	 *  This method is used to return a random index of an item
	 *  in the given ArrayList based on a biased percentage given
	 *  
	 * @param itemList Choices in the form of integer e.g [0, 1, 2]
	 * @param bias the percentage of bias towards an item
	 * @return the  of the item in the ArrayList
	 */
	public int biasRandom(ArrayList<Integer> itemList, int bias, int biasIndex) throws Exception {
		
		if(itemList.size() < 0) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<Integer> newList = new ArrayList<Integer>();
		
		newList = duplicate(itemList, bias, biasIndex);
		for(int i=0; i< newList.size(); i++) {
		}
		
		int ans = new Random().nextInt(newList.size());
		return newList.get(ans);
	}
	
	/**
	 *  This method is used to return an choices in the form of integer
	 *  starting from 0 to (N - 1) number of choices. 
	 *  
	 * @exception IllegalArgumentException if the sum of probability given is not 100 
	 * @param probability The probability of each event in integer
	 * @return The index of the event based on the probability list given
	 * 
	 */
	
	// TODO can refactor to require only prob( number of choice, probability list)
	// cuz the choice is the same as index in probability
	public int probRandom(int[] probability) {
		
		int choiceIndex = 0; 
		int sum = 0;
		ArrayList<Integer> clonedList = new ArrayList<Integer>();
		clonedList = cloneArray(probability);
		
		for(Integer element: probability) {
			sum += element;
		}
		if(sum != 100) {
			throw new IllegalArgumentException("Sum of proability must be 100");
		}
		
		clonedList.add(0, 1);
		int randNum = new Random().nextInt(99);
		for(int i=0 ;i < clonedList.size()-1; i++) {

			if(randNum >= (clonedList.get(i) - 1) & randNum < (clonedList.get(i) - 1 + clonedList.get(i+1) - 1)) {
				return choiceIndex = i;
			}
			else if( randNum >= (100 - clonedList.get(clonedList.size()-1) - 1)  & randNum < 100) {
				return choiceIndex = probability.length - 1;
			}
		}

		return choiceIndex;
	}
	
	private ArrayList<Integer> cloneArray(int[] original) {
		
		ArrayList<Integer> clonedArray = new ArrayList<Integer>();
		for(Integer item: original) {
			clonedArray.add(new Integer(item));
		}

		return clonedArray;
	}
	
	
	/**
	 *  Private method to get the greatest common divisor of two integer 
	 *  using the Euclidean algorithm
	 * 
	 * @param a integer 1
	 * @param b integer 2
	 * @return The greatest common divisor of the two number
	 */
	public int GreatestCommonDiv(int a, int b) {
		
		// if b < a ,swap their value so that a > b
		if( a < b) {
			int temp = b;
			b = a;
			a = temp;
		}
		
		if(a == 0 && b != 0) {
			return b;
		}else if( b == 0 && a!= 0) {
			return a;
		}
		
		//compute GCD using Euclidean algorithm
		int ans = 0;
		int i = 1;
		if (a % b == 0) {
			if (a < b) {
				return a;
			}
			else if(a > b){
				return b;
			}
		}
		while( (a % b) != 0) {
			if(a < b*i) {              // while remainder != 0
				ans = a -  b*(i - 1);  //  remainder = a -b*i
				a = b;                 // a =b
				b = ans;               // b = remainder
				i  = 0;                // reset i
			}
			else {
				i +=1; 
			}
		}
		return ans;
	}

}
