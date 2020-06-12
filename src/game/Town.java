package game;

import edu.monash.fit2099.engine.GameMap;


/**
 * @author Teh Qi Yuan
 * 
 * The town level
 */
public class Town extends Level {		
	
	/**
	 * Town constructor
	 * @param world The current world that is running by application
	 * @param gameMap The town gameMap which represents the groundFactory and townMap
	 */
	public Town(NewWorld world, GameMap gameMap) {
		super(world, gameMap);
	}

	@Override
	public void loadLevel() {
		
	    // Place some random humans
		String[] humans = {"Carlta", "Maria", "Vicent", "Andrew", "Wendy",
				"Lina", "Summer", "Clair", "Jackson", "Jacquire"};
		int x, y;
		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x,  y).addActor(new Human(name));	
		}
		
		// Place shotgun and shotgun bullet
		gameMap.at(10, 2).addItem(new Shotgun());
		gameMap.at(8, 2).addItem(new ShotgunBullet());
		
		// Place SniperRifle and sniper bullet
		gameMap.at(3, 7).addItem(new SniperRifle());
		gameMap.at(5, 7).addItem(new SniperBullet());
		
		// Place some random farmers
		String[] farmer = {"James", "Jason", "Jamie"};
		for(String farmer_names : farmer) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x,  y).addActor(new Farmer(farmer_names, 'F', 100));
		}
		
		// place a simple weapon
		gameMap.at(74, 20).addItem(new Plank());	
		gameMap.at(30, 20).addItem(new Plank());
		gameMap.at(30, 18).addItem(new Plank());
		
		// Place some random Zombies
		gameMap.at(30, 20).addActor(new Zombie("Groan"));
		gameMap.at(30,  18).addActor(new Zombie("Boo"));
		gameMap.at(10,  4).addActor(new Zombie("Uuuurgh"));
		gameMap.at(50, 18).addActor(new Zombie("Mortalis"));
		gameMap.at(1, 10).addActor(new Zombie("Gaaaah"));
		gameMap.at(62, 12).addActor(new Zombie("Aaargh"));	
	
		
		addVehicle();
	}
	
}
