package entities;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;
import static entities.Enemy.*;

import main.Game;

public class OrcBoyz extends Enemy{

	public OrcBoyz(float x, float y) {
		super(x, y, ORC_BOYZ_WIDTH, ORC_BOYZ_HEIGHT, ORC_BOYZ);
		initHitbox(x, y, (int) (32*Game.SCALE),(int) (32*Game.SCALE));
		
		
	} 
	
	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData, player);
		updateAnimationTick();
		
	}

	public void updateMove(int[][] lvlData, Player player) {
		if (firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		if (inAir ) {
			updateInAir(lvlData);
		}
		else {
			switch (enemyState) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				
				if(canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
				}
				if(isPlayerCloseForAttack(player) ) {
					newState(ATTACK);
				}
				
				move(lvlData);
				break;
			}
		}
	}
}
	
	

