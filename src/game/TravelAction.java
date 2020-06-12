package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * @author Teh Qi Yuan
 * A special action class to allow player to travel from map to map
 *
 */
public class TravelAction extends Action{
	
	//TODO where the player will land after traveling to new maps
	private GameMap nextMap;
	private int x;
	private int y;
	
	/**
	 * Constructor
	 * @param nextMap The destination map of the actor
	 */
	public TravelAction(GameMap nextMap) {
		this.nextMap = nextMap;

	}
	
	/**
	 * Inherited method from action class that move player from the old maps into 
	 * the new map
	 * @param actor The actor to move
	 * @param map The map the actor currently is in
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		map.locationOf(actor).map().moveActor(actor, this.nextMap.at(this.x, this.y));	
		return "Welcome to new map.";
	}

	@Override
	public String menuDescription(Actor actor) {
		return "Travel to other maps.";
	}

	
}
