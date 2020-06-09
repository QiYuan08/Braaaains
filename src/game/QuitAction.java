package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * QuitAction action class which is used by the player to quit the game.
 * @author Fong Zhong Kern
 *
 */
public class QuitAction extends Action{
	
	/**
	 * No parameters needed to quit the current game session
	 */
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
	@Override
	public String hotkey() {
		return "q";
	}

}
