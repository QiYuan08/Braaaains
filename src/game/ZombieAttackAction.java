package game;

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
 */
public class ZombieAttackAction extends AttackAction {

	public ZombieAttackAction(Actor target) {
		super(target);
	}

	/**
	 * This method will perform different attack action for the zombie based on the
	 * zombie condition e.g. - Zombie had only one hand or no hand - Zombie has
	 * weapon or not
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon(); // automatically return intrinsic weapon if actor no weapon

		// if the intrisic weapon is bite, it has a 40 % of hitting only
		if (isBite(weapon) == true) {
			try {
				int[] probability = { 60, 40 };
				RandomGenerator myRand = new RandomGenerator();

				int choiceIndex = myRand.probRandom(probability);
				if (choiceIndex == 0) {
					return actor + " misses " + target + ".";
				} else {
					// healing the zombie if biting hits
					actor.heal(5);
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} else { // if its other weapon, 50 % chance of missing
			if (rand.nextBoolean()) { // randomly let the actor miss his target
				return actor + " misses " + target + ".";
			}
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		target.hurt(damage);
		if (!target.isConscious()) {
			Corpse corpse = new Corpse("dead " + target, '%');
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

	/**
	 * A method to determine whether the weapon is biting
	 * 
	 * @param weapon The weapon to be checked
	 * @return True if the weapon is biting and false otherwise
	 */
	private boolean isBite(Weapon weapon) {
		return weapon.verb() == "bites";
	}

}
