package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.DuplicatePlayerException;
import com.cnu.blackjack.exceptions.NotEveyonePlacedBetException;
import com.cnu.blackjack.exceptions.PlayerDoesNotExistException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {

    Scanner scanner = new Scanner(System.in);

    private Dealer dealer;
    private Map<String, Player> playerList;
    private Deck deck;
    private Evaluator evaluator;

    public Game() {
        this.dealer = new Dealer();
        this.playerList = new HashMap<String, Player>();
        this.evaluator = new Evaluator(dealer, this.playerList);
    }

    public void addPlayer(String playerName, int seedMoney) {
        Player player = new Player(playerName, seedMoney);
        if (playerList.get(playerName) != null) {
            throw new DuplicatePlayerException();
        }
        playerList.put(playerName, player);
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Map<String, Player> getPlayerList() {
        return playerList;
    }

    public void initPlayers() {

        int PlayerSeed, PlayerNum;
        String PlayerName;

        System.out.print("블랙잭_CNU_REALCODING_5조\n플레이어 수를 입력하시오 : ");
        PlayerNum = Integer.parseInt(scanner.nextLine());

        for(int i = 0; i < PlayerNum; i++) {    // 플레이어 추가 루프
            System.out.print("플레이어 이름을 입력하시오 : ");
            PlayerName = scanner.nextLine();

            System.out.print("플레이어 \"" + PlayerName +"\"의 자본금을 입력하시오 : ");
            PlayerSeed = Integer.parseInt(scanner.nextLine());
            addPlayer(PlayerName, PlayerSeed);
        }
    }

    public void setDeckNumbers() {
        System.out.print("몇 개의 덱으로 게임하시겠습니까? (1덱 = 52장) : ");
        setDeck(new Deck(Integer.parseInt(scanner.nextLine())));
    }

    public void placeAllPlayersBet() {
        playerList.forEach((playerName, player)-> {
            System.out.println("플레이어 \'" + playerName + "\'의 현재 자금은 " + player.getBalance() +"원 입니다.");
            System.out.print("얼마를 배팅하시겠습니까? : ");
            player.placeBet(Integer.parseInt(scanner.nextLine()));
        });
        allPlayersBet();
    }

    public boolean allPlayersBet() {
        playerList.forEach((playerName, player)-> {
            if (player.getCurrentBet() == 0) {
                throw new NotEveyonePlacedBetException();
            }
        });
        return true;
    }

    public void eachPlayerHitOrStay() {
        playerList.forEach((playerName, player)-> {
            System.out.println(playerName + " 님의 차례입니다.");
            String yesOrNo;
            do {
                System.out.println(playerName + "님의 카드패와 카드패의 총합은 아래와 같습니다.");
                if (player.getHand().getCardList().size() == 0) {
                    System.out.println("없습니다.");
                }
                else {
                    System.out.println("===============");
                    player.getHand().printHand();
                    System.out.println("===============");
                }
                int totalValue = player.getHand().getTotalValue();
                if (totalValue > 21) {
                    System.out.println(player + "님이 " + totalValue + "점으로 Burst 되었습니다.");
                    break;
                }
                if (player.getHand().getTotalValue() == 21) {
                    System.out.println(playerName + "님이 BlackJack이므로 자동으로 Stay합니다.");
                    break;
                }
                else {
                    System.out.println();
                    System.out.println("Hit 하시겠습니까? (Y/N)");
                    yesOrNo = scanner.nextLine().toUpperCase();
                    switch (yesOrNo) {
                        case "Y":
                            player.hitCard(deck);
                        case "N":
                        default:
                            break;
                    }
                }
                System.out.println();
            } while (yesOrNo.equals("Y"));
        });
    }

    public void playersHandClear() {
        playerList.forEach((playerName, player)-> {
            player.getHand().clearHand();
        });
    }

    public int newGameInfo() {
        System.out.println("현재 플레이어 그대로 진행 : 1, 새 멤버로 다시 시작 : 2, 나머지 : 종료");
        System.out.print("입력 : ");
        int info = Integer.parseInt(scanner.nextLine());
        return info;
    }

    public void start() {
        boolean startFlag = true;
        do {
            if (playerList.isEmpty()) {
                initPlayers();
            }
            dealer = new Dealer();
            setDeckNumbers();

            dealer.getOneCardFromDeck(this.deck);                               // 딜러가 먼저 한장을 갖고
            dealer.dealOneCardToEveryPlayer(this.deck, this.playerList);      // 플레이어들에게 한장씩 돌린다.

            dealer.getOneCardFromDeck(this.deck);                               // 딜러가 먼저 한장을 갖고
            dealer.dealOneCardToEveryPlayer(this.deck, this.playerList);      // 플레이어들에게 한장씩 돌린다.
            // 딜러와 플레이어들은 총 두장씩 카드를 갖고 있다.

            System.out.println("\n현재 플레이어 수 : " + playerList.size());
            System.out.println("게임을 시작합니다.\n");

            placeAllPlayersBet();
            System.out.println("딜러가 가진 카드 중 한 장은 (" + dealer.getDealersFirstCard() + ")입니다.\n");

            eachPlayerHitOrStay();

            while (dealer.getDealerScore() < 17) {
                dealer.getOneCardFromDeck(deck);
            }

            System.out.println("\n딜러의 점수 : " + dealer.getDealerScore());

            evaluator.evaluateAllPlayer();
            int getNewGameInfo = newGameInfo();
            switch (getNewGameInfo) {
                case 1:
                    break;
                case 2:
                    playerList.clear();
                    break;
                default:
                    startFlag = false;
            }

            playersHandClear();
        } while (startFlag);
    }

    public void placeBet(String name, int bet) {
        Player player = playerList.get(name);
        if (player == null) {
            throw new PlayerDoesNotExistException();
        }
        player.placeBet(bet);
    }
}
