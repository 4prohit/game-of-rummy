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

            System.out.print("Please enter score to eliminate players: ");
            final Integer eliminationScore = scanner.nextInt();
            System.out.print("\n");

            scanner.nextLine();
            System.out.print("Do you want to include Joker (Y|N)? ");
            String includeJokerInput = scanner.nextLine();
            System.out.print("\n");
            Boolean includeJoker = false;
            if ("Y".equalsIgnoreCase(includeJokerInput) || "YES".equalsIgnoreCase(includeJokerInput)) {
                includeJoker = true;
            }

            System.out.println("Initializing deck for game...");
            Deck deck = new Deck(includeJoker, eliminationScore);
            deck.shuffle();
            System.out.print(deck);
            System.out.print("\n\n");

            // Keep playing until only winning player is remaining based on elimination score.
            while (1 < players.size()) {

                System.out.println("Starting new game...");
                System.out.print("\n\n");

                // Reset deck
                deck.initialize();
                deck.shuffle();

                // Reset cards of all players
                System.out.println("Current scores of all players...");
                players.forEach(player -> {
                    player.getCards().clear();
                    System.out.println("\t" + player.getName() + "=> " + player.getScore());
                });
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

                // Selecting first turn randomly
                Integer currentPlayerId = new Random().nextInt(players.size());

                while (true) {
                    Boolean result = false;
                    // If player has drawn Joker in previous turns then he will miss 2 turns.
                    Integer missingTurns = players.get(currentPlayerId).getMissingTurns();
                    if (null != missingTurns && 0 < missingTurns) {
                        if (1 < missingTurns) {
                            // Skip current turn and decrease missed turns count.
                            players.get(currentPlayerId).setMissingTurns(--missingTurns);
                        }
                    } else {
                        // Draw card and commence new turn
                        result = players.get(currentPlayerId).draw(scanner, deck);
                    }
                    if (result) {
                        calculateScore(deck, players, players.get(currentPlayerId).getName());
                        break;
                    }
                    // Next player's turn in round-robin manner
                    currentPlayerId++;
                    if (players.size() <= currentPlayerId) {
                        currentPlayerId = 0;
                    }
                }
            }
            System.out.println("\nGame Over");
        }
    }

    private static void calculateScore(Deck deck, List<Player> players, String name) {
        System.out.println("Calculating score for each player...");
        for (Player player : players) {
            if (player.getName().equals(name)) {
                System.out.println("\t" + player.getName() + " is a winner !!!");
            } else {
                Integer currentScore = 0;
                for (Card card : player.getCards()) {
                    currentScore += card.getFaceValue().getValue();
                }
                System.out.println("\t" + player.getName() + " has a score of " + currentScore);
                player.setScore(player.getScore() + currentScore);
            }
        }
        System.out.print("\n");
        // Remove players whose score has crossed elimination score
        System.out.println("Removing player whose score has crossed elimination score of " + deck.getEliminationScore());
        players.removeIf(player -> (null != player.getScore() && deck.getEliminationScore() <= player.getScore()));
        System.out.print("\n\n");
    }
}
