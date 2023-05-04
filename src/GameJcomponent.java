import java.util.ArrayList;

public class GameJcomponent {

	private ArrayList<Card> dealerHand; // as usual, we have to card arraylists which will serve as hands: one for the
										// dealer and for the player.
	private ArrayList<Card> playerHand;
	private int dealerScore; // we have two integers: one for dealer's score and the other for player's
								// score.
	private int playerScore;
	public boolean faceDown = true; // this boolean value will tell the program if we have the card facedown or
									// faceup.
	public static boolean betMade = false; // this boolean will tell the program if the player entered the bet.
	private int currentBalance; // this integer will store the value for the current Balance of the player.
	public static int currentBet; // this integer will store the value for the current bet.

	public GameJcomponent(ArrayList<Card> dH, ArrayList<Card> pH) { // this will be the constructor for this class which
																	// will accept two hands as a parameter.
		dealerHand = dH; // we initalize the instant fields.
		playerHand = pH;
		dealerScore = 0; // the scores start as 0.
		playerScore = 0;
		currentBalance = 1000; // the balance starts as 1000.
	}
}
