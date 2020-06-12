package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * Class representing an ordinary human.
 * 
 * 
 * @author ram
 *
 */
public class Human extends ZombieActor {
	private Behaviour behaviour = new WanderBehaviour();

	/**
	 * The default constructor creates default Humans
	 * 
	 * @param name the human's display name
	 */
	public Human(String name) {
		super(name, 'H', 100, ZombieCapability.ALIVE);
	}
	
	/**
	 * The protected constructor can be used to create subtypes
	 * of Human, such as the Player
	 * 
	 * @param name the human's display name
	 * @param displayChar character that will represent the Human in the map 
	 * @param hitPoints amount of damage that the Human can take before it dies
	 */
	protected Human(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints, ZombieCapability.ALIVE);
	}
	
	/**
	 * Checks if the human is standing on a Food object, pick it up if it is
	 * @param map The GameMap initialized
	 * @return PickUpItemAction on Food object
	 */
	protected Action existFood(GameMap map) {
		for(Item i : map.locationOf(this).getItems()) {
			if(i instanceof Food) {
				return new PickUpItemAction(i);
			}
		}
		return null;
	}
	
	/**
	 * Checks if the Human class is not at max health points. Returns HealAction
	 * @param map The GameMap initialized
	 * @return HealAction to allow healing of Human
	 */
	protected Action healActor(GameMap map) {
		if(this.hitPoints < this.maxHitPoints) {
			for(Item item : this.getInventory())
				if(item instanceof Food) {
					Food foodObj = ((Food) item);
					return foodObj.getHealAction();
				}
		}
		return null;
	}
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// FIXME humans are pretty dumb, maybe they should at least run away from zombies?
		// Prioritize survival, heal if hp is not max and inventory of Actor has Food
		Action healing = healActor(map);
		if(healing != null) {
			return healing;
		}
		// If human actor is standing on a Food object, pick it up
		Action pickUpFood = existFood(map);
		if(pickUpFood != null) {
			return pickUpFood;
		}
		return behaviour.getAction(this, map);
	}

}
