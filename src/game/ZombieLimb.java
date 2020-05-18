/**
 * TODO can change cast off limb at adjacent location
 * instead of at Zombie's location
 */
package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.ActorLocations;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Weapon;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * A class to shows the amount of legs and hand a zombie has left
 * It is automatically initialized in every zombie object
 * @author Teh Qi Yuan
 * 
 *
 */
public class ZombieLimb {
	
	private Display display;
	private ActorLocations actorLocation = new ActorLocations();
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
	 * Method to calculate the determine whether a limb will 
	 * be cast of based on a probability (currently its 25)
	 * 
	 * @return int The choice of whether a limb will be cast off
	 *  0 : not casting limb off
	 *  1 : casting an arm or leg off
	 */
	private int willFall(){
		
		// 75 percent for not cutting limb
		// 25 percent for casting off limb
		int[] probability = {75,25}; 		
		int choosenChoice = rand.probRandom(probability);
		
		return choosenChoice;
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
	private void dropWeapon(Actor actor, Location here, GameMap map) {
		
		// if loses both hand must drop
		if (this.hand == 0) {
			for(Item item: actor.getInventory()) {
				Action action = item.getDropAction();
				String result = action.execute(actor, map);
				display.println(result);
			}
		}
		// if loses 1 hand 50% of dropping a weapon
		else if (this.hand == 1) {
			if (equalProbability() == 1) {
				if(!(actor.getWeapon() instanceof IntrinsicWeapon)) { // if zombie holding a weapon
					WeaponItem weapon= (WeaponItem) actor.getWeapon();					
					actor.removeItemFromInventory(weapon);
					
					assert (actor.getWeapon() instanceof IntrinsicWeapon) : "Weapon not dropped!!";
				}
			}
		}
	}
	
	/**
	 * Method to determine whether a zombie limb will be
	 * cast off based on the probability calculated in
	 * willFall() 
	 * If zombie loses one hand, it will have 50% chance of
	 * dropping weapon
	 * If zombie loses both hand, it must drop the weapon
	 */
	public void castLimb(Actor actor) {
		
		Location here = actorLocation.locationOf(actor);
		GameMap map = here.map();
		
		// Determine whether a hand or leg will be cast of
		int handOrLeg = equalProbability();
		
		if(handOrLeg == 0) {  // casting off a hand
			if (this.hand > 0) {
				this.hand -= 1;
				
				// drop an ZombieHand weapon
				here.addItem(new ZombieHand());
				
				// if the zombie holding an weapon
				if(actor.getWeapon() instanceof IntrinsicWeapon) {
					dropWeapon(actor, here, map);
				}
			}
		}
		else if(handOrLeg == 1){ // casting of a leg
			if(this.leg > 0) {
				this.leg -= 1;
				
				here.addItem(new ZombieLeg());
			}
		}
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
			return false;
		}
		else {
			ans = true;
			this.moveCounter = 0;
		}
		
		return ans;
	}
}
