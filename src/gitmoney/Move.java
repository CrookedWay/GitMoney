package gitmoney;

/**
 * BlackJack Helper Application
 *
 * @authors: Michael Scott, Megan Gaynor, William Jackson, Konstantin Kazantsev
 * Due Date: 4/28/2016
 */
import java.util.ArrayList;
import java.util.Random;

public class Move {
    
    /**
     * @author Konstantin Kazantsev
     * @param dealer_hand
     * @return string of "what to do"
     */
    public static Boolean dealer_move(ArrayList<Integer> dealer_hand) {
        // Check if dealer has an ace
        if (dealer_hand.contains(Card.ACE)) {
            // get total t2: assuming ace = 11
            int t2 = 0;
            for (Integer card : dealer_hand) {
                if (card == Card.ACE) {
                    t2 += 11;
                } else {
                    t2 += card;
                }
            }
            int greater = t2; // greater value: default = t2
            if (greater > 21) { /* if greater is more than 21, use t1 */

                int t1 = 0;
                for (Integer card : dealer_hand) {
                    if (card == Card.ACE) {
                        t1 += 1;
                    } else {
                        t1 += card;
                    }
                }
                greater = t1;
            }
            // check if greater is less than 17
            if (greater < 17) {
                return true;
            } else if (greater < 22) {
                return false;
            } else {
                return false; // catch if something went wrong.
            }
        } else { /* if dealer doesn't have an ACE */

            // get dealer_hand total

            int total = 0;
            for (Integer card : dealer_hand) {
                total += card;
            }
            // check if total is less than 17
            if (total < 17) {
                return true;
            } // check if total is less than 22
            else if (total < 22) {
                return false;
            } else { /* if greater than 21, dealer busts and return null */
                return false;
            }
        }
    }

    /**
     * @author Konstntin Kazantsev
     * @param player_hand
     * @return string of "what to do"
     */
    String too_naive(ArrayList<Integer> player_hand) {
        // check if player has an ACE
        if (player_hand.contains(Card.ACE)) {
            // get total assuming ACE = 11
            int t2 = 0;
            for (Integer card : player_hand) {
                if (card == Card.ACE) {
                    t2 += 11;
                } else {
                    t2 += card;
                }
            }
            int greater = t2; // greater value: default = t2
            if (greater > 21) { /* if greater is more than 21, use t1 */

                int t1 = 0;
                for (Integer card : player_hand) {
                    if (card == Card.ACE) {
                        t1 += 1;
                    } else {
                        t1 += card;
                    }
                }
                greater = t1;
            }
            // check if greater is less than 17
            if (greater < 17) {
                return "hit";
            } else if (greater < 22) {
                return "stand";
            } else { /* catch if somehow it got here */

                return null;
            }
        } else { /* If player doesn't have an ACE */

            // get player_hand total

            int total = 0;
            for (Integer card : player_hand) {
                total += card;
            }
            // check if total is less than 17
            if (total < 17) {
                return "hit";
            } else if (total < 22) {
                return "stand";
            } else { /* if greater than 21, player busts */

                return null;
            }
        }
    }

    /**
     * @return 0 or 1 
     */
    static int flip() {
        Random ran = new Random();
        int ans = ran.nextInt(2);
        return ans;
    }

    /**
     * @author Konstantin Kazantsev
     * @param player_hand
     * @param dealer_hand
     * @return string of "what to do"
     */
    String naive_decision(ArrayList<Integer> player_hand, ArrayList<Integer> dealer_hand) {
        if (player_hand.contains(Card.ACE)) {
            // get totals: t1 (ACE = 1) and t2 (ACE = 11)
            int t1 = 0;
            int t2 = 0;
            for (Integer card : player_hand) {
                if (card == Card.ACE) {
                    t1 += 1;
                    t2 += 11;
                } else {
                    t1 += card;
                    t2 += card;
                }
            }
            // if total 1 or total 2 == 12
            if (t1 == 2 || t2 == 12) {
                int flip = flip();
                if (flip == 0) {
                    // use t1
                    return "hit";
                } else { /* use t2 */

                    if (dealer_hand.get(0)==4 || dealer_hand.get(0) == 5
                            || dealer_hand.get(0) == 6) {
                        return "stand";
                    } else { /* if dealer_hand has 2,3 or 7:11 */

                        return "hit";
                    }
                }
            } else if (t1 == 3 || t1 == 4 || t1 == 5 || t1 == 6 || t2 == 13 || t2 == 14
                    || t2 == 15 || t2 == 16) {
                int flip = flip();
                if (flip == 0) {
                    return "hit";
                } else { /* else use t2 */

                    if (dealer_hand.get(0) == 2 || dealer_hand.get(0) == 3
                            || dealer_hand.get(0) == 4 || dealer_hand.get(0) == 6) {
                        return "stand";
                    } else { /* if dealer_hand has 7:11 */

                        return "hit";
                    }
                }
            } else if (t1 == 7 || t1 == 8 || t2 == 17 || t2 == 18) {
                int flip = flip();
                if (flip == 0) {
                    return "hit";
                } else { /* use t2 */

                    return "stand";
                }
            } else if (t1 == 9 || t1 == 10 || t2 == 19 || t2 == 20 || t2 == 21) {
                if (t2 == 19 || t2 == 20 || t2 == 21) {
                    return "stand";
                } else { /* will not get here but, use t1 */

                    if (t1 == 9) {
                        if (dealer_hand.get(0) == 3 || dealer_hand.get(0) == 4
                                || dealer_hand.get(0) == 5 || dealer_hand.get(0) == 6) {
                            // double down only if player has 2 cards
                            if (player_hand.size() == 2){
                                return "double down";
                            } else { /* else hit */
                                return "hit";
                            }
                        } else {
                            return "hit";
                        }
                    } else if (t1 == 10) {
                        if (dealer_hand.get(0) == 10 || dealer_hand.get(0) == Card.ACE) {
                            return "hit";
                        } else {
                            // double down only if player has 2 cards
                            if (player_hand.size() == 2){
                                return "double down";
                            } else { /* else hit */
                                return "hit";
                            }
                        }
                    } else if (t1 == 11) {
                        if (dealer_hand.get(0) == Card.ACE) {
                            return "hit";
                        } else {
                            // double down only if player has 2 cards
                            if (player_hand.size() == 2){
                                return "double down";
                            } else { /* else hit */
                                return "hit";
                            }
                        }
                    } else {
                        return null;
                    }

                }
            } else if (t1 == 12) {
                if (dealer_hand.get(0) == 4 || dealer_hand.get(0) == 5
                        || dealer_hand.get(0) == 6) {
                    return "stand";
                } else { /* if dealer has 2,3 or 7:11 */

                    return "hit";
                }
            } else { /* catcher */

                return null;
            }
        } else {
            // get player_hand total
            int total = 0;
            for (Integer card : player_hand) {
                total += card;
            }
            if (total <= 8) {
                return "hit";
            } else if (total == 9) {
                if (dealer_hand.get(0) == 3 || dealer_hand.get(0) == 4
                        || dealer_hand.get(0) == 5 || dealer_hand.get(0) == 6) {
                    return "double down";
                } else { /* if 2 or 7 and above*/

                    return "hit";
                }
            } else if (total == 10) {
                if (dealer_hand.get(0) == 2 || dealer_hand.get(0) == 3
                        || dealer_hand.get(0) == 4 || dealer_hand.get(0) == 5
                        || dealer_hand.get(0) == 6 || dealer_hand.get(0) == 7
                        || dealer_hand.get(0) == 8 || dealer_hand.get(0) == 9) {
                    return "double down";
                } else {/* if 10 or ACE*/

                    return "hit";
                }
            } else if (total == 11) {
                if (dealer_hand.get(0) == Card.ACE) {
                    return "hit";
                } else { /* if 2:10 */
                    return "double down";
                }
            } else if (total == 12) {
                if (dealer_hand.get(0) == 4 || dealer_hand.get(0) == 5
                        || dealer_hand.get(0) == 6) {
                    return "stand";
                } else { /* if 2,3 or 7 and above */
                    return "hit";
                }
            } else if (total >= 13 && total <= 16) {
                if (dealer_hand.get(0) == 7 || dealer_hand.get(0) == 8
                        || dealer_hand.get(0) == 9 || dealer_hand.get(0) == 10) {
                    return "hit";
                } else { /* if 2:6 */

                    return "stand";
                }
            } else if (total >= 17 && total <= 21) {
                return "stand";
            } else if (total >= 22) {
                return null;
            } else { /* this statement should never be reached */

                return null;
            }
        }
    }
    
}
