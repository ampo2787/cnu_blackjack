package com.cnu.blackjack;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EvaluatorTest {

    @Test
    public void 게임초기화시_모든플레이어는_2장의카드를_받는다() {
        Deck deck = new Deck(1);
        Game game = new Game(deck);
        game.addPlayer("player1",10000);
        game.addPlayer("player2",10000);
        game.addPlayer("player3",10000);
        Evaluator evaluator = new Evaluator(game.getPlayerList());
        game.getPlayerList().forEach((name, player) -> {
            assertThat(player.getHand().getCardList().size(), is(2));
        });
    }

    @Test
    public void 각_플레이어는_16이하면_히트한다() {

    }

    @Test
    public void 블랙잭이나오면_2배로_보상받고_해당_플레이어의_턴은_끝난다() {

    }

    @Test
    public void 각_플레이어는_17이상이면_스테이한다() {
        Deck deck = new Deck(1);
        Game game = new Game(deck);
        game.addPlayer("player1",10000);
        Evaluator evaluator = new Evaluator(game.getPlayerList());
        evaluator.start();

        //각 플레이어들의 처음 받은 카드의 합들이 17 을 넘지 않으면 hit
        //아니면 stay 인지 플레이어의 카드갯수를 보고 알 수 있다.
        game.getPlayerList().forEach((name, player) -> {
            if(evaluator.getPlayerScoreOfFirstReceived(player) < 17) {
                assertThat(player.getHand().getCardList().size(), is(3));
            }
            else {
                assertThat(player.getHand().getCardList().size(), is(2));
            }
        });
    }
}
