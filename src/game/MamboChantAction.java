package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * MamboChantAction class, the main action class which is used by MamboMarie to create zombies on random parts of the map.
 * Called on every 10th turn when MamboMarie is revealed.
 * @author Fong Zhong Kern
 *
 */
public class MamboChantAction extends Action{
	
	/**
	 * Constructor of MamboChantAction which doesn't need any parameter values as object required are passed in execute
	 */
	public MamboChantAction() {
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		Random rand = new Random();
		int i = 0;
		// Creating 5 Zombies on the game map
		while(i < 5) {
			int x_coord = rand.nextInt(map.getXRange().max() + 1);
			int y_coord = rand.nextInt(map.getYRange().max() + 1);
			Location location = map.at(x_coord, y_coord);
			if(location.canActorEnter(actor) && !map.isAnActorAt(location)) {
				// Clear to spawn a zombie at this location
				map.addActor(new Zombie("Mambo Zombie " + i), location);
				i += 1;
			}
		}
		return actor + " spent a day chanting and created 5 new zombies!";
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " spends a turn chanting";
	}

}
