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
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.sql.*;


public class GameJframe extends BaseFrame {

	private int frameX = main.frameX;
	private int frameY = main.frameY;
	public boolean faceDown;
	public boolean dealerWon;
	public static boolean roundOver;
	public PlayingAreaPanel PlayingAreaPanel;
	public static int playerCardsValue;
	public static int dealerCardsValue;
	ArrayList<Card> dealerCards;
	ArrayList<Card> playerCards;
	Deck deck;
	JPanel buttonsPanel;
	JButton JbuttonHit;
	JButton JbuttonStand;
	JButton JbuttonDouble;
	static Connection connection;

	public GameJframe() {
		// set up the menu
		createMenu();
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
				// import sounds file
				try {
					File soundFile = new File("Wav/buttonClick.wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(soundFile));
					clip.start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				playerCards.add(deck.drawCard());
				// update the card's sum
				playerCardsValue = getSum(playerCards);
				dealerCardsValue = getSum(dealerCards);
				try {
					PlayingAreaPanel.updateHands(dealerCards, playerCards);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				checkCards(playerCards);
			}
		});

		JbuttonStand = new JButton("STAND");
		JbuttonStand.setFont(new Font("Arial", Font.BOLD, 16));
		JbuttonStand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// import sounds file
				try {
					File soundFile = new File("Wav/buttonClick.wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(soundFile));
					clip.start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				// If dealer's card is less than 17, we will let it hit until bigger than 17
				if (getSum(dealerCards) < 17) {
					while (getSum(dealerCards) < 17) {
						dealerCards.add(deck.drawCard());
						// update the card's sum
						playerCardsValue = getSum(playerCards);
						dealerCardsValue = getSum(dealerCards);
						// update cards image
						try {
							PlayingAreaPanel.updateHands(dealerCards, playerCards);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// check if dealer bust or a blackJack
						checkCards(dealerCards);
					}
				}
				// If the round is not over (dealer is not bust or not a blackJack)
				// we continue to compare the dealer's hand with player's hand
				if (!roundOver) {
					// if dealer hand less than playerCards
					if (getSum(dealerCards) < getSum(playerCards)) {
						roundOver = true;
						// update the card's sum
						playerCardsValue = getSum(playerCards);
						dealerCardsValue = getSum(dealerCards);
						main.playerScore++;
						UpdateDB();
						try {
							PlayingAreaPanel.updateHands(dealerCards, playerCards);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try {
							File soundFile = new File("Wav/Win.wav");
							Clip clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(soundFile));
							clip.start();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Player wins with a better cards!");
						rest();
					} else if (getSum(dealerCards) == getSum(playerCards)) {
						roundOver = true;
						// update the card's sum
						playerCardsValue = getSum(playerCards);
						dealerCardsValue = getSum(dealerCards);
						try {
							PlayingAreaPanel.updateHands(dealerCards, playerCards);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try {
							File soundFile = new File("Wav/Tie.wav");
							Clip clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(soundFile));
							clip.start();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "It's a tie!");
						rest();
					} else {
						roundOver = true;
						// update the card's sum
						playerCardsValue = getSum(playerCards);
						dealerCardsValue = getSum(dealerCards);
						main.dealerScore++;
						UpdateDB();
						try {
							PlayingAreaPanel.updateHands(dealerCards, playerCards);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try {
							File soundFile = new File("Wav/Lose.wav");
							Clip clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(soundFile));
							clip.start();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Dealer wins with a better cards!");
						rest();
					}
				}
			}
		});

		JbuttonDouble = new JButton("DRAW TWO CARDS");
		JbuttonDouble.setFont(new Font("Arial", Font.BOLD, 16));
		JbuttonDouble.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// import sounds file
				try {
					File soundFile = new File("Wav/buttonClick.wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(soundFile));
					clip.start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				playerCards.add(deck.drawCard());
				playerCards.add(deck.drawCard());
				// update the card's sum
				playerCardsValue = getSum(playerCards);
				dealerCardsValue = getSum(dealerCards);
				try {
					PlayingAreaPanel.updateHands(dealerCards, playerCards);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				checkCards(playerCards);
				if (getSum(playerCards) < 17 && getSum(dealerCards) < 17) {
					dealerCards.add(deck.drawCard());
					// update the card's sum
					playerCardsValue = getSum(playerCards);
					dealerCardsValue = getSum(dealerCards);
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
	 * This method will not have a "start a new game" choice, player should use reset button to start a new round
	 */
    public void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem exitItem = new JMenuItem("Exit");

        helpItem.addActionListener((e) -> JOptionPane.showMessageDialog(this,
                "The goal of blackjack is to have a hand value of 21 or as close to 21 as possible without going over (busting).\n"
                        + "Each player is dealt two cards face up, while the dealer receives one card face up and one card face down.\n"
                        + "Players can choose to \"hit\" and receive another card, or \"stand\" and keep their current hand value.\n"
                        + "Players can continue to hit until they choose to stand or their hand value exceeds 21.\n"
                        + "Once all players have completed their turns, the dealer reveals their face-down card and hits until their hand value is 17 or higher.\n"
                        + "If the dealer busts, all remaining players win. Otherwise, the dealer compares their hand value to each \n"
                        + "player's hand value and pays out accordingly (usually 1:1 for a win, and nothing for a loss or tie).\n",
                "QUICK&EASY BLACKJACK HELP", JOptionPane.INFORMATION_MESSAGE));

        exitItem.addActionListener((e) -> System.exit(0));
        menu.add(helpItem);
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
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
		playerCardsValue = getSum(playerCards);
		dealerCardsValue = getSum(dealerCards);
		try {
			PlayingAreaPanel.updateHands(dealerCards, playerCards);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Using thread to make GUI generate first, then the JOptionPane
		SwingUtilities.invokeLater(() -> {
			checkCards(dealerCards);
			if (!roundOver) {
				checkCards(playerCards);
			}
		});
	}

	/*
	 * checkCards method will do the checking if there is a bust or blackJack
	 */
	public void checkCards(ArrayList<Card> cards) {
		// check if the round is already over
		if (roundOver) {
			return;
		}
		int cardsSum = getSum(cards);
		// check for blackjack
		boolean isBlackjack = cardsSum == 21 && cards.size() == 2;

		// check for bust
		if (cardsSum > 21) {
			roundOver = true;
			playerCardsValue = getSum(playerCards);
			dealerCardsValue = getSum(dealerCards);
			if (cards.equals(dealerCards)) {
				main.playerScore++;
				UpdateDB();
			} else {
				main.dealerScore++;
				UpdateDB();
			}
			try {
				PlayingAreaPanel.updateHands(dealerCards, playerCards);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (cards.equals(dealerCards)) {
				try {
					File soundFile = new File("Wav/Win.wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(soundFile));
					clip.start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Dealer bust! Player wins!");
			} else {
				try {
					File soundFile = new File("Wav/Lose.wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(soundFile));
					clip.start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Player bust! Dealer wins!");
			}
			rest();
		} else if (isBlackjack) {
			roundOver = true;
			playerCardsValue = getSum(playerCards);
			dealerCardsValue = getSum(dealerCards);
			if (cards.equals(dealerCards)) {
				main.dealerScore++;
				UpdateDB();
			} else {
				main.playerScore++;
				UpdateDB();
			}
			try {
				PlayingAreaPanel.updateHands(dealerCards, playerCards);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (cards.equals(dealerCards)) {
				try {
					File soundFile = new File("Wav/Lose.wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(soundFile));
					clip.start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Dealer is Blackjack! Dealer wins!");
			} else {
				try {
					File soundFile = new File("Wav/Win.wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(soundFile));
					clip.start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Player is Blackjack! Player wins!");
			}
			rest();
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
	
    public static void UpdateDB() {
        // JDBC connection parameters
        String url = "jdbc:sqlite:blackjack.db";

        // SQL statement
        String updateDataQuery = "UPDATE blackjack SET PlayerScore = ?, DealerScore = ? WHERE ID = 1";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(updateDataQuery)) {

            // Set the new values
            statement.setInt(1, main.playerScore);
            statement.setInt(2, main.dealerScore);

            // Doing the update statement
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Data updated successfully.");
            } else {
                System.out.println("No rows were updated.");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
	
	// disable the buttons
	public void buttonOff() {
		JbuttonHit.setEnabled(false);
		JbuttonStand.setEnabled(false);
		JbuttonDouble.setEnabled(false);
	}

	// reset the properties
	public void reset() {
        System.out.println("dealerScore: " + main.dealerScore);
        System.out.println("playerScore: " + main.playerScore);
        UpdateDB();
		dealerCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();
		roundOver = false;
		playerCardsValue = 0;
		dealerCardsValue = 0;
	}

	// rest for 1 second
	public void rest() {
		buttonOff();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
}