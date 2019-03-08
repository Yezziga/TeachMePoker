package aiClass;

import deck.Card;
import deck.CardValue;
import deck.Deck;
import deck.Suit;
/**
 * Class that is for testing of the AI.
 * And also been used for doing the WhiteBox testing of the ai-player.
 * @author Max Frennessen 17-05-25 (version 1.0) + Elina Kock, Marcel Laska 190305 (version 2.0)
 * @version 2.0
 */
public class TestAi {
	Deck deck;	//The deck used
	Ai ai;		//An ai-player
	private Card card1;	//The ai-player's first card
	private Card card2;	//The ai-player's second card
	private Card[] flop = new Card[3];
	private Card cardTurn;
	private Card cardRiver;
	
	/**
	 * Mainly used to test if the AI can make a decision each turn. 
	 * Simulates an AI-player playing a round of Texas Hold'em (pre-flop, flop, turn, and river included).
	 * The method prints which cards the AI has, which cards are on the table each turn, and what the 
	 * AI decides to do. 
	 */
	public void testTurns() {
		deck = new Deck();
		ai = new Ai(1000, "Pelle");
		deck.shuffle();
		card1 = deck.getCard();
		card2 = deck.getCard();

		ai.setStartingHand(card1, card2);
		System.out.println("AI cards: " + card1.getCardValue() + "-" + card1.getCardSuit() + ", " + card2.getCardValue() + "-" + card2.getCardSuit());


		System.out.println("PRE-FLOP");
		ai.makeDecision(16);
		System.out.println();

		System.out.println("FLOP");
		for(int i = 0; i<3; i++) {
			flop[i] = deck.getCard();
		}
		System.out.println("On table: " + flop[0].getCardValue() + "-" + flop[0].getCardSuit() + ", " + flop[1].getCardValue() + "-" 
				+ flop[1].getCardSuit() + ", " + flop[2].getCardValue() + "-" + flop[2].getCardSuit());
		ai.makeDecision(200, flop);
		System.out.println();

		System.out.println("TURN");
		cardTurn = deck.getCard();
		System.out.println("On table: " + flop[0].getCardValue() + "-" + flop[0].getCardSuit() + ", " + flop[1].getCardValue() + "-" 
				+ flop[1].getCardSuit() + ", " + flop[2].getCardValue() + "-" + flop[2].getCardSuit() 
				+ ", " + cardTurn.getCardValue() + "-" + cardTurn.getCardSuit());
		ai.makeDecision(500, cardTurn);
		System.out.println();

		System.out.println("RIVER");
		cardRiver = deck.getCard();
		System.out.println("On table: " + flop[0].getCardValue() + "-" + flop[0].getCardSuit() + ", " + flop[1].getCardValue() + "-" 
				+ flop[1].getCardSuit() + ", " + flop[2].getCardValue() + "-" + flop[2].getCardSuit() 
				+ ", " + cardTurn.getCardValue() + "-" + cardTurn.getCardSuit() + ", " + cardRiver.getCardValue() 
				+ "-" + cardRiver.getCardSuit());
		ai.makeDecision(500, cardRiver);
		System.out.println();
	}

	/**
	 * Used to test what decision an AI-player makes based on a specified hand. 
	 * TF5, TF6, ..., TF13 are test cases 5 to 13 included.
	 */
	public void specificAiDecisions() {
		//TF5
		//Hand: high card - low
		System.out.println("TF5");
		ai = new Ai(1000, "Pelle");
		ai.setStartingHand(new Card(Suit.HEARTS, CardValue.TWO, null), 
				new Card(Suit.SPADES, CardValue.FOUR, null));
		ai.makeDecision(50);
		System.out.println();
		
		//TF6
		//Hand: high card - high
		System.out.println("TF6");
		ai = new Ai(1000, "Pelle");
		ai.setStartingHand(new Card(Suit.HEARTS, CardValue.ACE, null), 
				new Card(Suit.SPADES, CardValue.SIX, null));
		ai.makeDecision(50);
		System.out.println();
		
		//TF7
		//Hand: pair
		System.out.println("TF7");
		ai = new Ai(1000, "Pelle");
		ai.setStartingHand(new Card(Suit.HEARTS, CardValue.SIX, null), 
				new Card(Suit.SPADES, CardValue.SIX, null));
		ai.makeDecision(50);
		System.out.println();
		
		//TF8 (ska ge call - ger raise)
		//Hand: two pair
		System.out.println("TF8");
		ai = new Ai(1000, "Pelle");
		ai.setStartingHand(new Card(Suit.HEARTS, CardValue.TWO, null), 
				new Card(Suit.SPADES, CardValue.FOUR, null));
		flop[0] = new Card(Suit.SPADES, CardValue.TWO, null);
		flop[1] = new Card(Suit.CLUBS, CardValue.FOUR, null);
		flop[2] = new Card(Suit.DIAMONDS, CardValue.THREE, null);
		ai.makeDecision(50, flop);
		System.out.println();
		
		//TF9
		//Hand: Three of a kind
		System.out.println("TF9");
		ai = new Ai(1000, "Pelle");
		ai.setStartingHand(new Card(Suit.HEARTS, CardValue.TWO, null), 
				new Card(Suit.SPADES, CardValue.FOUR, null));
		flop[0] = new Card(Suit.DIAMONDS, CardValue.TWO, null);
		flop[1] = new Card(Suit.CLUBS, CardValue.FOUR, null);
		flop[2] = new Card(Suit.DIAMONDS, CardValue.THREE, null);
		ai.makeDecision(50, flop);
		System.out.println();
		
		//TF10
		//Hand: Straight flush
		System.out.println("TF10");
		ai = new Ai(1000, "Pelle");
		ai.setStartingHand(new Card(Suit.HEARTS, CardValue.TWO, null), 
				new Card(Suit.HEARTS, CardValue.THREE, null));
		flop[0] = new Card(Suit.HEARTS, CardValue.FOUR, null);
		flop[1] = new Card(Suit.HEARTS, CardValue.FIVE, null);
		flop[2] = new Card(Suit.HEARTS, CardValue.SIX, null);
		ai.makeDecision(50, flop);
		System.out.println();
		
		//TF11
		//Hand: Flush
		System.out.println("TF11");
		ai = new Ai(1000, "Pelle");
		ai.setStartingHand(new Card(Suit.HEARTS, CardValue.TWO, null), 
				new Card(Suit.HEARTS, CardValue.FOUR, null));
		flop[0] = new Card(Suit.HEARTS, CardValue.SIX, null);
		flop[1] = new Card(Suit.HEARTS, CardValue.EIGHT, null);
		flop[2] = new Card(Suit.HEARTS, CardValue.TEN, null);
		ai.makeDecision(50, flop);
		System.out.println();
		
		//TF12
		//Hand: Full house
		System.out.println("TF12");
		ai = new Ai(1000, "Pelle");
		ai.setStartingHand(new Card(Suit.HEARTS, CardValue.TWO, null), 
				new Card(Suit.SPADES, CardValue.TWO, null));
		flop[0] = new Card(Suit.CLUBS, CardValue.TWO, null);
		flop[1] = new Card(Suit.HEARTS, CardValue.FOUR, null);
		flop[2] = new Card(Suit.SPADES, CardValue.FOUR, null);
		ai.makeDecision(50, flop);
		System.out.println();
		
		//TF13
		//Hand: Four of a kind
		System.out.println("TF13");
		ai = new Ai(1000, "Pelle");
		ai.setStartingHand(new Card(Suit.HEARTS, CardValue.TWO, null), 
				new Card(Suit.SPADES, CardValue.FOUR, null));
		flop[0] = new Card(Suit.CLUBS, CardValue.TWO, null);
		flop[1] = new Card(Suit.DIAMONDS, CardValue.TWO, null);
		flop[2] = new Card(Suit.HEARTS, CardValue.FOUR, null);
		ai.makeDecision(50, flop);
		System.out.println();
	}
	
	public static void main(String[] args) {
		TestAi test = new TestAi();
//		test.testTurns();
		test.specificAiDecisions();
		
	}
}
