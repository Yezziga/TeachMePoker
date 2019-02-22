package gui;

import java.io.IOException;

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
 *  @version 2.1
 * Added possibility to turn music on and off
 *
 */

public class FMController {

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
	 * @throws Exception
	 */
	public void NewGameClicked() throws Exception {

		changeScene.switchScenetoSetting();
		
	}

	/**
	 * Should load a saved game file. This method is currently a non-functional
	 * method that is not implemented for the final version.
	 * 
	 * @throws IOException
	 */
	public void LoadGameClicked() throws IOException {
		// fileHandler = new FileHandler();
		// String pot = fileHandler.loadPot();
		// System.out.println(fileHandler.loadPot());

		System.out.println("LoadGame");
		sound = new Sound();
		sound.playSound("wrong");

	}
	
	/**
	 * Turns background music on and off
	 */
	
	public void soundSetting() {
		 if (sound.mp.getVolume() > 0) {
		      sound.mp.setVolume(0);
		      ivSound.setImage(soundOff);
		       
		    } else if (sound.mp.getVolume() == 0) {
		      sound.mp.setVolume(1);
		      ivSound.setImage(soundOn);
		  }
	}

}
