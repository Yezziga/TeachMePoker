package deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * A deck of cards
 * 
 * @author Vedrana Zeba
 */
public class Deck {
  private ArrayList<Card> deck = new ArrayList<Card>();
  private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();


  /**
   * Creates a deck of cards
   */
  public Deck() {
    createDeck();
  }


  /**
   * Shuffles the deck
   */
  public void shuffle() {
    Collections.shuffle(deck);
  }


  /**
   * Returns the reference of the card that's being removed
   * 
   * @return the reference of the card that's being removed
   */
  public Card getCard() {
    return deck.remove(0);
  }


  /**
   * Returns the current size of the deck
   * 
   * @return the current size of the deck
   */
  public int getNumberOfCardsInDeck() {
    return deck.size();
  }


  /**
   * Creates a deck of cards
   */
  public void createDeck() {
    deck = new ArrayList<Card>();

    for (Suit suit : Suit.values()) {
      try {
        for (CardValue card : CardValue.values()) {
          deck.add(new Card(suit, card, new ImageIcon(ImageIO.read(classLoader.getResourceAsStream(
              "images/" + card.getCardValue() + suit.getSuitLetter() + ".png")))));
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
