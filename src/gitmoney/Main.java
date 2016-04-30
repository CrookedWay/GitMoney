package gitmoney;

/**
 * BlackJack Helper Application
 *
 * @authors: Michael Scott, Megan Gaynor, William Jackson, Konstantin Kazantsev
 * Due Date: 4/28/2016
 */

import java.util.ArrayList;
import java.util.Random;

public class Main {

    //helper methods
    /**
     * 
     * @param c
     * @return integer rank value of card
     */
    private static int scrub(Card c) {
        int r = c.rank();
        return r;
    }

    /**
     * 
     * @param deck
     * @param card
     * @param count
     * @return 
     */
    private static ArrayList<Card> discardPile(ArrayList<Card> deck, Card card, int count) {
        deck.add(count, card);
        return deck;
    }

    /**
     * 
     * @param deck
     * @param spot
     * @return 
     */
    private static Card getCard(ArrayList<Card> deck, int spot) {
        return deck.get(spot);
    }

    /**
     * 
     * @param hand
     * @return return total value of hand
     */
    private static int totalHand(ArrayList<Integer> hand){
        int counter = 0;
        for (Integer i : hand)
            counter += i;
        return counter;
    }

    /**
     * 
     * @return random bet value
     */
    private static int randomBet(){
        int[] bets = {5, 10, 20};
        int rnd = new Random().nextInt(bets.length);
        return bets[rnd];
    }
    
    public static void main(String[] args) {
        
        // simulation variables
        boolean handOver = false;
        int playerWins = 0;
        int dealerWins = 0;
        int numberOfHands = 0;
        int playerMoney = 1000;
        boolean playerTurnOver = false;
        int cardsLeft = 0;
        String move = "";
        ArrayList<Integer> discard = new ArrayList<Integer>();
        int discardC = 0;
        int discardP = 0;
        boolean autoWin = false;
        boolean playerWin = false;
        boolean bust = false;
        boolean push = false;
        boolean dBust = false;
        ArrayList<Integer> hand = new ArrayList<Integer>();
        ArrayList<Integer> dealerHand = new ArrayList<Integer>();

        // build deck and shuffle
        DeckOfCards deck = new DeckOfCards();
        deck.shuffle(100);

        // main iterative loop
        while (numberOfHands < 5) {
            
            // player bet
            int bet = randomBet();

            // play hand
            while (!handOver) {

                System.out.println("Player bet: $" + bet);
                
                // reset values to false
                push = false;
                autoWin = false;
                playerWin = false;
                bust = false;
                dBust = false;
                
                // clear all hands
                hand.clear();
                dealerHand.clear();
                
                // each gets 1 card
                int temp = scrub(deck.deal());
                discard.add(temp);
                hand.add(temp); // player hand
                temp = scrub(deck.deal());
                discard.add(temp);
                dealerHand.add(temp); // dealer hand
                
                // each gets second card
                temp = scrub(deck.deal());
                discard.add(temp);
                hand.add(temp); // player hand
                temp = scrub(deck.deal());
                // dealers second card is not added to discard to keep hidden untill dealer move
                dealerHand.add(temp); // dealer hand
                
                int tplayer = totalHand(hand);
                int tdealer = totalHand(dealerHand);
                
                 // if hand contains an ACE
                if(hand.contains(1))
                    // if ACE = 11 and total hand less than 22
                    if((tplayer+10) < 22)
                        // count ACE as 11
                        tplayer += 10;

                // if dealerHand contains an ACE
                if(dealerHand.contains(1))
                    // if ACE == 11 and total dealerHand less than 22
                    if((tdealer+10) < 22)
                        // count ACE as 11
                        tdealer += 10;

                // determine if autowin is to occur or push based on Blackjack
                if (tdealer == 21 && tplayer != 21) {
                    autoWin = true;
                }
                if (tplayer == 21 && tdealer != 21) {
                    autoWin = true;
                    playerWin = true;
                }
                if (tplayer == 21 && tdealer == 21) {
                    push = true;
                }

                if (push | autoWin) {
                    break;
                }

                System.out.println("auto win: " + autoWin);
                move = Move.naive_decision(hand, dealerHand);

                // player decision
                while (!move.equals("stand") && !autoWin && !bust){
                    if (move.equals("hit")){
                        temp = scrub(deck.deal());
                        discard.add(temp);
                        hand.add(temp);
                    }
                    else if (move.equals("double down")){
                        temp = scrub(deck.deal());
                        discard.add(temp);
                        hand.add(temp);
                        // double the bet
                        bet += bet;
                        // break out of while loop
                        move = "stand";
                        break;
                    }
                    move = Move.naive_decision(hand, dealerHand);
                }
                
                // place dealers second card to discard (reveal dealers second card)
                discard.add(dealerHand.get(1));

                // Dealer decision (if necessary)
                if (!bust && !autoWin) {
                    move = Move.dealer_move(dealerHand);
                    while (!move.equals("stand")){
                        temp = scrub(deck.deal());
                        discard.add(temp);
                        dealerHand.add(temp);
                        move = Move.dealer_move(dealerHand);
                    }
                }
                
                // end current hand
                handOver = true;
            }

            //check deck for reshuffle
            if (deck.cardsLeft() < 20) {
                deck = new DeckOfCards();
                deck.shuffle(100);
            }
            
            //get total of hand and dealerHand
            int tplayer = totalHand(hand);
            int tdealer = totalHand(dealerHand);
            // if hand contains an ACE
            if(hand.contains(1))
                // if ACE = 11 and total hand less than 22
                if((tplayer+10) < 22)
                    // count ACE as 11
                    tplayer += 10;
            // if dealerHand contains an ACE
            if(dealerHand.contains(1))
                // if ACE == 11 and total dealerHand less than 22
                if((tdealer+10) < 22)
                    // count ACE as 11
                    tdealer += 10;
            

            //finish game
            if (tplayer > 21) {
                bust = true;
            }
            if (tdealer > 21) {
                dBust = true;
            }
            if (tdealer < tplayer && !bust) {
                playerWin = true;
            }
            if (!bust && dBust) {
                playerWin = true;
            }
            if (tdealer == tplayer) {
                push = true;
            }

            //enact payout
            playerMoney -= bet; // initial bet
            if (playerWin && autoWin) {
                playerMoney += bet * (3/2);
                playerWins++;
            } else if (playerWin) {
                playerMoney += bet * 2;
                playerWins++;
            } else if (push) {
                playerMoney += bet;
            }
            else
                dealerWins++;

            // print some test results to console
            System.out.println("player total final: " + tplayer);
            System.out.println("dealer total final: " + tdealer);
            System.out.println("player win? " + playerWin);
            System.out.println("push? " + push);
            System.out.println("auto win? " + autoWin);
            System.out.println("cards left: " + deck.cardsLeft());
            System.out.println("player money: $" + playerMoney);
            System.out.print("Player cards: ");
            for(Integer i : hand)
                System.out.print(i + ", ");
            System.out.println("");
            System.out.print("Dealer cards: ");
            for (Integer i : dealerHand)
                System.out.print(i+", ");
            numberOfHands++;
            System.out.println("\nPlayer wins: " + playerWins);
            System.out.println("Dealer wins: " + dealerWins);
            System.out.println("Player win ration: " + (double)playerWins /  (double)numberOfHands);
            System.out.println(numberOfHands + "\n");

            // reset hand switch
            handOver = false;
        }
        // iterate back through till numberOfHands match while block
    }

}
