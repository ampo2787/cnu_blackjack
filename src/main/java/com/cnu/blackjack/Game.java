package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.DuplicatePlayerException;
import com.cnu.blackjack.exceptions.NotEveyonePlacedBetException;
import com.cnu.blackjack.exceptions.PlayerDoesNotExistException;

import java.util.HashMap;
import java.util.Map;

public class Game {

    private Map<String, Player> playerList = new HashMap<>();
    private Deck deck;


    public Game(Deck deck) {
        this.deck = deck;
    }

    public void addPlayer(String playerName, int seedMoney) {
        Player player = new Player(seedMoney, new Hand(deck));
        if (playerList.get(playerName) != null) {
            throw new DuplicatePlayerException();
        }
        playerList.put(playerName, player);
    }

    public Map<String, Player> getPlayerList() {
        return playerList;
    }

    public void start() {

        playerList.forEach((name, player) -> {
            if (player.getCurrentBet() == 0) {
                throw new NotEveyonePlacedBetException();
            }
            Dealer dealer = new Dealer();
            player.hitCard();

            int handValue = player.getHand().getCardList().size(); //플레이어 핸드 개수;
            int cardRankValue = 0;//플레이어 카드
            for(int i=0;i<handValue;i++){
                cardRankValue += player.getHand().getCardList().get(i).getRank();
            }
            if(cardRankValue > 21) { //21 이상이면 무조건 진다. 딜러가 21이상이어도 마찬가지.
                player.setCurrentBet(0);
                System.out.println("You LOSE!");
            }else {
                if (dealer.getDealerScore() == cardRankValue) {
                    player.setBalance(player.getBalance() + player.getCurrentBet());
                    player.setCurrentBet(0);
                    System.out.println("Draw!!");
                } else if (cardRankValue > dealer.getDealerScore()) {
                    player.setBalance(player.getBalance() + player.getCurrentBet() * 2);
                    player.setCurrentBet(0);
                    System.out.println("You WIN!");
                } else if (cardRankValue < dealer.getDealerScore()) {
                    player.setCurrentBet(0);
                    System.out.println("You LOSE!");
                } else if (cardRankValue == 21) {
                    player.setBalance(player.getBalance() + player.getCurrentBet() * 3);
                    player.setCurrentBet(0);
                    System.out.println("BLACK JACK!! YOU WIN!!!!");
                } else {
                    System.out.println("???");
                }

            }
        });


    }

    public void placeBet(String name, int bet) {
        Player player = playerList.get(name);
        if (player == null) {
            throw new PlayerDoesNotExistException();
        }
        player.placeBet(bet);
    }
}
