/**
 * 
 */
package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * An action class that allow a human or player to craft a cast of zombie limb
 * into a mace or club
 * 
 * @author Teh Qi Yuan
 *
 */
public class CraftAction extends Action {

	private String weaponType;
	private String craftedWeapon;

	/**
	 * Constructor
	 * 
	 * @param weaponType    weapon class name before crafting
	 * @param craftedWeapon weapon class name after crafting
	 */
	public CraftAction(String weaponType, String craftedWeapon) {
		this.weaponType = weaponType;
		this.craftedWeapon = craftedWeapon;

	}

	/**
	 * Craft an zombie leg into a mace of a zombie hand into a club
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Item weapon = null;
		String result = "";

		if (this.weaponType == "ZombieHand") {

			for (Item item : actor.getInventory()) {
				if (item instanceof ZombieHand) {
					weapon = item;
				}
			}
			assert weapon instanceof ZombieHand : "Wrong Item Deleted";

			actor.removeItemFromInventory(weapon);
			actor.addItemToInventory(new ZombieClub());
		} else if (this.weaponType == "ZombieLeg") {

			for (Item item : actor.getInventory()) {
				if (item instanceof ZombieLeg) {
					weapon = item;
				}
			}
			assert weapon instanceof ZombieLeg : "Wrong Item Deleted";

			actor.removeItemFromInventory(weapon);
			actor.addItemToInventory(new ZombieMace());

		}
		result = actor + " crafted " + this.weaponType + " into " + this.craftedWeapon;
		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		
		return "Craft " + this.weaponType + " into " + this.craftedWeapon;
	}

}
