package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.DrawWhenValueAbove17Exception;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
        Dealer dealer = new Dealer();

        Player[] players = new Player[2];
        players[0] = new Player("Kim", 30000);
        players[1] = new Player("Park", 30000);
        playerMap.put(players[0].getPlayerName(), players[0]);
        playerMap.put(players[1].getPlayerName(), players[1]);

        Evaluator evaluator = new Evaluator(dealer, playerMap);

        players[0].hitWithCertainCard(deck, Suit.DIAMONDS, 10);
        players[0].hitWithCertainCard(deck, Suit.DIAMONDS, 5);
        players[1].hitWithCertainCard(deck, Suit.SPADES, 10);
        players[1].hitWithCertainCard(deck, Suit.SPADES, 4);

        if (evaluator.evaluateTotalValueOfPlayer(players[0]) <= 16) {
            players[0].hitWithCertainCard(deck, Suit.DIAMONDS, 10);
        }

        if (evaluator.evaluateTotalValueOfPlayer(players[1]) <= 16) {
            players[1].hitWithCertainCard(deck, Suit.DIAMONDS, 10);
        }
    }

    @Test
    public void 블랙잭이나오면_2배로_보상받고_해당_플레이어의_턴은_끝난다() {
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
            evaluator.getBetFromDealer(players[0], true);
        }

        assertTrue(players[0].getBalance() == 50000);
        assertTrue(players[0].getCurrentBet() == 0);

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

}
