import java.util.ArrayList;

/*
 * This class represent a spanish21 card player. 
 */

public class CardPlayer {
	//Variables used for a spanish21 card player. 
	protected static double bank;
	protected static double betAmount;
	protected double winnings;
	protected double originalBet;
	protected double sideBet;
	protected String playerName;
	protected ArrayList<Hand> playerHands;
	protected boolean hasBeenHit = false;

	/**
	 * Constructor
	 * @param name Name of player
	 * @param money The amount of money the player is going to start with
	 */
	public CardPlayer(String name, double money) {
		this.playerName = name;
		bank = money;
		this.winnings = bank;
		this.playerHands = new ArrayList<Hand>();
	}

	/**
	 * When the player doubles down, the bet changes.
	 */
	protected void doubleDown() {
		bank -= this.originalBet;
		betAmount = betAmount * 2;
	}
	
	/**
	 * When the player splits his cards the bet changes.
	 */
	protected void split() {
		bank -= this.originalBet;
		betAmount += this.originalBet;
	}

	/**
	 * The amount the player has won overall. 
	 * @return The winnings the player has recieved
	 */
	protected double winnings() {
		return bank - this.winnings;
	}

	/**
	 * The amount the player is going to bet per game. 
	 * @param bet The amount the player bets
	 */
	protected void bet(double bet) {
		betAmount = bet;
		this.originalBet = bet;
		bank -= bet;
	}

	/**
	 * The side bet. If the players card matches the rank or rank and suite of the 
	 * dealers showing card, then the player gets paid 3x or 12x respectively. 
	 * @param sideBet The amount of money the player bets on the side. 
	 */
	protected void sideBet(double sideBet) {
		this.sideBet = sideBet;
		bank -= sideBet;
	}

	/**
	 * The amount of money the player collects after the round. 
	 * @param amount Collection amount. 
	 */
	protected void collect(double amount) {
		bank += amount;
	}
	
	/**
	 * Basic payment for the player if they win. 
	 */
	protected void pay() {
		this.collect(betAmount * 2);
	}
	
	/**
	 * If the player receives the same hand value as the dealer, they 
	 * get their money back. 
	 */
	protected void push() {
		this.collect(betAmount);
	}
	
	/**
	 * Adds cards to a players hand. 
	 * @param card The card to be added, which is a string. 
	 */
	protected void hit(String card) {

		if (hasBeenHit) {
			this.playerHands.get(0).addCard(card);
		} else if (!hasBeenHit) {
			this.playerHands.add(new Hand(card));
			hasBeenHit = true;
		}
	}

	/**
	 * Clears all of the hands and cards for the next round. 
	 */
	protected void clear() {
		for (int i = 0; i < playerHands.size(); i++)
			playerHands.get(i).clear();
	}


}
