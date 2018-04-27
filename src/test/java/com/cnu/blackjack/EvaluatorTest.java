package com.cnu.blackjack;

import org.junit.Test;

public class EvaluatorTest {

    @Test
    public void 게임초기화시_모든플레이어는_2장의카드를_받는다() {
        Deck deck = new Deck(1);
        Game game = new Game(deck);



    }

    @Test
    public void 각_플레이어는_16이하면_히트한다() {
        Deck deck = new Deck(1);
        Hand hand = new Hand(deck);
        Player player1 = new Player(30000, hand);
        int handValue = 0;
        for(int i=0;i<player1.getHand().getCardList().size();i++) {
            handValue += player1.getHand().getCardList().get(i).getRank();
        }
        if(handValue <= 16){
            player1.hitCard();
        }
    }


    @Test
    public void 블랙잭이나오면_2배로_보상받고_해당_플레이어의_턴은_끝난다() {

    }

    @Test
    public void 각_플레이어는_17이상이면_스테이한다() {

    }
}
