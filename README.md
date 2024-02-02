# BlackJackGUI

# Preview
<img width="1312" alt="Screen Shot 2024-02-02 at 18 05 21" src="https://github.com/TonyYin35/BlackJackGUI/assets/62121652/9ecfb678-c5e2-4998-bae3-dda00b850cb9">
<img width="1312" alt="Screen Shot 2024-02-02 at 18 05 29" src="https://github.com/TonyYin35/BlackJackGUI/assets/62121652/623a0098-6bc0-4b82-a9a3-3134d8c63c90">
<img width="1200" alt="Screen Shot 2024-02-02 at 18 06 05" src="https://github.com/TonyYin35/BlackJackGUI/assets/62121652/0d438aa7-0a46-49d0-92ec-b658dbfd2bd3">

## Introduction
**Welcome to BlackJackGUI**, an engaging digital rendition of the classic casino card game, Blackjack. This Java-based game combines the interactive elements of a graphical user interface (GUI) with the robust back-end capabilities of Java Database Connectivity (JDBC) to provide a seamless and entertaining experience for players. Developed by Tony Yin, this project not only allows you to enjoy the game but also keeps track of your scores locally, adding a competitive edge to your gaming experience.

## Features
- **Graphical User Interface:** Utilizing Java Swing, BlackJackGUI presents a user-friendly and interactive playing surface that's easy to navigate.
- **Local Score Storage:** Through JDBC, your scores are stored locally, enabling you to keep track of your performance over time.
- **Referenced Card Graphics:** Aesthetic and visually appealing card graphics adapted from an open-source project to enhance the gaming experience.

## Getting Started

### Prerequisites
To run this game, you'll need:
- Java Runtime Environment (JRE) or Java Development Kit (JDK) installed on your machine.
- Access to a local database server (if local scorekeeping via JDBC is set up to require this).

### Installation
1. Clone the repository to your local machine:
2. Navigate to the cloned repository.
3. Compile the Java files (if not using an IDE that does this automatically):
4. Run the application:


## How to Play Blackjack

Blackjack is a card game where the goal is to beat the dealer's hand without going over 21. Here's a quick rundown:

1. Each player starts with two cards, one of the dealer's cards is hidden until the end.
2. "Hit" to ask for another card. If you go over 21, you "bust" and lose the round.
3. "Stand" if you are satisfied with your cards.
4. If you are dealt 21 from the start (Ace & 10), you got a blackjack.
5. Dealer will reveal their hidden card and must hit until their cards total 17 or higher.
6. Scoring:
- If you get a higher score than the dealer without busting, you win.
- If the dealer busts and you do not, you win.
- If you both score the same, it's a "push," and your bet is returned.
- Blackjack usually means you win 1.5 the amount of your bet.

## Configuration (Optional)
If you need to configure the connection to the local database:

1. Navigate to the database configuration file.
2. Update the database URL, user, and password to match your local database server.

## Acknowledgements
- Card graphics in this game were adapted from Uzay Macar's project found at [uzaymacar/blackjack-with-gui](https://github.com/uzaymacar/blackjack-with-gui). A special thanks to them for providing a rich set of assets which have greatly enhanced this game's interface.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
