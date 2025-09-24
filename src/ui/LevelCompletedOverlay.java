package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestates;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import static utils.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {

	private Playing playing;
	private UrmButton menuButton, nextLevelButton;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;
	
	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initButtons();
		initImg();
	}
	
	private void initButtons() {
		int menuX = (int) (330 * Game.SCALE);
		int nextX = (int) (445 * Game.SCALE);
		int y = (int) (200 * Game.SCALE);
		nextLevelButton = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
		menuButton = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
	}

	private void initImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
		bgW = (int) (img.getWidth() * Game.SCALE);
		bgH = (int) (img.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (75 * Game.SCALE);
	}
	
	public void draw(Graphics g) {
//		if (img == null)
//			initImg();
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		nextLevelButton.draw(g);
		menuButton.draw(g);
	}
	
	public void update() {
		nextLevelButton.update();
		menuButton.update();
	}
	
	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	public void mouseMoved(MouseEvent e) {
		nextLevelButton.setMouseOver(false);
		menuButton.setMouseOver(false);
		
		if (isIn(nextLevelButton, e))
			nextLevelButton.setMouseOver(true);
		else if (isIn(menuButton, e))
			menuButton.setMouseOver(true);
	}
	
	public void mouseReleased(MouseEvent e) {
		if (isIn(nextLevelButton, e)) {
			if (nextLevelButton.isMousePressed()) 
			{
				playing.loadNextLevel();
				}
		}
		else if (isIn(menuButton, e)) {
			if (menuButton.isMousePressed()) {
				playing.resetAll();
				Gamestates.state = Gamestates.MENU;
				}
			}
			
		
		nextLevelButton.resetBools();
		menuButton.resetBools();
	}
	
	public void mousePressed(MouseEvent e) {
		if (isIn(nextLevelButton, e))
			nextLevelButton.setMousePressed(true);
		else if (isIn(menuButton, e))
			menuButton.setMousePressed(true);
	}
	
}
