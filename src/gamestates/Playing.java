package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utils.Constants.Environment.*;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utils.LoadSave;

public class Playing extends State implements Statemethods{
	
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private boolean paused = false;
	
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;
	
	private BufferedImage backgroundPlayingImg, bigCloud, smallCloud;
	private int[] smallCloudPos;
	private Random rnd = new Random();
	
	private boolean gameOver;
	private boolean levelCompleted = false;
	
	public Playing(Game game) {
		super(game);
		initClasses();
		
		backgroundPlayingImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
		smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
		smallCloudPos = new int[8];
		for(int i = 0; i < smallCloudPos.length; i++)
			smallCloudPos[i] = (int) (70 * Game.SCALE) + rnd.nextInt((int) (150 * Game.SCALE));
		
		calcLvlOffset();
		loadStartLevel();
	}
	
	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
	}


	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getCurrentLevel());
	}


	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
		
	}


	private void initClasses() {
		enemyManager = new EnemyManager(this);
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (64 * Game.SCALE), this);

		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
	}
	



	@Override
	public void update() {
		
		if(paused) {
			pauseOverlay.update();
		} else if(levelCompleted) {
			levelCompletedOverlay.update();
		} else if (!gameOver){
			player.update();
			levelManager.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
		}
	}


	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;

		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;

	}


	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundPlayingImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		drawClouds(g);
		
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
		
		if(paused) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
		else if(gameOver) {
			gameOverOverlay.draw(g);
		}
		else if(levelCompleted) {
			levelCompletedOverlay.draw(g); }
	}
	
	private void drawClouds(Graphics g) {
		for(int i = 0; i < 3; i++)
			g.drawImage(bigCloud, 0 + i * BIG_CLOUDS_WIDTH - (int) (xLvlOffset *0.3), (int) (10 * Game.SCALE), BIG_CLOUDS_WIDTH, BIG_CLOUDS_HEIGHT, null);
		
		for(int i = 0; i < smallCloudPos.length; i++)
			g.drawImage(smallCloud, SMALL_CLOUDS_WIDTH * 4 * i - (int) (xLvlOffset *0.7), smallCloudPos[i], SMALL_CLOUDS_WIDTH, SMALL_CLOUDS_HEIGHT, null);
	}

	public void resetAll() {
		gameOver = false;
		paused = false;
		levelCompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		xLvlOffset = 0;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}

	public void unpauseGame() {
		paused = false;
	}
	
	public void mouseDragged(MouseEvent e) {
		if (!gameOver) {
			if(paused)
				pauseOverlay.mouseDragged(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				player.setAttacking(true);
			}	
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if(paused) {
				pauseOverlay.mousePressed(e);}
			else if(levelCompleted) {
				levelCompletedOverlay.mousePressed(e);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if(paused) {
				pauseOverlay.mouseReleased(e);}
			else if(levelCompleted) {
				levelCompletedOverlay.mouseReleased(e);
			}
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if(paused) {
				pauseOverlay.mouseMoved(e);}
			else if(levelCompleted) {
				levelCompletedOverlay.mouseMoved(e);
			}
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
				switch(e.getKeyCode()) {
					case KeyEvent.VK_A:
						player.setLeft(true);
						break;
					case KeyEvent.VK_D:
						player.setRight(true);
						break;
					case KeyEvent.VK_SPACE:
						player.setJump(true);
						break;
					case KeyEvent.VK_ESCAPE:
						paused = !paused;
						break;
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver)
			switch(e.getKeyCode()) {		
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
		}
		
	}
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.levelCompleted = levelCompleted;
		if (levelCompleted)
			player.resetDirBooleans();
	}
	
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	
}