package com.cnu.blackjack;

import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class EvaluatorTest {

    @Test
    public void 게임초기화시_모든플레이어는_2장의카드를_받는다() {
        Deck deck = new Deck(1);
        Game game = new Game(deck);
        game.addPlayer("player1",30000);
        game.addPlayer("player2",10000);
        game.addPlayer("player3",90000);
        Evaluator evaluator = new Evaluator(game.getPlayerList());
        for(String key : game.getPlayerList().keySet()) {
            System.out.println(key + "의 카드는 " + game.getPlayerList().get(key).getHand().getCardList());
        }


    }

    @Test
    public void 각_플레이어는_16이하면_히트한다() {
        Deck deck = new Deck(1);
        Hand hand = new Hand(deck);
        hand.drawCard();
        hand.drawCard();
        Player player1 = new Player(30000, hand);
        int handValue = 0;
        int cardListSize = player1.getHand().getCardList().size();
        for (int i = 0; i < cardListSize; i++) {
            handValue += player1.getHand().getCardList().get(i).getRank();
        }
        if (handValue <= 16) {
            player1.hitCard();
            int afterHitListSize = player1.getHand().getCardList().size();
            assertThat(++cardListSize, is(afterHitListSize));
        } else {
            int thisSize = player1.getHand().getCardList().size();
            assertThat(cardListSize, is(thisSize));

        }


    @Test
    public void 블랙잭이나오면_2배로_보상받고_해당_플레이어의_턴은_끝난다() {
        Evaluator evaluator = new Evaluator();
        Deck deck = new Deck(1);
        Hand hand = new Hand(deck);
        Card card1 = new Card(8, Suit.SPADES);
        Card card2 = new Card(3, Suit.HEARTS);
        Card card3 = new Card(10, Suit.DIAMONDS);
        hand.setCardList(card1);
        hand.setCardList(card2);
        hand.setCardList(card3);
        Player player1 = new Player(30000, hand);
        player1.placeBet(20000);
        int handValue = 0;
        for (int i = 0; i < player1.getHand().getCardList().size(); i++) {
            handValue += player1.getHand().getCardList().get(i).getRank();
        }
        int thisBalance = player1.getBalance();
        int thisBet = player1.getCurrentBet();
        if (handValue == 21) {
            evaluator.rewardToPlayer(player1,thisBalance);
            int after_balance = player1.getBalance();
            assertThat(thisBet*2+thisBalance, is(after_balance));
            //턴끝난거 테스트..?
        }
    }

    @Test
    public void 각_플레이어는_17이상이면_스테이한다() {
        Deck deck = new Deck(1);
        Hand hand = new Hand(deck);
        Player player1 = new Player(30000, hand);
        hand.drawCard();
        hand.drawCard();
        int handValue = 0;
        for(int i=0;i<player1.getHand().getCardList().size();i++) {
            handValue += player1.getHand().getCardList().get(i).getRank();
        }
        if(handValue >= 17){
            assertTrue("값 : "+handValue, false);
        }else{
            assertFalse("값 : "+handValue, true);
        }
    }
}
