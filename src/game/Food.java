package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * The Food class that will be used along with HealAction
 * @author Fong Zhong Kern
 *
 */
public class Food extends PortableItem{
	private int healAmount;
	
	/**
	 * The constructor of Food. Accepts 2 parameters name and displayChar which are the same as parent Item class constructor
	 * @param name Name of food
	 * @param displayChar Display character on game map
	 */
	public Food(String name, char displayChar, int healAmount) {
		super(name, displayChar);
		this.healAmount = healAmount;
	}
	
	/**
	 * Method to get the heal amount of the food object when it is instantiated.
	 * Used with HealAction to heal the Actor
	 * @return An Integer value corresponding to the amount that should be healed for. 
	 */
	public int getHealAmount() {
		return this.healAmount;
	}
	
	/**
	 * Overriden tick method to check whether a Food item exist at this current location
	 */
	public void tick(Location currentLocation) {
		boolean itemExists = false;
		for(Item items : currentLocation.getItems()) {
			if(items == this) {
				itemExists = true;
			}
		}
		if(itemExists == false) {
			currentLocation.removeItem(this);
		}
	}
	
	public Action getHealAction() {
		return new HealAction(this.healAmount, this);
	}

}
