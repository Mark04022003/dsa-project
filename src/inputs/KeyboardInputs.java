package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.Game;
import main.GamePanel;
import main.GameWindow;
import static utils.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Game game;

	public KeyboardInputs(GamePanel gamePanel, Game game) {
		this.gamePanel = gamePanel;
		this.game = game;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (gamestates.Gamestates.state == gamestates.Gamestates.MENU) {
			game.getMenu().keyReleased(e);
		} else if (gamestates.Gamestates.state == gamestates.Gamestates.PLAYING) {
			game.getPlaying().keyReleased(e);
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (gamestates.Gamestates.state == gamestates.Gamestates.MENU) {
			game.getMenu().keyPressed(e);
		} else if (gamestates.Gamestates.state == gamestates.Gamestates.PLAYING) {
			game.getPlaying().keyPressed(e);
		}
	}


	
	
}