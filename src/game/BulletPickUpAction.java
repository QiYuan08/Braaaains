/**
 * 
 */
package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * @author Teh Qi Yuan
 * A class specially for picking up bullet
 * This class add the bullet into existing bullet count
 * in inventory instead of adding new bullet
 *
 */
public class BulletPickUpAction extends PickUpItemAction {

	
	public BulletPickUpAction(Item item) {
		super(item);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Override the PickupItemAction to add to existing
	 * bullet count instead of adding new bullet into 
	 * inventory everytime
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		boolean added = false;
		
		map.locationOf(actor).removeItem(item);
		for(Item item: actor.getInventory()) {
			// check for bullet for different gun
			if(item.getClass() == this.item.getClass()) { 
				((Bullet) item).add_bullet(5);
				added = true;
			}
		}
		
		if(added == false) {
			actor.addItemToInventory(item);
		}
		return menuDescription(actor);
	}

	
	

}
