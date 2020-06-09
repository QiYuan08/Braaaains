package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * The MamboRevealAction action class. This action object is returned when MamboMarie revealed herself on the map which is 5%
 * It returns a dialogue for the display.
 * @author Fong Zhong Kern
 *
 */
public class MamboRevealAction extends Action{
	
	/**
	 * An empty constructor as no parameters are needed to create a dialogue
	 */
	public MamboRevealAction() {
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		return actor + ": I'm here! HAHAHAHA!";
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " reveals herself";
	}

}
