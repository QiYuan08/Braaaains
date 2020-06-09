package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

/**
 * 
 * @author Fong Zhong Kern
 *
 */
public class MamboMarie extends ZombieActor {
	private Behaviour behaviour = new WanderBehaviour();
	private Random rand = new Random();
	private int turn_counter = 0;
	private int x_coord, y_coord;

	public MamboMarie(String name, GameMap gameMap, int x_coord, int y_coord) {
		super(name, gameMap.at(x_coord, y_coord).getGround().getDisplayChar(), 20, ZombieCapability.UNSEEN);
		this.x_coord = x_coord;
		this.y_coord = y_coord;
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (this.displayChar == map.at(this.x_coord, this.y_coord).getGround().getDisplayChar()) { // Mambo is still vanishing on the current ground
			// Calculate 5% to see if she should un-vanish
			if (rand.nextInt(100) <= 3) {
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
				MamboSpawn mamboSpawner = new MamboSpawn(map);
				mamboSpawner.moveMambo(this);
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
