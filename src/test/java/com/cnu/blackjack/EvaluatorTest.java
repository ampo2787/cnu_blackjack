package com.cnu.blackjack;

import org.junit.Test;

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
        for(int i=0;i<player1.getHand().getCardList().size();i++) {
            handValue += player1.getHand().getCardList().get(i).getRank();
        }

            System.out.println("현재 값 : " + handValue);
        if(handValue <= 16) {
            player1.hitCard();
        }else{
            assertFalse("카드 값이 16 이상입니다.", true);
        }
            handValue = 0;
            for(int i=0;i<player1.getHand().getCardList().size();i++) {
                handValue += player1.getHand().getCardList().get(i).getRank();
            }
            System.out.println("현재 값 : "  + handValue);
        }



    @Test
    public void 블랙잭이나오면_2배로_보상받고_해당_플레이어의_턴은_끝난다() {
        Deck deck = new Deck(1);
        Hand hand = new Hand(deck);
        Card card = new Card(10,Suit.DIAMONDS);
        Card card1 = new Card(8, Suit.DIAMONDS);
        Card card2 = new Card(3, Suit.DIAMONDS);
        hand.setCardList(card);
        hand.setCardList(card1);
        hand.setCardList(card2);
        Player player1 = new Player(30000, hand);
        player1.placeBet(20000);
        int handValue = 0;
        for(int i=0;i<player1.getHand().getCardList().size();i++) {
            handValue += player1.getHand().getCardList().get(i).getRank();
        }
        if(handValue == 21){
            System.out.println("블랙잭 전 자금 : (현 자금) = " + player1.getBalance() + " (게임에 건 돈) = "  + player1.getCurrentBet());
            player1.setBalance(player1.getCurrentBet() * 2 + player1.getBalance());//건돈 * 2 + 원래 돈
            player1.setCurrentBet(0);
            System.out.println("블랙잭 후 자금 : " + player1.getBalance());
            //턴 끝난다????
        }else{
            System.out.println("블랙잭이 아님");
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
