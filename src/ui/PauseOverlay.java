package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestates;
import gamestates.Playing;
import main.Game;
import utils.Constants.UI.VolumeButtons;
import utils.LoadSave;
import static utils.Constants.UI.PauseButtons.*;
import static utils.Constants.UI.URMButtons.*;

public class PauseOverlay {
    private Playing playing;

    // --- COMMENTED OUT: background image not used ---
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;

    // --- COMMENTED OUT: sound and volume UI not used ---
    private SoundButton musicButton, sfxButton;
    private VolumeButton volumeButton;

    private UrmButton menuButton, replayButton, unpauseButton;

    public PauseOverlay(Playing playing) {
        this.playing = playing;

        // loadBackground();           
        // createSoundButtons();       
        createUrmButtons();          
        // createVolumeButton();       
    }

    // private void createVolumeButton() {
    //     int vX = (int)(309 * Game.SCALE);
    //     int vY = (int)(276 * Game.SCALE);
    //     volumeButton = new VolumeButton(vX, vY, VolumeButtons.SLIDER_WIDTH, VolumeButtons.VOLUME_HEIGHT);
    // }

    private void createUrmButtons() {
		int buttonSize = URM_SIZE;
		int spacing = (int) (20 * Game.SCALE); // space between buttons

		int totalWidth = (buttonSize * 3) + (spacing * 2);
		int startX = (Game.GAME_WIDTH / 2) - (totalWidth / 2);
		int bY = (Game.GAME_HEIGHT / 2) - (buttonSize / 2);

		menuButton = new UrmButton(startX, bY, URM_SIZE, URM_SIZE, 2);
		replayButton = new UrmButton(startX + buttonSize + spacing, bY, URM_SIZE, URM_SIZE, 1);
		unpauseButton = new UrmButton(startX + (buttonSize + spacing) * 2, bY, URM_SIZE, URM_SIZE, 0);
	}

    // private void createSoundButtons() {
    //     int soundX = (int) (450 * Game.SCALE);
    //     int musicY = (int) (140 * Game.SCALE);
    //     int sfxY = (int) (186 * Game.SCALE);
    //     musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
    //     sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    // }

    // private void loadBackground() {
    //     backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
    //     bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
    //     bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
    //     bgX = Game.GAME_WIDTH / 2 - bgW / 2;
    //     bgY = (int) (25 * Game.SCALE);
    // }

    public void update() {
        // musicButton.update();  
        // sfxButton.update();   

        menuButton.update();
        replayButton.update();
        unpauseButton.update();

        // volumeButton.update(); 
    }

    public void draw(Graphics g) {
        // g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);  

        // musicButton.draw(g);
        // sfxButton.draw(g);    

        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

        // volumeButton.draw(g); 
    }

    // public void mouseDragged(MouseEvent e) {
    //     if(volumeButton.isMousePressed()) {
    //         volumeButton.changeX(e.getX());
    //     }
    // }

    public void mousePressed(MouseEvent e) {
        // if(isIn(e, musicButton)) musicButton.setMousePressed(true);
        // else if(isIn(e, sfxButton)) sfxButton.setMousePressed(true);
        if(isIn(e, menuButton)) menuButton.setMousePressed(true);
        else if(isIn(e, replayButton)) replayButton.setMousePressed(true);
        else if(isIn(e, unpauseButton)) unpauseButton.setMousePressed(true);
        // else if(isIn(e, volumeButton)) volumeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        // if(isIn(e, musicButton) && musicButton.isMousePressed()) {
        //     musicButton.setMuted(!musicButton.isMuted());
        // }
        // else if(isIn(e, sfxButton) && sfxButton.isMousePressed()) {
        //     sfxButton.setMuted(!sfxButton.isMuted());
        // }
        if(isIn(e, menuButton) && menuButton.isMousePressed()) {
            Gamestates.state = Gamestates.MENU;
            playing.unpauseGame();
        }
        else if(isIn(e, replayButton) && replayButton.isMousePressed()) {
            playing.resetAll();
            playing.unpauseGame();
        }
        else if(isIn(e, unpauseButton) && unpauseButton.isMousePressed()) {
            playing.unpauseGame();
        }

        // musicButton.resetBools();
        // sfxButton.resetBools();
        menuButton.resetBools();
        replayButton.resetBools();
        unpauseButton.resetBools();
        // volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        // musicButton.setMouseOver(false);
        // sfxButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);
        // volumeButton.setMouseOver(false);

        // if(isIn(e, musicButton)) musicButton.setMouseOver(true);
        // else if(isIn(e, sfxButton)) sfxButton.setMouseOver(true);
        if(isIn(e, menuButton)) menuButton.setMouseOver(true);
        else if(isIn(e, replayButton)) replayButton.setMouseOver(true);
        else if(isIn(e, unpauseButton)) unpauseButton.setMouseOver(true);
        // else if(isIn(e, volumeButton)) volumeButton.setMouseOver(true);
    }

    public boolean isIn(MouseEvent e, UrmButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
