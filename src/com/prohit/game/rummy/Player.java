package com.prohit.game.rummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {

    private String name;

    private List<Card> cards;

    Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    List<Card> getCards() {
        return cards;
    }

    void sort() {
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
        System.out.print("\tDo you want to use this card (Y|N)? ");
        String useCard = scanner.nextLine();
        System.out.print("\n");
        if ("Y".equalsIgnoreCase(useCard) || "YES".equalsIgnoreCase(useCard)) {
            if (arrangeCards(scanner, deck, discardedCard)) {
                return true;
            }
        } else {
            deck.getDiscardedPile().push(discardedCard);
            System.out.println("\tDrawing new card from the stock... ");
            Card newCard = deck.getStock().pop();
            System.out.println("\tNew card: " + newCard);
            if (FaceValue.JOKER.equals(newCard.getFaceValue())) {
                System.out.print("\n");
                System.out.println("\t" + name + ", you will miss next 2 turns because of drawing Joker...");
                System.out.print("\n");
                deck.getMissedTurnCount().put(name, 2);
            } else {
                System.out.print("\tDo you want to use this card (Y|N)? ");
                useCard = scanner.nextLine();
                System.out.print("\n");
                if ("Y".equalsIgnoreCase(useCard) || "YES".equalsIgnoreCase(useCard)) {
                    if (arrangeCards(scanner, deck, newCard)) {
                        return true;
                    }
                } else {
                    deck.getDiscardedPile().push(newCard);
                }
            }
        }
        return false;
    }

    private boolean arrangeCards(Scanner scanner, Deck deck, Card currentCard) {
        System.out.print("\tRemove one card(1-7): ");
        Card removedCard = cards.remove(scanner.nextInt() - 1);
        System.out.print("\n");
        System.out.println("\tYou removed: " + removedCard);
        cards.add(currentCard);
        sort();
        deck.getDiscardedPile().push(removedCard);
        scanner.nextLine();
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
