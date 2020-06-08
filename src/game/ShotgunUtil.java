/**
 * 
 */
package game;

import edu.monash.fit2099.engine.Location;

/**
 * A utility class for the weapon shotgun to determine area of affect
 *
 * @author Teh Qi Yuan
 *
 */
public class ShotgunUtil {
	
	
	/**
	 * A method to call the corresponding sub method based on the
	 * direction to shoot to get the area of effect
	 * 
	 * @throws IllegalArgumentException if the direction given is invalid
	 * @param here The location of the shooter
	 * @param directionName The direction of where the shooter want to shoot
	 * @return The coordinate of the area affected in an array of matrix
	 */
	public int[][] getAffectedArea(Location here, String directionName){
		
		int[][] area = new int[9][2];
		int[] direction = Direction(directionName);
		
		if(
				directionName == "North-East" || 
				directionName == "South-East" ||
				directionName == "North-West" ||
				directionName == "South-West") {
			area = diagonal(here,direction);
		}
		else if( directionName == "East" || directionName == "West") {
			area = LeftRight(here,direction);
		}
		else if( directionName == "North" || directionName == "South") {
			area = UpDown(here, direction);
		}
		else {
			throw new IllegalArgumentException("Direction given invalid");
		}
		
		return area;
		
	}

	/**
	 * Method to return the direction in form of x and y coordinate given the
	 * direction name e.g North will return [0,1] where the first integer is the
	 * x-coordinate and the second integer is the y-coordinate
	 * 
	 * @param directionName The name of the direction
	 * @return The direction in the form of x and y coordinate
	 */
	private int[] Direction(String directionName) {

		/*
		 * direction in the form of [west,east,north,south,north-east, south-east,
		 * north-west, south-west]
		 */
		int[] direction = { 0, 0 };
		String[] name = { "West", "East", "North", "South", "North-East", "South-East", "North-West", "South-West" };
		int[] x = { -1, 1, 0, 0, 1, 1, -1, -1 };
		int[] y = { 0, 0, -1, 1, -1, 1, -1, 1 };

		for (int i = 0; i < name.length; i++) {
			if (name[i] == directionName) {
				direction[0] = x[i];
				direction[1] = y[i];
			}
		}

		return direction;
	}

	/**
	 * Method to get the area affected by shotgun if fired diagonally e.g
	 * South-East, North-West
	 * 
	 * @param here      Location of the shooter
	 * @param direction to shoot in the form of 1,0 and -1 coordinate
	 * @return The coordinate of the area affected in an array of matrix
	 */
	private int[][] diagonal(Location here, int[] direction) {

		int x = here.x();
		int y = here.y();

		int[][] area = new int[9][2];
		int counter = 1;
		int area_counter = 0; // counter for the matrix to fill up

		/*
		 * For every loop There are three coordinate with same y with only different x,
		 * hence the inner while loop to change the x coordinate for every y
		 * 
		 */
		for (int i = 1; i <= 3; i++) {
			while (counter <= 3) {
				area[area_counter][0] = x + counter * direction[0];
				area[area_counter][1] = y + i * direction[1];
				counter += 1;
				area_counter += 1;
			}
			counter = 1;
		}
		return area;
	}

	/**
	 * Method to get the area affected by shotgun if fired in the north and south
	 * direction
	 *
	 * @param here      Location of the shooter
	 * @param direction to shoot in the form of 1,0 and -1 coordinate
	 * @return The coordinate of the area affected in an array of matrix
	 */
	private int[][] UpDown(Location here, int[] direction) {

		int x = here.x();
		int y = here.y();
		int[][] area = new int[9][2];
		int area_counter = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = x - i; j <= x + i; j++) {
				area[area_counter][0] = j;
				area[area_counter][1] = y + (i + 1) * direction[1];
				area_counter += 1;
			}
		}

		return area;
	}

	/**
	 * Method to get the area affected by shotgun if fired in the west and east
	 * direction
	 *
	 * @param here      Location of the shooter
	 * @param direction to shoot in the form of 1,0 and -1 coordinate
	 * @return The coordinate of the area affected in an array of matrix
	 */

	private int[][] LeftRight(Location here, int[] direction) {

		int x = here.x();
		int y = here.y();

		int[][] area = new int[9][2];
		int area_counter = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = y - i; j <= y + i; j++) {
				area[area_counter][0] = x + (i + 1) * direction[0];
				area[area_counter][1] = j;
				area_counter += 1;
			}
		}

		return area;
	}

}
