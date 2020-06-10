package game;

import java.util.List;

import edu.monash.fit2099.engine.GameMap;

/**
 * @author Teh Qi Yuan 
 * This abstract classes allows us to create multiple levels that
 *  has different variation using loadLevel method
 */
public abstract class Level {

	protected NewWorld world;
	protected GameMap gameMap;

	public Level(NewWorld world, GameMap gameMap) {
		this.gameMap = gameMap;
		this.world = world;
	}
	
	/**
	 * This method allows different variation of maps by placing
	 * items and actor in different part of the map
	 */
	public void loadLevel() {
		addVehicle();
	};

	/**
	 * A method to add vehicle to each map according to where the map is in the
	 * world If this is the last map: add a vehicle at upper left to go to last map
	 * If this is the first map: add a vehicle at upper right to go to next map If
	 * this is the map in the middle: add vehicle and upper left and right to go to
	 * next or previous map
	 * The down side is the we can't place other obstructive object(e.g fence, tree) at these area
	 */
	protected void addVehicle() {

		List<GameMap> maps = this.world.getMap();

		if (maps.indexOf(this.gameMap) == maps.size() - 1) { // if this is last map
			addPrevious(this.gameMap, maps);
			
		} else if (maps.indexOf(this.gameMap) == 0) { // if this is first map

			addNext(this.gameMap, maps);
			
		} else { // if this is map in middle
			addNext(this.gameMap, maps);
			addPrevious(this.gameMap, maps);
		}
	}
	
	/**
	 * Method to add vehicle in the current map to go to previous map
	 * @param map The current map
	 * @param maps List of map in the world
	 */
	private void addPrevious(GameMap map, List<GameMap> maps) {
		
		int x = this.gameMap.getXRange().min() + 8;
		int y = this.gameMap.getYRange().min() + 5;
		GameMap nextMap = maps.get(maps.indexOf(this.gameMap) - 1);
		
		/*
		 * If there is obstructive object(e.g fence, wall) minus x-coordinate by 1
		 */
		while(nextMap.at(x, y).getGround().blocksThrownObjects()) {
			x -= 1;
		}
		this.gameMap.at(x, y).addItem(new Vehicle(world, nextMap));

	}
	
	/**
	 * Method to add vehicle in the current map to go to next map
	 * @param map The current map
	 * @param maps List of map in the world
	 */
	private void addNext(GameMap map, List<GameMap> maps) {
		
		int x = this.gameMap.getXRange().max() - 8;
		int y = this.gameMap.getYRange().min() + 5;
		GameMap nextMap = maps.get(maps.indexOf(this.gameMap) + 1);
		
		while(nextMap.at(x, y).getGround().blocksThrownObjects()) {
			x += 1;
		}
		this.gameMap.at(x, y).addItem(new Vehicle(world, nextMap));

	}

}
