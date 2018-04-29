package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.DrawingAtBurstStateException;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cardList;

    public Hand() {
        cardList = new ArrayList<Card>();
    }

    public Card drawCard(Deck deck) {
        if (getTotalValue() > 21) {         // 카드의 총합이 21이 넘는 상태에서는 덱에서 카드를 뽑을 수 없다.
            throw new DrawingAtBurstStateException();
        }
        Card card = deck.drawCard();
        cardList.add(card);
        return card;
    }

    public Card drawCertainCard(Deck deck, Suit suit, int rank) {
        Card card = deck.drawCertainCard(suit, rank);
        cardList.add(card);
        return card;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void clearHand() {
        cardList.clear();
    }

    public int getTotalValue() {
        List<Card> aceList = new ArrayList<Card>();
        int totalValue = 0;
        int size = cardList.size();
        for (int i = 0; i < size; i++) {    // 먼저 Ace 카드를 따로 빼놓고 합계를 계산함.
            Card card = cardList.get(i);
            if (card.getRank() == 1) {
                aceList.add(card);
            }
            else if (card.getRank() > 10) {
                totalValue += 10;
            }
            else {
                totalValue += cardList.get(i).getRank();
            }
        }
        for (int i = aceList.size(); i > 0; i--) { // Ace 카드들도 점수에 포함한다.
            if (i > 1 || (i == 1 && totalValue + 11 > 21)) { // Ace의 점수를 1로 계산해야 하는 경우
                totalValue += 1;
            }
            else {      // Ace를 11로 계산하는것이 유리한 경우
                totalValue += 11;
            }
        }
        return totalValue;
    }

    public void printHand() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cardList.size(); i++) {
            sb.append(cardList.get(i));
            sb.append("\n");
        }
        sb.append("Total value : " + getTotalValue());
        System.out.println(sb);
    }
  
    public void setCardList(Card card) {
        cardList.add(card);
    }
}
