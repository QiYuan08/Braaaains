package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;


/**
 * SowAction class that is used as an Action
 * @author Fong Zhong Kern
 *
 */
public class SowAction extends Action{
	Location location;
	public final static double PROBABILITY = 0.33;
	
	/**
	 * A SowAction constructor where the parameter is a Location object which is 
	 * determines the location where sow action should occur
	 * @param location The location where the sow action is performed on
	 */
	public SowAction(Location location) {
		this.location = location;
	}

	@Override
	/**
	 * An overriden method that creates a Crop as a result of SowAction.
	 * Returns a string if SowAction is successful with the chance of 33%
	 */
	public String execute(Actor actor, GameMap map) {
		double rand = Math.random(); // Create a random number
		if(rand > 0.00 && rand <= SowAction.PROBABILITY) {
			this.location.setGround(new Crop(('c')));  // Create a new Crop and return the action string
			return actor + " " + "sowed a crop at (" + this.location.x() + "," + this.location.y() + ")";
		}
		return actor + " failed to sow any crop."; 
	}

	@Override
	/**
	 * Returns the menu description string of this action to the Player
	 */
	public String menuDescription(Actor actor) {
		return actor + " sows a crop.";
	}

}
