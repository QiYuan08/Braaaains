/**
 * 
 */
package game;

import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * @author Teh Qi Yuan
 * 
 * Abstract classes for creating bullet for different gun
 *
 */
public abstract class Bullet extends PortableItem{

	private int bullet_count = 7;

	/**
	 * Constructor for the bullet class 
	 * @param name The bullet for which gun
	 * @param displayChar The character to display on map
	 */
	public Bullet(String name, char displayChar) {
		super(name, displayChar);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Reduce bullet count by 1 during firing
	 */
	public void reduce_bullet() {
		if(bullet_count > 0) {
			bullet_count -= 1;
		}
	}
	
	/**
	 * Method to return the number of bullet left
	 * @return int the number of bullet left
	 */
	public int bullet_count() {
		return this.bullet_count;
	}
	
	/**
	 * Add bullet count when player picksup bullet of the same type
	 * @param bullet The number of bullet to add
	 */
	public void add_bullet(int bullet) {
		bullet_count += bullet;
	}

	/**
	 * Override to use the bullet pickup action
	 */
	@Override
	public BulletPickUpAction getPickUpAction() {
		if(portable)
			return new BulletPickUpAction(this);
		
		return null;
	}
	
	
}
