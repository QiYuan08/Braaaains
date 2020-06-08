package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * An special action class to compute the shooting action of a gun
 * 
 * @author Teh Qi Yuan
 *
 */
public class ShotgunShootAction extends Action {
	
	private String direction;
	
	/**
	 * Constructor
	 * 
	 * @param direction Direction as of where to shoot
	 */
	public ShotgunShootAction(String direction) {
		this.direction = direction;
	}



	/**
	 * Overridden method from Action class
	 * This method first get the area of affect based on the direction
	 * to shoot and the position of the player.
	 * And then check if an enemy exist in an area.
	 * The weapon will damage ally in the area too.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Location here = map.locationOf(actor);
		OnDead ondead = new OnDead();
		RandomGenerator rand = new RandomGenerator();
		String result = "";
		int[][] area = new int[9][2];

		try {
			area = new ShotgunUtil().getAffectedArea(here, this.direction);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		/*
		 * Loop through the coordinate of affected area 
		 * and compute whether an enemy will receive damage
		 */
		for (int x = 0; x < area.length; x++) {

				Location affectedArea = new Location(map, area[x][0], area[x][1]);
				if (map.isAnActorAt(affectedArea)) {

					int[] probability = { 75, 25 };
					if (rand.probRandom(probability) == 0) {

						Actor wictim = affectedArea.getActor();
						wictim.hurt(new Shotgun().damage());
						result += actor + new Shotgun().verb() + wictim + ".\n";
						if(!actor.isConscious()) {
							result += ondead.dead(actor, wictim, map);
						}

				}
			}
		}
		
		if(result =="") { // if the player didn't hit anything
			result = actor + " hit no one.";
		}
		
		// reduce bullet count from player inventory
		for(Item item: actor.getInventory()) {
			if(item instanceof ShotgunBullet) {
				((ShotgunBullet) item).reduce_bullet();
			}
		}
		return result;
	}

	@Override
	public String menuDescription(Actor actor) {

		return actor + " shoot towards " + this.direction;
	}

}
