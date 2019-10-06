import java.util.ArrayList;

/*
 * This class is a driver class for the spanish21 game. This class 
 * contains all of the logic and operation of the game. All moves by
 * players set in the game are printed. 
 */
public class Spanish21Demo {

	protected ArrayList<CardPlayer> players;
	private static Dealer dealer = new Dealer();
	
	//Constructor
	public Spanish21Demo(ArrayList<CardPlayer> players) {
		this.players = new ArrayList<>();
		for(int i = 0; i < players.size(); i++) {
			this.players.add(players.get(i));
		}
	}

	//DEMONSTRATING RUNNING PROGRAM AND ALL LOGICAL. 
	public static void main(String[] args) {
		
		ArrayList<CardPlayer> cardPlayers = new ArrayList<>();
		
		for(int i = 5; i >= 0; i--) 
			cardPlayers.add(new CardPlayer("("+(i+1)+")", 1500));
		
		Spanish21Demo tableOne = new Spanish21Demo(cardPlayers);

		for (int i = 0; i < 50; i++) {
			System.out.println("Game " + (i+1) + ":");
			
				for (int b = tableOne.players.size() - 1; b >= 0; b--) {
					tableOne.players.get(b).bet(50);
					tableOne.players.get(b).sideBet(25);
				}
			
			//Hitting each player with one card. 
			for (int b = tableOne.players.size() - 1; b >= 0; b--) {
				tableOne.players.get(b).hit(Dealer.dealCard());
			}
			
			//Dealer hitting himself
			dealer.setShowCard(Dealer.dealCard());
			
			//Printing dealers show card
			System.out.println("Dealer show card: " + Dealer.showCard);
			
			//Hitting players again with second card.
			for (int b = tableOne.players.size() - 1; b >= 0; b--) {
				tableOne.players.get(b).hit(Dealer.dealCard());
			}
			
			//Setting the hide card for the dealer. 
			dealer.setHideCard(Dealer.dealCard());
			
			//Looping through each player.
			for (int b = tableOne.players.size() - 1; b >= 0; b--) {
				//Looping through each players set of hands and seeing if they have blackjack.
				for (int c = tableOne.players.get(b).playerHands.size() - 1; c >= 0; c--) {
					//Looping through each individual card in the hand. 
					for (int d = tableOne.players.get(b).playerHands.get(c).size() - 1; d >= 0; d--) {
						// If the card is EXACTLY EQUAL to the dealers card then pay him/her perfect 
						//match multiple and continue to next card. 
						String card = tableOne.players.get(b).playerHands.get(c).getCard(d);
						if (card.equals(Dealer.showCard)) {
							tableOne.players.get(b).collect(tableOne.players.get(b).sideBet * 13);
							System.out.println("(12x) PERFECT MATCH Dealer card: " + Dealer.showCard + " Player card: "
									+ tableOne.players.get(b).playerHands.get(c).getCard(d) + " Player number: "
									+ tableOne.players.get(b).playerName);
							continue;
						}
						//If the number on the card is a match then pay the player REGULAR MATCH multiple and continue.
						if (tableOne.players.get(b).playerHands.get(c).getCard(d).charAt(0) == Dealer.showCard.charAt(0)) {
							tableOne.players.get(b).collect(tableOne.players.get(b).sideBet * 4);
							System.out.println("(3x) MATCH Dealer card: " + Dealer.showCard + " Player card: "
									+ tableOne.players.get(b).playerHands.get(c).getCard(d) + " Player number: "
									+ tableOne.players.get(b).playerName);
							continue;
						}

					}
				}
			}
			
			// Looping through each player.
			for (int b = tableOne.players.size() - 1; b >= 0; b--) {
				// Looping through each players set of hands and seeing if they have blackjack.
				for (int c = tableOne.players.get(b).playerHands.size() - 1; c >= 0; c--) {
					// Looping through each individual card in the hand.
					tableOne.players.get(b).playerHands.get(c).handValue();
					if (tableOne.players.get(b).playerHands.get(c).hasBlackJack()) {
						System.out
								.println("Player " + tableOne.players.get(b).playerName + " has BLACKJACK with cards: "
										+ tableOne.players.get(b).playerHands.get(c).toString());
						tableOne.players.get(b).collect(tableOne.players.get(b).betAmount * 2.5);
						tableOne.players.get(b).playerHands.get(c).switchOff();
					}
				}
			}
			
			//If the dealer has Blackjack, then clear the table to and begin next round. 
			if (dealer.hasBlackJack()) {
				System.out.println("DEALER HAS BLACKJACK " + Dealer.showCard + " " + dealer.hideCard + "\nEveryone else Loses");

				for (int b = tableOne.players.size() - 1; b >= 0; b--) 
					tableOne.players.get(b).clear();
				
				dealer.clear();
				System.out.println();
				continue; //When we implement looping so it would just start a new game.
			}

			// Decision making for players.
			for (int b = tableOne.players.size() - 1; b >= 0; b--) {
				for (int c = tableOne.players.get(b).playerHands.size() - 1; c >= 0; c--) {
					//CHECK FOR DOUBLE DOWN
					if (tableOne.players.get(b).playerHands.get(c).hasDoubled) {
						System.out.println("Player " + tableOne.players.get(b).playerName + " Doubled down");
						break;
					}
					//CHECK IF PLAYING
					if (tableOne.players.get(b).playerHands.get(c).isPlaying == false) {
						System.out.println("Player " + tableOne.players.get(b).playerName + " is not playing this hand");
						break;
					}
					//EVENT THAT HAND VALUE IS LESS THAN 0
					if (tableOne.players.get(b).playerHands.get(c).decision() == "N") {
						System.out.println("The player is not in the game for some reason");
						break;
					}
					//IF THE PLAYER IS SUPPOSED TO STAND
					if (tableOne.players.get(b).playerHands.get(c).decision() == "S") {
						System.out.println("Player " + tableOne.players.get(b).playerName + " has stood (S) with cards: " 
								+ tableOne.players.get(b).playerHands.get(c).toString());
						continue;
					}
					//IF THE PLAYER IS SUPPOSED TO HIT.
					if (tableOne.players.get(b).playerHands.get(c).decision() == "H") {
						System.out.println(
								"Player " + tableOne.players.get(b).playerName + " is going to hit (H) with cards: "
										+ tableOne.players.get(b).playerHands.get(c).toString());
						tableOne.players.get(b).playerHands.get(c).addCard(Dealer.dealCard());
						System.out.println("Player " + tableOne.players.get(b).playerName
								+ " received card and now has: " + tableOne.players.get(b).playerHands.get(c).toString());
						
						b++;
						break;
					}
					//IF THE PLAYER MUST SPLIT 
					if (tableOne.players.get(b).playerHands.get(c).decision() == "P") {
						System.out.println("Player " + tableOne.players.get(b).playerName 
								+ " is going to split (P) with cards: "
								+ tableOne.players.get(b).playerHands.get(c).toString());
						tableOne.players.get(b).playerHands.add(new Hand(tableOne.players.get(b).playerHands.get(c).split()));
						
						System.out.print("Player " + tableOne.players.get(b).playerName
								+ " hand split and now has hands: "); 
								for(int hands = 0;  hands < tableOne.players.get(b).playerHands.size(); hands++) {
									tableOne.players.get(b).playerHands.get(hands).handValue();
									
									System.out.print(tableOne.players.get(b).playerHands.get(hands).toString() 
								+ ", ");
									
								}
								System.out.println();
						
						b++;
						break;
					}
					//IF THE PLAYER MUST DOUBLE DOWN
					if (tableOne.players.get(b).playerHands.get(c).decision() == "D") {
						System.out.println("Player " + tableOne.players.get(b).playerName
								+ " is going to double down (D) with cards: "
								+ tableOne.players.get(b).playerHands.get(c).toString());
						tableOne.players.get(b).playerHands.get(c).doubleDown();
						tableOne.players.get(b).doubleDown();
						tableOne.players.get(b).playerHands.get(c).addCard(Dealer.dealCard());
						System.out.println("Player " + tableOne.players.get(b).playerName + " has doubled down and has: "
								+ tableOne.players.get(b).playerHands.get(c).toString());
						if (tableOne.players.get(b).playerHands.get(c).has21())
							tableOne.players.get(b).pay();
						c++;
						break;
					}
					//IF THE PLAYER MUST SURRENDER
					if (tableOne.players.get(b).playerHands.get(c).decision() == "R") {
						tableOne.players.get(b).playerHands.get(c).surrender();
						System.out.println("Player " + tableOne.players.get(b).playerName 
								+ " has surrendered (R) with cards: "
								+ tableOne.players.get(b).playerHands.get(c).toString());
					}
					//IF THE PLAYER WON
					if (tableOne.players.get(b).playerHands.get(c).decision() == "W") {
						System.out.println("Player " + tableOne.players.get(b).playerName 
								+ " has won (W) with cards: "
								+ tableOne.players.get(b).playerHands.get(c).toString());
						tableOne.players.get(b).pay();
					}
					//IF THE PLAYER LOST/BUSTED.
					if (tableOne.players.get(b).playerHands.get(c).decision() == "B" && tableOne.players.get(b).playerHands.size() == 1)
						System.out.println("Player " + tableOne.players.get(b).playerName + " has lost (B) with cards: "
								+ tableOne.players.get(b).playerHands.get(c).toString());
					tableOne.players.get(b).playerHands.get(c).switchOff();

				}

			}
			
			//After the choices have been made, the dealer reveals cards and hits until >= 17. 
			System.out.println("Dealer cards: " + dealer.gameCards);
			while (dealer.dealerValue() < 17) {
				dealer.dealSelf(Dealer.dealCard());
				System.out.println("Dealer has hit and now has: " + dealer.gameCards + " " + dealer.dealerValue());
			}

			System.out.println("Dealer cards: " + dealer.gameCards + " " + dealer.dealerValue());

			//Dealing payments to players based off their hands. 
			for (int b = tableOne.players.size() - 1; b >= 0; b--) {
				for (int c = tableOne.players.get(b).playerHands.size() - 1; c >= 0; c--) {
					//In the event of PUSH
					if (tableOne.players.get(b).playerHands.get(c).isPlaying 
							&& tableOne.players.get(b).playerHands.get(c).getHandValue() == dealer.dealerValue()) {
						tableOne.players.get(b).push();//Giving player back his money.
						System.out.println("Player " + tableOne.players.get(b).playerName + " has PUSHED with cards: " 
								+ tableOne.players.get(b).playerHands.get(c).toString());
						continue;
					}
					//In the event of a WIN
					if ((tableOne.players.get(b).playerHands.get(c).isPlaying 
							&& tableOne.players.get(b).playerHands.get(c).getHandValue() > dealer.dealerValue()) 
							|| (dealer.dealerValue() > 21 && tableOne.players.get(b).playerHands.get(c).isPlaying)) {
						System.out.println("Player " + tableOne.players.get(b).playerName 
								+ " has WON with cards: "
								+ tableOne.players.get(b).playerHands.get(c).toString());
						tableOne.players.get(b).pay();//Paying player if his cards are greater than dealers
						continue;
					} 
					//In the event of a LOSS
					if (tableOne.players.get(b).playerHands.get(c).isPlaying) {
						System.out.println("Player " + tableOne.players.get(b).playerName + " has LOST with cards: "
								+ tableOne.players.get(b).playerHands.get(c).toString());
						continue;
					}
				}
			}
			
			//Clearing all players for next round.
			for (int b = tableOne.players.size() - 1; b >= 0; b--) {
				for (int c = tableOne.players.get(b).playerHands.size() - 1; c >= 0; c--) {
					tableOne.players.get(b).playerHands.remove(c);
				}
				tableOne.players.get(b).playerHands.add(new Hand());
				tableOne.players.get(b).clear();
				
			}
			
			//Clearing dealer cards
			dealer.clear();
			
			//Shuffling in the event that there are only 1 deck of cards is left. 
			if (Dealer.shoe.size() <= 48) {
				dealer.buildShoe();
				System.out.println("The dealer is SHUFFLING");
			}
			System.out.println();

		}

	}
}
