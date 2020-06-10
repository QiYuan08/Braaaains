package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * MamboMarie relocate action which is used on its 30th turn to relocate and camouflage herself.
 * @author Fong Zhong Kern
 *
 */
public class MamboRelocateAction extends Action{
	
	/**
	 * Empty constructor as we wont be needing any parameters for this action
	 */
	public MamboRelocateAction() {
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		return actor + ": Your time is up, I'm no where to be found!"; 
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " relocates.";
	}

}
