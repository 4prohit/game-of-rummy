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

            System.out.print("Please enter number of players(2-4): ");
            final Integer noOfPlayers = scanner.nextInt();
            System.out.print("\n");

            if (2 > noOfPlayers || 4 < noOfPlayers) {
                System.out.println(String.format("Can not play this game with %s players. Minimum 2 or maximum 4 players are required.", noOfPlayers));
                System.exit(0);
            }

            for (int i = 1; i <= noOfPlayers; i++) {
                players.add(new Player("Player-" + i));
            }

            scanner.nextLine();
            System.out.print("Do you want to include Joker (Y|N)? ");
            String includeJokerInput = scanner.nextLine();
            System.out.print("\n");
            Boolean includeJoker = false;
            if ("Y".equalsIgnoreCase(includeJokerInput) || "YES".equalsIgnoreCase(includeJokerInput)) {
                includeJoker = true;
            }

            System.out.println("Initializing deck for new game...");
            Deck deck = new Deck(includeJoker);
            System.out.print(deck);
            System.out.print("\n\n");

            System.out.println("Shuffling cards...");
            deck.shuffle();
            System.out.print(deck);
            System.out.print("\n\n");

            System.out.println("Distributing cards to each player...");
            deck.distribute(players);
            System.out.print("\n");

            System.out.println("Shuffle again and start the game...");
            deck.shuffle();
            System.out.print("\n");

            Integer currentPlayerId = new Random().nextInt(players.size());

            while (true) {
                Boolean result = false;
                Integer missedCount = deck.getMissedTurnCount().get(players.get(currentPlayerId).getName());
                if (null != missedCount && 0 < missedCount) {
                    if (1 < missedCount) {
                        deck.getMissedTurnCount().put(players.get(currentPlayerId).getName(), --missedCount);
                    } else {
                        deck.getMissedTurnCount().remove(players.get(currentPlayerId).getName());
                    }
                } else {
                    result = players.get(currentPlayerId).draw(scanner, deck);
                }
                if (result) {
                    calculateScore(players, currentPlayerId);
                    break;
                }
                currentPlayerId++;
                if (players.size() <= currentPlayerId) {
                    currentPlayerId = 0;
                }
            }
        }
    }

    private static void calculateScore(List<Player> players, Integer currentPlayerId) {
        System.out.println("Calculating score for each player...");
        for (Player player : players) {
            if (player.getName().equals(players.get(currentPlayerId).getName())) {
                System.out.println("\t" + player.getName() + " is a winner !!!");
            } else {
                Integer score = 0;
                for (Card card : player.getCards()) {
                    score += card.getFaceValue().getValue();
                }
                System.out.println("\t" + player.getName() + " has a score of " + score);
            }
        }
    }
}
