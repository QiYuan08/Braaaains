package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

/**
 * MamboMarie class implementation for Assignment 3
 * @author Fong Zhong Kern
 *
 */
public class MamboMarie extends ZombieActor {
	private Behaviour behaviour = new WanderBehaviour();
	private Random rand = new Random();
	private int turn_counter = 0;
	private int x_coord, y_coord;
	private MamboSpawn mamboSpawner;

	/**
	 * Constructor for MamboMarie which takes in 4 parameters
	 * @param name: The name of MamboMarie character, defaulted as ("Voodoo Priestess Mambo Marie")
	 * @param gameMap The current gameMap
	 * @param x_coord The x-coordinate that MamboMarie spawns at
	 * @param y_coord The y-coordinate tht MamboMarie spawns at
	 */
	public MamboMarie(String name, GameMap gameMap, int x_coord, int y_coord, MamboSpawn mamboSpawner) {
		super(name, gameMap.at(x_coord, y_coord).getGround().getDisplayChar(), 20, ZombieCapability.UNSEEN);
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.mamboSpawner = mamboSpawner;
	}

	/**
	 * Explanation of playTurn.
	 * Mambo would be spawned onto the gameMap but would be camouflaged into the ground. Every turn, on 5% chance, if it doesn't reveal itself, it would perform
	 * MamboHidingAction which consists of several dialogues that we've implemented. 
	 * If it reveals itself, it would return MamboRevealAction which returns a unique dialogue of MamboMarie revealing herself.
	 * Once she is revealed, every 10th turn except the 30th, it would return a ChantAction which creates 5 new zombies.
	 * Else it would wander around the map
	 * Finally, on the 30th turn it would relocate to a random edge along the map and camouflaged itself, returning a new dialogue in MamboRelocateAction
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (this.getDisplayChar() == map.at(this.x_coord, this.y_coord).getGround().getDisplayChar()) { // Mambo is still vanishing on the current ground
			// Calculate 5% to see if she should un-vanish
			if (rand.nextInt(100) <= 4) {
				// Reveal here
				this.displayChar = 'M';
				this.removeCapability(ZombieCapability.UNSEEN); // Invulnerable 
				this.addCapability(ZombieCapability.UNDEAD); // Can be attacked by Player
				return new MamboRevealAction();
			}
			else {
				return new MamboHidingAction();
			}
		}
		else {
			this.turn_counter += 1;
			// On the 30th turn
			if(this.turn_counter == 30) {
				this.turn_counter = 0;
				this.removeCapability(ZombieCapability.UNDEAD);
				this.addCapability(ZombieCapability.UNSEEN);
				this.mamboSpawner.moveMambo(this);
				this.displayChar = map.locationOf(this).getGround().getDisplayChar();
				return new MamboRelocateAction();
			}
			// Every 10th turn
			else if(this.turn_counter % 10 == 0) {
				return new MamboChantAction();
			}
			else {
				// Wandering around
				return behaviour.getAction(this, map);
			}
		}
	}
}
