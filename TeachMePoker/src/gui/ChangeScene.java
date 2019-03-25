package gui;

import java.awt.Component;
import java.io.IOException;

import javax.swing.JOptionPane;

import controller.SPController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Class that handles the switching of scenes in the main window and the gui
 * controll class that manages that scene.
 * 
 * @author Lykke Levin & Rikard Almgren
 * @version 1.0
 *
 * @author Loise Borg
 * @version 4.1 Fixed sound settings
 * 
 * @author Malin Zederfeldt
 * @version 4.2 Attempt at implementing save/load game
 */

public class ChangeScene {

	Pane rootMenu;
	Pane rootNewGame;
	Pane root2;
	Scene bestScene;
	private FMController fmController;
	private SettingsController settingsController;
	private GameController gameController;
	private Sound sound = new Sound();

	/**
	 * Method which prepares the FXMLs and by extension the game itself.
	 * 
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void prepGame() throws IOException, InstantiationException, IllegalAccessException {
		sound.playBackgroundMusic();
		FXMLLoader loaderFM = new FXMLLoader(FMController.class.getResource("/FirstMenu.fxml"));
		this.rootMenu = loaderFM.load();
		this.fmController = loaderFM.getController();
		this.fmController.setSoundClass(sound);
		FXMLLoader loaderSS = new FXMLLoader(SettingsController.class.getResource("/GameSettingMenu.fxml"));
		this.rootNewGame = loaderSS.load();
		this.settingsController = loaderSS.getController();
		this.settingsController.setSoundClass(sound);

		FXMLLoader loaderGS = new FXMLLoader(GameController.class.getResource("/GameState.fxml"));
		this.root2 = loaderGS.load();
		this.gameController = loaderGS.getController();
		this.gameController.setSoundClass(sound);

		this.bestScene = new Scene(rootMenu);

		gameController.setChangeScene(this);
		settingsController.setChangeScene(this);
		fmController.setChangeScene(this);

	}

	/**
	 * Method which switches the scene to the settings menu.
	 * 
	 * @throws IOException
	 */
	public void switchScenetoSetting() throws IOException {
		main.window.getScene().setRoot(rootNewGame);
		settingsController.initializeSound();
	}

	/**
	 * Method which switches the scene to the GameState.
	 * 
	 * @throws IOException
	 */
	public void switchScenetoGame() throws IOException {
		main.window.getScene().setRoot(root2);
		gameController.setUsername(settingsController.getName());
		gameController.initializeSound();
	}
	
	/**
	 * Method which switches the scene to the GameState from a loaded game. 
	 * 
	 * @throws IOException
	 */
	  public void switchScenetoLoad() throws IOException {
		  
		  main.window.getScene().setRoot(root2);
		  gameController.loadGame(new LoadGame());
		  
			
		  Sound.mp.setVolume(0.3);
		  
	  }

	/**
	 * Method which returns the Scene(and First/main menu).
	 * 
	 * @return bestScene the scene for the game.
	 * @throws IOException
	 */
	public Scene firstScene() throws IOException {
		return bestScene;
	}

	/**
	 * Method which switches to the main menu.
	 * 
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void switchToMainMenu() throws IOException, InstantiationException, IllegalAccessException {
		prepGame();
		fmController.initializeSound();
		main.window.setScene(bestScene);
	}

	/**
	 * Method which sets the SPController (the controller that runs the actual game
	 * behind the scenes).
	 * 
	 * @param spController
	 */
	public void setSPController(SPController spController) {
		gameController.setSPController(spController);
	}

}
