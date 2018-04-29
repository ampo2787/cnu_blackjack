package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.NoCertainCardAtDeck;
import com.cnu.blackjack.exceptions.NoMoreCardException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final int number;
    private final List<Card> cardList;

    public Deck(int number) {
        this.number = number;
        this.cardList = new ArrayList<Card>();
        createCards(number);
        Collections.shuffle(cardList);
    }

    private void createCards(int number) {
        // create card for single deck
        for (int j = 0; j < number; j++) {
            for (Suit suit : Suit.values()) {
                for (int i = 1 ; i < 14; i++) {
                    Card card = new Card(i, suit);
                    cardList.add(card);
                }
            }
        }
    }

    public int getTotalCard() {
        return cardList.size();
    }

    public Card drawCard() {
        if (cardList.size() == 0) {
            throw new NoMoreCardException();
        }
        return cardList.remove(0);
    }

    public Card drawCertainCard(Suit suit, int rank) {
    // 덱에서 특정한 숫자와 모양을 가진 카드를 뽑는 메소드. 테스트용 (2018-04-28 추가)
        for (int i = 0; i < getTotalCard(); i++) {
            Card card = cardList.get(i);
            if (card.getSuit() == suit && card.getRank() == rank) {
                return cardList.remove(i);
            }
        }
        throw new NoCertainCardAtDeck();
    }
}
