package gui;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main method to start the program.
 * 
 * @author Lykke Levin
 * @version 1.0
 *
 *
 * @author Loise Borg
 * @version 4.1
 */

public class main extends Application {
	public static Stage window;
	public ChangeScene cs = new ChangeScene();

	/**
	 * The applications calls start(Stage primaryStage) after launch has been
	 * executed.
	 */
	public void start(Stage primaryStage) throws Exception {
		cs.prepGame();

		window = primaryStage;
		window.setTitle("TeachMePoker");
		Font.loadFont(main.class.getResource("TCBI____.TTF").toExternalForm(), 10);
		window.setResizable(true);
		window.setOnCloseRequest(e -> closeProgram());

		window.setScene(cs.firstScene());
		window.show();
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

	/**
	 * Closes the window and exits the program.
	 */
	public void closeProgram() {
		System.exit(0);
	}
}
