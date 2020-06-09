package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.World;

/**
 * Subclass of World which would be used as the new world within application to process the game. 
 * @author Fong Zhong Kern
 *
 */
public class NewWorld extends World{
	
	/**
	 * NewWorld constructor which requires display parameter to display actors actions on the screen
	 * @param display A display object
	 */
	public NewWorld(Display display) {
		super(display);
	}
	
	@Override
	public void run() {
		if (player == null)
			throw new IllegalStateException();

		// initialize the last action map to nothing actions (1st round);
		for (Actor actor : actorLocations) {
			lastActionMap.put(actor, new DoNothingAction());
		}
		
		// This loop is basically the whole game
		boolean playerQuitted = false;
		while (stillRunning()) {
			// Checks if the player quitted the game by checking the returned action of player.
			if (playerQuitted) {
				break;
			}
			// If there is no more human on the map, the player loses.
			if (noMoreHuman()) {
				display.println(endHumanMessage());
				break;
			}
			// When all zombies and MamboMarie is dead, the player wins
			else if(noMoreZombieAndMambo()) {
				display.println(endZombieMamboMessage());
				break;
			}
			GameMap playersMap = actorLocations.locationOf(player).map(); // get the map where the player is in
			playersMap.draw(display);                                     // draw the map on console

			// Process all the actors.
			// Updated process of actors, returns the action to check if the player has inputed a quit action to terminate the game
			for (Actor actor : actorLocations) {
				Action action = null;
				if (stillRunning()) {
					action = processActorsTurn(actor);
				}
				if (actor instanceof Player && action instanceof QuitAction) {
					playerQuitted = true;
					break;
				}
			}

			// Tick over all the maps. For the map stuff.
			for (GameMap gameMap : gameMaps) {
				gameMap.tick();
			}

		}
		display.println(endGameMessage());
	}
	
	/**
	 * Method that is similar to the processActorsTurn class from the parent but with a return value.
	 * @param actor The current actor to process its action
	 * @return The action used by the actor
	 */
	protected Action processActorsTurn(Actor actor) {
		Location here = actorLocations.locationOf(actor);
		GameMap map = here.map();

		Actions actions = new Actions();
		
		// get action for every item in inventory
		for (Item item : actor.getInventory()) {
			actions.add(item.getAllowableActions());
			// Game rule. If you're carrying it, you can drop it.
			actions.add(item.getDropAction());
		}
		
		
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();

			// Game rule. You don't get to interact with the ground if someone is standing
			// on it.
			if (actorLocations.isAnActorAt(destination)) {                                           // if there is another actor
				actions.add(actorLocations.getActorAt(destination).getAllowableActions(actor, exit.getName(), map));  // get action to interact with it
			} else {
				actions.add(destination.getGround().allowableActions(actor, destination, exit.getName()));    // else, return an action to interact with ground
			}
			actions.add(destination.getMoveAction(actor, exit.getName(), exit.getHotKey()));
		}

		for (Item item : here.getItems()) {
			actions.add(item.getAllowableActions());
			// Game rule. If it's on the ground you can pick it up.
			actions.add(item.getPickUpAction());
		}
		actions.add(new DoNothingAction());


		Action action = actor.playTurn(actions, lastActionMap.get(actor), map, display);
		lastActionMap.put(actor, action);
		
		String result = action.execute(actor, map);
		display.println(result);
		return action;
	}
	
	/**
	 * noMoreHuman method which is used to check if there are any more humans on the current gameMap
	 * @return Boolean indicating if there are any more humans on the gameMap
	 */
	protected boolean noMoreHuman() {
		boolean noMoreHuman = true;
		for(Actor actor : actorLocations) {
			if (actor instanceof Human && actor instanceof Player) {
				// Do nothing if the actor is a Player (Player is also a Human)
				;
			}
			else if(actor instanceof Human) {
				noMoreHuman = false;
			}
		}
		return noMoreHuman;
	}
	
	/**
	 * noMoreZombieAndMambo method that checks if there are any more Zombies or MamboMarie on the current gameMap
	 * @return A boolean which checks if there are any more Zombies or MamboMarie on the current map
	 */
	protected boolean noMoreZombieAndMambo() {
		boolean noMoreZombie = true;
		boolean noMoreMambo = true;
		for(Actor actor : actorLocations) {
			if (actor instanceof Zombie) {
				noMoreZombie = false;
			}
			else if(actor instanceof MamboMarie) {
				noMoreMambo = false;
			}
		}
		if (noMoreZombie && noMoreMambo) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * endPlayerMessage used to indicate final message that player has died.
	 * @return A string "You died"
	 */
	protected String endPlayerMessage() {
		return "You died";
	}
	
	/**
	 * endHumanMessage used to indicate final message that player has loss due to all humans dead
	 * @return A string "All humans died"
	 */
	protected String endHumanMessage() {
		return "All humans died";
	}
	
	/**
	 * endZombieMamboMessage used to indicate that the player has won as all Zombies and MamboMarie is dead
	 * @return A string "You WIN!"
	 */
	protected String endZombieMamboMessage() {
		return "You WIN!";
	}
	
}
