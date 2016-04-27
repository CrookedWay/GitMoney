package gitmoney;

/**
 * BlackJack Helper Application
 *
 * @authors: Michael Scott, Megan Gaynor, William Jackson, Konstantin Kazantsev
 * Due Date: 4/28/2016
 */

import java.util.ArrayList;

public class Main {

    //helper methods
    private static int scrub(Card c) {
        int r = c.rank();
        return r;
    }

    private static ArrayList<Card> discardPile(ArrayList<Card> deck, Card card, int count) {
        deck.add(count, card);
        return deck;
    }

    private static Card getCard(ArrayList<Card> deck, int spot) {
        return deck.get(spot);
    }

    /*
    private static int totalHand(ArrayList<Integer> hand){
        int counter = 0;
        for (Integer i : hand)
            counter += 1;
        return counter;
    }
    */
    private static int totalHand(ArrayList<Integer> hand){
        int total = 0;
        for (Integer i : hand)
            total += i;
        return total;
    }

    public static void main(String[] args) {
        boolean handOver = false;
        int numberOfHands = 0;
        int playerMoney = 1000;
        boolean playerTurnOver = false;
        int cardsLeft = 0;
        boolean hit = false;
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

        
        DeckOfCards deck = new DeckOfCards();
        deck.shuffle(100);

        while (numberOfHands < 100) {
            
            // reset values to false
            push = false;
            bust = false;
            autoWin = false;
            playerWin = false;
            dBust = false;
            
            // play hand
            while (!handOver) {

                // players hand
                hand.clear();
                int temp = scrub(deck.deal());
                discard.add(temp);
                hand.add(temp);
                temp = scrub(deck.deal());
                discard.add(temp);
                hand.add(temp);

                //dealers hand
                dealerHand.clear();
                temp = scrub(deck.deal());
                discard.add(temp);
                dealerHand.add(temp);
                temp = scrub(deck.deal());
                dealerHand.add(temp);

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

                System.out.println(Move.dealer_move(hand));
                System.out.println("auto win" + autoWin);
                hit = Move.dealer_move(hand);

                // player decision
                while (hit && !autoWin && !bust) {
                    temp = scrub(deck.deal());
                    discard.add(temp);
                    hand.add(temp);
                    hit = Move.dealer_move(hand);
                    if (totalHand(hand) > 21) {
                        bust = true;
                    }

                }

                // Dealer decision (if necessary)
                if (!bust && !autoWin) {
                    while (totalHand(dealerHand) <= 17) {

                        temp = scrub(deck.deal());
                        discard.add(temp);
                        dealerHand.add(temp);

                    }
                }

                handOver = true;

            }

            //check deck for reshuffle
            if (deck.cardsLeft() < 20) {
                deck = new DeckOfCards();
                deck.shuffle(100);
                discardC = 0;
                discardP = 0;
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
            playerMoney -= 10; // initial bet
            if (playerWin && autoWin) {
                playerMoney += 15;
            } else if (playerWin) {
                playerMoney += 20;
            } else if (push) {
                playerMoney += 10;
            }

            System.out.println("player total final" + totalHand(hand));
            System.out.println("dealer total final" + totalHand(dealerHand));
            System.out.println("player win?" + playerWin);
            System.out.println("push?" + push);
            System.out.println("auto win?" + autoWin);
            System.out.println("cards left" + deck.cardsLeft());
            System.out.println("player money" + playerMoney);
            numberOfHands++;
            System.out.println(numberOfHands + "\n");

            handOver = false;
        }
    }

}
