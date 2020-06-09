package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class MamboRelocateAction extends Action{
	
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
