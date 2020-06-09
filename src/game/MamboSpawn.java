package game;

import java.util.Random;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class MamboSpawn {
	GameMap gameMap;
	Random rand = new Random();

	public MamboSpawn(GameMap gameMap) {
		this.gameMap = gameMap;
	}
	
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
	
	private int x_spawn() {
		int x_coord = rand.nextInt(gameMap.getXRange().max()); // Get the max x range of the map
		return x_coord;
	}
	
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
			y_coord = rand.nextInt(gameMap.getYRange().max());
		}
		return y_coord;
	}
}
