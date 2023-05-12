import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	// We record score
	public static int dealerScore;
	public static int playerScore;
	static Connection connection = null;

	public static void main(String[] args) {
		// Set up the database
		DBsetUp();
		// Set up the menu
		menuSetUp();
	}

	// Set up the menu
	private static void menuSetUp() {
		menuFrame = new MenuJframe();
		menuFrame.setVisible(true);
	}
	
	// Set up the database
    public static void DBsetUp() {
        // Check if the database file exists
        File databaseFile = new File("blackjack.db");
        boolean databaseExists = databaseFile.exists();

        // JDBC connection parameters
        String url = "jdbc:sqlite:blackjack.db";

        // SQL statements
        String createTableQuery = "CREATE TABLE IF NOT EXISTS blackjack (ID INT PRIMARY KEY, PlayerScore INT, DealerScore INT)";
        String insertDataQuery = "INSERT INTO blackjack (ID, PlayerScore, DealerScore) VALUES (1, 0, 0)";
        String selectDataQuery = "SELECT PlayerScore, DealerScore FROM blackjack WHERE ID = 1";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // If the database doesn't exist, we will initialize it
            if (!databaseExists) {
                // Create the table
                statement.execute(createTableQuery);
                System.out.println("Table created successfully.");

                // Insert data into the table
                statement.execute(insertDataQuery);
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Database already exists. Connecting to existing database.");
            }

            // Retrieve the scores from the database
            ResultSet resultSet = statement.executeQuery(selectDataQuery);

            // Get the recorded score and set to the current score
            if (resultSet.next()) {
                playerScore = resultSet.getInt("PlayerScore");
                dealerScore = resultSet.getInt("DealerScore");
                System.out.println("Scores retrieved successfully.");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
