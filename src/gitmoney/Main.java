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
        while (numberOfHands < 100) {
            
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

                // determine if autowin is to occur or push based on Blackjack
                if (totalHand(dealerHand) == 21 && totalHand(hand) != 21) {
                    autoWin = true;
                }
                if (totalHand(hand) == 21 && totalHand(dealerHand) != 21) {
                    autoWin = true;
                    playerWin = true;
                }
                if (totalHand(hand) == 21 && totalHand(dealerHand) == 21) {
                    push = true;
                }

                if (push | autoWin) {
                    break;
                }

                System.out.println(Move.naive_decision(hand, dealerHand));
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
                    while (totalHand(dealerHand) <= 17) {
                        temp = scrub(deck.deal());
                        discard.add(temp);
                        dealerHand.add(temp);

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

            //finish game
            if (totalHand(hand) > 21) {
                bust = true;
            }
            if (totalHand(dealerHand) > 21) {
                dBust = true;
            }
            if (totalHand(dealerHand) < totalHand(hand) && !bust) {
                playerWin = true;
            }
            if (!bust && dBust) {
                playerWin = true;
            }
            if (totalHand(dealerHand) == totalHand(hand)) {
                push = true;
            }

            //enact payout
            playerMoney -= bet; // initial bet
            if (playerWin && autoWin) {
                playerMoney += bet * (3/2);
            } else if (playerWin) {
                playerMoney += bet * 2;
            } else if (push) {
                playerMoney += bet;
            }

            // print some test results to console
            System.out.println("player total final: " + totalHand(hand));
            System.out.println("dealer total final: " + totalHand(dealerHand));
            System.out.println("player win? " + playerWin);
            System.out.println("push? " + push);
            System.out.println("auto win? " + autoWin);
            System.out.println("cards left: " + deck.cardsLeft());
            System.out.println("player money: " + playerMoney);
            System.out.print("Player cards: ");
            for(Integer i : hand)
                System.out.print(i + ", ");
            System.out.println("");
            System.out.print("Dealer cards: ");
            for (Integer i : dealerHand)
                System.out.print(i+", ");
            numberOfHands++;
            System.out.println("\n" + numberOfHands + "\n");

            // reset hand switch
            handOver = false;
        }
        // iterate back through till numberOfHands match while block
    }

}
