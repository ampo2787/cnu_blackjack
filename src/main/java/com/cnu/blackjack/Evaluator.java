package com.cnu.blackjack;

import java.util.Map;

public class Evaluator {

    private Map<String, Player> playerMap;
    private Dealer dealer;

    public Evaluator(Map<String, Player> playerMap) {
        this.playerMap = playerMap;
        dealer = new Dealer();
        dealCardToPlayers();
    }

    public Evaluator() {
        
    }

    public void start() {
        hitOrStay();
    }

    private void dealCardToPlayers() {
        playerMap.forEach((name, player) -> {
            player.hitCard();
            player.hitCard();
        });
    }

    public int getPlayerScoreOfFirstReceived(Player player) {
        //플레이어가 처음 받은 카드들의 합을 구하여 반환한다.
            int handValue = 0;
            for(int i=0; i < 2; i++) {
                handValue += player.getHand().getCardList().get(i).getRank();
            }
            return handValue;
    }

    private int getPlayerScore(Player player) {
        int handValue = 0;
        for(int i=0; i < player.getHand().getCardList().size(); i++) {
            handValue += player.getHand().getCardList().get(i).getRank();
        }
        return handValue;
    }

    private void hitOrStay() {
        //플레이어가 들고 있는 카드의 합이 17이 넘지 않으면 hit, 아니면 stay
        playerMap.forEach((name, player)-> {
            if(getPlayerScoreOfFirstReceived(player) < 17) {
                player.hitCard();
            }
        });
    }
  
    public void rewardToPlayer(Player player,int balance) {
        player.setBalance(player.getCurrentBet() * 2 + balance);
    }
}
