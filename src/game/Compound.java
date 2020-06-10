package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * @author Teh Qi Yuan
 * The first level of the game
 *
 */
public class Compound extends Level{
	
	/**
	 * Constructor of Compound object
	 * @param world The current world that is running by application
	 * @param gameMap The compound gameMap which represents the groundFactory and compoundMap
	 */
	public Compound(NewWorld world, GameMap gameMap) {
		super(world, gameMap);
	}

	@Override
	public void loadLevel() {
		
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap.at(42, 15));
		
	    // Place some random humans
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};
		int x, y;
		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 50.0 + 30.0);
				y = (int) Math.floor(Math.random() * 9.0 + 5.0);
			} 
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x,  y).addActor(new Human(name));	
		}
		
		// Place some random farmers
		String[] farmer = {"Jamerson", "Justin", "Jake"};
		for(String farmer_names : farmer) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 10.0 + 5.0);
			} 
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x,  y).addActor(new Farmer(farmer_names, 'F', 100));
		}
		
		// place a simple weapon
		gameMap.at(74, 20).addItem(new Plank());
		gameMap.at(30, 20).addItem(new Plank());
		gameMap.at(30, 18).addItem(new Plank());
		
		// Spawning MamboMarie
		MamboSpawn mamboSpawner = new MamboSpawn(gameMap);
		mamboSpawner.spawnMambo();
		
		// FIXME: Add more zombies!
		gameMap.at(30, 20).addActor(new Zombie("Groan"));
		gameMap.at(30,  18).addActor(new Zombie("Boo"));
		gameMap.at(10,  4).addActor(new Zombie("Uuuurgh"));
		gameMap.at(50, 18).addActor(new Zombie("Mortalis"));
		gameMap.at(1, 10).addActor(new Zombie("Gaaaah"));
		gameMap.at(62, 12).addActor(new Zombie("Aaargh"));
		
		addVehicle();
	}

}
