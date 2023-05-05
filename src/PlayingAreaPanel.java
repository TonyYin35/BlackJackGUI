import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class PlayingAreaPanel extends JComponent {

	public PlayingAreaPanel(){
		setBounds(50, 50, 400, 400); // set the position (50, 50) and size (400, 400) of the panel
	    
		
		// Create a JLabel with some text
		JLabel label = new JLabel("Hello, world!");
		label.setFont(new Font("Arial", Font.BOLD, 24));
		label.setForeground(Color.WHITE);

		// Set the position and size of the label
		label.setBounds(50, 50, 200, 50);
		
		add(label);
		// setVisible(true);
	}
}
