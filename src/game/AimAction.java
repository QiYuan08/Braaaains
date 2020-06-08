/**
 * 
 */
package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * @author Teh Qi Yuan
 * An action that will be executed if player choose to aim 
 * while using an sniper rifle
 *
 */
public class AimAction extends Action {

	@Override
	public String execute(Actor actor, GameMap map) {
		return actor + " successfully aimed at target";
	}

	@Override
	public String menuDescription(Actor actor) {
		
		return actor + " continue aiming ";
	}

}
