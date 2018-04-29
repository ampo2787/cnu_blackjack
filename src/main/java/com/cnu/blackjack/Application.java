package com.cnu.blackjack;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int PlayerSeed, PlayerNum;
        String PlayerName;

        Deck deck = new Deck(1); //덱 생성
        Game game = new Game(deck); //

        System.out.println("블랙잭_CNU_REALCODING_5조\n플레이어 수를 입력하시오");
        PlayerNum = scanner.nextInt();


        for(int i=1;i<=PlayerNum;i++) {
            System.out.println("플레이어 이름을 입력하시오");
            PlayerName = scanner.nextLine();
            scanner.nextLine(); //scanner 중복입력 방지
            System.out.println("플레이어의 자본금을 입력하시오");
            PlayerSeed = scanner.nextInt();
            game.addPlayer(PlayerName, PlayerSeed);
            System.out.println("플레이어 수 : " + PlayerName);
            System.out.println("현재 플레이어 수 : " + i);

            System.out.println("현재 자금은 " +game.getPlayerList().get(PlayerName).getBalance() + " 입니다.\n얼마를 배팅하시겠습니까?");
            game.placeBet(PlayerName, scanner.nextInt());

        }
        System.out.println("게임을 시작합니다.");

        while(game.getPlayerList().size() == 0) {
            for (String key : game.getPlayerList().keySet()) { //Map의 key - 여기서는 플레이어네임.
                System.out.println(key + " 님의 차례입니다.");
                System.out.print("현재 가지고있는 카드는 ");
                if (game.getPlayerList().get(key).getHand().getCardList().size() == 0) {
                    System.out.println("없습니다. ");
                } else {
                    for (int i = 0; i < game.getPlayerList().get(key).getHand().getCardList().size(); i++) {
                        System.out.print(game.getPlayerList().get(key).getHand().getCardList().get(i));
                    }
                }
                System.out.println("Hit 하시겠습니까? (Y/N)");
                scanner.nextLine();
                String HitYN = scanner.nextLine();
                if (HitYN == "N\n") {
                    game.start();
                    break;
                } else if (HitYN == "Y\n") {
                    game.getPlayerList().get(key).hitCard();
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            }

        }

    }

}
