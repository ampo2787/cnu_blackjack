package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.NoMoreCardException;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeckTest {

    @Test
    public void Deck_2개에서_총_카드수는_104장이다() {
        Deck deck = new Deck(2);
        assertThat(deck.getTotalCard(), is(104));
    }

    @Test
    public void Deck_5개에서_총_카드수는_260장이다() {
        Deck deck = new Deck(5);
        assertThat(deck.getTotalCard(), is(260));
    }

    @Test
    public void 한개의_Deck에서_하나의_카드를_뽑으면_51장의_카드가_남는다() {
        Deck deck = new Deck(1);
        deck.drawCard();
        assertTrue(deck.getTotalCard() == 51);
    }

    @Test(expected = NoMoreCardException.class)
    public void 빈_덱에서_카드를_뽑으면_예외가_발생한다() {
        Deck deck = new Deck(1);
        int totalCardNum = deck.getTotalCard();
        for (int i = 0; i < totalCardNum; i++) {
           deck.drawCard();             // 2018-04-28 수정, 출력 구문 삭제(김연훈)
        }
        deck.drawCard();
    }
}
