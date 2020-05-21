package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Human {

	private Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		// add craft action into player if they have a zombiehand or leg
		for(Item item : this.getInventory()) {
			if(item instanceof ZombieHand) {
				actions.add(new CraftAction("ZombieHand", "ZombieClub"));
			}
			else if( item instanceof ZombieLeg) {
				actions.add(new CraftAction("ZombieLeg", "ZombieMace"));
			}
		}
		// Return HarvestAction when on ripe crop
		if(map.locationOf(this).getGround().getDisplayChar() == 'C'){
			actions.add(new HarvestAction(map.locationOf(this)));
		}
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}
}
