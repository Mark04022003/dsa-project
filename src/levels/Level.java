package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.OrcBoyz;
import main.Game;
import static utils.HelpMethods.GetLevelData;
import static utils.HelpMethods.GetOrcBoyz;
import static utils.HelpMethods.GetPlayerSpawn;

public class Level {
	
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<OrcBoyz> orcBoyz;
	private int lvlTilesWide;
	private int maxTileOffsetX;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		calcPlayerSpawn();
	}
	
	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTileOffsetX = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = maxTileOffsetX * Game.TILES_SIZE;
	}

	private void createEnemies() {
		orcBoyz = GetOrcBoyz(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getLevelData() {
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
	
	public ArrayList<OrcBoyz> getOrcBoyz() {
		return orcBoyz;
	}
	
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
}
