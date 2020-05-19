/**
 * 
 */
package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Teh Qi Yuan
 * 
 * This is as implementation of the random number generator with 
 * specific probability for each element
 *
 */
public class RandomGenerator {

	/**
	 * This method is used to return an choices in the form of integer starting from
	 * 0 to N number of choices, where N is the length of input parameter probability
	 * 
	 * @exception IllegalArgumentException if the sum of probability given is not
	 *                                     100
	 * @param probability The probability of each event in integer
	 * @return The index of the event based on the probability list given
	 * 
	 */
	public int probRandom(int[] probability) {

		int choiceIndex = 0;
		int sum = 0;
		ArrayList<Integer> clonedList = new ArrayList<Integer>();
		clonedList = cloneArray(probability);

		for (Integer element : probability) {
			sum += element;
		}
		if (sum != 100) {
			throw new IllegalArgumentException("Sum of proability must be 100");
		}

		clonedList.add(0, 1);
		int randNum = new Random().nextInt(99);
		for (int i = 0; i < clonedList.size() - 1; i++) {

			if (randNum >= (clonedList.get(i) - 1) & randNum < (clonedList.get(i) - 1 + clonedList.get(i + 1) - 1)) {
				return choiceIndex = i;
			} else if (randNum >= (100 - clonedList.get(clonedList.size() - 1) - 1) & randNum < 100) {
				return choiceIndex = probability.length - 1;
			}
		}

		return choiceIndex;
	}

	private ArrayList<Integer> cloneArray(int[] original) {

		ArrayList<Integer> clonedArray = new ArrayList<Integer>();
		for (Integer item : original) {
			clonedArray.add(new Integer(item));
		}

		return clonedArray;
	}
}
