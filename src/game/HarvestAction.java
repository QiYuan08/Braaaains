package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Farmer;
import game.Player;

/**
 * The HarvestAction class that is an extend of Action class to be used within FarmBehaviour
 * @author Fong Zhong Kern
 *
 */
public class HarvestAction extends Action{
	Location location;
	
	/**
	 * Constructor of HarvestAction, created when Crop object is ripe. 
	 * @param location The location of where the actor should carry out the HarvestAction
	 */
	public HarvestAction(Location location) {
		this.location = location;
	}
	
	@Override
	/**
	 * An overriden method of execute that creates a Food object watermelon and replacing the
	 * ground instance of Crop to dirt to allow re-sow of crop at the location
	 * Returns a String of the action
	 */
	public String execute(Actor actor, GameMap map) {
		Food watermelon = new Food("Watermelon" , 'w', 10);
		this.location.setGround(new Dirt());
		if(actor instanceof Farmer) {
			this.location.addItem(watermelon);
			return actor + " has dropped a watermelon at its location (" + this.location.x() + "," + this.location.y() + ").";
		}
		else if(actor instanceof Player) {
			actor.addItemToInventory(watermelon);
			return actor + " added a watermelon to their inventory.";
		}
		return null;
	}

	@Override
	/**
	 * An overriden method of menuDescription that returns the menu description to the PLayer
	 */
	public String menuDescription(Actor actor) {
		return actor + " harvests a crop.";
	}

}
