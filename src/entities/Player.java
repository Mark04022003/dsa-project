package entities;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.PlayerConstants.GetSpriteAmount;

import static utils.HelpMethods.*;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

public class Player extends Entity{
 

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
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
	
	//status bar
	private BufferedImage statusBarImg;
	
	private int statusBarWidth = (int) (192 * main.Game.SCALE);
	private int statusBarHeight = (int) (58 * main.Game.SCALE);
	private int statusBarX = (int) (10 * main.Game.SCALE);
	private int statusBarY = (int) (10 * main.Game.SCALE);
	
	private int healthBarWidth = (int) (150 * main.Game.SCALE);
	private int healthBarHeight = (int) (4 * main.Game.SCALE);
	private int healthBarXStart = (int) (34 * main.Game.SCALE);
	private int healthBarYStart = (int) (14 * main.Game.SCALE);

	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	
	//attack box
	private Rectangle2D.Float attackBox;
	
	private int flipX = 0;
	private int flipW = 1;
	
	private boolean attackChecked;
	private Playing playing;
    
	
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		loadAnimations();
//		initHitbox((int) (x + xDrawOffset), (int) (y + yDrawOffset), (int) (width - 2 * xDrawOffset), (int) (height - yDrawOffset));
	    initHitbox(x, y, 23 * main.Game.SCALE, 38 * main.Game.SCALE);
	    initAttackbox();
	}
	
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}
	
	private void initAttackbox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (50*Game.SCALE), (int) (30*Game.SCALE));
	}
	
	public void update() {
		
		updateHealthBar();
		
		if(currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}
		
		updateHealthBar();
		updateAttackBox();
		updatePos();
		
		if(attacking) {
			checkAttack();
		}
		
		updateAnimationTick();
		setAnimation();

	}
	
	private void checkAttack() {
		if(attackChecked || aniIndex != 1)
			return;
		
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
	}

	private void updateAttackBox() {
		if (right) {
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10) - 25; }
		else if (left) {
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10) + 7;}

		attackBox.y = hitbox.y + (Game.SCALE * 10);
	}
	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float)maxHealth) * healthBarWidth);
	}

	public void render(Graphics g, int lvlOffset) {
	    g.drawImage(animations[playerAction][aniIndex],
	    		(int) (hitbox.x - xDrawOffset - 15) - lvlOffset + flipX,
	    		(int) (hitbox.y - yDrawOffset), 
	    		width * flipW, height, null);
//	    drawHitbox(g, lvlOffset);
//	    drawAttackBox(g, lvlOffset);
	    drawUI(g);
	}
	
	
	
	private void drawAttackBox(Graphics g, int lvlOffset) {
		g.setColor(java.awt.Color.RED);
		g.drawRect((int)attackBox.x - lvlOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(java.awt.Color.RED);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}

	private void updateAnimationTick() {
			
			aniTick++;
			if(aniTick >= aniSpeed) {
				aniTick = 0;
				aniIndex++;
				if(aniIndex >= GetSpriteAmount(playerAction)) {
					aniIndex = 0;
					attacking = false;
					attackChecked = false;
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
				if (startAni != ATTACK_1) {
					aniIndex = 1;
					aniTick = 0;
					return;
				}
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
		
//		if (!left && !right && !inAir)
//			return;
		
		if (!inAir)
			if ((!left && !right) || (left && right))
				return;
			
		float xSpeed = 0;
		
		if (left) {
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		
		if (right) {
			xSpeed += playerSpeed;
			flipX = 0;
			flipW = 1;
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
	
	protected void changeHealth(int value) {
		currentHealth += value;
		if (currentHealth <= 0) {
			currentHealth = 0;
		} else if (currentHealth >= maxHealth) {
			currentHealth = maxHealth;
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
			statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
			
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

	public void resetAll() {
		// TODO Auto-generated method stub
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;
		hitbox.x = x;
		hitbox.y = y;
		if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
	}
		
}