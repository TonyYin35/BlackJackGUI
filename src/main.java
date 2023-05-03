import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*
 * This is main class
 * It will perform the GUI and combine all components
 */
public class main {
	// We have two Jframes
	// one is the first time open menu frame
	// one is the game frame
	public static JFrame menuFrame = new JFrame();
	public static JFrame gameFrame = new JFrame();
	// We record score
	private static int dealerScore = 0;
	private static int playerScore = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		menuSetUp();
	}

	private static void menuSetUp() {
		menuFrame = new MenuJframe();
		menuFrame.setVisible(true);
	}
	
}
