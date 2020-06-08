/**
 * 
 */
package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * A class to create submenu for shotgun
 * after player selected to use shotgun
 * Extend the Action class so that I can
 * use the execute method to allow user to choose
 * an action
 * 
 * @author Teh Qi Yuan
 *
 */
public class ShotgunMenu extends Action{
	
	private Display display;
	private Menu menu =  new Menu();
	
	public ShotgunMenu(Actor target, Display display) {
		this.display = display;
	}
	
	/**
	 * Create a submenu for the player to choose which direction
	 * to shoot to and execute the action and return
	 * the result back to world class
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		Actions actions = new Actions();
		/*
		 * Add all the direction that the actor can 
		 * shoot at
		 */
		actions.add(new ShotgunShootAction("North"));
		actions.add(new ShotgunShootAction("South"));
		actions.add(new ShotgunShootAction("East"));
		actions.add(new ShotgunShootAction("West"));
		actions.add(new ShotgunShootAction("South-East"));
		actions.add(new ShotgunShootAction("South-West"));
		actions.add(new ShotgunShootAction("North-East"));
		actions.add(new ShotgunShootAction("North-West"));

		Action action = menu.showMenu(actor, actions, this.display);
		String result = action.execute(actor, map);
		
		return result;
	}

	@Override
	public String menuDescription(Actor actor) {		
		return "Use the shotgun";
	}
	
	

}
