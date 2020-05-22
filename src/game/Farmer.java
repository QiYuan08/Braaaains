package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

/**
 * Farmer class which is an extend of Human class which has FarmBehaviour. Instantiated in Application class
 * @author Fong Zhong Kern
 *
 */
public class Farmer extends Human{
	private Behaviour[] behaviours = {
			new FarmBehaviour(),
			new WanderBehaviour()
			};
	
	/**
	 * Constructor of a Farmer object.
	 * @param name The name of the farmer
	 * @param displayChar The display character of the farmer on the UI display
	 * @param hitPoints The total hitpoints (Health) of this  farmer
	 */
	protected Farmer(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}
	
	/**
	 * The playTurn method of Farmer which will carry out farming behaviour if possible
	 * which includes (SowAction, HarvestAction and FertilizeAction). Else, it wanders around
	 */
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Farmer picking up food at its location
		Action pickUpFood = existFood(map);
		if(pickUpFood != null) {
			return pickUpFood;
		}
		// Farmer performing healing if possible (Survival priority)
		Action healing = this.healActor(map);
		if(healing != null) {
			return healing;
		}
		// Farmer perform farming actions
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) {
				return action;
			}
		}
		
		// Wander if unable to perform any actions
		return behaviours[1].getAction(this, map);
	}
}
