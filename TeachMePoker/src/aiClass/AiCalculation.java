package aiClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import deck.Card;
import deck.CardValue;
import deck.Suit;

/**
 * Class that figures out what the ai-players cards are.
 * @author Max Frennessen
 * 17-05-25
 * @version 2.0
 * 
 * @author Henrik
 * @version 3.0
 * Added checks for finding and saving the best combination. Added checks for any existing flush.
 */
public class AiCalculation {

	private ArrayList<Card> aiHand = new ArrayList<Card>();
	private ArrayList<Card> allCards = new ArrayList<Card>();
	private ArrayList<Card> bestCombination = new ArrayList<Card>();
	private int handStrength = 0;
	private int pairs = 0, same = 0;
	private boolean flush = false, straight = false, fullHouse = false;

	/**
	 * Gets the cards that will be needed to calculate a respons that the ai will do.
	 * @param aiHand the current cards that are used by the ai.
	 */
	public AiCalculation(ArrayList<Card> aiHand, ArrayList<Card> tableCards) {
		this.aiHand = aiHand;
		allCards.addAll(aiHand);
		allCards.addAll(tableCards);
		checkHighCards();
		checkSuit();
		checkPairAndMore();
		checkStraight();
		System.out.println(aiHand);
		System.out.println(calcHandstrenght());
	}

	/**
	 * Checks if ai-players has high cards or not.
	 * @return returns if the cards have a combined value of 17 or more.
	 */
	public boolean checkHighCards() {
		boolean high = false;

		int card1 = aiHand.get(0).getCardValue();
		int card2 = aiHand.get(1).getCardValue();
		if ((card1 + card2) >= 17) {
			high = true;
		}
		
		if(bestCombination.isEmpty()) {
			if(card1 > card2) {
				bestCombination.add(aiHand.get(0));
			} else {
				bestCombination.add(aiHand.get(1));
			}
		}		

		return high;
	}

	/**
	 * calculates the number of same suits the ai players has.
	 * @return returns if the AI has a chance or has a flush
	 */
	public int checkSuit() {
		int C = 0, S = 0, H = 0, D = 0;
		int color = 0;
		String suit = "";

		for (Card card : allCards) {
			String cardColor = card.getCardSuit();
			if (cardColor.equals("S")) {
				S++;
			} else if (cardColor.equals("C")) {
				C++;
			} else if (cardColor.equals("D")) {
				D++;
			} else if (cardColor.equals("H")) {
				H++;
			}
		}

		if (S > color) {
			color = S;
			suit = "S";
		}
		if (H > color) {
			color = H;
			suit = "H";
		}
		if (D > color) {
			color = D;
			suit = "D";
		}
		if (C > color) {
			color = C;
			suit = "C";
		}

		if(color == 5) {
			bestCombination.clear();
			for(Card card : allCards) {
				if(card.getCardSuit().equals(suit)) {
					bestCombination.add(card);
				}
			}

			flush = true;				
		}		

		return color;
	}

	/**
	 * calculates the amount of same cards that the ai-player has to use.
	 * @return returns how many pairs or more that the ai has.
	 */
	public int checkPairAndMore() {

		int[] cardValuesOccurrences = new int[15];
		for (int i = 0; i < allCards.size(); i++) {
			cardValuesOccurrences[allCards.get(i).getCardValue()]++;
		}

		//Gets amount of pairs and the highest amount of cards with the same value
		for(int i : cardValuesOccurrences) {
			if(i > same) {
				same = i;
			}
			if(i >= 2) {
				pairs++;
			}
		}

		//Checks for full house
		if(same >= 3 && pairs >= 2) {
			fullHouse = true;
		}

		//Gets the best combination
		if(fullHouse && same < 4) { //Gets the fullhouse combination
			int pairValue = 0, threeOfAKindValue = 0;
			for(int i = (cardValuesOccurrences.length-1); i >= 0; i--) {
				if(cardValuesOccurrences[i] >= 3 && threeOfAKindValue == 0) {
					threeOfAKindValue = i;
				} else if(cardValuesOccurrences[i] >= 2 && pairValue == 0) {
					pairValue = i;
				}
			}
			bestCombination.clear();
			for(Card card : allCards) {
				if(card.getCardValue() == pairValue || card.getCardValue() == threeOfAKindValue) {
					bestCombination.add(card);
				}
			}
		} else if(same > 2) { //Adds three of a kind or four of a kind to best combination
			int tempValue = 0;
			for(int i = (cardValuesOccurrences.length-1); i >= 0; i--) {
				if(same == 4 && cardValuesOccurrences[i] == 4) {
					tempValue = i;
				} else if(same == 3 && cardValuesOccurrences[i] == 3 && tempValue == 0) {
					tempValue = i;
				}
			}
			bestCombination.clear();
			for(Card card : allCards) {
				if(card.getCardValue() == tempValue) {
					bestCombination.add(card);
				}
			}
		} else { //Adds one pair or two pair to the best combination
			int pairOneValue = 0, pairTwoValue = 0, pairOneCount = 0, pairTwoCount = 0;
			for(int i = (cardValuesOccurrences.length-1); i >= 0; i--) {
				if(cardValuesOccurrences[i] >= 2) {
					if(pairOneValue == 0) {
						pairOneValue = i;
					} else if(pairTwoValue == 0) {
						pairTwoValue = i;
					}
				}
			}
			bestCombination.clear();
			for(Card card : allCards) {
				if(card.getCardValue() == pairOneValue && pairOneCount < 2) {
					bestCombination.add(card);
					pairOneCount++;
				} else if(card.getCardValue() == pairTwoValue && pairTwoCount < 2) {
					bestCombination.add(card);
					pairTwoCount++;
				}
			}
		}

		return same;
	}

	//TODO Prioritize Straight Flush
	/**
	 * calculates the number of cards that can be in a straight
	 * @return returns if the Ai has a chance or has a Straight.
	 */
	public int checkStraight() {
		int treshold = 0;
		
		ArrayList<Integer> cardValues = new ArrayList<Integer>();
		for(Card card : allCards) { //Creates a sorted list with the values of all cards
			cardValues.add(card.getCardValue());
			if(card.getCardValue() == 14) {
				cardValues.add(1);
			}
		}
		Collections.sort(cardValues, Collections.reverseOrder());
		
		for(int startValue : cardValues) {
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			tempList.add(startValue);
			for(int value : cardValues) { //Compares the start value with all other values to check for a straight
				if(value == tempList.get(tempList.size() - 1)) {
					if(tempList.size() < 5) {
						tempList.add(value);
					}					
				}
			}
			if(tempList.size() == 5) { //Found a straight
				if(same < 4 ) { //Only replace the best combination if there is no four of a kind
					bestCombination.clear();
					for(int partValue : tempList) { //Converts the values to cards and saves it in the best combination
						//TODO Check for royal flush
						for(Card card : allCards) {
							if(partValue == 1) {
								partValue = 14;
							}
							if(partValue == card.getCardValue()){
								bestCombination.add(card);
							}
						}
					}	
				}							
				straight = true;
				return 5;
			} else if(tempList.size() > treshold) {
				treshold = tempList.size();
			}
		}
		
		return treshold;
	}

	/**
	 * Sets the handStrenght of the ai-player.
	 * @return returns the ai-players current handStrenght.
	 */
	public int calcHandstrenght(){

		if(same==2){ //One pair
			handStrength=1;	
		}
		if(pairs>=2){ //Two pair
			handStrength=2;
		}
		if(same==3){ //Three of a kind
			handStrength=3;
		}
		if(straight){ //Straight
			handStrength=4;
		}
		if(flush){ //Flush
			handStrength=5;
		}
		if(fullHouse){ //Full house
			handStrength=6;
		}
		if(same==4){ //Four of a kind
			handStrength = 7;
		}
		if(flush && straight){ //Straight flush
			handStrength = 8;
		}

		//Missing Royal Flush check?

		return handStrength;
	}

	//TODO Move calculations of the best combination to this function
	public ArrayList<Card> getWinningCards() {
		return bestCombination;
	}
	
	public ArrayList<Card> getAIHand() {
		return aiHand;
	}
	
	public ArrayList<Card> getAllCards() {
		return allCards;
	}

}
