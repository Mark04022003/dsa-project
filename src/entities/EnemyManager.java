package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utils.LoadSave;

import static utils.Constants.EnemyConstants.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][]orcBoyzArr;
	private ArrayList<OrcBoyz> orcBoyz = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}
	
	public void loadEnemies(Level level) {
		orcBoyz = level.getOrcBoyz();
	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive = false;
		for(OrcBoyz ob : orcBoyz)
			if (ob.isActive()) {
				ob.update(lvlData, player);
				isAnyActive = true;
			}
		if (!isAnyActive)
			playing.setLevelCompleted(true);
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawOrcBoyz(g, xLvlOffset);
//		drawOrcBoyzHitboxes(g, xLvlOffset);
		
	}
	
	private void drawOrcBoyz(Graphics g, int xLvlOffset) {
	    for(OrcBoyz ob : orcBoyz) {
	    	if (ob.isActive()) {
	    
	    {
	        int offsetX = (ORC_BOYZ_WIDTH - (int)ob.getHitbox().width) / 2;
	        int offsetY = ORC_BOYZ_HEIGHT - (int)ob.getHitbox().height;
	        int drawX = (int) ob.getHitbox().x - xLvlOffset - offsetX - 5;
	        if (ob.flipW() == -1) {
	            drawX += ORC_BOYZ_WIDTH;
	            drawX += 20; // Add 10 when facing left
	        } else {
	            drawX -= 10; // Subtract 10 when facing right
	        }
	        int drawY = (int) ob.getHitbox().y - offsetY + 15;
	        g.drawImage(orcBoyzArr[ob.getenemyState()][ob.getAniIndex()],
	            drawX,
	            drawY,
	            ORC_BOYZ_WIDTH * ob.flipW(),
	            ORC_BOYZ_HEIGHT,
	            null);
//	        ob.drawAttackbox(g, xLvlOffset);
	    		}
	    	}
	    }
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for(OrcBoyz ob : orcBoyz) {
			if (ob.isActive()) {
				if(attackBox.intersects(ob.getHitbox())) {
					ob.hurt(10);
					return;
				}
			}
		}
	}

	private void loadEnemyImgs() {
		orcBoyzArr = new BufferedImage[5][8];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ORC_BOYZ_SPRITE);
		for(int j = 0; j < orcBoyzArr.length; j++)
			for(int i = 0; i < orcBoyzArr[j].length; i++)
				orcBoyzArr[j][i] = temp.getSubimage(i * ORC_BOYZ_WIDTH_DEFAULT, j * ORC_BOYZ_HEIGHT_DEFAULT, ORC_BOYZ_WIDTH_DEFAULT, ORC_BOYZ_HEIGHT_DEFAULT);
	}
	
	public void resetAllEnemies() {
		for(OrcBoyz ob : orcBoyz)
			ob.resetEnemy();
	}
	
//	private void drawOrcBoyzHitboxes(Graphics g, int xLvlOffset) {
//	    g.setColor(java.awt.Color.RED); // Use a visible color for debugging
//	    for (OrcBoyz ob : orcBoyz) {
//	        int x = (int) ob.getHitbox().x - xLvlOffset;
//	        int y = (int) ob.getHitbox().y;
//	        int width = (int) ob.getHitbox().width;
//	        int height = (int) ob.getHitbox().height;
//	        g.drawRect(x, y, width, height);
//	    }
//	}

}