package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.DrawWhenValueAbove17Exception;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class EvaluatorTest {

    @Test
    public void 게임초기화시_모든플레이어는_2장의카드를_받는다() {
        Map<String, Player> playerMap = new HashMap<>();
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer();

        Player[] players = new Player[2];
        players[0] = new Player("Kim", 30000);
        players[1] = new Player("Park", 30000);
        playerMap.put(players[0].getPlayerName(), players[0]);
        playerMap.put(players[1].getPlayerName(), players[1]);

        Evaluator evaluator = new Evaluator(dealer, playerMap);
        evaluator.dealTwoCardsToPlayers(deck);

        for (int i = 0; i < 2; i++) {
            assertTrue(players[i].getHand().getCardList().size() == 2);
        }
    }

    @Test
    public void 각_플레이어는_16이하면_히트한다() {
        Map<String, Player> playerMap = new HashMap<>();
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
    }

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
  
    @Test(expected = DrawWhenValueAbove17Exception.class)
    public void 각_플레이어는_17이상이면_스테이한다() {
        Map<String, Player> playerMap = new HashMap<>();
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer();

        Player[] players = new Player[2];
        players[0] = new Player("Kim", 30000);
        players[1] = new Player("Park", 30000);
        playerMap.put(players[0].getPlayerName(), players[0]);
        playerMap.put(players[1].getPlayerName(), players[1]);

        Evaluator evaluator = new Evaluator(dealer, playerMap);

        players[0].hitWithCertainCard(deck, Suit.DIAMONDS, 10);
        players[0].hitWithCertainCard(deck, Suit.DIAMONDS, 8);
        players[1].hitWithCertainCard(deck, Suit.SPADES, 10);
        players[1].hitWithCertainCard(deck, Suit.SPADES, 8);

        players[0].TesthitWhenTotalAbove17(deck);
        players[1].TesthitWhenTotalAbove17(deck);

    }

    /*
    @Test
    public void 블랙잭이나오면_2배로_보상받고_해당_플레이어의_턴은_끝난다_2() {
        Map<String, Player> playerMap = new HashMap<>();
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer();

        Player[] players = new Player[2];
        players[0] = new Player("Kim", 30000);
        players[1] = new Player("Park", 30000);
        playerMap.put(players[0].getPlayerName(), players[0]);
        playerMap.put(players[1].getPlayerName(), players[1]);

        Evaluator evaluator = new Evaluator(dealer, playerMap);

        players[0].hitWithCertainCard(deck, Suit.DIAMONDS, 10);
        players[0].hitWithCertainCard(deck, Suit.DIAMONDS, 1);
        players[1].hitWithCertainCard(deck, Suit.SPADES, 10);
        players[1].hitWithCertainCard(deck, Suit.SPADES, 1);

        assertTrue(players[0].getBalance() == 30000);
        players[0].placeBet(10000);
        assertTrue(players[0].getBalance() == 20000);
        assertTrue(players[0].getCurrentBet() == 10000);


        if (players[0].isBlackJack()) {
            evaluator.getBetFromDealer(players[0], true);         // 게임에서 이겼을 때 보상받는 부분.
        }

        assertTrue(players[0].getBalance() == 50000);             // 기존 잔액 3만원에서 만원을 걸고 블랙잭으로 이겨 5만원이 됨
        assertTrue(players[0].getCurrentBet() == 0);
    }
    */
}
