package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * @author Teh Qi Yuan
 *
 *         Method for user to implement shooting using a sniper rifle
 */
public class SniperShootAction extends AttackAction {

	private SniperUtil utility = new SniperUtil();
	private int round_Count;

	public SniperShootAction(Actor target, int round_Count) {
		super(target);
		this.round_Count = round_Count;
	}

	/**
	 * Method to traverse in a line between shooter and target and determine the
	 * damage if it hits and actor or does nothing if it hits a wall or something
	 * else
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Location shooter_location = map.locationOf(actor);
		Location affectedLine = null;
		OnDead ondead = new OnDead();
		Location target_location = map.locationOf(this.target);
		utility.getLine(shooter_location, target_location); // calculate the straight line equation using shooter and
															// target coordinate
		int distance = shooter_location.x() - target_location.x();
		String result = "";

		((Player) actor).isAiming(false); // tell player class we already take the shot
		// reduce bullet count from player inventory
		for (Item item : actor.getInventory()) {
			if (item instanceof SniperBullet) {
				((SniperBullet) item).reduce_bullet();
			}
		}

		/*
		 * Loop through the horizontal coordinate to get the y coordinate using the
		 * equation and determine if a shoot hit an enemy or other object
		 */
		if (distance == 0) { // if target directly above or below
			affectedLine = new Location(map, target_location.x(), target_location.y());
		} else {
			for (int i = 1; i <= Math.abs(distance); i++) {

				int x_coord = shooter_location.x() + calCounter(distance, i);
				int y_coord = (int) utility.getY(x_coord);
				affectedLine = map.at(x_coord, y_coord);

				if (map.isAnActorAt(affectedLine)) { // if the shot hit an actor
					break;
				} else if (affectedLine.getGround().blocksThrownObjects()) { // if hit a barrier
					return actor + " hit something else.";

				}
			}
		}
		Actor wictim = map.getActorAt(affectedLine);

		if (hitOrMiss()) {
			wictim.hurt(getDamage());
			result += actor + " " + new SniperRifle().verb() + " " + this.target;
			if (!wictim.isConscious()) {
				result += ondead.dead(actor, wictim, map);
			}
		} else {
			result += actor + " miss " + this.target;
		}

		return result;
	}

	/**
	 * A method to determine whether a bullet will hit or miss based on the
	 * probability after n round of aiming
	 * 
	 * @return true if the bullet hits and false otherwise
	 */
	private boolean hitOrMiss() {

		RandomGenerator rand = new RandomGenerator();

		if (this.round_Count == 3) {
			return true;
		} else if (this.round_Count == 2) {
			int[] probability = { 90, 10 };

			if (rand.probRandom(probability) == 1) {
				return true;
			}

		} else if (this.round_Count == 1) {

			int[] probability = { 75, 25 };
			if (rand.probRandom(probability) == 1) {
				return true;
			}
		}

		return false;
	}

	/**
	 * A sub function to determine whether to increment or decrement the x
	 * coordinate of player based on whether the target is on the left or right of
	 * player
	 * 
	 * @param distance The x distance between target and shooter
	 * @param counter  The counter
	 * @return The counter after increment or decrement by 1
	 */
	private int calCounter(int distance, int counter) {

		if (distance < 0) {
			return counter;
		}

		return -counter;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " shoot " + this.target;
	}

	/**
	 * A method to return the appropriate damage based on the turn used to aim
	 * 
	 * @return The damage amount
	 */
	public int getDamage() {
		if (this.round_Count == 1) {
			return new SniperRifle().damage();
		} else if (this.round_Count == 2) {
			return new SniperRifle().damage() * 2;
		} else {
			return 9999;
		}
	}

}
