import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/* Every round we need a new shuffled card 
 * This class is respond to provide a new shuffled card
 */
public class Deck {
	// deck will be default as an ArrayList store a collection of Card objects
	private ArrayList<Card> deck;

	// default constructor
	public Deck() {
		this.deck = new ArrayList<Card>();
		// we will add cards into deck
		// 4 suits
		for (int i = 0; i < 4; i++) {
			// 13 ranks
			for (int j = 0; j < 13; j++) {
				// This is ace, value default is 11
				if (j == 0) {
					deck.add(new Card(i, j, 11));
				}
				// This is for Jack, Queen and king, value 11 12 13;
				else if (j > 9) {
					deck.add(new Card(i, j, 10));
				}
				// This is for j:1~10, which is 2 to 10 base on value
				else {
					deck.add(new Card(i, j, j + 1));
				}
			}
		}
	}
	// Shuffle the cards in the deck
	public void shuffle() {
		Collections.shuffle(deck);
	}
	// draw the following card
    public Card drawCard() {
        return deck.remove(0);
    }
    // draw a specifically index card
    public Card drawCard(int i) {
        return deck.remove(i);
    }
	// get Deck
	public ArrayList<Card> getDeck() {
		return deck;
	}

//	public static void main(String[] args) throws IOException {
//		Deck deck = new Deck();
//		ArrayList<Card> cards = deck.getDeck();
//		int counter = 0;
//		for (Card i : cards) {
//			System.out.println(i.toString());
//			i.printCard();
//			counter++;
//		}
//		deck.shuffle();
//		cards = deck.getDeck();
//		for (Card i : cards) {
//			System.out.println(i.toString());
//			counter++;
//		}
//		System.out.println(counter);
//	}
}
