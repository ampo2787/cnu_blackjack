package com.cnu.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Evaluator {

    private Map<String, Player> playerMap;
    private Dealer dealer;

    public Evaluator(Dealer dealer, Map<String, Player> playerMap) {
        this.dealer = dealer;
        this.playerMap = playerMap;
    }

    public void getBetFromDealer(Player player, boolean playerIsBlackJack) {
        player.setBalance(player.getBalance() + player.getCurrentBet() * (playerIsBlackJack ? 3 : 1));
        player.setCurrentBet(0);
    }

    public void giveBetToDealer(Player player, boolean dealerIsBlackJack) {
        player.setBalance(player.getBalance() - player.getCurrentBet() * (dealerIsBlackJack ? 3 : 1));
        player.setCurrentBet(0);
    }

    private void evaluatePlayer(Player player) {
        int dealerScore = dealer.getDealerScore();
        System.out.println("플레이어 " + player.getPlayerName() + "의 점수 : " + player.getHand().getTotalValue());
        if (player.getHand().getTotalValue() > 21) {    // 플레이어가 버스트인 경우
            System.out.println("플레이어 " + player.getPlayerName() + "가 Burst 되었으므로 딜러의 승리입니다.");
            giveBetToDealer(player, dealerScore == 21);
        }
        else if (player.getHand().getTotalValue() == dealerScore) { // 플레이어가 버스트가 아니고 동점인 경우
            System.out.println("무승부입니다.");
        }
        else if (player.getHand().getTotalValue() == 21) { // 동점이 아닌 상황에서 플레이어가 블랙잭인 경우
            System.out.println("플레이어 " + player.getPlayerName() + "가 블랙잭으로 승리하였습니다.");
            getBetFromDealer(player, true);
        }
        else {  // 플레이어의 점수가 21점 미만인 경우
            if (dealer.getDealerScore() > 21) {
                System.out.println("플레이어 " + player.getPlayerName() + "가 승리하였습니다.");
                getBetFromDealer(player, false);
            }
            else if (dealer.getDealerScore() == 21) {
                System.out.println("딜러가 블랙잭으로 승리하였습니다.");
                giveBetToDealer(player, true);
            }
            else {
                if (player.getHand().getTotalValue() > dealer.getDealerScore()) {
                    System.out.println("플레이어 " + player.getPlayerName() + "가 승리하였습니다.");
                    getBetFromDealer(player, false);
                }
                else {
                    System.out.println("딜러가 승리하였습니다.");
                    giveBetToDealer(player, false);
                }
            }
        }
    }

    public void evaluateAllPlayer() {
        playerMap.forEach((playerName, player)-> {
            evaluatePlayer(player);
        });

    }

    public int evaluateTotalValueOfPlayer(Player player) {
        List<Card> cardList = player.getHand().getCardList();
        List<Card> aceList = new ArrayList<>();
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

    //과제 제출용 메소드
    public void dealTwoCardsToPlayers(Deck deck) {
        dealer.dealOneCardToEveryPlayer(deck, playerMap);
        dealer.dealOneCardToEveryPlayer(deck, playerMap);
    }

    private boolean playerIsBlackJack(Player player) {
        return player.isBlackJack();
    }
}
