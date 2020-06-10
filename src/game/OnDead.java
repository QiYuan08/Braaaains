/**
 * 
 */
package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * @author Teh Qi Yuan
 * This class decides what to do after an actor died
 *
 */
public class OnDead {
	
	/**
	 * A method to drop all the item from an actor and turn it into a corpse if
	 * its killed by someone
	 * @param map The map in which the actor stay
	 * @param target The actor that is killed
	 * @return a description of what happened that can be displayed to the user.
	 */
	public String dead(Actor target, GameMap map) {
		Item corpse = new PortableItem("dead " + target, '%');
		map.locationOf(target).addItem(corpse);
		
		Actions dropActions = new Actions();
		for (Item item : target.getInventory())
			dropActions.add(item.getDropAction());
		for (Action drop : dropActions)		
			drop.execute(target, map);
		map.removeActor(target);	
		
		return System.lineSeparator() + target + " is killed.";
	}

}
