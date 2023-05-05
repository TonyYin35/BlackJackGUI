import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameJframe extends BaseFrame {

	private int frameX = 1200;
	private int frameY = 800;
	ArrayList<Card> dealerCards;
	ArrayList<Card> playerCards;
	public boolean faceDown;
	public boolean dealerWon;
	public volatile boolean roundOver;
	public PlayingAreaPanel PlayingAreaPanel;
	Deck deck;

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
		dealerCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();
		faceDown = true;
		dealerWon = true;
		roundOver = false;

		/*
		 * create a JPanel for the background image
		 * 
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
		JButton JbuttonHit = new JButton("HIT");
		JbuttonHit.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
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

		JButton JbuttonStand = new JButton("STAND");
		JbuttonStand.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
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
					faceDown = false;
					dealerWon = false;
					JOptionPane.showMessageDialog(null, "Dealer busts! Player wins!");
					rest();
					roundOver = true;
				} else if (getSum(dealerCards) >= getSum(playerCards) && getSum(dealerCards) <= 21) {
					if (getSum(playerCards) > getSum(dealerCards)) {
						faceDown = false;
						dealerWon = false;
						JOptionPane.showMessageDialog(null, "Player wins with a better cards!");
						rest();
						roundOver = true;
					} else {
						faceDown = false;
						JOptionPane.showMessageDialog(null, "Dealer wins with a better cards!");
						rest();
						roundOver = true;
					}
				}
			}
		});

		JButton JbuttonDouble = new JButton("DOUBLE");
		JbuttonDouble.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
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
		JPanel buttonsPanel = new JPanel();
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
				faceDown = false;
				dealerWon = false;
				JOptionPane.showMessageDialog(null, "Dealer bust! Player wins!");
				rest();
				roundOver = true;
			}
			// if it is blackJack
			if (getSum(cards) == 21) {
				faceDown = false;
				JOptionPane.showMessageDialog(null, "Dealer is BlackJack! Dealer wins!");
				rest();
				roundOver = true;
			}
		}
		// if it is player's cards
		if (cards.equals(playerCards)) {
			// if busted
			if (getSum(cards) > 21) {
				faceDown = false;
				JOptionPane.showMessageDialog(null, "Player bust! Dealer wins!");
				rest();
				roundOver = true;
			}
			// if it is blackJack
			if (getSum(cards) == 21) {
				faceDown = false;
				dealerWon = false;
				JOptionPane.showMessageDialog(null, "Player is BlackJack! Player wins!");
				rest();
				roundOver = true;
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

	public static void rest() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}
}
