package entities;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;

import java.awt.geom.Rectangle2D;

import main.Game;

public class OrcBoyz extends Enemy{
	
	private int attackBoxOffsetX;

	public OrcBoyz(float x, float y) {
		super(x, y, ORC_BOYZ_WIDTH, ORC_BOYZ_HEIGHT, ORC_BOYZ);
		initHitbox(32, 32);
		initAttackBox();
		
	}   
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (30*Game.SCALE), (int) (40*Game.SCALE));
		attackBoxOffsetX = (int) (Game.SCALE * 20);
	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}

	private void updateAttackBox() {
		if (walkDir == LEFT) {
			attackBox.x = hitbox.x - attackBoxOffsetX;
		} else if (walkDir == RIGHT) {
			attackBox.x = hitbox.x + hitbox.width + attackBoxOffsetX - 45;
		}
//		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;
	}

	public void updateBehavior(int[][] lvlData, Player player) {
		if (firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		if (inAir ) {
			updateInAir(lvlData);
		}
		else {
			switch (state) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				
				if(canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
				
					if(isPlayerCloseForAttack(player) ) 
						newState(ATTACK);
				
				}
				
				move(lvlData);
				break;
			case ATTACK:
				if (aniIndex == 0) {
					attackChecked = false;
				}
				
				if (aniIndex == 3 && !attackChecked) {
					checkEnemyHit(attackBox, player);
				}
				break;
			case HIT:
				
				break;
			}
		}
	}
	
	


	
	public int flipX() {
		if (walkDir == LEFT) {
			return width;
		}
		else {
			return 0;
		}
	}
	
	public int flipW() {
		if (walkDir == RIGHT) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	@Override
	protected boolean isPlayerCloseForAttack(Player player) {
	    return attackBox.intersects(player.hitbox);
	}

	
	
}