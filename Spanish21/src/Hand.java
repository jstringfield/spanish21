import java.util.ArrayList;

/*
 * This class represents a spanish21 card hand with a strategy card.
 */

public class Hand {
	//Variables used for keeping data for a spanish21 card players hand. 
	protected ArrayList<String> cards;
	protected int handValue;
	protected boolean hasHit = false;
	protected boolean hasDoubled = false;
	protected boolean isPlaying = true;
	protected boolean softHand;
	protected boolean splittable;
	protected boolean blackJack;
	
	// S = Stand
	// P = Split
	// H = Hit
	// R = Surrender
	// D = Double Down
	// W = Player has won
	//The strategy card used for calcualing a decision.
	protected String[][] stratCard = {
			///2....3....4....5....6....7....8....9....10...A
			{ "H", "H", "H", "H", "H", "H", "H", "H", "H", "H" }, // 4
			{ "H", "H", "H", "H", "H", "H", "H", "H", "H", "H" }, // 5
			{ "H", "H", "H", "H", "H", "H", "H", "H", "H", "H" }, // 6
			{ "H", "H", "H", "H", "D", "H", "H", "H", "H", "H" }, // 7
			{ "H", "H", "H", "D", "D", "H", "H", "H", "H", "H" }, // 8
			{ "H", "D", "D", "D", "D", "H", "H", "H", "H", "H" }, // 9
			{ "D", "D", "D", "D", "D", "D", "D", "H", "H", "H" }, // 10
			{ "D", "D", "D", "D", "D", "D", "D", "D", "D", "D" }, // 11
			{ "H", "H", "H", "H", "H", "H", "H", "H", "H", "H" }, // 12
			{ "H", "H", "H", "H", "H", "H", "H", "H", "H", "H" }, // 13
			{ "H", "H", "S", "S", "S", "H", "H", "H", "H", "H" }, // 14
			{ "S", "S", "S", "S", "S", "H", "H", "H", "H", "H" }, // 15
			{ "S", "S", "S", "S", "S", "H", "H", "H", "H", "R" }, // 16
			{ "S", "S", "S", "S", "S", "S", "S", "S", "S", "R" }, // 17
			{ "S", "S", "S", "S", "S", "S", "S", "S", "S", "S" }, // 18
			{ "S", "S", "S", "S", "S", "S", "S", "S", "S", "S" }, // This is 19
			{ "S", "S", "S", "S", "S", "S", "S", "S", "S", "S" }, // This is 20
			{ "W", "W", "W", "W", "W", "W", "W", "W", "W", "W" }, // This is 21

			{ "H", "H", "D", "D", "D", "H", "H", "H", "H", "H" }, // A,2 ---THIS IS WHEN YOU HAVE SOFT HAND
			{ "H", "H", "D", "D", "D", "H", "H", "H", "H", "H" }, // A,3
			{ "H", "H", "D", "D", "D", "H", "H", "H", "H", "H" }, // A,4
			{ "H", "H", "D", "D", "D", "H", "H", "H", "H", "H" }, // A,5
			{ "H", "H", "D", "D", "D", "H", "H", "H", "H", "H" }, // A,6
			{ "S", "S", "D", "D", "D", "S", "S", "H", "H", "H" }, // A,7
			{ "S", "S", "S", "S", "S", "S", "S", "S", "S", "S" }, // A,8
			{ "S", "S", "S", "S", "S", "S", "S", "S", "S", "S" } };// A,9

	private String[][] splitCard = { // IS WHEN SPLITTING HAPPENS
			{ "P", "P", "P", "P", "P", "P", "P", "H", "H", "H" }, // 2,2 															
			{ "P", "P", "P", "P", "P", "P", "P", "H", "H", "H" }, // 3,3
			{ "H", "H", "H", "D", "D", "H", "H", "H", "H", "H" }, // 4,4
			{ "D", "D", "D", "D", "D", "D", "D", "H", "H", "H" }, // 5,5
			{ "H", "H", "P", "P", "P", "H", "H", "H", "H", "H" }, // 6,6
			{ "P", "P", "P", "P", "P", "H", "H", "H", "H", "H" }, // 7,7
			{ "P", "P", "P", "P", "P", "P", "P", "P", "P", "R" }, // 8,8
			{ "S", "P", "P", "P", "P", "S", "P", "P", "S", "S" }, // 9,9
			{ "S", "S", "S", "S", "S", "S", "S", "S", "S", "S" }, // 10,10
			{ "P", "P", "P", "P", "P", "P", "P", "P", "P", "P" } };// A,A


	/**
	 * Constructor
	 */
	protected Hand() {
		this.cards = new ArrayList<String>();
		isPlaying = true;
	}
	
	/**
	 * Constructor with card to add. 
	 * @param card The card to be added when initializing. 
	 */
	protected Hand(String card) {
		this.cards = new ArrayList<String>();
		this.cards.add(card);
	}
	
	/**
	 * Turns this hand off to indicate that the player has 
	 * lost/won/no longer allowed to hit
	 */
	protected void switchOff() {
		this.isPlaying = false;
	}
	
	/**
	 * Sets the boolean double down to true. 
	 */
	protected void doubleDown() {
		hasDoubled = true;
	}
	
	/**
	 *Adds a card to the ArrayList of cards.  
	 * @param card The card to be added. 
	 */
	protected void addCard(String card) {
		this.cards.add(card);
	}
	
	/**
	 * Retrieves the card from a hand. 
	 * @param index The index of the ArrayList of cards to get from. 
	 * @return
	 */
	protected String getCard(int index) {
		return this.cards.get(index);
	}

	/**
	 * Gets the size of the ArrayList of cards
	 * @return The number of cards that are in this hand. 
	 */
	protected int size() {
		return this.cards.size();
	}
	
	/**
	 * Returns the hand value of this hand.
	 * @return The hand value.
	 */
	protected int getHandValue() {
		return this.handValue;
	}
	
	/**
	 * Removes the card from this hand to put into 
	 * another hand. 
	 * @return The card to be placed into the other hand. 
	 */
	protected String split() {
		return this.cards.remove(0);
	}

	/**
	 * Determines if the player has 21. 
	 * @return The boolean value to determine if true/false.
	 */
	protected boolean has21() {
		int temp = handValue();
		return temp == 21;
	}

	/**
	 * Determines if the player had BlackJack.
	 * @return Boolean value.
	 */
	protected boolean hasBlackJack() {
		return this.blackJack;
	}
	
	/**
	 * Turns the hand off (not able to hit anymore) and
	 * adjusts the amount in the bank. 
	 */
	protected void surrender() {
		this.isPlaying = false;
		CardPlayer.bank += CardPlayer.betAmount / 2;
	}
	
	/**
	 * Sets the hand up for the next fresh round. 
	 */
	protected void clear() {
		this.blackJack = false;
		this.hasDoubled = false;
		this.isPlaying = true;
		this.cards.clear();
	}

	/**
	 * toString method to print strings in specified way.
	 */
	public String toString() {
		if (cards.size() < 1)
			return "The hand was cleared ";
		return this.cards + " (" + Integer.toString(this.handValue()) + ") ";
	}

	/**
	 * Calculates the hand value from the 
	 * ArrayList of cards. 
	 * @return The calculated value.
	 */
	protected int handValue() {
		this.handValue = 0;
		int numAces = 0;
		boolean hasFace = false;
		boolean hasAce = false;

		if (this.cards.size() > 2)
			hasHit = true;

		if (this.cards.size() == 1) {
			this.cards.add(Dealer.dealCard());
		}

		for (int i = 0; i < cards.size(); i++) {

			if (this.cards.get(i).charAt(0) == 'J' || this.cards.get(i).charAt(0) == 'Q'
					|| this.cards.get(i).charAt(0) == 'K') {
				this.handValue += 10;
				hasFace = true;
				continue;
			} else if (this.cards.get(i).charAt(0) == 'A') {
				numAces++;
				hasAce = true;
				continue;
			} else {
				this.handValue += Integer.parseInt(this.cards.get(i).substring(0, 1));
			}
		}

		this.splittable = false;

		if (this.cards.size() == 2 && this.cards.get(0).charAt(0) == this.cards.get(1).charAt(0)) {
			this.splittable = true;
		}

		if (hasFace && hasAce && this.cards.size() == 2) {
			this.handValue = 21;
			this.blackJack = true;
			return this.handValue;
		}

		if (hasAce && this.handValue < 10) {
			this.softHand = true;
			if (this.cards.size() == 2 && this.cards.get(0).charAt(0) == 'A' && this.cards.get(1).charAt(0) == 'A')
				this.softHand = false;
		} else {
			this.softHand = false;
		}

		if (this.handValue > 10)
			this.handValue += numAces;

		if (this.handValue <= 10 && numAces >= 1)
			this.handValue += 11 + (numAces - 1);

		return this.handValue;
	}

	/**
	 * Determines the decision the player should make based off
	 * of the dealer show card and the player cards using the 
	 * 2D array strategy card. 
	 * @return The decision the player should make. 
	 */
	protected String decision() {
		int test = handValue();
		if (test < 1) {
			return "N";
		} else if (test == 2 || test == 3) {
			return "H";
		} else if (test > 21) {
			return "B";
		} else if (test == 21) {
			return "W";
		}

		int stratCol;
		if (Dealer.showCard.charAt(0) == 'A') {
			stratCol = 9;
		} else if (Dealer.showCard.charAt(0) == 'J' || Dealer.showCard.charAt(0) == 'Q'
				|| Dealer.showCard.charAt(0) == 'K') {
			stratCol = 8;
		} else {
			stratCol = Integer.parseInt(Dealer.showCard.substring(0, 1)) - 2;
		}

		if (this.softHand) {
			return stratCard[(this.handValue() - 4) + 9][stratCol];
		} else if (this.splittable) {
			if (this.cards.get(0).charAt(0) == 'Q' || this.cards.get(0).charAt(0) == 'K'
					|| this.cards.get(0).charAt(0) == 'J') {
				return "S";
			} else if (this.cards.get(0).charAt(0) == 'A') {
				return "P";
			} else {
				return splitCard[Integer.parseInt(this.cards.get(0).substring(0, 1)) - 2][stratCol];
			}
		} else if (stratCard[this.handValue() - 4][stratCol] == "R" && hasHit) {
			return "S";
		} else {
			return stratCard[this.handValue() - 4][stratCol];
		}
	}
}
