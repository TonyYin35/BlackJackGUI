import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class CardsPanel extends JPanel {

	private ArrayList<Card> dealerHand;
	private ArrayList<Card> playerHand;
	private boolean faceDown;

	public CardsPanel() {
		dealerHand = new ArrayList<>();
		playerHand = new ArrayList<>();
		faceDown = true;
		setPreferredSize(new Dimension(800, 600));
	}

	public void setDealerHand(ArrayList<Card> hand) {
		dealerHand = hand;
	}

	public void setPlayerHand(ArrayList<Card> hand) {
		playerHand = hand;
	}

	public void setFaceDown(boolean faceDown) {
		this.faceDown = faceDown;
	}

	public void drawCards() {
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		try {
			for (int i = 0; i < dealerHand.size(); i++) {
				if (i == 0) {
					if (faceDown) {
						dealerHand.get(i).printCard(g2, true, true, i);
					} else {
						dealerHand.get(i).printCard(g2, true, false, i);
					}
				} else {
					dealerHand.get(i).printCard(g2, true, false, i);
				}
			}
		} catch (IOException e) {
		}

		try {
			for (int i = 0; i < playerHand.size(); i++) {
				playerHand.get(i).printCard(g2, false, false, i);
			}
		} catch (IOException e) {
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			for (int i = 0; i < dealerHand.size(); i++) {
				if (i == 0 && faceDown) {
					dealerHand.get(i).printCard(g2, true, true, i);
				} else {
					dealerHand.get(i).printCard(g2, true, false, i);
				}
			}
		} catch (IOException e) {
		}

		try {
			for (int i = 0; i < playerHand.size(); i++) {
				playerHand.get(i).printCard(g2, false, false, i);
			}
		} catch (IOException e) {
		}
	}
}
