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
 * breaking Limb
 * TODO: call a method in Limb to see whether to  break the limb or not
 * 		 also check if no. of broken limb exceed 2
 * 
 * 
 */

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.ActorLocations;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * A class to create a zombie in the game.
 * 
 * @author Teh Qi Yuan, ram
 *
 */
public class Zombie extends ZombieActor {
	
	private Display display;
	private ZombieLimb limb = new ZombieLimb();
	private boolean cutLimb = false;
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE), // zombies are only allowed to attack human (i.e ZombieCapability.ALIVE)
			new HuntBehaviour(Human.class, 10),          // HuntBehaviour(target, range to look for target)
			new WanderBehaviour()
	};
	
	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
	}
	
	/**
	 * Have a probability of biting instead of punches when
	 * the zombie have no weapon
	 * 
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		
		IntrinsicWeapon bite = new IntrinsicWeapon(15, "bites");
		IntrinsicWeapon punch = new IntrinsicWeapon(10, "punches");
		
		int att = new Random().nextInt(2);
		if(att == 1) {
			return bite;
		}
		else {
			return punch;
		}
	}
	
	/**
	 * Decrease the health of the zombie by 
	 * 'points' amount and notify the playTurn
	 * function to call the castLimb function
	 */
	@Override
	public void hurt(int points) {
		hitPoints -= points;
		
		cutLimb = true;
	}

	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * Have 10 percent chance of saying 'Braiinnns' every turn
	 * Call the castLimb method to determine whether or not to cast off a limb when damage are taken
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
		RandomGenerator myRand = new RandomGenerator();

		if(cutLimb == true) {
			limb.castLimb(this, map);
			cutLimb = false;
		}
		
		try {
			int[] probability = {10,90};
			int choiceIndex = myRand.probRandom(probability);
			if(choiceIndex == 0) {
				display.println(this.name + " says Braaaaaaaains");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		// if there is item at zombie location pick it up
		if(actions.get(actions.size() -2) instanceof PickUpItemAction) {
			if(this.getInventory().size() < 2) {          // zombie are only allowed to hold one weapon
				return actions.get(actions.size() -2);
			}
		}
		
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				if (action instanceof MoveActorAction){
					if(limb.canMove() == true) {
						return action;
					}
				}else {
					return action;
				}
		}
		return new DoNothingAction();	
	}	
}
