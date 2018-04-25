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
            System.out.println("플레이어의 자본금을 입력하시오");
            PlayerSeed = scanner.nextInt();
            game.addPlayer(PlayerName, PlayerSeed);
            System.out.println("플레이어 수 : " + PlayerName);
            System.out.println("현재 플레이어 수 : " + i);
        }
        game.start();
    }

}
