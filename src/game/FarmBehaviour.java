package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;

/**
 * FarmBehaviour class that is a child class of Behaviour. This class will prioritize actions
 * in the following order: Fertilize if possible, Harvest if possible or else Sow a new crop.
 * @author Fong Zhong Kern
 *
 */
public class FarmBehaviour implements Behaviour{
	
	public FarmBehaviour() {
	}

	@Override
	public Action getAction(Actor actor, GameMap map) {
		// Init
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(actor).getExits());
		Collections.shuffle(exits);
		
		// Fertilizing
		// At this currentLocation where I am standing, is it a crop that I can fertilize?
		// I am standing on Crop?
		if(map.locationOf(actor).getGround() instanceof Crop) {
			Crop cropObj = (Crop) map.locationOf(actor).getGround();
			// Can I harvest this crop?
			if(cropObj.durationPlanted >= Crop.RIPING_TIME) {
				return new HarvestAction(map.locationOf(actor));
			}
			// Since I cannot harvest the crop, then I will Fertilize it
			if(cropObj.durationPlanted < Crop.RIPING_TIME) {
				return new FertilizeAction();
			}
		}
		// Around my position, are there any ripe crops or dirt?
		for(Exit e : exits) {
			// Can I harvest any ripe crops around me?
			if(e.getDestination().getGround() instanceof Crop) {
				Crop cropObj = (Crop) e.getDestination().getGround();
				if(cropObj.getDurationPlanted() >= Crop.RIPING_TIME) {
					return new HarvestAction(e.getDestination());
				}
			}
			// Is there a dirt around me so I could sow a crop?
			if(e.getDestination().getGround() instanceof Dirt) {
				return new SowAction(e.getDestination());
				}
		}
		// Guess I need to move from this position
		return null;
	}

}
