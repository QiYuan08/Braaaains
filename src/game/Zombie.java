package game;
/* TODO: can overrides the getIntrinsicWeapon methods to add probability of bites to punch
 * 		 the use random(fist, bite)
 *		 then heal using heal function
 * 
 * playTurn()
 * TODO: check for item in the location then
 *       pickup item from the actions list @ (actions[size - 1])
 *       cuz if there is an item, the pickup action is always at same position
 * 
 * TODO: can add probability to say "BRAINNNSS" here too ?
 * 
 *       
 * TODO: override the hurt function to have the probability of broken limb and dropping weapon
 * 
 * plyTurn()
 * TODO: add a if statement before the for loop to check if lastAction
 *       is not null and there is a broken leg, then return doNothing
 *       action if it is. 
 *       
 *       check for broken leg oso
 * 
 */

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE), // zombies are only allowed to attack human (i.e ZombieCapability.ALIVE)
			new HuntBehaviour(Human.class, 10),          // HuntBehaviour(target, range to look for target)
			new WanderBehaviour()
	};

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
	}
	
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(10, "punches"); 	// override punches damage from 5 to 10 from Actor class
	}

	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 * 
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
//		System.out.println("!!!" + actions.get(actions.size() - 2).menuDescription(this));
//		for (Action a: actions) {
//		
//			System.out.println("! " + a.menuDescription(this));
//		}
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();	
	}	
}
