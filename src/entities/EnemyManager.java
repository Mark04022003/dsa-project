package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utils.LoadSave;

import static utils.Constants.EnemyConstants.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][]orcBoyzArr;
	private ArrayList<OrcBoyz> orcBoyz = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}
	
	private void addEnemies() {
		orcBoyz = LoadSave.GetOrcBoyz();
		System.out.println("size of orc boyz: " + orcBoyz.size());
	}

	public void update(int[][] lvlData, Player player) {
		for(OrcBoyz ob : orcBoyz)
			ob.update(lvlData, player);
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawOrcBoyz(g, xLvlOffset);
		drawOrcBoyzHitboxes(g, xLvlOffset);
		
	}
	
	private void drawOrcBoyz(Graphics g, int xLvlOffset) {
		for(OrcBoyz ob : orcBoyz)
			g.drawImage(orcBoyzArr[ob.getenemyState()][ob.getAniIndex()], (int) ob.getHitbox().x - xLvlOffset - 32, (int) ob.getHitbox().y - 32, ORC_BOYZ_WIDTH, ORC_BOYZ_HEIGHT, null);
	}

	private void loadEnemyImgs() {
		orcBoyzArr = new BufferedImage[5][8];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ORC_BOYZ_SPRITE);
		for(int j = 0; j < orcBoyzArr.length; j++)
			for(int i = 0; i < orcBoyzArr[j].length; i++)
				orcBoyzArr[j][i] = temp.getSubimage(i * ORC_BOYZ_WIDTH_DEFAULT, j * ORC_BOYZ_HEIGHT_DEFAULT, ORC_BOYZ_WIDTH_DEFAULT, ORC_BOYZ_HEIGHT_DEFAULT);
	}
	
	private void drawOrcBoyzHitboxes(Graphics g, int xLvlOffset) {
	    g.setColor(java.awt.Color.RED); // Use a visible color for debugging
	    for (OrcBoyz ob : orcBoyz) {
	        int x = (int) ob.getHitbox().x - xLvlOffset;
	        int y = (int) ob.getHitbox().y;
	        int width = (int) ob.getHitbox().width;
	        int height = (int) ob.getHitbox().height;
	        g.drawRect(x, y, width, height);
	    }
	}

}
