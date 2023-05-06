
/* This is the class for cards
 * There are 52 cards in Black Jack game, we exclude 2 joker cards
 * Each card should have three properties: Suit, Rank, Number
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Card {
	// private properties
	private int suit; // four suits: clubs, diamonds, hearts, and spades
	private int rank; // 12 ranks: Ace, 2, 3 ... 10, Jack, Queen, King;
	private int value; // The value of the card in black jack
	private int x;
	private int y;

	// default constructor
	public Card() {
		suit = 0;
		rank = 0;
		value = 0;
	}

	// parameterized constructor
	public Card(int suit, int rank, int value) {
		this.suit = suit;
		this.rank = rank;
		this.value = value;
	}

	// get suit method
	public int getSuit() {
		return suit;
	}

	// get rank method
	public int getRank() {
		return rank;
	}

	// get value method
	public int getValue() {
		return value;
	}

	// overwrite
	public String toString() {
		String suit;
		String rank;
		if (this.suit == 0) {
			suit = "Club";
		} else if (this.suit == 1) {
			suit = "Diamond";
		} else if (this.suit == 2) {
			suit = "Heart";
		} else {
			suit = "Spade";
		}
		if (this.rank == 0) {
			rank = "Ace";
		} else if (this.rank == 1) {
			rank = "Two";
		} else if (this.rank == 2) {
			rank = "Three";
		} else if (this.rank == 3) {
			rank = "Four";
		} else if (this.rank == 4) {
			rank = "Five";
		} else if (this.rank == 5) {
			rank = "Six";
		} else if (this.rank == 6) {
			rank = "Seven";
		} else if (this.rank == 7) {
			rank = "Eight";
		} else if (this.rank == 8) {
			rank = "Nine";
		} else if (this.rank == 9) {
			rank = "Ten";
		} else if (this.rank == 10) {
			rank = "Jack";
		} else if (this.rank == 11) {
			rank = "Queen";
		} else {
			rank = "King";
		}
		return "Suit is " + suit + ",Rank is " + rank + ",Value is " + value;
	}
}