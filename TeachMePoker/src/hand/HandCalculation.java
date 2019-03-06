package hand;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Does the actuall calculation and decides what help the noob player gets.
 * 
 * @author Max Frennessen
 * @version 1.5 17-04-12
 * 
 * @author Quach
 * @version 3.2 19-03-05 Updated the advises 
 */
public class HandCalculation {
	private ArrayList<String> finalHihglight = new ArrayList<String>();
	private ArrayList<String> nbrForStraight = new ArrayList<String>();
	private ArrayList<String> nbrForStraight1 = new ArrayList<String>();
	private ArrayList<String> playerCards = new ArrayList<String>();
	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();
	private boolean highCards, lowCards, rlyhighCards = false;
	private int colorChance, straightChance, pairsNmore;
	private String yourCard = "1,1";
	private String yourCard2 = "1,1";
	private String otherCard = "1,1";
	private String theColor;
	private ArrayList<String> toHighlight = new ArrayList<String>();
	private String advice;
	private String whatStraight;
	private int handStrength = 0;

	/**
	 * 
	 * @param playerCards
	 *            Current cards needed for evaluate.
	 */
	public HandCalculation(ArrayList<String> playerCards) {

		this.playerCards = playerCards;
		getCardValues(playerCards);
		toHighlight.clear();
		highCards = checkHighCards();
		colorChance = checkSuit();
		pairsNmore = checkPairAndMore();
		straightChance = checkStraight();

		Help();
	}

	/**
	 * converts the cards value into two diffirent arraylists. one for cardnumber
	 * and one for cardcolor.
	 * 
	 * @param aiCards
	 *            current card being used
	 */
	public void getCardValues(ArrayList<String> aiCards) {

		for (int i = 0; i < aiCards.size(); i++) { // CardNumber
			String temp = aiCards.get(i);
			String[] splitter = temp.split(",");
			int tempInt = Integer.parseInt(splitter[0]);
			cardNbr.add(tempInt);
		}

		for (int i = 0; i < aiCards.size(); i++) { // CardColor
			String temp = aiCards.get(i);
			String[] splitter = temp.split(",");
			String tempString = splitter[1];
			cardClr.add(tempString);
		}
	}

	/**
	 * 
	 * @return returns how many pairs or more the player has.
	 */
	public int checkPairAndMore() {
		int same = 1;
		int nbrOftemp = 0;
		int nbrOftemp1 = 0;
		int nbrOftemp2 = 0;
		int size = playerCards.size();
		int[] cards = new int[size + 1];

		for (int i = 0; i < size; i++) {
			cards[i] = cardNbr.get(i);
		}

		if (cards[0] == cards[1]) {
			int temp = cards[0];
			nbrOftemp = 2;
			toHighlight.clear();
			toHighlight.add(playerCards.get(1));
			toHighlight.add(playerCards.get(0));
			yourCard = playerCards.get(0);
			otherCard = playerCards.get(1);

			for (int i = 2; i < cards.length; i++) {
				if (cards[i] == temp) {
					nbrOftemp++;

				}
			}
		}

		else {
			int temp1 = cards[0];
			int temp2 = cards[1];

			nbrOftemp1 = 1;
			nbrOftemp2 = 1;

			for (int i = 2; i < cards.length; i++) {

				if (cards[i] == temp1) {
					if (cards[i] + temp2 <= 10) {
						lowCards = true;
					}
					if (cards[i] + temp2 > 17) {
						highCards = true;
					}

					nbrOftemp1++;

					yourCard = playerCards.get(0);
					otherCard = playerCards.get(i);
					yourCard2 = playerCards.get(0);
				}
				if (cards[i] == temp2) {

					if (cards[i] + temp2 > 17) {
						highCards = true;
					}

					if (cards[i] + temp2 <= 10) {
						lowCards = true;
					}
					nbrOftemp2++;
					yourCard = playerCards.get(1);
					otherCard = playerCards.get(i);

				}
			}
		}

		if (nbrOftemp > 0) {
			same = nbrOftemp;
		}

		if (nbrOftemp1 > 1) {
			same = nbrOftemp1;
		}

		if (nbrOftemp2 > 1) {
			if (nbrOftemp1 > 1) {
				same = Integer.parseInt(nbrOftemp1 + "" + nbrOftemp2);
			} else
				same = nbrOftemp2;
		}

		if (same == 1)
			same = 0;
		return same;

	}

	/**
	 * 
	 * @return returns true if cards value >= 17. 'rlyHigh not yet implemented.
	 */
	public boolean checkHighCards() {
		boolean high = false;

		int card1 = cardNbr.get(0);
		int card2 = cardNbr.get(1);

		int total = (card1 + card2);

		if (total >= 17) {
			high = true;
		}
		if (card1 >= 10 && card2 >= 10) {
			rlyhighCards = true;
		}

		return high;
	}

	/**
	 * 
	 * @return returns if the player has a suit or even has a chance for one.
	 */
	public int checkSuit() {
		int C = 0;
		int S = 0;
		int H = 0;
		int D = 0;
		int color = 0;

		for (String x : cardClr) {
			if (x.equals("S")) {
				S++;
			}
			if (x.equals("C")) {
				C++;
			}
			if (x.equals("D")) {
				D++;
			}
			if (x.equals("H")) {
				H++;
			}
		}

		if (S > color) {
			toHighlight.clear();
			color = S;
			theColor = "spader";
			for (int i = 0; i < cardClr.size(); i++) {
				String temp = cardClr.get(i);
				if (S == 5)
					if (temp.equals("S")) {
						toHighlight.add(playerCards.get(i));
					}
			}
		}

		if (H > color) {
			toHighlight.clear();
			color = H;
			theColor = "hjärter";
			for (int i = 0; i < cardClr.size(); i++) {
				String temp = cardClr.get(i);
				if (H == 5)
					if (temp.equals("H")) {
						toHighlight.add(playerCards.get(i));
					}
			}
		}
		if (D > color) {
			toHighlight.clear();
			color = D;
			theColor = "ruter";
			for (int i = 0; i < cardClr.size(); i++) {
				String temp = cardClr.get(i);
				if (D == 5)
					if (temp.equals("D")) {
						toHighlight.add(playerCards.get(i));
					}
			}
		}
		if (C > color) {
			toHighlight.clear();
			color = C;
			theColor = "klöver";
			for (int i = 0; i < cardClr.size(); i++) {
				String temp = cardClr.get(i);
				if (C == 5)
					if (temp.equals("C")) {
						toHighlight.add(playerCards.get(i));
					}
			}
		}

		return color;
	}

	/**
	 * 
	 * @return returns if the player has a straight or even has a chance for one.
	 */
	public int checkStraight() {

		int threshold = 0;

		int[] CorrectOrderArray = new int[cardNbr.size()];

		for (int i = 0; i < cardNbr.size(); i++) { // as referance when getting to highlight.
			CorrectOrderArray[i] = cardNbr.get(i);
		}

		for (int i = 0; i < cardNbr.size(); i++) {
			if (cardNbr.get(i) == 14) {
				cardNbr.add(1);
			}
		}

		int[] CurrentCardsArray = new int[cardNbr.size()];

		for (int i = 0; i < cardNbr.size(); i++) {
			CurrentCardsArray[i] = cardNbr.get(i);
		}

		Arrays.sort(CurrentCardsArray);
		int inStraight = 0;
		int check = 4;

		for (int x = 0; x < CurrentCardsArray.length; x++) {
			int CurrentHighestInStraight = CurrentCardsArray[x] + check;
			int CurrentLowestInStraight = CurrentCardsArray[x];
			String tempStraight = CurrentLowestInStraight + "-" + CurrentHighestInStraight;

			inStraight = 0;

			for (int i = 0; i < CurrentCardsArray.length; i++) {

				if (CurrentCardsArray[i] <= CurrentHighestInStraight
						&& !(CurrentCardsArray[i] < CurrentLowestInStraight)) {

					if (i == 0) { // kollar om 0 är samma som 1.

						inStraight++;
						if (CurrentCardsArray[i] == 1) {
							nbrForStraight.add(String.valueOf(CurrentCardsArray[CurrentCardsArray.length - 1]));
						} else {
							nbrForStraight.add(String.valueOf(CurrentCardsArray[i]));
						}

					}

					if (i >= 1) {
						if (!(CurrentCardsArray[i] == CurrentCardsArray[i - 1])) { // kollar om 1-4 är samma som nån
																					// annan.
							inStraight++;
							nbrForStraight.add(String.valueOf(CurrentCardsArray[i]));
						}
					}

				}
			}

			if (inStraight >= threshold) { // >= så om man får 5 igen men med högre tal så blir det den som visas.

				threshold = inStraight;
				whatStraight = tempStraight;
				nbrForStraight1.clear();
				for (String a : nbrForStraight) {
					nbrForStraight1.add(a);
				}
			}
			nbrForStraight.clear();
		}

		return threshold;
	}

	public ArrayList<String> getToHighlight() {
		for (int y = 0; y < nbrForStraight1.size(); y++) {
			int same = 1;
			for (int i = 0; i < playerCards.size(); i++) {

				String temp = playerCards.get(i);
				String[] tempSplit = temp.split(",");
				if (nbrForStraight1.get(y).equals(tempSplit[0])) {
					if (same == 1) {
						finalHihglight.add(playerCards.get(i));
						same++;
					}
				}
			}
		}
		return finalHihglight;
	}

	public int calcPwrBarLvl() {
		int pwrBar = 1;
		// TURNONE PWRLEVEL
		if (playerCards.size() == 2) {
			pwrBar = pwrBarLvlOnTurnOne();
		}
		// TURNTWO PWRLEVEL
		if (playerCards.size() == 5) {
			pwrBar = pwrBarLvlOnTurnTwo();
		}
		// TURNTHREE PWRLEVEL
		if (playerCards.size() == 6) {
			pwrBar = pwrBarLvlOnTurnThree();
		}
		// TURNFOUR PWRLEVEL
		if (playerCards.size() == 7) {
			pwrBar = pwrBarLvlOnTurnFour();
		}
		return pwrBar;
	}

	public int pwrBarLvlOnTurnOne() {
		int pwrBar = 1;
		if (colorChance == 2) {
			pwrBar = 2;
		}
		if (highCards) {
			pwrBar = 2;
			if (rlyhighCards) {
				pwrBar = 3;
			}
		}
		if (straightChance == 2) {
			pwrBar = 2;
			if (rlyhighCards) {
				pwrBar = 3;
			}
			if (colorChance == 2) {
				pwrBar = 3;
			}
		}
		if (pairsNmore > 0) {
			pwrBar = 4;
		}
		return pwrBar;
	}

	public int pwrBarLvlOnTurnTwo() {
		int pwrBar = 1;

		if (highCards) {
			pwrBar = 1;
			if (rlyhighCards) {
				pwrBar = 2;
			}
		}
		if (colorChance == 3) {
			pwrBar = 2;
		}
		if (straightChance >= 2) {
			pwrBar = 2;
			if (straightChance >= 4) {
				pwrBar = 3;
			}
			if (colorChance == 3) {
				pwrBar = 3;
			}
		}
		if (pairsNmore == 2) {
			pwrBar = 3;
		}
		if (pairsNmore == 22) {
			pwrBar = 4;
		}
		if (pairsNmore == 3) {
			pwrBar = 4;
		}
		if (pairsNmore == 4 || pairsNmore == 24) {
			pwrBar = 4;
		}
		if (pairsNmore == 23 || pairsNmore == 32) {
			pwrBar = 4;
		}
		if (straightChance == 5 || colorChance == 5) {
			pwrBar = 4;
		}

		return pwrBar;
	}

	public int pwrBarLvlOnTurnThree() {
		int pwrBar = 1;

		if (highCards) {
			pwrBar = 1;
			if (rlyhighCards) {
				pwrBar = 2;
			}
		}
		if (colorChance == 4) {
			pwrBar = 2;
		}
		if (straightChance == 4) {
			pwrBar = 2;

			if (colorChance == 4) {
				pwrBar = 3;
			}
		}
		if (pairsNmore == 2) {
			pwrBar = 2;
		}
		if (pairsNmore == 22) {
			pwrBar = 3;
		}
		if (pairsNmore == 3) {
			pwrBar = 4;
		}
		if (pairsNmore == 4 || pairsNmore == 24) {
			pwrBar = 4;
		}
		if (pairsNmore == 23 || pairsNmore == 32) {
			pwrBar = 4;
		}
		if (straightChance == 5 || colorChance == 5) {
			pwrBar = 4;
		}

		return pwrBar;
	}

	public int pwrBarLvlOnTurnFour() {
		int pwrBar = 1;

		if (highCards) {
			pwrBar = 1;
			if (rlyhighCards) {
				pwrBar = 1;
			}
		}
		if (pairsNmore == 2) {
			pwrBar = 2;
		}
		if (pairsNmore == 22) {
			pwrBar = 3;
		}
		if (pairsNmore == 3) {
			pwrBar = 4;
		}
		if (pairsNmore == 4 || pairsNmore == 24) {
			pwrBar = 4;
		}
		if (pairsNmore == 23 || pairsNmore == 32) {
			pwrBar = 4;
		}
		if (straightChance == 5 || colorChance == 5) {
			pwrBar = 4;
		}

		return pwrBar;
	}

	/**
	 * 
	 * @return returns a advice for the player that is current for his or her hand.
	 */
	public String Help() {

		String helper = "Ingenting, tyvärr...\n";
		String[] splitter = yourCard.split(",");
		int intCardNbr = Integer.parseInt(splitter[0]);
		String yourCardInt = "";
		yourCardInt = String.valueOf(intCardNbr);
		String cardOne = String.valueOf(cardNbr.get(0)) + ":or";
		String cardTwo = String.valueOf(cardNbr.get(1)) + ":or";
		boolean hasSomething = false;

		if (cardNbr.get(0) > 10) {
			if (cardNbr.get(0) == 11) {
				cardOne = "Knektar";
			}
			if (cardNbr.get(0) == 12) {
				cardOne = "Damer";
			}

			if (cardNbr.get(0) == 13) {
				cardOne = "Kungar";
			}
			if (cardNbr.get(0) == 14) {
				cardOne = "Ess";
			}
		}

		if (cardNbr.get(1) > 10) {
			if (cardNbr.get(1) == 11) {
				cardTwo = "Knektar";
			}
			if (cardNbr.get(1) == 12) {
				cardTwo = "Damer";
			}

			if (cardNbr.get(1) == 13) {
				cardTwo = "Kungar";
			}
			if (cardNbr.get(1) == 14) {
				cardTwo = "Ess";
			}
		}
		if (intCardNbr < 11) {
			yourCardInt += ":or";
		}
		if (intCardNbr > 10) {
			if (intCardNbr == 11) {
				yourCardInt = "Knektar";
			}
			if (intCardNbr == 12) {
				yourCardInt = "Damer";
			}

			if (intCardNbr == 13) {
				yourCardInt = "Kungar";
			}
			if (intCardNbr == 14) {
				yourCardInt = "Ess";
			}

		}
		// Writing out what advice to give and help for player, starting to check the
		// lowest possible and if the player has better than it,
		// im overwriting it with a better card. starting from high card only and ending
		// on straight flush.

		advice = "CALL:a eller FOLD:a. Varken RAISE eller ALL-IN är rekommenderat.\n";

		if (highCards) {
			helper = "'HIGH CARD'\n";
			advice = "Att starta med ett högt kort är bra. \nCALL:a. Varken FOLD eller ALL-IN är rekommenderat.\n";
		}
		// ONE PAIR
		if (pairsNmore == 2) {
			hasSomething = true;
			helper = "'ONE-PAIR' i " + yourCardInt + "\n";
			if (playerCards.size() >= 2) {
				advice = "CALL:a eller FOLD:a. ALL-IN är inte rekommenderat.\n";
			}

			// writes the active cards to hihglight
			if (straightChance < 5 && colorChance < 5) {
				toHighlight.clear();
				for (int i = 0; i < playerCards.size(); i++) {
					String[] seeIfSame = playerCards.get(i).split(",");
					int temp = Integer.parseInt(seeIfSame[0]);
					if (intCardNbr == temp) {
						toHighlight.add(playerCards.get(i));
					}
				}
			}
		}

		// TWO PAIRS
		if (pairsNmore == 22) {
			hasSomething = true;
			helper = "'TWO PAIRS'  i " + cardOne + " och " + cardTwo + "\n";
			if (highCards) {
				advice = "CALL:a eller RAISE:a ungefär lika mycket som Big Blind. ALL-IN är inte rekommenderat.";
			} else {
				advice = "Det är bättre än ett par, fast det finns mycket bättre. CALL:a eller FOLD:a. ALL-IN är inte rekommenderat.";
			}

			// writes the active cards to hihglight
			if (straightChance < 5 && colorChance < 5) {
				toHighlight.clear();
				for (int i = 0; i < playerCards.size(); i++) {
					int cardIntOne = cardNbr.get(0);
					int cardIntTwo = cardNbr.get(1);

					if (cardIntOne == cardNbr.get(i)) {
						toHighlight.add(playerCards.get(i));
					}
					if (cardIntTwo == cardNbr.get(i)) {
						toHighlight.add(playerCards.get(i));
					}
				}
			}
		}

		// THREE OF A KIND
		if (pairsNmore == 3) {
			hasSomething = true;
			helper = "'THREE OF A KIND' i " + yourCardInt + "\n";
			advice = "'THREE OF A KIND' är en stark hand. CALL:a. FOLD:a är inte rekommenderat. \n";
			// writes the active cards to hihglight
			if (straightChance < 5 && colorChance < 5) {
				toHighlight.clear();
				for (int i = 0; i < playerCards.size(); i++) {
					String[] seeIfSame = playerCards.get(i).split(",");
					int temp = Integer.parseInt(seeIfSame[0]);
					if (intCardNbr == temp) {
						toHighlight.add(playerCards.get(i));
					}
				}
			}
		}

		// STRAIGHT
		if (straightChance == 5) {
			hasSomething = true;
			helper = "En 'STRAIGHT'!!\n";
			advice = "En 'STRAIGHT' är en riktigt bra hand. CALL:a eller RAISE:a trippelt så mycket som Big Blind!\nFOLD:a är inte rekommenderat.";
			toHighlight.clear();
			toHighlight = getToHighlight();

		}

		// FLUSH
		if (colorChance == 5) {
			hasSomething = true;
			helper = "En 'FLUSH' i " + theColor + "!!\n";
			advice = "RAISE:a med 1/3 av det du har!\nFOLD:a är inte rekommenderat.";
			// To HIHGLIGHT IS IN checkSuit Method.
			toHighlight.clear();
			checkSuit();
		}

		// FULL HOUSE
		if (pairsNmore == 23 || pairsNmore == 32) {
			hasSomething = true;
			helper = "'FULL HOUSE' med " + cardOne + " och " + cardTwo + "!!";
			advice = "'FULL HOUSE' är väldigt svår att slå! RAISE:a med ungefär hälften av det du har om du vågar!\nFOLD:a är inte rekommenderat.";
			// writes the active cards to hihglight
			toHighlight.clear();
			for (int i = 0; i < playerCards.size(); i++) {
				int cardIntOne = cardNbr.get(0);
				int cardIntTwo = cardNbr.get(1);

				if (cardIntOne == cardNbr.get(i)) {
					toHighlight.add(playerCards.get(i));
				}
				if (cardIntTwo == cardNbr.get(i)) {
					toHighlight.add(playerCards.get(i));
				}
			}

		}

		// FOUR OF A KIND
		if (pairsNmore == 4 || pairsNmore == 42 || pairsNmore == 24) {
			hasSomething = true;
			helper = "'FOUR OF A KIND' i " + yourCardInt;
			advice = "'FOUR OF A KIND' är en av de bästa händerna. RAISE:a med hälften av det du har om du vågar!\nFOLD:a är inte rekommenderat.";
			// writes the active cards to hihglight
			if (straightChance < 5 && colorChance < 5) {
				toHighlight.clear();
				for (int i = 0; i < playerCards.size(); i++) {
					String[] seeIfSame = playerCards.get(i).split(",");
					int temp = Integer.parseInt(seeIfSame[0]);
					if (intCardNbr == temp) {
						toHighlight.add(playerCards.get(i));
					}
				}
			}
		}

		// STRAIGHT FLUSH
		if (straightChance == 5 && colorChance == 5) { // "i stegen " + whatStraight;
			hasSomething = true;
			helper = "'STRAIGHT FLUSH' i färgen " + theColor + "! "; // ev add what straight it is ex 2-6.
			advice = "'STRAIGHT FLUSH' är den näst bästa handen i spelet. RAISE:a med mer än hälften av det du har,\n eller ALL-IN om du vågar! FOLD:a är inte rekommenderat.";
			// Highlightning happens in checkStraight and checkSuit.
		}

		// STRAIGHTCHANCE TEXT AND COLORCHANCE TEXT
		if (playerCards.size() < 3) {
			if (straightChance == 2) {
				advice += "Du har 2 av 5 kort för att få 'STRAIGHT'.\n";
			}
			if (colorChance == 2) {
				advice += "Du har 2 av 5 kort för att få 'FLUSH' i " + theColor + "\n";
			}
		}
		if (playerCards.size() < 6) {
			if (straightChance == 3) {
				advice += "Du har 3 av 5 kort för att få 'STRAIGHT'\n";
			}
			if (colorChance == 3) {
				advice += "Du har 3 av 5 kort för att få 'FLUSH' i " + theColor + "\n";
			}
		}

		if (playerCards.size() < 7) {
			if (straightChance == 4) {
				advice += "Du har 4 av 5 kort för att få 'STRAIGHT'!\n";
			}
			if (colorChance == 4) {
				advice += "Du har 4 av 5 kort för att få 'FLUSH' i " + theColor + "!\n";
			}
		}
		if (playerCards.size() == 7 && !hasSomething) {
			advice = "Det är sista rundan och du har ingenting. \nFOLD:a är rekommenderat.";
		}

		return helper;

	}

	/**
	 * returns what advice to give the user
	 * 
	 * @return what advice to give the user
	 */
	public String advice() {
		//Unmark "advice = aaa..." if you want to test that the entire piece of advice can be read in game.
//		advice = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//				+ "HEJDÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅÅ"
//				+ "plop";				
		return advice;
	}

	/**
	 * @return what to be highlighed.
	 */
	public ArrayList<String> toHiglight() {
		return toHighlight;

	}

	/**
	 * sets and returns the current handStrength of the users cards.
	 * 
	 * @return sets and returns the current handStrength of the users cards.
	 */
	public int calcHandstrenght() {

		if (pairsNmore == 2) { // Pair
			handStrength = 1;
		}
		if (pairsNmore == 22) { // Two pair
			handStrength = 2;
		}
		if (pairsNmore == 3) { // Three of a kind
			handStrength = 3;
		}
		if (straightChance == 5) { // Straight
			handStrength = 4;
		}
		if (colorChance == 5) { // Flush
			handStrength = 5;
		}
		if (pairsNmore == 23 || pairsNmore == 32) { // Full house
			handStrength = 6;
		}
		if (pairsNmore == 4 || pairsNmore == 42 || pairsNmore == 24) { // Four of a kind
			handStrength = 7;
		}
		if (colorChance == 5 && straightChance == 5) { // Straight flush
			handStrength = 8;
		}

		return handStrength;
	}
}
