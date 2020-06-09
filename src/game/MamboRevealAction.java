package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class MamboRevealAction extends Action{
	
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
