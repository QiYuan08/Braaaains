package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Crop class that is used with SowAction, HarvestAction and FertilizeAction within
 * FarmBehaviour to produce a Food object by the Farmer.
 * @author Fong Zhong Kern
 *
 */
public class Crop extends Ground {
	public final static int RIPING_TIME = 20;
	public int durationPlanted = 0;
	boolean ripe = false;
	
	/**
	 * Crop constructor with 3 parameters. Instantiated from SowAction by a Farmer.
	 * @param displayChar The display character on user interface
	 */
	public Crop(char displayChar) {
		super(displayChar);
	}
	
	/**
	 * Overriden tick method that checks if the crop object is ripe to change UI display
	 * and switches boolean variable to ripe.
	 */
	public void tick(Location location) {
		this.durationPlanted += 1;
		if(this.durationPlanted >= Crop.RIPING_TIME && this.displayChar != 'C' && this.ripe == false) {
			this.displayChar = 'C';
			this.ripe = true;
		}
	}
	
	/**
	 * Overriden allowableActions that returns the HarvestAction to Player specifically 
	 * to allow player to harvest the crop
	 */
	public Actions allowableActions(Actor actor, Location location, String direction) {
		if(this.ripe) {
			return new Actions(new HarvestAction(location));
		}
		return new Actions();
	}
	
	/**
	 * A setter method to get the duration planted during checks from FarmBehaviour 
	 * specifically FertilizeAction
	 * @param duration An integer value that should be higher than the durationPlanted as an effect of fertilization
	 */
	public void setDurationPlanted(int duration) {
		this.durationPlanted = duration;
	}
	
	/**
	 * A getter method to return the duration planted of this crop object.
	 * @return An Integer value of the duration of this crop planted.
	 */
	public int getDurationPlanted() {
		return this.durationPlanted;
	}
	
}
