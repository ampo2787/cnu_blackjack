package com.cnu.blackjack;

import org.junit.Test;

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
        Deck deck = new Deck(1);
        Hand hand = new Hand(deck);
        Player player1 = new Player(30000, hand);
        int handValue = 0;
        for(int i=0;i<player1.getHand().getCardList().size();i++) {
            handValue += player1.getHand().getCardList().get(i).getRank();
        }
        if(handValue == 21){
            System.out.println("블랙잭 전 자금 : " + player1.getBalance() + player1.getCurrentBet());
            player1.setBalance(player1.getCurrentBet() * 3 + player1.getBalance());//건 돈 + 건돈 * 2 + 원래 돈
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
        int handValue = 0;
        for(int i=0;i<player1.getHand().getCardList().size();i++) {
            handValue += player1.getHand().getCardList().get(i).getRank();
        }
        if(handValue >= 17){
            //스테이가 그냥 아무것도 안하는거 아닌가?
        }else{
            player1.hitCard(); //이하면 히트
        }
    }
}
