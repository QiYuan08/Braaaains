package game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * The MamboHidingAction is used when MamboMarie is camouflaged within the gameMap. It returns random dialogues to th display on execute.
 * @author Fong Zhong Kern
 *
 */
public class MamboHidingAction extends Action{
    private List<String> mamboDialogues = Arrays.asList("You'll never find me!", "Curse you!", "Damn you!", "Get them Zombies!", "I curse upon this land!", "MUAHAHAHAHAHAHA!");  

	/**
	 * Empty constructor as we wont be needing any parameters for dialogue executions.
	 */
	public MamboHidingAction() {
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		Collections.shuffle(this.mamboDialogues);
		String dialogue = this.mamboDialogues.get(0);
		return actor + ": " + dialogue;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " speaks while hidden.";
	}

}
