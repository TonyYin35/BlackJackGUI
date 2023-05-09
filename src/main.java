import javax.swing.JFrame;

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
	public static int frameX = 1200;
	public static int frameY = 800;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		menuSetUp();
	}

	private static void menuSetUp() {
		menuFrame = new MenuJframe();
		menuFrame.setVisible(true);
	}
}
