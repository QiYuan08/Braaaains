package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class QuitAction extends Action{
	
	public QuitAction() {
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		return actor + " quitted the game.";
	}

	@Override
	public String menuDescription(Actor actor) {
		return "Player quits game";
	}
	
	public String hotkey() {
		return "q";
	}

}
