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
	private boolean isAiming = false; // to indicate whether the player is aiming using sniper

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
	 * Modified the method to interrupt aiming if getting attack
	 */
	public void hurt(int points) {
		hitPoints -= points;
		isAiming = false;
	}

	/**
	 * Method to allow other class to determine whether the player aiming is
	 * modified
	 * 
	 * @param aiming
	 */
	public void isAiming(boolean aiming) {
		this.isAiming = aiming;
	}

	/**
	 * Player will have the option of crafting weapon if a zombie hand or leg are in
	 * their inventory
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

		// Handle multi-turn Actions
		// add craft action into player if they have a zombiehand or leg
		// add HealAction into player if they have a Food
		for (Item item : this.getInventory()) {
			if (item instanceof ZombieHand) {
				actions.add(new CraftAction("ZombieHand", "ZombieClub"));
			} else if (item instanceof ZombieLeg) {
				actions.add(new CraftAction("ZombieLeg", "ZombieMace"));
			}
			else if (item instanceof Shotgun) {
				if (haveBullet(item)) {
					actions.add(new ShotgunMenu(this, display));
				}
			} else if (item instanceof SniperRifle) {
				if (haveBullet(item)) {
					actions.add(new TargetMenu(display));
				}
			}

			Action healing = this.healActor(map);
			if (healing != null) {
				actions.add(healing);
			}

		}

		// Add HarvestAction when on ripe crop using Crop.allowableActions
		if (map.locationOf(this).getGround() instanceof Crop) {
			Crop cropObj = (Crop) map.locationOf(this).getGround();
			Actions allowableActions = cropObj.allowableActions(this, map.locationOf(this), "here");
			for (Action action : allowableActions) {
				actions.add(action);
			}
		}
		
		// Adding Quit to menu
		actions.add(new QuitAction());
		
		if (lastAction.getNextAction() != null && isAiming == true)
			actions.add(lastAction.getNextAction());

		return menu.showMenu(this, actions, display);
	}

	/**
	 * A method to check whether player have bullets of specific gun in the
	 * inventory
	 * 
	 * @param weapon The type of gun
	 * @return True if the player have bullet for the gun and false otherwise
	 */
	private boolean haveBullet(Item weapon) {

		if (weapon instanceof Shotgun) {
			for (Item item : this.getInventory()) {
				if (item instanceof ShotgunBullet) {
					if (((ShotgunBullet) item).bullet_count() > 0) {
						return true;
					}
				}
			}
		} else {
			for (Item item : this.getInventory()) {
				if (item instanceof SniperBullet) {
					if (((SniperBullet) item).bullet_count() > 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
