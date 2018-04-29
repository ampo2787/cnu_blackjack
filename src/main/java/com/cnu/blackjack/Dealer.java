package com.cnu.blackjack;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Dealer {

    private Hand hand; //카드패

    public Dealer() {
        this.hand = new Hand();
    }

    /*
    public int getDealerScore() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int score = random.nextInt(17, 24);
        System.out.println(score);
        return score;
    }
    */
  
    public int getDealerScore() {
        return hand.getTotalValue();
    }

    public void getOneCardFromDeck(Deck deck) {
        hand.drawCard(deck);
    }

    public Card getDealersFirstCard() {
        return hand.getCardList().get(0);
    }

    public void dealOneCardToEveryPlayer(Deck deck, Map<String, Player> playerList) {
        for (Player player : playerList.values()) {
            player.getHand().drawCard(deck);
        }
    }
  
}
