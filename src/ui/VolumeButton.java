package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;
import static utils.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton {

	
	private BufferedImage[] volumeImgs;
	private BufferedImage slider;
	private boolean mouseOver, mousePressed;
	private int index = 0;
	private int buttonX, minX, maxX;
	

	
	public VolumeButton(int x, int y, int width, int height) {
		super(x + width/2, y, VOLUME_WIDTH, height);
		bounds.x -= VOLUME_WIDTH / 2;
		buttonX = x + width/2;
		this.x = x;
		this.width = width;
		minX = x + VOLUME_WIDTH / 2;
		maxX = x + width - VOLUME_WIDTH / 2;
		loadVolumeImgs();
	}
	
	public void loadVolumeImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
		volumeImgs = new BufferedImage[3]; // Assuming volume levels from 0 to 10
		for(int i = 0; i < volumeImgs.length; i++)
			volumeImgs[i] = temp.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT); // Assuming each icon is 32x32 pixels
		slider = temp.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0, SLIDER_DEFAULT_WIDTH, VOLUME_HEIGHT_DEFAULT);
	
	}
	
	public void update() {
		index = 0;
		if(mouseOver) 
			index = 1;
		if(mousePressed)
			index = 2;
	}
	
	public void draw(Graphics g) {
		g.drawImage(slider, x, y, width, height, null);
		g.drawImage(volumeImgs[index], buttonX - VOLUME_WIDTH / 2 , y, VOLUME_WIDTH, height, null);
	}
	
	public void changeX(int x) {
		if(x < minX)
			buttonX = minX;
		else if(x > maxX)
			buttonX = maxX;
		else
			buttonX = x;
		
		bounds.x = buttonX - VOLUME_WIDTH / 2;
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	public boolean isMousePressed() {
		return mousePressed;
	}
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

}
