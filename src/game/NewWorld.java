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

public class NewWorld extends World{

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
			if (playerQuitted) {
				break;
			}
			if (noMoreHuman()) {
				display.println(endHumanMessage());
				break;
			}
			
			else if(noMoreZombieAndMambo()) {
				display.println(endZombieMamboMessage());
				break;
			}
			GameMap playersMap = actorLocations.locationOf(player).map(); // get the map where the player is in
			playersMap.draw(display);                                     // draw the map on console

			// Process all the actors.
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
	
	protected String endPlayerMessage() {
		return "You died";
	}
	
	protected String endHumanMessage() {
		return "All humans died";
	}
	
	protected String endZombieMamboMessage() {
		return "You WIN!";
	}
	
}
