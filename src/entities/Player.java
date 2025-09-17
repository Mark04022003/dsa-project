package entities;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.PlayerConstants.GetSpriteAmount;

import static utils.HelpMethods.*;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import utils.LoadSave;

public class Player extends Entity{


	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 20;
	private int playerAction = IDLE;
	private int playerDir = -1;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 1.0f * main.Game.SCALE;
	private int[][]	 lvlData;
	private float xDrawOffset = 13 * main.Game.SCALE;
	private float yDrawOffset =  16 * main.Game.SCALE;
	
	// Jumping / Gravity 
	private float airSpeed = 0f;
	private float gravity = 0.04f * main.Game.SCALE;
	private float jumpSpeed = -2.25f * main.Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * main.Game.SCALE;
	private boolean inAir = false;

    
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
//		initHitbox((int) (x + xDrawOffset), (int) (y + yDrawOffset), (int) (width - 2 * xDrawOffset), (int) (height - yDrawOffset));
	    initHitbox(x, y, 23 * main.Game.SCALE, 38 * main.Game.SCALE);
	}
	
	public void update() {
		
		updatePos();
		updateAnimationTick();
		setAnimation();

	}
	
	public void render(Graphics g) {
	    g.drawImage(animations[playerAction][aniIndex],(int) (hitbox.x - xDrawOffset),(int) (hitbox.y - yDrawOffset), width, height, null);
//	    drawHitbox(g);
	}
	
	
	
	private void updateAnimationTick() {
			
			aniTick++;
			if(aniTick >= aniSpeed) {
				aniTick = 0;
				aniIndex++;
				if(aniIndex >= GetSpriteAmount(playerAction)) {
					aniIndex = 0;
					attacking = false;
				}
			}
			
		}

	private void setAnimation() {
		
		int startAni = playerAction;
		
		if (moving) {
				
				playerAction = RUNNING;
			}
		else 	
			playerAction = IDLE;
		
		if (inAir) {
			if (airSpeed < 0)
				playerAction = JUMP;
			else
				playerAction = FALLING;
		}
			
		
		if (attacking) {
				playerAction = ATTACK_1;
			}
		
		if (startAni != playerAction) {
			resetAniTick();
			}
		}

	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		
		moving = false;
		
		if (jump)
			jump();
		
		if (!left && !right && !inAir)
			return;
		
		float xSpeed = 0;
		
		if (left) {
			xSpeed -= playerSpeed;
		}
		
		if (right) {
			xSpeed += playerSpeed;
		}
		
		if (!inAir) {
			if (!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;
			} }
		if (inAir) {
			
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				// Collision while falling
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0) {
					// Landed on the ground
					resetInAir();
				} else {
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
			
		}
			
		} else 
			updateXPos(xSpeed);
		
//		float oldX = hitbox.x;
//		updateXPos(xSpeed);
//		if (hitbox.x != oldX) {
		    moving = true;
		//}
		
	}
	

	private void jump() {
		
		if (inAir) 
				return; 
			
			inAir = true; 
			airSpeed = jumpSpeed; 
			
		
	}


	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
		
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
		hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
			}
		
	}

	private void loadAnimations() {
		
		
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
			animations = new BufferedImage[9][9];
			for (int j = 0; j < animations.length; j++) {
				for (int i = 0; i < animations[j].length; i++) { // FIX: use animations[j].length
					animations[j][i] = img.getSubimage(i*32, j*32, 32, 32);
				}
			}
			
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		// Debug: print the tile value at the player's starting position
		int tileX = (int)(x / main.Game.TILES_SIZE);
		int tileY = (int)(y / main.Game.TILES_SIZE);
		int tileValue = lvlData[tileY][tileX];
		System.out.println("Player start tile value: " + tileValue + " at (" + tileX + ", " + tileY + ")");
		
		if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true; }
		}
	
	
	public void resetDirBooleans() {
		left = false;
		up = false;
		right = false;
		down = false;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	
	public void setJump(boolean jump) {
		this.jump = jump;
	}

		
}

	

