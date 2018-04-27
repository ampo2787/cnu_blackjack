package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.DrawingAtBurstStateException;
import com.cnu.blackjack.exceptions.NotEnoughBalanceException;
import lombok.Data;

@Data
public class Player {

    private String playerName;
    private int balance; //자금
    private int currentBet; //이번게임에서 쓸 자금
    private Hand hand; //카드패

    public Player(String playerName, int seedMoney) {
        this.playerName = playerName;
        this.balance = seedMoney;
        this.currentBet = 0;
        this.hand = new Hand();
    }

    public void placeBet(int bet) {
        if(balance < bet) {
            throw new NotEnoughBalanceException();
        }
        balance -= bet;
        currentBet = bet;
    }

    public Card hitCard(Deck deck) {
        return hand.drawCard(deck);
    }

    public Card hitWithCertainCard(Deck deck, Suit suit, int rank) {
    // 테스트용 메소드. 덱에서 특정한 카드를 선택하여 뽑을 수 있다. 2018-04-28 추가.
        return hand.drawCertainCard(deck, suit, rank);
    }

    public boolean isBlackJack() {
        return hand.getTotalValue() == 21;
    }
}
