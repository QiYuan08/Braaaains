package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * FertilizeAction which is an extend of Action which is used to Fertilize an unripe crop
 * @author Fong Zhong Kern
 *
 */
public class FertilizeAction extends Action{
	
	// Empty parameter constructor, no info is needed as Actor object can only fertilize Crop
	// at its current location
	public FertilizeAction() {
	}

	@Override
	/**
	 * Overriden method that increases the duration planted attribute of the crop
	 */
	public String execute(Actor actor, GameMap map) {
		Location actor_loc = map.locationOf(actor);  //
		Crop cropObj = (Crop) actor_loc.getGround();
		cropObj.setDurationPlanted(cropObj.getDurationPlanted() + 10);
		return actor + " fertilized the crop at its current location (" + actor_loc.x() + "," + actor_loc.y() + ").";
	}

	@Override
	/**
	 * Overriden method that returns the menu description of this class to the Player
	 */
	public String menuDescription(Actor actor) {
		return actor + " fertilizes crop on current location.";
	}

}
