import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameJframe extends BaseFrame {

	private int frameX = 1200;
	private int frameY = 800;
	ArrayList<Card> dealerHand;
	ArrayList<Card> playerHand;
	public boolean faceDown;
	public boolean dealerWon;
	public volatile boolean roundOver;
	Deck deck; // we have our deck.
	GameJcomponent atmosphereComponent;
	GameJcomponent cardComponent;

	public GameJframe() {
		deck = new Deck();
	    deck.shuffle(); //we randomize the deck.
	    dealerHand = new ArrayList<Card>();
	    playerHand = new ArrayList<Card>();
	    // atmosphereComponent = new GameJcomponent(dealerHand, playerHand);
	    faceDown = true;
	    dealerWon = true;
	    roundOver = false;
	    
	    JButton JbuttonHit;
	    JButton JbuttonStand;
	    JButton JbuttonDouble;
	    
		// Jframe stuff
		setTitle("Menu");
		setSize(frameX, frameY);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		// Jbuttons
		JbuttonHit = new JButton("HIT");
		JbuttonHit.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		JbuttonStand = new JButton("STAND");
		JbuttonStand.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		JbuttonDouble = new JButton("DOUBLE");
		JbuttonDouble.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		
		// add Jbuttons
	    add(JbuttonHit);
	    add(JbuttonStand);
	    add(JbuttonDouble);
	    
//		// Load the background image
//		try {
//			Image image = ImageIO.read(new File("Images/gamepaper.jpg"));
//			Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
//			JLabel background = new JLabel(new ImageIcon(scaledImage));
//			setContentPane(background);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		// create a JPanel for the background image
		JPanel backgroundPanel = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        try {
		            Image image = ImageIO.read(new File("Images/gamepaper.jpg"));
		            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		};
		
		backgroundPanel.setLayout(null);

		// add the JButtons to the background panel
		backgroundPanel.add(JbuttonHit);
		backgroundPanel.add(JbuttonStand);
		backgroundPanel.add(JbuttonDouble);

		// set the bounds of the JButtons
		JbuttonHit.setBounds(390, 550, 100, 50);
		JbuttonStand.setBounds(520, 550, 100, 50);
		JbuttonDouble.setBounds(650, 550, 100, 50);

		// add the background panel to the JFrame
		add(backgroundPanel);
		
		

		// this.add();
		setVisible(true);
	}
}
