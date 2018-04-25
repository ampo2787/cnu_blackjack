package com.cnu.blackjack;

public class Application {


    public static void main(String[] args) {
        Game game = new Game(new Deck(1));
        game.addPlayer("player1", 10000);
        game.addPlayer("player2", 10000);
        game.start();   // 
    }

}
