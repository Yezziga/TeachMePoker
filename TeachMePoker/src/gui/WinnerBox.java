
package gui;


import java.nio.file.Paths;
import java.util.ArrayList;

import aiClass.Ai;
import deck.Card;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Box that shows the winner of round. 
 * @author Lykke Levin 
 * version 1.0
 *
 *@author dalvig
 *version 2.2
 * Added images of the winners cards to the announcement window 
 * 
 * @author Henrik
 * @version 3.0
 * Updated the winner box layout to show the table cards, the winners hand and highlight the winning combination.
 */
public class WinnerBox {

	private Ai AI;
	public boolean answer = false;
	public Stage window = new Stage();
	public Font font = new Font("Tw Cen MT", 18);
	private ImageView back = new ImageView(Paths.get("resources/images/background.png").toUri().toString());
	private ImageView btnOk = new ImageView(Paths.get("resources/images/okButton.png").toUri().toString());


	/**
	 * Creates a window containting messages of who won or lost. 
	 * @param title String title of the window from the method that uses WinnerBox. 
	 * @param message String message to display in the window from the method that uses ConfirmBox. 
	 * @param nr Int to check which message should be displayed. 
	 * @param handType String to print the handstrength the player or AI won with. 
	 * @return answer Boolean that returns an answer.
	 */
	public boolean displayWinner(String title, String message, int nr, String handType, ArrayList<Card> winningCombination, ArrayList<Card> winningHand, ArrayList<Card> allCards) {



		String aiWin = new String("Rundan vanns av " + message + " som hade " + handType + ".");
		String playerWin = new String("Grattis " + message + ", du vann den här rundan! Du vann med " + handType + ".");
		String playerWinAIFold = new String("Grattis " + message + ". " + handType); 
		String aiWinOthersFold = new String("Rundan vanns av " + message + " " + handType + ".");
		String playerLose = new String (message);

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(400);
		window.setHeight(400);
		window.setOnCloseRequest(e -> closeProgram());

		Pane pane = new Pane();

		Label messageText = new Label();
		messageText.setFont(font);
		messageText.setTextFill(Color.WHITE);
		messageText.setWrapText(true);

		if(nr == 1){
			messageText.setText(playerWin);



		} else if(nr == 2){
			messageText.setText(aiWin);


		} else if(nr == 3){
			messageText.setText(playerWinAIFold);


		} else if(nr == 4){
			messageText.setText(aiWinOthersFold);


		}
		else if (nr == 5){
			messageText.setText(playerLose);



		}

		btnOk.setOnMouseReleased(e -> {
			answer = true;
			closeProgram();
		});

		back.setFitHeight(window.getHeight());
		back.setFitWidth(window.getWidth());
		messageText.setPrefSize(200, 100);
		messageText.setLayoutX(100);
		messageText.setLayoutY(10);
		btnOk.setFitHeight(35);
		btnOk.setFitWidth(35);
		btnOk.setLayoutX(175);

		pane.getChildren().addAll(back, messageText, btnOk);

		if(allCards != null) {
			int xPosition = 15, handxPosition = 120, handCards = 0;
			String highlight = "";
			System.out.println("All cards: " + allCards);
			System.out.print("Winning combo:" + winningCombination);
			for(Card card : allCards) {
				if(winningCombination.contains(card)) {
					highlight = "O";
				} else {
					highlight = "";
				}
				
				ImageView cardIcon = new ImageView(Paths.get("resources/images/" + card.getCardValue() + card.getCardSuit().charAt(0) + highlight + ".png").toUri().toString());
				
				if(handCards < 2) {
					cardIcon.setLayoutY(200);
					cardIcon.setLayoutX(handxPosition);
					handxPosition += 70;
					handCards++;
				} else {
					cardIcon.setLayoutY(100);
					cardIcon.setLayoutX(xPosition);
					xPosition += 70;
				}				
				cardIcon.setFitHeight(100);
				cardIcon.setFitWidth(70);			
				
				pane.getChildren().add(cardIcon);
				
				btnOk.setLayoutY(310);
			}
		} else {
			btnOk.setLayoutY(110);
		}

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
