import java.util.ArrayList;
import java.util.Collections;

/*
 * This class represents a spanish21 card dealer. 
 */
public class Dealer {
	protected static ArrayList<String> shoe;
	protected ArrayList<String> gameCards;
	protected int dealerValue;
	protected static String showCard;
	protected String hideCard;
	protected boolean hasBlackJack;

	/**
	 * Constructor
	 */
	protected Dealer() {
		this.buildShoe();
		this.gameCards = new ArrayList<String>();
		this.hasBlackJack = false;
	}

	/**
	 * Sets the show card.
	 * @param showCard The card picked from the shoe. 
	 */
	protected void setShowCard(String showCard) {
		Dealer.showCard = showCard;
		this.gameCards.add(showCard);
	}

	/**
	 * Sets the hide card.
	 * @param hideCard The card picked from the shoe. 
	 */
	protected void setHideCard(String hideCard) {
		this.hideCard = hideCard;
		this.gameCards.add(hideCard);
	}

	/**
	 * Determines if the dealer has blackjack.
	 * @return The boolean value.
	 */
	protected boolean hasBlackJack() {
		this.dealerValue();
		return this.hasBlackJack;
	}

	/**
	 * Clears the cards the dealer has and resets boolean value. 
	 */
	protected void clear() {
		this.hasBlackJack = false;
		this.gameCards.clear();
	}
	
	/**
	 * Puts the card that the dealer is supposed to have in the ArrayList
	 * cards
	 * @param card The card to be added
	 */
	protected void dealSelf(String card) {
		this.gameCards.add(card);
	}

	/**
	 * Retrieves a card from the first position in the ArrayList
	 * shoe.
	 * @return The card retrieved.  
	 */
	protected static String dealCard() {
		String card = shoe.get(0);
		shoe.remove(0);
		return card;
	}

	/**
	 * Building a base of strings that represent all the cards in a spanish21 deck.
	 */
	protected void buildShoe() {
		shoe = new ArrayList<String>();
		String[] cardsNoSuit = { "2", "3", "4", "5", "6", "7", "8", "9", "J", "Q", "K", "A" };
		// Concatenating each string to a suit value and putting them into a the
		// ArrayList deck.
		for (int p = 0; p < 8; p++) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < cardsNoSuit.length; j++) {
					if (i == 0) {
						shoe.add(cardsNoSuit[j] + "h");
					} else if (i == 1) {
						shoe.add(cardsNoSuit[j] + "s");
					} else if (i == 2) {
						shoe.add(cardsNoSuit[j] + "d");
					} else if (i == 3) {
						shoe.add(cardsNoSuit[j] + "c");
					}
				}
			}
		}
		//Shuffling shoe multiple times. 
		Collections.shuffle(shoe);
		Collections.shuffle(shoe);
		Collections.shuffle(shoe);
	}
	
	/**
	 * Calculates the dealers value from the cards. 
	 * @return The value the cards are worth. 
	 */
	protected int dealerValue() {
		this.dealerValue = 0;
		int numAces = 0;
		boolean hasFace = false;
		boolean hasAce = false;

		for (int i = 0; i < gameCards.size(); i++) {
			if (this.gameCards.get(i).charAt(0) == 'J' || this.gameCards.get(i).charAt(0) == 'Q'
					|| this.gameCards.get(i).charAt(0) == 'K') {
				this.dealerValue += 10;
				hasFace = true;
				continue;
			} else if (this.gameCards.get(i).charAt(0) == 'A') {
				numAces++;
				hasAce = true;
				continue;
			} else {
				this.dealerValue += Integer.parseInt(this.gameCards.get(i).substring(0, 1));
			}
		}

		if (hasFace && hasAce && this.gameCards.size() == 2) {
			this.hasBlackJack = true;
			this.dealerValue = 21;
			return this.dealerValue;
		} else if (this.dealerValue > 10) {
			this.dealerValue += numAces;
		} else if (this.dealerValue <= 10 && numAces >= 1) {
			this.dealerValue += 11 + (numAces - 1);
		}

		return this.dealerValue;
	}
}
