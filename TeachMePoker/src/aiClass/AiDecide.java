package aiClass;

import java.util.ArrayList;
import java.util.Random;

import deck.Card;

/**
 * Class that actually figures out what Ai-player should do with its cards.
 * @author Max Frennessen
 * 17-05-25
 * @version 1.0
 */
public class AiDecide {

	private AiCalculation calculation;
	private int colorChance;
	private int straightChance;
	private int likelyhood = 0;
	private boolean highCards;
	private boolean rlyhighCards;
	private String toDo = "fold";
	private int aiPot;
	private int toBet;
	private int raiseAmount;
	private boolean sameTurn;
	private int raiseBet = 0;
	private int turn;
	private int handStrength;
	private int howMuchToTakeAwayFromAiPot = 0;
	private int alreadyPaid;

	/**
	 * Gets all the neccesary stuff to make a decision for the current turn.
	 * @param aiHand the current cards the ai-player has to its disposal.
	 * @param aiPot the current potsize of the ai-players pot.
	 * @param toBet how much the ai-player has to pay to be in this turn.
	 * @param alreadyPaid how much the ai-player has already paid.
	 * @param sameTurn if it is or isnt the same turn.
	 */
	public AiDecide(ArrayList<Card> aiHand, ArrayList<Card> tableCards, int aiPot, int toBet, int alreadyPaid, boolean sameTurn) {
		this.aiPot = aiPot;
		this.toBet = toBet;
		this.alreadyPaid = alreadyPaid;
		this.sameTurn = sameTurn;

		if (toBet != 0) {
			this.toBet = this.toBet - alreadyPaid;
		}
		this.raiseBet = toBet;
		this.raiseAmount = (int) (1.25 * raiseBet);

		calculation = new AiCalculation(aiHand, tableCards);
		highCards = calculation.checkHighCards();
		colorChance = calculation.checkSuit();
		straightChance = calculation.checkStraight();
		handStrength = calculation.calcHandstrenght();
		turn = tableCards.size();

		if (turn == 0) {
			turnOne();
		} else if (turn == 3) {
			turnTwo();
		} else if (turn == 4) {
			turnThree();
		} else if (turn == 5) {
			turnFour();
		}
	}

	/**
	 * Decides a decision for the first turn of a new round.
	 */
	public void turnOne() {

		boolean check = false;
		if (toBet == 0) {
			check = true;
		}
		if (straightChance == 2) {
			likelyhood += 25;
		}

		if (highCards) {
			likelyhood += 20;
			if (rlyhighCards) {
				likelyhood += 15;
			}
		}

		if (colorChance == 2) {
			likelyhood += 20;
		}

		if (aiPot / 10 >= toBet) {
			likelyhood += 20;
		} else if (aiPot / 5 <= toBet) {
			likelyhood -= 10;
		}

		else if (aiPot / 3 <= toBet) {
			likelyhood -= 20;
		}

		else if (aiPot / 2 <= toBet) {
			likelyhood -= 20;
		}

		Random rand = new Random();
		int roll = rand.nextInt(100);

		if (likelyhood < 35 && roll <= 15 && !(check)) { // BLUFF
			if (aiPot > toBet) {
				toDo = "call," + toBet;
				System.out.println("BLUFF!!!");
				howMuchToTakeAwayFromAiPot = toBet;
			}
		} else if (likelyhood <= 100 && check) {
			toDo = "check";
		} else if (likelyhood >= 35 && aiPot > toBet && !(check)) {
			toDo = "call," + toBet;
			howMuchToTakeAwayFromAiPot = toBet;
		} 

		if (handStrength == 1) {

			if (raiseAmount < aiPot && !(sameTurn)) {
				toDo = "raise," + raiseAmount;
				howMuchToTakeAwayFromAiPot = raiseAmount-alreadyPaid;
			} 
			else if(aiPot > toBet && check){
				toDo = "check" + toBet;
			}else if (aiPot > toBet && !check) {
				toDo = "call," + toBet;
				howMuchToTakeAwayFromAiPot = toBet;
			} else if (toBet >= aiPot) {
				toDo = "all-in," + aiPot;
				howMuchToTakeAwayFromAiPot = aiPot;
			}

		}
		aiPot -= howMuchToTakeAwayFromAiPot;
	}

	/**
	 * Decides a decision for the second turn of a new round.
	 */
	public void turnTwo() {

		boolean check = false;
		if (toBet == 0) {
			check = true;
		}
		if (straightChance >= 3) {
			likelyhood += 25;
		}

		if (highCards) {
			likelyhood += 20;
			if (rlyhighCards) {
				likelyhood += 15;
			}
		}

		if (colorChance >= 3) {
			likelyhood += 20;
		}

		if (aiPot / 10 >= toBet) {
			likelyhood += 20;
		} else if (aiPot / 5 <= toBet) {
			likelyhood -= 10;
		}

		else if (aiPot / 3 <= toBet) {
			likelyhood -= 20;
		}

		else if (aiPot / 2 <= toBet) {
			likelyhood -= 20;
		}


		Random rand = new Random();
		int roll = rand.nextInt(100);

		if (likelyhood < 35 && roll <= 15 && !(check)) { // BLUFF
			if (aiPot > toBet) {
				toDo = "call," + toBet;
				System.out.println("BLUFF!!!");
				howMuchToTakeAwayFromAiPot = toBet;
			}
		} else if (likelyhood <= 100 && check) {
			toDo = "check";
		} else if (likelyhood >= 35 && aiPot > toBet && !(check)) {
			toDo = "call," + toBet;
			howMuchToTakeAwayFromAiPot = toBet;
		} 

		if (handStrength >= 1) {

			if (raiseAmount < aiPot && !(sameTurn)) {
				toDo = "raise," + raiseAmount;
				howMuchToTakeAwayFromAiPot = raiseAmount-alreadyPaid;
			} else if(aiPot > toBet && check){
				toDo = "check" + toBet;
			}
			else if (aiPot > toBet && !check) {
				toDo = "call," + toBet;
				howMuchToTakeAwayFromAiPot = toBet;
			} else if (toBet >= aiPot) {
				toDo = "all-in," + aiPot;
				howMuchToTakeAwayFromAiPot = aiPot;
			}

		}
		aiPot -= howMuchToTakeAwayFromAiPot;
	}

	/**
	 * Decides a decision for the third turn of a new round.
	 */
	public void turnThree() {

		boolean check = false;
		if (toBet == 0) {
			check = true;
		}
		if (straightChance >= 4) {
			likelyhood += 25;
		}

		if (highCards) {
			likelyhood += 20;
			if (rlyhighCards) {
				likelyhood += 15;
			}
		}

		if (colorChance >= 4) {
			likelyhood += 20;
		}

		if (aiPot / 10 >= toBet) {
			likelyhood += 20;
		} else if (aiPot / 5 <= toBet) {
			likelyhood -= 10;
		}

		else if (aiPot / 3 <= toBet) {
			likelyhood -= 20;
		}

		else if (aiPot / 2 <= toBet) {
			likelyhood -= 20;
		}


		Random rand = new Random();
		int roll = rand.nextInt(100);

		if (likelyhood < 35 && roll <= 15 && !(check)) { // BLUFF
			if (aiPot > toBet) {
				toDo = "call," + toBet;
				System.out.println("BLUFF!!!");
				howMuchToTakeAwayFromAiPot = toBet;
			}
		} else if (likelyhood <= 100 && check) {
			toDo = "check";
		} else if (likelyhood >= 35 && aiPot > toBet && !(check)) {
			toDo = "call," + toBet;
			howMuchToTakeAwayFromAiPot = toBet;
		} 

		if (handStrength > 1) {

			if (raiseAmount < aiPot && !(sameTurn)) {
				toDo = "raise," + raiseAmount;
				howMuchToTakeAwayFromAiPot = raiseAmount-alreadyPaid;
			} 
			else if(aiPot > toBet && check){
				toDo = "check" + toBet;
			}else if (aiPot > toBet && !check) {
				toDo = "call," + toBet;
				howMuchToTakeAwayFromAiPot = toBet;
			} else if (toBet >= aiPot) {
				toDo = "all-in," + aiPot;
				howMuchToTakeAwayFromAiPot = aiPot;
			}

		}
		aiPot -= howMuchToTakeAwayFromAiPot;

	}

	/**
	 * Decides a decision for the last turn of a new round.
	 */
	public void turnFour() {

		boolean check = false;
		if (toBet == 0) {
			check = true;
		}

		if (highCards) {
			likelyhood += 20;
			if (rlyhighCards) {
				likelyhood += 15;
			}
		}

		if (aiPot / 10 >= toBet) {
			likelyhood += 20;
		} else if (aiPot / 5 <= toBet) {
			likelyhood -= 10;
		}

		else if (aiPot / 3 <= toBet) {
			likelyhood -= 20;
		}

		else if (aiPot / 2 <= toBet) {
			likelyhood -= 20;
		}

		Random rand = new Random();
		int roll = rand.nextInt(100);

		if (likelyhood < 35 && roll <= 15 && !(check)) { // BLUFF
			if (aiPot > toBet) {
				toDo = "call," + toBet;
				System.out.println("BLUFF!!!");
				howMuchToTakeAwayFromAiPot = toBet;
			}
		} else if (likelyhood <= 100 && check) {
			toDo = "check";
		} else if (likelyhood >= 35 && aiPot > toBet && !(check)) {
			toDo = "call," + toBet;
			howMuchToTakeAwayFromAiPot = toBet;
		} 

		if (handStrength > 1) {

			if (raiseAmount < aiPot && !(sameTurn)) {
				toDo = "raise," + raiseAmount;
				howMuchToTakeAwayFromAiPot = raiseAmount-alreadyPaid;
			} 
			else if(aiPot > toBet && check){
				toDo = "check" + toBet;
			}
			else if (aiPot > toBet && !check) {
				toDo = "call," + toBet;
				howMuchToTakeAwayFromAiPot = toBet;
			} else if (toBet >= aiPot) {
				toDo = "all-in," + aiPot;
				howMuchToTakeAwayFromAiPot = aiPot;
			}

		}
		aiPot -= howMuchToTakeAwayFromAiPot;

	}


	// TO GAME

	/**
	 * Returns the ai-players handStrenght
	 * @return returns the ai-players handStrenght
	 */
	public int gethandStrength() {

		return handStrength;
	}

	/**
	 * returns a updated version of the ai-players pot after this turn.
	 * @return returns a updated version of the ai-players pot after this turn.
	 */
	public int updateAiPot() {

		return aiPot;
	}

	/**
	 * Returns what the ai-players is going to do this turn.
	 * @return Returns what the ai-players is going to do this turn.
	 */
	public String decision() {

		return toDo;
	}

	public ArrayList<Card> getWinningCards() {
		return calculation.getWinningCards();
	}
	
	public ArrayList<Card> getAIHand() {
		return calculation.getAIHand();
	}
	
	public ArrayList<Card> getAllCards() {
		return calculation.getAllCards();
	}
}
