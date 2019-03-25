package gui;

import java.nio.file.Paths;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class for displaying a messagebox when loading/saving a game
 * @author malinzederfeldt
 *
 */

public class MessageBox {
	
	public boolean answer = false;
	public Stage window = new Stage();
	public Font font = new Font("Tw Cen MT", 18);
	private ImageView back = new ImageView(Paths.get("resources/images/background.png").toUri().toString());
	private ImageView btnOk = new ImageView(Paths.get("resources/images/okButton.png").toUri().toString());

	/**
	 * creates a window displaying a message
	 * @param type
	 * @param username
	 * @return answer
	 */
	
	public boolean showMessage(String type, String username) {
		
		String check = type;
		String saved = "Spel sparat!";
		String load = "Spel laddat för spelare " + username;
		String noload = "Spel kunde inte laddas för spelare " + username;
		String nostart = "Spel kunde inte startas för spelare " + username;
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(type);
		window.setWidth(400);
		window.setHeight(200);
		window.setOnCloseRequest(e -> closeProgram());

		Pane pane = new Pane();

		Label messageText = new Label();
		messageText.setFont(font);
		messageText.setTextFill(Color.WHITE);
		messageText.setWrapText(true);
		
		if(check.equals("saved")) {
			messageText.setText(saved);
		}else if (check.equals("load")) {
			messageText.setText(load);
		}
		else if (check.equals("noload")) {
			messageText.setText(noload);
		}
		else if (check.equals("nostart")) {
			messageText.setText(nostart);
		}
		
		btnOk.setOnMouseReleased(e -> {
			answer = true;
			closeProgram();
		});

		back.setFitHeight(window.getHeight());
		back.setFitWidth(window.getWidth());
		messageText.setPrefSize(200, 100);
		messageText.setLayoutX(100);
		messageText.setLayoutY(30);
		btnOk.setFitHeight(35);
		btnOk.setFitWidth(35);
		btnOk.setLayoutX(200);
		btnOk.setLayoutY(120);

		pane.getChildren().addAll(back, messageText, btnOk);
		
		Scene scene = new Scene(pane);
		window.setScene(scene);
		window.showAndWait();
		return answer;
			
		
	}
	
	/**
	 * Closes the window. 
	 */
	public void closeProgram() {
		window.close();
	}
}
