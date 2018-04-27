package com.cnu.blackjack;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HandTest {

    @Test
    public void 핸드는_카드를_한장씩_받을수_있다() {
        Deck deck = new Deck(1);
        Hand hand = new Hand();
        hand.drawCard(deck);
        List<Card> currentCardList = hand.getCardList();
        assertThat(currentCardList.size(), is(1));

        hand.drawCard(deck);
        currentCardList = hand.getCardList();
        assertThat(currentCardList.size(), is(2));
    }
}
