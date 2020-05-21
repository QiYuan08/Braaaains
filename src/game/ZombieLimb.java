/*
 * TODO can change cast off limb at adjacent location
 * instead of at Zombie's location
 */
package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * A class to keep track the amount of legs and hand a zombie has left
 * It is automatically initialized in every zombie object
 * This class also contain method to cast off a zombie limb when damage is 
 * received and movement control for the zombie if a zombie has broken leg
 * 
 * @author Teh Qi Yuan
 *
 */
public class ZombieLimb {
	
	private int hand;
	private int leg;
	private RandomGenerator rand = new RandomGenerator();
	private int moveCounter;
	
	public ZombieLimb() {
		this.hand = 2;
		this.leg  = 2;
		this.moveCounter = 0;
	}
	/**
	 *  Setter to retrieve the number of hand
	 *  left for this zombie
	 * @return The number of hand left
	 */
	public int hand() {
		return this.hand;
	}

	/**
	 * Method to calculate the determine whether a limb will 
	 * be cast of based on a probability (currently its 25)
	 * 
	 * @return boolean True when a limb is cast of and false otherwise

	 */
	private boolean willFall(){
		
		// 75 percent for not cutting limb
		// 25 percent for casting off limb
		int[] probability = {75,25}; 		
		int choosenChoice = rand.probRandom(probability);
		
		if (choosenChoice == 1) {
			return true;
		}
		return false;
	}
	
	/** 
	 * Method to determine whether an event will occur
	 * given that they have similar probability of happening
	 * 
	 * @return 0 for choice 1 and 1 for choice 2
	 */
	private int equalProbability() {
		
		// equal probability of holding or dropping weapon
		int[ ] dropOrNot = {50,50};
		int choosenChoice = rand.probRandom(dropOrNot);
		
		return choosenChoice;
		
	}
	
	/**
	 *  Method to drop any weapon the zombie is holding
	 * @param actor The actor to perform the action
	 */
	public String dropWeapon(Actor actor, GameMap map) {
		
		String result = null;
		
		// if loses both hand must drop all weapon
		if (this.hand == 0) {
			for(Item item: actor.getInventory()) {
				Action action = item.getDropAction();
				result = action.execute(actor, map);
			}
		}
		// if loses 1 hand 50% of dropping a weapon
		else if (this.hand == 1) {
			if (equalProbability() == 1) {
				for(Item item: actor.getInventory()) {
					Action action = item.getDropAction();
					result = action.execute(actor, map);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Method to determine whether a zombie limb will be
	 * cast off based on the probability calculated in
	 * willFall() 
	 * If zombie loses one hand, it will have 50% chance of
	 * dropping weapon
	 * If zombie loses both hand, it must drop the weapon
	 */
	public String castLimb(Actor actor, GameMap map) {
		
		Location here = map.locationOf(actor);
		String result =  null;
		
		if (willFall()) {
			// Determine whether a hand or leg will be cast of
			int handOrLeg = equalProbability();
			
			if(handOrLeg == 0) {  // casting off a hand
				if (this.hand > 0) {
					this.hand -= 1;
					result += actor.toString() + " has fallen a hand";
					
					// drop an ZombieHand weapon
					here.addItem(new ZombieHand());
					
//					// if the zombie holding an weapon drop it
//					if(actor.getWeapon() instanceof IntrinsicWeapon) {
//						dropWeapon(actor, here, map);
//					}
				}
			}
			else if(handOrLeg == 1){ // casting of a leg
				if(this.leg > 0) {
					this.leg -= 1;
					result = actor.toString() + " has fallen a leg";

					
					here.addItem(new ZombieLeg());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Method that determine whether a zombie
	 * are allowed to move this turn
	 * @return true if zombie can move this turn and false otherwise
	 */
	public boolean canMove() {
		
		boolean ans = true;
		
		if(this.leg == 0) {
			return false;
		}
		else if (this.leg == 1 && moveCounter != 2) {
			this.moveCounter += 1;
			return false;
		}
		else {
			ans = true;
			this.moveCounter = 0;
		}
		
		return ans;
	}
}
