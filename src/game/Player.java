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
	
	
	/**
	 * Player will have the option of crafting weapon if a zombie hand or leg
	 * are in their inventory
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		// add craft action into player if they have a zombiehand or leg
		// add HealAction into player if they have a Food
		for(Item item : this.getInventory()) {
			if(item instanceof ZombieHand) {
				actions.add(new CraftAction("ZombieHand", "ZombieClub"));
			}
			if(item instanceof ZombieLeg) {
				actions.add(new CraftAction("ZombieLeg", "ZombieMace"));
			}
			Action healing = this.healActor(map);
			if(healing != null) {
				actions.add(healing);
			}
		}
		// Add HarvestAction when on ripe crop using Crop.allowableActions
		if(map.locationOf(this).getGround() instanceof Crop){
			Crop cropObj = (Crop) map.locationOf(this).getGround();
			Actions allowableActions = cropObj.allowableActions(this, map.locationOf(this), "here");
			for(Action action : allowableActions) {
				actions.add(action);
			}
		}
		
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}
}
