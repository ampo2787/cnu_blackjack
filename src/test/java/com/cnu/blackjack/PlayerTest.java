package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.DrawingAtBurstStateException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlayerTest {

    @Test
    public void 플레이어는_시드머니를_가지고_시작한다() {
        Player player = new Player("Park",5000);
        assertThat(player.getBalance(), is(5000));
    }

    @Test
    public void 플레이어는_배팅을_할수_있어야한다() {
        Player player = new Player("Kim",10000);
        player.placeBet(1000);
        int currentBet = player.getCurrentBet();
        assertThat(currentBet, is(1000));
    }

    @Test(expected = Exception.class)
    public void 플레이어는_발란스_이하로_배팅할수_없다() {
        Player player = new Player("Ahn", 5000);
        player.placeBet(6000);
    }

    @Test
    public void 플레이어는_카드를_HIT_할수_있어야한다() {
        Deck deck = new Deck(1);
        Player player = new Player("Shim",5000);
        assertThat(player.hitCard(deck), notNullValue());
    }

    @Test
    public void Hand_내의_에이스는_21을_넘지않을때만_11의값을_갖는다() {
        Deck deck = new Deck(1);
        Player player = new Player("Kim",5000);
        player.hitWithCertainCard(deck, Suit.DIAMONDS, 1);
        assertTrue(player.getHand().getTotalValue() == 11);
        player.hitWithCertainCard(deck, Suit.DIAMONDS, 10);
        assertTrue(player.getHand().getTotalValue() == 21); // 힛해서 받은 에이스의 값이 11로 계산됨
        player.hitWithCertainCard(deck, Suit.SPADES, 10);
        assertTrue(player.getHand().getTotalValue() == 21); // 힛해서 받은 에이스의 값이 1로 계산됨
    }

    @Test
    public void 플레이어는_카드값_총합이_21미만일때_히트할_수_있다() {   // 2014-04-28 추가, 김연훈
        // 게임 규칙 : 플레이어는 카드 값 총합이 21 이하일 때 얼마든지 히트할 수 있다.
        // 히트 결과 카드패 합이 정확히 21인경우 블랙잭, 21 초과인경우 버스트
        Deck deck = new Deck(1);
        Player player1 = new Player("Kim", 20000);
        player1.hitWithCertainCard(deck, Suit.DIAMONDS, 10);
        player1.hitWithCertainCard(deck, Suit.DIAMONDS, 5);
        player1.hitWithCertainCard(deck, Suit.DIAMONDS, 6);
        player1.hitCard(deck);
    }

    @Test(expected = DrawingAtBurstStateException.class)
    public void 플레이어는_카드패_총합이_21초과일때_히트할수없다() {
        Deck deck = new Deck(1);
        Player player1 = new Player("Kim", 20000);
        player1.hitWithCertainCard(deck, Suit.DIAMONDS, 10);
        player1.hitWithCertainCard(deck, Suit.DIAMONDS, 5);
        player1.hitWithCertainCard(deck, Suit.DIAMONDS, 6);
        player1.hitCard(deck);
    }

}
