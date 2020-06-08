package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * @author Teh Qi Yuan
 * 
 *         A class to determine all the target and range of the rifle. The range
 *         of a sniper rifle is half a map.
 *
 */
public class SniperUtil {

	private double gradient;
	private double b;

	public ArrayList<Integer[]> getRange(Location here, GameMap map) {

		ArrayList<Integer[]> coordinate = new ArrayList<Integer[]>();
		int x = here.x();
		int y = here.y();
		int x_range = (map.getXRange().max() - map.getXRange().min()) / 2;
		int y_range = (map.getYRange().max() - map.getYRange().min()) / 2;

		/*
		 * Add the coordinate in range in the opposite direction e.g up and down of the
		 * player and left and right of the player
		 */
		for (int i = 0; i < x_range; i++) {
			for (int j = 0; j < y_range; j++) {
				Integer[] rightUp = { x + i, y + j };
				Integer[] leftDown = { x - i, y - j };
				Integer[] rightDown = { x + i, y - j };
				Integer[] leftUp = { x - i, y + j };

				if (InRange(rightDown, map)) {
					coordinate.add(rightDown);
				}
				if (InRange(leftUp, map)) {
					coordinate.add(leftUp);
				}

				if (InRange(rightUp, map)) {
					coordinate.add(rightUp);
				}

				if (InRange(leftDown, map)) {
					coordinate.add(leftDown);
				}

			}
		}

		return coordinate;
	}

	/**
	 * Method to check whether a given coordinate is within the map
	 * 
	 * @param coord The coordinate to check
	 * @param map   The map to check
	 * @return true if the coordinate is within the map and false otherwise
	 */
	private boolean InRange(Integer[] coord, GameMap map) {
		int x_max = map.getXRange().max();
		int y_max = map.getXRange().max();
		int x_min = map.getXRange().min();
		int y_min = map.getYRange().min();

		if (coord[0] > x_max || coord[0] < x_min) {
			return false;
		} else if (coord[1] > y_max || coord[1] < y_min) {
			return false;
		}

		return true;
	}

	/**
	 * Method to calculate the straight line equation given the location of the
	 * shooter and target
	 * 
	 * @param shooter Location of the shooter
	 * @param target  Location of the target
	 */
	public void getLine(Location shooter, Location target) {

		double shooter_x = shooter.x();
		double shooter_y = shooter.y();
		double target_x = target.x();
		double target_y = target.y();

		this.gradient = (target_y - shooter_y) / (target_x - shooter_x);
		this.b =  (shooter_y - (gradient * shooter_x));
	}

	/**
	 * Method to return the y coordinate given the x coordinate using the gradient
	 * and intercept calculated in getLine method
	 * 
	 * @param x The x coordinate
	 * @return The corresponding y coordinate
	 */
	public double getY(int x) {

		return (gradient * x) + b;
	}
}
