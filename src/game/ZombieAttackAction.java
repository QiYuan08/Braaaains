/**
 * 
 */
package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * @author Teh Qi Yuan
 * 
 * A attack action class for the zombie
 * 
 * TODO: need to check for losing arms probability
 * 	- loss weapon when both arm loss
 *  - 50% of dropping weapon if one arm loss
 *  - probability of punching is halved if one arm is loss
 *
 */
public class ZombieAttackAction extends AttackAction {

	public ZombieAttackAction(Actor target) {
		super(target);
	}
	
	/**
	 * This method will perform different attack action for the zombie
	 * based on the zombie condition
	 * e.g.
	 * - Zombie had only one hand or no hand
	 * - Zombie has weapon or not
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon(); // automatically return intrinsic weapon if actor no weapon
		
		if (isBite(weapon) ==  true) {
			ArrayList<Integer> choice = new ArrayList<Integer>();
			choice.add(0);
			choice.add(1);
			
			int choiceIndex;
			try {
				choiceIndex = rand(choice, 60, 0);
				if(choiceIndex == 0) {
					return actor + " misses " + target + ".";
				}
				else {
					actor.heal(5);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if (rand.nextBoolean()) {  // randomly let the actor miss his target
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();      
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);
		if (!target.isConscious()) {
			Item corpse = new PortableItem("dead " + target, '%');
			map.locationOf(target).addItem(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}
	
	private int rand(ArrayList<Integer> choice, int bias, int biasIndex) throws Exception {
		RandomGenerator b = new RandomGenerator();
		return b.biasRandom(choice, bias, biasIndex);
	}
	
	private boolean isBite(Weapon weapon) {
		return weapon.verb() == "bites";
	}

}
