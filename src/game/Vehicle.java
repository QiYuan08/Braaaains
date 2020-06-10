package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.World;

/**
 * @author Teh Qi Yuan
 * A class that implement vehicle that allows player
 * to travel between map
 */
public class Vehicle extends Item{
	
	/**
	 * Constructor
	 * The constructor adds a news instance of TravelAction into the list of
	 * allowableActions
	 * @param world The world this vehicle is currently in
	 * @param nextMap The destination map after user choose to travel
	 * @param x The x coordinate of the player in the new map
	 * @param y The y coordinate of the player in the new map
	 */
	public Vehicle(World world, GameMap nextMap) {
		super("Car", 'O', false);
		this.allowableActions.add(new TravelAction(nextMap));
	}

}
