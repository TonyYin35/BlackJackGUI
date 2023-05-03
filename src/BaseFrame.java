import javax.swing.*;

public class BaseFrame extends JFrame {
    
	GameJframe gameFrame;
	
    public BaseFrame() {
        createMenu();
    }
    
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem startItem = new JMenuItem("Start a new game");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem exitItem = new JMenuItem("Exit");
        startItem.addActionListener((e) -> startGame());
        helpItem.addActionListener((e) -> JOptionPane.showMessageDialog(this, "The goal of blackjack is to have a hand value of 21 or as close to 21 as possible without going over (busting).\n"
        		+ "Each player is dealt two cards face up, while the dealer receives one card face up and one card face down.\n"
        		+ "Players can choose to \"hit\" and receive another card, or \"stand\" and keep their current hand value.\n"
        		+ "Players can continue to hit until they choose to stand or their hand value exceeds 21.\n"
        		+ "Once all players have completed their turns, the dealer reveals their face-down card and hits until their hand value is 17 or higher.\n"
        		+ "If the dealer busts, all remaining players win. Otherwise, the dealer compares their hand value to each \n"
        		+ "player's hand value and pays out accordingly (usually 1:1 for a win, and nothing for a loss or tie).\n"
        		,"QUICK&EASY BLACKJACK HELP",
        JOptionPane.INFORMATION_MESSAGE));
        
        exitItem.addActionListener((e) -> System.exit(0));
        menu.add(startItem);
        menu.add(helpItem);
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    
    public static void startGame() {
        // Create the game frame
        GameJframe gameFrame = new GameJframe();
        
        // Make it visible and dispose the menu frame
        gameFrame.setVisible(true);
        main.menuFrame.dispose();
//        main.gameRefreshThread.start();
//        main.gameCheckThread.start();
    }
    
}
