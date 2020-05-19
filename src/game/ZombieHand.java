package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * @author Teh Qi Yuan
 *
 * A weapon made from zombie fallen limb
 */
public class ZombieHand extends WeaponItem {

	/**
	 * Constructor
	 * 
	 * Added a craft action into the allowableActions for every zombie limb
	 */
	public ZombieHand() {
		super("ZombieHand", '%', 11, "swing");
	}
}
