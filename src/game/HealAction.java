package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * The HealAction class that is extended from Action class used to heal an injured Actor
 * @author Fong Zhong Kern
 *
 */
public class HealAction extends Action{
	int points;
	Food food;
	
	/**
	 * The constructor of HealAction with a single integer parameter
	 * @param healAmount The amount that should be healed for when the actor uses this action
	 */
	public HealAction(int healAmount, Food foodObj) {
		this.points = healAmount;
		this.food = foodObj;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		actor.heal(this.points);
		actor.removeItemFromInventory(this.food);
		return actor + " is healed for " + this.points + ".";
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " heals for " + this.points + ".";
	}
	
}
