package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Location;

/**
 * Corpse object that is created when a Human is killed by a Zombie
 * @author Fong Zhong Kern
 *
 */

public class Corpse extends PortableItem{
	int counter = 0;  // When counter reaches 5-10 turns activate probability which calculates the chances of resurrection of corpse to Zombie object
    double probability = 0; // The probability of the corpse becoming a Zombie after 5 - 10 turns.
	boolean becameZombie = false;
    
	/**
	 * Corpse constructor that requires 2 parameters name and display char. It should contain no allowable actions as there are no actions for this object
	 * @param name Name of the corpse object
	 * @param displayChar Display character of the corpse object on game map
	 */
	public Corpse(String name, char displayChar) {
		super(name, displayChar);
	}
	
	/**
	 * The tick method which override method from Item class. The method contains 2 parameter which is used when Corpse object is picked up
	 */
	public void tick(Location currentLocation, Actor actor) {
		this.counter += 1;
		if (this.checkCounter(this.counter)) {
			this.probability += 0.2;
		}
		double randomNumber = Math.random() + this.probability;
		if(randomNumber >= 1 && this.becameZombie == false){
			// Havent implemented pickup zombie		
			List<Exit> exits = new ArrayList<Exit>(currentLocation.getExits());
			Collections.shuffle(exits);
			for(Exit e : exits) {
				if (e.getDestination().containsAnActor() == false) {
					e.getDestination().addActor(new Zombie(this.name));
					this.becameZombie = true;
					actor.removeItemFromInventory(this);
					break;
				}
			}
		}
	}
	
	/**
	 * A tick method which override method from Item class. The method contains 1 parameter which is used when Corpse is not picked up
	 */
	public void tick(Location currentLocation) {
		this.counter += 1;
		if (this.checkCounter(this.counter)) {
			this.probability += 0.2;
		}
		double randomNumber = Math.random() + this.probability;
		if(randomNumber >= 1 && this.becameZombie == false && currentLocation.containsAnActor() == false) {
			currentLocation.addActor(new Zombie(this.name));
			this.becameZombie = true;
			currentLocation.removeItem(this);
		}
	}
	
	/**
	 * Local boolean method which checks the number of ticks of the Corpse object to allow resurrection as Zombie
	 * @param counter The number of tick when the method is called
	 * @return A boolean value which determines if the counter is between 5 and 10
	 */
	private boolean checkCounter(int counter) {
		if (counter >= 5 && counter <= 10) {
			return true;
		}
		return false;
	}
}
