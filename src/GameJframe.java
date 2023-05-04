import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
	Deck deck;
	private JPanel contentPane;
	private JPanel gamePanel;
	private Image backgroundImage;

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

		JButton JbuttonHit;
		JButton JbuttonStand;
		JButton JbuttonDouble;

		// Jbuttons
		JbuttonHit = new JButton("HIT");
		JbuttonHit.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		JbuttonStand = new JButton("STAND");
		JbuttonStand.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		JbuttonDouble = new JButton("DOUBLE");
		JbuttonDouble.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

		// create a JPanel for the background image
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

		// set the bounds of the JButtons
		JbuttonHit.setBounds(390, 550, 100, 50);
		JbuttonStand.setBounds(520, 550, 100, 50);
		JbuttonDouble.setBounds(650, 550, 100, 50);

		// add the JButtons to the background panel
		backgroundPanel.add(JbuttonHit);
		backgroundPanel.add(JbuttonStand);
		backgroundPanel.add(JbuttonDouble);

		// game starts
		gameStart();

		JbuttonHit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerCards.add(deck.drawCard());
				checkCards(playerCards);
				if (getSum(playerCards) < 17 && getSum(dealerCards) < 17) {
					dealerCards.add(deck.drawCard());
					checkCards(dealerCards);
				}
			}
		});

		JbuttonStand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				while (getSum(dealerCards) < 17) {
					dealerCards.add(deck.drawCard());
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

		JbuttonDouble.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerCards.add(deck.drawCard());
				playerCards.add(deck.drawCard());
				checkCards(playerCards);
				if (getSum(playerCards) < 17 && getSum(dealerCards) < 17) {
					dealerCards.add(deck.drawCard());
					checkCards(dealerCards);
				}
			}
		});
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
	}

	public void gameStart() {
		// dealer get two cards
		for (int i = 0; i < 2; i++) {
			dealerCards.add(deck.drawCard(i));
		}
		// player get two cards
		for (int i = 2; i < 4; i++) {
			playerCards.add(deck.drawCard(i));
		}

//	    cardComponent = new GameComponent(dealerCards, playerCards);
//	    cardComponent.setBounds(0, 0, 1130, 665);
//	    add(cardComponent);
//	    setVisible(true);

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
