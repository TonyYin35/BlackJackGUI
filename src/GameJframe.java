import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameJframe extends BaseFrame {

	private int frameX = 1200;
	private int frameY = 800;
	ArrayList<Card> dealerCards;
	ArrayList<Card> playerCards;
	public boolean faceDown;
	public boolean dealerWon;
	public volatile static boolean roundOver;
	public PlayingAreaPanel PlayingAreaPanel;
	public static int playerCardsValue;
	public static int dealerCardsValue;
	Deck deck;
	JPanel buttonsPanel;
	JButton JbuttonHit;
	JButton JbuttonStand;
	JButton JbuttonDouble;

	public GameJframe() {
		// Jframe stuff
		setTitle("Blackjack");
		setSize(frameX, frameY);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// set up the deck
		deck = new Deck();
		deck.shuffle();

		// set up
		reset();

		/*
		 * create a JPanel for the background image
		 */
		JPanel backgroundPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					Image image = ImageIO.read(new File("Images/gamepaper1.png"));
					g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		backgroundPanel.setLayout(null);

		// add the background panel to the JFrame
		add(backgroundPanel);

		// add the panel that display the cards
		PlayingAreaPanel = new PlayingAreaPanel();
		backgroundPanel.add(PlayingAreaPanel);

		/*
		 * Below are the JButton, we have three buttons
		 */
		JButton JbuttonReset = new JButton("Reset");
		JbuttonReset.setFont(new Font("Arial", Font.BOLD, 16));
		JbuttonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame = new GameJframe();
				gameFrame.setVisible(true);
				// dispose of the old frame
				((JFrame) SwingUtilities.getWindowAncestor(JbuttonReset)).dispose();
				reset();
			}
		});

		JbuttonHit = new JButton("HIT");
		JbuttonHit.setFont(new Font("Arial", Font.BOLD, 16));
		JbuttonHit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerCards.add(deck.drawCard());
				try {
					PlayingAreaPanel.updateHands(dealerCards, playerCards);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				checkCards(playerCards);
				if (getSum(playerCards) < 17 && getSum(dealerCards) < 17) {
					dealerCards.add(deck.drawCard());
					try {
						PlayingAreaPanel.updateHands(dealerCards, playerCards);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					checkCards(dealerCards);
				}
			}
		});

		JbuttonStand = new JButton("STAND");
		JbuttonStand.setFont(new Font("Arial", Font.BOLD, 16));
		JbuttonStand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				while (getSum(dealerCards) < 17) {
					dealerCards.add(deck.drawCard());
					try {
						PlayingAreaPanel.updateHands(dealerCards, playerCards);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					checkCards(dealerCards);
				}
				if (getSum(dealerCards) > 21) {
					roundOver = true;
					// update cards
					playerCardsValue = getSum(playerCards);
					dealerCardsValue = getSum(dealerCards);
					PlayingAreaPanel.updateLabels();
					JOptionPane.showMessageDialog(null, "Dealer busts! Player wins!");
					rest();
				} else if (getSum(dealerCards) >= getSum(playerCards) && getSum(dealerCards) <= 21) {
					if (getSum(playerCards) > getSum(dealerCards)) {
						roundOver = true;
						// update cards
						playerCardsValue = getSum(playerCards);
						dealerCardsValue = getSum(dealerCards);
						try {
							PlayingAreaPanel.updateHands(dealerCards, playerCards);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						PlayingAreaPanel.updateLabels();
						JOptionPane.showMessageDialog(null, "Player wins with a better cards!");
						rest();
					} else if (getSum(playerCards) == getSum(dealerCards)) {
						roundOver = true;
						// update cards
						playerCardsValue = getSum(playerCards);
						dealerCardsValue = getSum(dealerCards);
						PlayingAreaPanel.updateLabels();
						try {
							PlayingAreaPanel.updateHands(dealerCards, playerCards);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "It's a tie!");
						rest();
					} else {
						roundOver = true;
						// update cards
						playerCardsValue = getSum(playerCards);
						dealerCardsValue = getSum(dealerCards);
						PlayingAreaPanel.updateLabels();
						try {
							PlayingAreaPanel.updateHands(dealerCards, playerCards);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Dealer wins with a better cards!");
						rest();
					}
				}
			}
		});

		JbuttonDouble = new JButton("DOUBLE");
		JbuttonDouble.setFont(new Font("Arial", Font.BOLD, 16));
		JbuttonDouble.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerCards.add(deck.drawCard());
				playerCards.add(deck.drawCard());
				try {
					PlayingAreaPanel.updateHands(dealerCards, playerCards);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				checkCards(playerCards);
				if (getSum(playerCards) < 17 && getSum(dealerCards) < 17) {
					dealerCards.add(deck.drawCard());
					try {
						PlayingAreaPanel.updateHands(dealerCards, playerCards);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					checkCards(dealerCards);
				}
			}
		});

		// Add the buttons to your JFrame
		buttonsPanel = new JPanel();
		buttonsPanel.add(JbuttonReset);
		buttonsPanel.add(JbuttonHit);
		buttonsPanel.add(JbuttonStand);
		buttonsPanel.add(JbuttonDouble);
		add(buttonsPanel, BorderLayout.SOUTH);

		// game starts
		gameStart();

		setVisible(true);
	}

	/*
	 * Initial the game start, the game should start with both player and dealer
	 * have two cards
	 */
	public void gameStart() {
		// dealer get two cards
		for (int i = 0; i < 2; i++) {
			dealerCards.add(deck.drawCard(i));
		}
		// player get two cards
		for (int i = 2; i < 4; i++) {
			playerCards.add(deck.drawCard(i));
		}

		try {
			PlayingAreaPanel.updateHands(dealerCards, playerCards);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checkCards(dealerCards);
		checkCards(playerCards);
	}

	public void checkCards(ArrayList<Card> cards) {

		// if it is dealer's cards
		if (cards.equals(dealerCards)) {
			// if busted
			if (getSum(cards) > 21) {
				roundOver = true;
				// update the card's sum
				dealerCardsValue = getSum(cards);
				PlayingAreaPanel.updateLabels();
				try {
					PlayingAreaPanel.updateHands(dealerCards, playerCards);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Dealer bust! Player wins!");
				rest();
			}
			// if it is blackJack
			if (getSum(cards) == 21) {
				roundOver = true;
				// update the card's sum
				dealerCardsValue = getSum(cards);
				PlayingAreaPanel.updateLabels();
				try {
					PlayingAreaPanel.updateHands(dealerCards, playerCards);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Dealer is BlackJack! Dealer wins!");
				rest();
			}
		}
		// if it is player's cards
		if (cards.equals(playerCards)) {
			// if busted
			if (getSum(cards) > 21) {
				roundOver = true;
				// update the card's sum
				playerCardsValue = getSum(cards);
				PlayingAreaPanel.updateLabels();
				try {
					PlayingAreaPanel.updateHands(dealerCards, playerCards);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Player bust! Dealer wins!");
				rest();
			}
			// if it is blackJack
			if (getSum(cards) == 21) {
				roundOver = true;
				// update the card's sum
				playerCardsValue = getSum(cards);
				PlayingAreaPanel.updateLabels();
				try {
					PlayingAreaPanel.updateHands(dealerCards, playerCards);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Player is BlackJack! Player wins!");
				rest();
			}
		}
	}

	public static int getSum(ArrayList<Card> cards) {
		int sum = 0;
		int numAces = 0;
		for (Card card : cards) {
			int value = card.getValue();
			sum += value;
			if (value == 11) {
				numAces++;
			}
		}
		// If the sum is greater than 21 and there are aces in the hand,
		// convert the value of some aces from 11 to 1 until the sum is
		// less than or equal to 21 or there are no more aces.
		while (sum > 21 && numAces > 0) {
			sum -= 10;
			numAces--;
		}
		return sum;
	}

	public void buttonOff() {
		JbuttonHit.setEnabled(false);
		JbuttonStand.setEnabled(false);
		JbuttonDouble.setEnabled(false);
	}

	public void reset() {
		dealerCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();
		roundOver = false;
		playerCardsValue = 0;
		dealerCardsValue = 0;
	}

	public void rest() {
		buttonOff();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}
}
