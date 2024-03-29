import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PlayingAreaPanel extends JComponent {

	public PlayingAreaPanel() {
		setBounds(0, 0, main.frameX, main.frameY); // set the position (50, 50) and size (400, 400) of the panel
		drawLabels();
		updateLabels();
		timeLabel();
	}

	public void updateHands(ArrayList<Card> dealerHand, ArrayList<Card> playerHand) throws IOException {
		// Remove all existing card labels from the panel
		removeAll();

		// Add the dealer's card labels
		BufferedImage[] dealerImg = getCardImage(dealerHand);
		for (int i = 0; i < dealerImg.length; i++) {
			if (GameJframe.roundOver) {
				JLabel dealerCardLabel = new JLabel(new ImageIcon(dealerImg[i]));
				dealerCardLabel.setBounds(200 + i * 100, 50, 72, 96);
				add(dealerCardLabel);
			} else {
				if (i == 1) {
					BufferedImage backSide = ImageIO.read(new File("Images/backSide.jpeg"));
					Image scaledBackSide = backSide.getScaledInstance(72, 96, Image.SCALE_SMOOTH);
					ImageIcon backSideIcon = new ImageIcon(scaledBackSide);
					JLabel dealerCardLabel = new JLabel(backSideIcon);
					dealerCardLabel.setBounds(200 + i * 100, 50, 72, 96);
					add(dealerCardLabel);
				} else {
					JLabel dealerCardLabel = new JLabel(new ImageIcon(dealerImg[i]));
					dealerCardLabel.setBounds(200 + i * 100, 50, 72, 96);
					add(dealerCardLabel);
				}
			}
		}
		// Add the player's card labels
		BufferedImage[] playerImg = getCardImage(playerHand);
		for (int i = 0; i < playerImg.length; i++) {
			JLabel playerCardLabel = new JLabel(new ImageIcon(playerImg[i]));
			playerCardLabel.setBounds(200 + i * 100, 200, 72, 96);
			add(playerCardLabel);
		}

		// Draw Labels
		drawLabels();
		updateLabels();
		timeLabel();

		// Repaint the panel to show the new card labels
		repaint();
	}

	// we use this method to print cards
	public BufferedImage[] getCardImage(ArrayList<Card> cards) throws IOException {
		BufferedImage[] result = new BufferedImage[cards.size()];
		int width = 950;
		int height = 392;
		BufferedImage cardImg = ImageIO.read(new File("Images/cards.png"));
		BufferedImage[][] deck = new BufferedImage[4][13];
		// BufferedImage cardBack = ImageIO.read(new File("Images/backSide.jpeg"));
		// use double for loop crop the card images to the deck 2d array
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				deck[i][j] = cardImg.getSubimage(j * width / 13, i * height / 4, width / 13, height / 4);
			}
		}
		for (int i = 0; i < cards.size(); i++) {
			int cardOfSuit = cards.get(i).getSuit();
			int cardOfrank = cards.get(i).getRank();
			result[i] = deck[cardOfSuit][cardOfrank];
		}
		return result;
	}

	// draw the label to show which card is for dealer and which for player
	public void drawLabels() {
		// Labels
		JLabel dealerLabel = new JLabel("Dealer's card: ");
		dealerLabel.setFont(new Font("Arial", Font.BOLD, 24));
		dealerLabel.setForeground(Color.WHITE);

		dealerLabel.setBounds(0, 0, 200, 50);

		JLabel playerLabel = new JLabel("Player's card: ");
		playerLabel.setFont(new Font("Arial", Font.BOLD, 24));
		playerLabel.setForeground(Color.WHITE);

		playerLabel.setBounds(0, 0, 200, 350);

		add(dealerLabel);
		add(playerLabel);
	}

	// this method will update player and dealer score and hand value label
	public void updateLabels() {
		if (GameJframe.roundOver) {
			JLabel dealerLabelScore = new JLabel("Dealer's card value is : " + GameJframe.dealerCardsValue);
			dealerLabelScore.setFont(new Font("Arial", Font.BOLD, 12));
			dealerLabelScore.setForeground(Color.WHITE);

			JLabel playerLabelScore = new JLabel("Player's card value is : " + GameJframe.playerCardsValue);
			playerLabelScore.setFont(new Font("Arial", Font.BOLD, 12));
			playerLabelScore.setForeground(Color.WHITE);

			dealerLabelScore.setBounds(0, 0, 200, 150);
			playerLabelScore.setBounds(0, 0, 200, 450);
			add(dealerLabelScore);
			add(playerLabelScore);
		} else {
			JLabel dealerLabelScore = new JLabel("Dealer's card value is : ??? ");
			dealerLabelScore.setFont(new Font("Arial", Font.BOLD, 12));
			dealerLabelScore.setForeground(Color.WHITE);

			JLabel playerLabelScore = new JLabel("Player's card value is : " + GameJframe.playerCardsValue);
			playerLabelScore.setFont(new Font("Arial", Font.BOLD, 12));
			playerLabelScore.setForeground(Color.WHITE);

			dealerLabelScore.setBounds(0, 0, 200, 150);
			playerLabelScore.setBounds(0, 0, 200, 450);
			add(dealerLabelScore);
			add(playerLabelScore);
		}
		// update total wins score label
		JLabel dealerScoreLabel = new JLabel("Round won by the dealer: " + main.dealerScore);
		dealerScoreLabel.setFont(new Font("Arial", Font.BOLD, 12));
		dealerScoreLabel.setForeground(Color.WHITE);

		JLabel playerScoreLabel = new JLabel("Round won by the player: " + main.playerScore);
		playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 12));
		playerScoreLabel.setForeground(Color.WHITE);

		dealerScoreLabel.setBounds(0, 0, 200, 100);
		playerScoreLabel.setBounds(0, 0, 200, 400);
		add(dealerScoreLabel);
		add(playerScoreLabel);
	}
	
	// this label will show the current time
    public void timeLabel() {
    	JLabel timeLabel = new JLabel(getCurrentTime());
        timeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBounds(1030, 460, 400, 450);
        add(timeLabel);

        Timer timer = new Timer(1000, e -> {
            timeLabel.setText(getCurrentTime());
        });
        timer.start();

        setVisible(true);
    }

    // get current time
    private String getCurrentTime() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(now);
    }
}
