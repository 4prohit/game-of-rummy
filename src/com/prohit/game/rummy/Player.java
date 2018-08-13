package com.prohit.game.rummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {

    private String name;

    private List<Card> cards;

    private Integer missingTurns;

    private Integer score;

    Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
        this.missingTurns = 0;
    }

    public String getName() {
        return name;
    }

    List<Card> getCards() {
        return cards;
    }

    public Integer getMissingTurns() {
        return missingTurns;
    }

    public void setMissingTurns(Integer missingTurns) {
        this.missingTurns = missingTurns;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    void sort() {
        // Sort cards according to face values in increasing order.
        cards.sort((card1, card2) -> {
            if (card1.getFaceValue().getValue().equals(card2.getFaceValue().getValue()))
                return 0;
            else if (card1.getFaceValue().getValue() > card2.getFaceValue().getValue())
                return 1;
            else
                return -1;
        });
    }

    Boolean draw(Scanner scanner, Deck deck) {

        System.out.println(toString());

        Card discardedCard = deck.getDiscardedPile().pop();
        System.out.println("\tCurrent discarded card: " + discardedCard);

        System.out.print("\tDo you want to use discarded card (Y|N)? ");
        String useCard = scanner.nextLine();
        System.out.print("\n");

        if ("Y".equalsIgnoreCase(useCard) || "YES".equalsIgnoreCase(useCard)) {
            arrangeCards(scanner, deck, discardedCard);
        } else {

            deck.getDiscardedPile().push(discardedCard);

            System.out.println("\tDrawing new card from the stock... ");
            Card newCard = deck.getStock().pop();
            System.out.println("\tNew card: " + newCard);

            if (FaceValue.JOKER.equals(newCard.getFaceValue())) {
                System.out.print("\n");
                System.out.println("\t" + name + ", you will miss next 2 turns because of drawing Joker !!!");
                System.out.print("\n");
                missingTurns = 2;
            } else {
                System.out.print("\tDo you want to use new card (Y|N)? ");
                useCard = scanner.nextLine();
                System.out.print("\n");
                if ("Y".equalsIgnoreCase(useCard) || "YES".equalsIgnoreCase(useCard)) {
                    arrangeCards(scanner, deck, newCard);
                } else {
                    deck.getDiscardedPile().push(newCard);
                }
            }
        }
        return checkMelds(scanner);
    }

    private void arrangeCards(Scanner scanner, Deck deck, Card currentCard) {

        System.out.print("\tRemove one card(1-7): ");
        Card removedCard = cards.remove(scanner.nextInt() - 1);
        System.out.print("\n");
        System.out.println("\tYou removed: " + removedCard);
        System.out.print("\n");
        scanner.nextLine();

        cards.add(currentCard);
        sort();
        deck.getDiscardedPile().push(removedCard);
    }

    private boolean checkMelds(Scanner scanner) {

        System.out.print("\tYour melds are ready? ");
        String meldReady = scanner.nextLine();
        System.out.print("\n");

        if ("Y".equalsIgnoreCase(meldReady) || "YES".equalsIgnoreCase(meldReady)) {
            System.out.println(toString());
            System.out.print("\n");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "\t" + name + ": " + cards;
    }
}
