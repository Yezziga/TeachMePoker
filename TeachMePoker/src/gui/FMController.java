package gui;

import java.awt.Component;
import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller for FXML-doc FirstMenu.fxml.
 * 
 * @author Lykke Levin
 * @version 1.0
 *
 ** @author dalvig
 * @version 2.1 Added possibility to turn music on and off
 * 
 * @author Loise Borg
 * @version 4.1 Fixed sound settings
 *
 * @author Malin Zederfeldt
 * @version 4.2 Attempt at implementing save/load game
 */

public class FMController {

	private GameController gController;
	private ChangeScene changeScene;
	private Sound sound;
	private SettingsController sc;
	@FXML
	private TextField tfNameInput;
	@FXML
	private ImageView ivNewGame;
	@FXML
	private ImageView ivLoadGame;
	@FXML
	private ImageView ivSound;

	private Image soundOn = new Image("images/soundButton.png");

	private Image soundOff = new Image("images/soundOffButton.png");

	/**
	 * Generated method for the FXML.
	 * 
	 * @throws Exception
	 */
	public void initialize() throws Exception {

	}

	/**
	 * Sets the changeScene for this FMController
	 * 
	 * @param sceneChanger an instance of the class ChangeScene
	 */
	public void setChangeScene(ChangeScene sceneChanger) {
		this.changeScene = sceneChanger;

	}

	/**
	 * Tells class changeScene to perform the swithScene-action.
	 * 
	 * @throws Exception
	 */
	public void NewGameClicked() throws Exception {

		changeScene.switchScenetoSetting();

	}

	/**
	 * Should load a saved game file. 
	 * 
	 * @throws IOException
	 */
	public void LoadGameClicked() throws IOException {
		System.out.println("Load button clicked");
		changeScene.switchScenetoLoad();

	}

	/**
	 * Sets the Sound variable
	 * @param sound
	 */
	public void setSoundClass(Sound sound) {
		this.sound = sound;
	}

	/**
	 * Turns background music on and off
	 */

	public void soundButtonClicked() {
		if (sound.isSoundTurnedOn()) {
			sound.turnSoundOff();
			ivSound.setImage(soundOff);
		} else if (!sound.isSoundTurnedOn()) {
			sound.turnSoundOn();
			ivSound.setImage(soundOn);
		}
	}
	
	/**
	 * Checks if the sound should be on or off. 
	 */
	public void initializeSound() {
		if (sound.isSoundTurnedOn()) {
			sound.turnSoundOn();
			ivSound.setImage(soundOn);
		} else if (!sound.isSoundTurnedOn()) {
			sound.turnSoundOff();
			ivSound.setImage(soundOff);
		}
	}

}
