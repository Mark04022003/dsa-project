package utils;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
		
	}
	
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
//		int maxWidth = lvlData[0].length * 32;
		if (x < 0 || x >= Game.GAME_WIDTH)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		int value = lvlData[(int) yIndex][(int) xIndex];
		
		if (value >= 48 || value < 0 || value != 11) {
			return true; } else {
		return false; }
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
		if(xSpeed > 0) {
		//right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset -1;
		}
		else {
		//left
			return currentTile * Game.TILES_SIZE ; }
		}
		
//	    if (xSpeed > 0) {
//	        // moving right → snap to left side of next tile
//	        int tileX = (int) ((hitbox.x + hitbox.width) / Game.TILES_SIZE);
//	        return tileX * Game.TILES_SIZE - hitbox.width;
//	    } else {
//	        // moving left → snap to right side of previous tile
//	        int tileX = (int) (hitbox.x / Game.TILES_SIZE);
//	        return tileX * Game.TILES_SIZE;
//	    }
//		
//	}
//	
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
		if(airSpeed > 0) {
		//falling, touching floor
			//current tile + 1 * tile size - entity height
			int tileYPos = (currentTile + 1) * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset -1;
		}
		else {
		//jumping
			return currentTile * Game.TILES_SIZE;
		}
		
//	    if (airSpeed > 0) {
//	        // Falling → snap to floor
//	        int tileY = (int) ((hitbox.y + hitbox.height) / Game.TILES_SIZE);
//	        return tileY * Game.TILES_SIZE - hitbox.height;
//	    } else {
//	        // Jumping → snap just below roof
//	        int tileY = (int) (hitbox.y / Game.TILES_SIZE);
//	        return (tileY + 1) * Game.TILES_SIZE;
//	    }

	}
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;

		
//	    float xLeft  = hitbox.x;
//	    float xRight = hitbox.x + hitbox.width - 1; // -1 so we stay inside box
//	    float yCheck = hitbox.y + hitbox.height + 1;
//
//	    return IsSolid(xLeft, yCheck, lvlData) || IsSolid(xRight, yCheck, lvlData);
	}
	
}
	

