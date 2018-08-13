package com.prohit.game.rummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final List<Player> players = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("*** Game of Rummy ***");

            System.out.println("Please enter number of players(2-4): ");
            final Integer noOfPlayers = scanner.nextInt();

            if (2 > noOfPlayers || 4 < noOfPlayers) {
                System.out.println(String.format("Can not play this game with %s players. Minimum 2 or maximum 4 players are required.", noOfPlayers));
                System.exit(0);
            }

            for (int i = 1; i <= noOfPlayers; i++) {
                players.add(new Player("Player-" + i));
            }

            scanner.nextLine();
            System.out.println("Do you want to include Joker (Y|N)? ");
            String includeJokerInput = scanner.nextLine();
            Boolean includeJoker = false;
            if ("Y".equalsIgnoreCase(includeJokerInput) || "YES".equalsIgnoreCase(includeJokerInput)) {
                includeJoker = true;
            }

            Deck deck = new Deck(includeJoker);
            deck.shuffle();
            deck.distribute(players);

            System.out.println("Let's start the game: ");

            Integer turn = new Random().nextInt(players.size());
            while(true) {


            }
        }
    }
}
