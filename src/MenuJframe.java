import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuJframe extends BaseFrame{
	
	private int frameX = 1200;
	private int frameY = 800;
	
	public MenuJframe() {
        setTitle("Menu");
        setSize(frameX, frameY);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Load the background image
        try {
            Image image = ImageIO.read(new File("Images/background.jpg"));
            Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            JLabel background = new JLabel(new ImageIcon(scaledImage));
            setContentPane(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Create a JPanel to hold the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Create a button and add it to the panel
        JButton button = new JButton("Click me!");
        button.setPreferredSize(new Dimension(100, 30));
        button.setOpaque(true);
        button.setBackground(Color.RED); // set to a different color to see if it appears
        buttonPanel.add(button);

        // Add the button panel to the top of the frame
        add(buttonPanel, BorderLayout.NORTH);

        // Create and add a welcome label with white text
        JLabel welcomeLabel = new JLabel("Welcome to Blackjack!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(frameX/3, frameY/4, frameX/3, frameY/16); // 400, 200, 400, 50
        JLabel noticeLabel = new JLabel("Click menu on the left top to start!");
        noticeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        noticeLabel.setForeground(Color.WHITE);
        noticeLabel.setBounds(frameX/3, frameY/3, frameX/3, frameY/16); // 400, 200, 400, 50
        
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noticeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);
        add(noticeLabel, BorderLayout.CENTER);

        setVisible(true);
    }
}