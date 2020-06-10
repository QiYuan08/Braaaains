package game;

import java.util.Random;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * MamboSpawn utility class implemented to support MamboMarie
 * @author Fong Zhong Kern
 *
 */
public class MamboSpawn {
	GameMap gameMap;
	Random rand = new Random();
	
	/**
	 * Constructor of MamboSpawn which needs the gameMap to keep track of the current gameMap to utilize its methods
	 * @param gameMap The map of the current game
	 */
	public MamboSpawn(GameMap gameMap) {
		this.gameMap = gameMap;
	}
	
	/**
	 * spawnMambo is a method which spawns MamboMarie onto the gameMap, this method should only be called once within Application class
	 */
	public void spawnMambo() {
		boolean mambo_spawned = false;
		while (!mambo_spawned) {
			int x_coord = this.x_spawn();
			int y_coord = this.y_spawn(x_coord);
			Location location = this.gameMap.at(x_coord, y_coord);
			if(!gameMap.isAnActorAt(location)) {
				// Clear to spawn mambo here
				this.gameMap.at(x_coord, y_coord).addActor(new MamboMarie("Voodoo Priestess Mambo Marie", this.gameMap, x_coord, y_coord));
				mambo_spawned = true;
			}
		}
	}
	
	/**
	 * moveMambo method is used to relocate MamboMarie on its 30th turn. It would relocate itself to a new edge of the map where there is no actors.
	 * @param mambo The MamboMarie object that was created by spawnMambo
	 */
	public void moveMambo(MamboMarie mambo) {
		boolean mambo_relocated = false;
		while (!mambo_relocated) {
			int x_coord = this.x_spawn();
			int y_coord = this.y_spawn(x_coord);
			Location location = this.gameMap.at(x_coord, y_coord);
			if(!this.gameMap.isAnActorAt(location)) {
				this.gameMap.moveActor(mambo, location);
				mambo_relocated = true;
			}
		}
	}
	
	/**
	 * Private method that is used within the class to calculate the maximum x_coord of the gameMap
	 * @return An integer that corresponds to the x_coord that was randomly selected
	 */
	private int x_spawn() {
		int x_coord = rand.nextInt(gameMap.getXRange().max() + 1); // Get the random max x range of the map
		return x_coord;
	}
	/**
	 * y_spawn method which randomly selects a y_coord within the gameMap based on the x_coord value
	 * @param x_coord The x_coord value which was returned from calling x_spawn() method within the class.
	 * @return y_coord A y_coord that was selected based on the values of x_coord to ensure the coordinates are on the edge.
	 */
	private int y_spawn(int x_coord) {
		int y_coord = -1;
		if (x_coord >= 75 && x_coord <= 79) {
			y_coord = rand.nextInt(21) + 4;
		}
		else if(x_coord >= 5 && x_coord < 75) {
			// 50-50 north or south
			if(rand.nextInt(2) == 0) {
				y_coord = rand.nextInt(5);
			}
			else {
				y_coord = rand.nextInt(5) + 20;
			}
		}
		else {
			y_coord = rand.nextInt(gameMap.getYRange().max() + 1);
		}
		return y_coord;
	}
}
