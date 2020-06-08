/**
 * 
 */
package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;

/**
 * @author Teh Qi Yuan
 * A class to allow user to choose target
 * based on the sniper rifle range
 *
 */
public class TargetMenu extends Action{
	
	private Display display;
	private Action action = null;
	
	
	public TargetMenu(Display display) {
		this.display = display;
	}
	
	/**
	 * Overriden method from Action class that add every zombie in the
	 * range of the player as target and let the player choose
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		((Player) actor).isAiming(true); // change condition of player to is aiming;
		Menu menu = new Menu();
		Actions actions = new Actions();
		ArrayList<Actor> targetList = pickTarget(map.locationOf(actor), map);
		String result = "";	
		
		if (targetList.size() > 0) {
			for(int i=0; i < targetList.size(); i++) {
				actions.add(new SniperMenu(targetList.get(i), this.display));
			}				
		}else {
			result += "No target in range.";
			action = new DoNothingAction();
		}
		
		this.action = menu.showMenu(actor, actions, this.display);
		 result += action.execute(actor, map);
		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return "Use the Sniper";
	}

	@Override
	public Action getNextAction() {
		if(this.action.getNextAction() != null) {
			return this.action;
		}
		
		return null;
	}
	
	
	/**
	 * A function to return an arrayList of possible target based on the 
	 * actor location
	 * @param actor Location of the player
	 * @param map The map which the player currently in
	 * @return arrayList An arrrayList of possible target
	 */
	private ArrayList<Actor> pickTarget(Location actor, GameMap map) {
		
		ArrayList<Integer[]> range = new SniperUtil().getRange(actor, map);
		ArrayList<Actor> targetList = new ArrayList<Actor>();
		/*
		 * Use the coordinate from SniperUtil class to get every zombie in 
		 * the range as target
		 */
		for(Integer[] coordinate: range) {
			Location here = new Location(map, coordinate[0], coordinate[1]);
			if(here.containsAnActor()){
				Actor actorAtHere = here.getActor();
				if(actorAtHere.hasCapability(ZombieCapability.UNDEAD)) {
					targetList.add(here.getActor());
				}		
			}
		}
		return targetList;
	}	
	

}
