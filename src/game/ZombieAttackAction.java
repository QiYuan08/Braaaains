/**
 * 
 */
package game;

import edu.monash.fit2099.engine.Actor;

/**
 * @author Teh Qi Yuan
 * 
 * A attack action class for the zombie
 * 
 * TODO: need to check for losing arms probability
 * 	- loss weapon when both arm loss
 *  - 50% of dropping weapon if one arm loss
 *  - probability of punching is halved if one arm is loss
 *
 */
public class ZombieAttackAction extends AttackAction {

	public ZombieAttackAction(Actor target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

}
