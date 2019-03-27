package gui;

import java.io.IOException;

import javax.swing.JOptionPane;

import controller.SPController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Controller for FXML-doc GameSettingMenu.fxml
 * 
 * @author Lykke Levin
 * @version 1.0
 * 
 * @author dalvig
 * @version 2.1 Added possibility to turn music on and off
 *
 * @author Kold
 * @version 2.2 Changed from confirm box to alert box, on two separate places.
 *          In the "about project" and when a player tries to play without
 *          entering a name.
 * 
 * @author Quach
 * @version 3.1 Removed the confirm box when player starts a game.
 * 
 * @author Loise Borg
 * @version 4.1 Fixed sound settings.

 * 
 * @author Malin Zederfeldt
 * @version 4.2 Attempt at implementing save/load game
 */
public class SettingsController {
	private SPController spController;
	private MessageBox messageBox;
	private ChangeScene changeScene;
	private ConfirmBox confirmBox;
	private String name;
	private int aiValue;
	private int potValue;
	private FMController fm;

	@FXML
	private TextField tfNameInput;
	@FXML
	private Slider aiSlider;
	@FXML
	private Slider potSlider;
	@FXML
	private CheckBox cbOn;
	@FXML
	private CheckBox cbOff;
	@FXML
	private ImageView ivStartGame;
	@FXML
	private ImageView ivQuestionAi;
	@FXML
	private ImageView ivQuestionPot;
	@FXML
	private ImageView ivQuestionTutorial;
	@FXML
	private Label lblAiInfo;
	@FXML
	private Label lblPotInfo;
	@FXML
	private Label lblTutorialInfo;
	@FXML
	private ImageView ivBack;

	@FXML
	private ImageView imgTutorial;
	@FXML
	private Pane tutorialPane;
	@FXML
	private ImageView btnNext;

	@FXML
	private ImageView btnBack;

	private Sound sound;
	private TutorialController tutorialWindow;

	@FXML
	private ImageView ivSound;

	private Image soundOn = new Image("images/soundButton.png");

	private Image soundOff = new Image("images/soundOffButton.png");

	/**
	 * Method for initializing FXML.
	 * 
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		potSlider.setSnapToTicks(true);
		potSlider.setValue(5000);
		aiSlider.setSnapToTicks(true);
		
		//Sets limit of character input in name textfield
		tfNameInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (tfNameInput.getText().length() > 15) {
					String s = tfNameInput.getText().substring(0, 15);
					tfNameInput.setText(s);
				}
			}
		});
	}

	/**
	 * Stores the name from the TextField that the user has inserted.
	 */
	public void tfNameInputChange() {
		this.name = tfNameInput.getText();
	}

	/**
	 * Sets the changeScene for this SettingsController
	 * 
	 * @param sceneChanger an instance of the class ChangeScene
	 */
	public void setChangeScene(ChangeScene sceneChanger) {

		this.changeScene = sceneChanger;
	}

	/**
	 * Sets the Sound variable
	 * 
	 * @param sound
	 */
	public void setSoundClass(Sound sound) {
		this.sound = sound;
	}

	/**
	 * Stores the value from the Slider that the user has chosen.
	 */
	public void aiSliderChange() {
		Double val = aiSlider.getValue();
		aiValue = val.intValue();

	}

	/**
	 * Stores the value from the Slider that the user has chosen.
	 */
	public void potSliderChange() {

		Double val = potSlider.getValue();
		potValue = val.intValue();

	}

	/**
	 * If ComboBox is selected by the user, disable the button true.
	 */
	public void cbOnClicked() {

		if (cbOff.isSelected()) {
			cbOff.setSelected(false);
			cbOn.setSelected(true);

		}
	}

	/**
	 * If ComboBox is selected by the user, disable the button true.
	 */
	public void cbOffClicked() {

		if (cbOn.isSelected()) {
			cbOn.setSelected(false);
			cbOff.setSelected(true);

		}
	}

	/**
	 * Starts the game and checks so the Username it not empty and checks if the
	 * Tutorial should be playing at the beginning.
	 * 
	 * @throws IOException
	 */
	public void startGame() throws IOException {

		potSliderChange();
		aiSliderChange();
		if (!tfNameInput.getText().isEmpty()) {
			name = tfNameInput.getText();
			spController = new SPController();
			changeScene.setSPController(spController);

			if (cbOn.isSelected()) {
				System.out.println("Tutorial ska visas");
				Platform.runLater(() -> {

					try {
						this.tutorialWindow = new TutorialController(this, 1);
						tutorialWindow.setupUI();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

			} else {
				// do it here
				startGameWindow();
			}
		} else if (tfNameInput.getText().isEmpty()) {
			sound.playSound("wrong");
			Alert invalidUsernameBox = new Alert(AlertType.ERROR);
			invalidUsernameBox.setTitle("Varning");
			invalidUsernameBox.setHeaderText(null);
			invalidUsernameBox.setContentText("Du måste välja ett användarnamn för att starta spelet.");

			System.out.println("Du måste välja ett användarnamn");

			invalidUsernameBox.showAndWait();
		}

	}

	/**
	 * Creates the progressForm and the loadingbar.
	 */
	public void startGameWindow() {
		ProgressForm pForm = new ProgressForm();
		// In real life this task would do something useful and return
		// some meaningful result:
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {
				for (int i = 0; i < 10; i++) {
					updateProgress(i += 1, 10);
					Thread.sleep(200);

				}
				updateProgress(10, 10);
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.start();
		// binds progress of progress bars to progress of task:
		pForm.activateProgressBar(task);

		// in real life this method would get the result of the task
		// and update the UI based on its value:
		task.setOnSucceeded(event -> {
			pForm.getDialogStage().close();

			try {
				changeScene.switchScenetoGame();

				spController.startGame(aiValue, potValue, name);
				sound.mp.stop();
				sound.playSound("shuffle");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("Spel startas!");
	}
	
	/**
	 * Creates the progressForm and the loadingbar for a loaded game.
	 * Currently does not work as should. Gives null pointer for SPController class. 
	 */
	
	public void startLoadedGameWindow(int nbrOfAi, int pot, String username) {

		spController = new SPController();

		System.out
				.println("Trying to start loaded game: " + username + ", $" + pot + ", Number of players: " + nbrOfAi);
		ProgressForm pForm = new ProgressForm();
		// In real life this task would do something useful and return
		// some meaningful result:
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {
				for (int i = 0; i < 10; i++) {
					updateProgress(i += 1, 10);
					Thread.sleep(200);

				}
				updateProgress(10, 10);
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.start();
		// binds progress of progress bars to progress of task:
		pForm.activateProgressBar(task);

		// in real life this method would get the result of the task
		// and update the UI based on its value:
		task.setOnSucceeded(event -> {
			pForm.getDialogStage().close();

			try {
				
		//		changeScene.switchScenetoLoad();
				spController.startLoadedGame(nbrOfAi, pot, username);
				Sound.mp.stop();
				sound.playSound("shuffle");

				
			} catch (NullPointerException e) {
				System.out.println("null pointer for loaded game");
		//		JOptionPane.showMessageDialog(null, "Failed to load game for user" + username, "ERROR", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				try {

					messageBox = new MessageBox();
					messageBox.showMessage("nostart", username);
					
				}catch(NullPointerException e2) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("Sparat spel startas!");
	}

	/**
	 * Shows a label if question mark is hovered.
	 */
	public void ivQuestionAiHovered() {

		lblAiInfo.setVisible(true);
		ivQuestionAi.setOnMouseExited(e -> lblAiInfo.setVisible(false));

	}

	/**
	 * Shows a label if question mark is hovered.
	 */
	public void ivQuestionPotHovered() {

		lblPotInfo.setVisible(true);
		ivQuestionPot.setOnMouseExited(e -> lblPotInfo.setVisible(false));

	}

	/**
	 * Shows a label if question mark is hovered.
	 */
	public void ivQuestionTutorialHovered() {

		lblTutorialInfo.setVisible(true);
		ivQuestionTutorial.setOnMouseExited(e -> lblTutorialInfo.setVisible(false));
	}

	/**
	 * Tells class changeScene to perform the swithScene-action.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void back() throws InstantiationException, IllegalAccessException {
		try {
			changeScene.switchToMainMenu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Name of the user.
	 * 
	 * @return String name of the user.
	 */
	public String getName() {
		return name;
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
