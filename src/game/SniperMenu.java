package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;

/**
 * @author Teh Qi Yuan 
 * This is a class used to let user choose whether to
 * continue aiming or take a shot for sniper rifle given a different
 * number of turn used to aim
 *
 */
public class SniperMenu extends AttackAction {

	private int round_Count = 0;
	private Display display;

	public SniperMenu(Actor target,Display display) {
		super(target);
		this.display = display;
	}

	/**
	 * Return different action depending on how many round has the player
	 * aimed at its target
	 * 
	 * @param actor The actor using the sniper
	 * @param map The map the actor is currently in 
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		Actions actions = new Actions();
		Menu menu = new Menu();
		
		round_Count += 1;
		if (round_Count == 3) {
			actions.add(new SniperShootAction(this.target, round_Count));
		}
		else if (round_Count == 2){
			actions.add(new AimAction());
			actions.add(new SniperShootAction(this.target, round_Count));
		}else if (round_Count == 1) {
			actions.add(new AimAction());
			actions.add(new SniperShootAction(this.target, round_Count));
		}
		
		Action action = menu.showMenu(actor, actions, this.display);
		String result = action.execute(actor, map);
		
		return result;
	}
	

	/**
	 * Return string
	 * @param actor Text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "Target: " + this.target;
	}

	/**
	 * While the number of round used to aim is smaller than 1,
	 * return the submenu to allow player to aim or shoot
	 */
	@Override
	public Action getNextAction() {
		if (round_Count < 3) {
			return this;
		}
		return null;
	}
	

}
