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
        this.score = 0;
    }

    /**
     * Sort cards in ascending order of facevalues
     */
    void sort() {
        // Sort cards according to face values in increasing order.
        cards.sort((card1, card2) -> {
            if (card1.getFaceValue().getOrder().equals(card2.getFaceValue().getOrder()))
                return 0;
            else if (card1.getFaceValue().getOrder() > card2.getFaceValue().getOrder())
                return 1;
            else
                return -1;
        });
    }

    /**
     * Draw new card or use top card from discarded pile
     */
    Boolean draw(Scanner scanner, Deck deck) {

        System.out.println(toString());

        Card discardedCard = deck.getDiscardedPile().pop();
        System.out.println("\tCurrent discarded card: " + discardedCard);

        System.out.print("\tDo you want to use discarded card (Y|N)? ");
        String useCard = scanner.nextLine();
        System.out.print("\n");

        if ("Y".equalsIgnoreCase(useCard) || "YES".equalsIgnoreCase(useCard)) {
            removeCard(scanner, deck, discardedCard);
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
                    removeCard(scanner, deck, newCard);
                } else {
                    deck.getDiscardedPile().push(newCard);
                }
            }
        }
        return validateMelds();
    }

    /**
     * Remove unused card
     */
    private void removeCard(Scanner scanner, Deck deck, Card currentCard) {

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

    /**
     * Check if player has got all the required melds or not
     */
    private Boolean validateMelds() {

        // Sort cards in ascending order
        sort();

        Integer sameFaceValues = 1;
        Integer sequenceLength = 1;

        for (int i = 0; i < cards.size() - 1; i++) {

            // Previous card
            Card previous = null;
            if (0 < i) {
                previous = cards.get(i - 1);
            }

            // Current card
            Card current = cards.get(i);

            // Next card
            Card next = null;
            if (cards.size() - 1 > i) {
                next = cards.get(i + 1);
            }

            // Check if cards have same facevalue with different suit
            if (current.getFaceValue().equals(next.getFaceValue())) {
                if (!current.getSuit().equals(next.getSuit())) {

                    // If reset incomplete or invalid meld using sequence back to 1
                    if (3 > sequenceLength) {
                        sequenceLength = 1;
                    }

                    // Check if new meld  is different than existing or not. If yes add additional 1 point
                    if (3 == sameFaceValues && !previous.getFaceValue().equals(current.getFaceValue())) {
                        sameFaceValues++;
                    } else if (4 == sameFaceValues) {
                        sameFaceValues++;
                    }

                    sameFaceValues++;
                }
            } else {
                // Check if cards have sequence with same suit
                if ((current.getFaceValue().getOrder() + 1) == next.getFaceValue().getOrder()) {
                    if (current.getSuit().equals(next.getSuit())) {

                        // If reset incomplete or invalid meld using same facevalues back to 1
                        if (3 > sameFaceValues) {
                            sameFaceValues = 1;
                        }
                        sequenceLength++;
                    }
                }
            }
            // If no meld has been found for half of the cards then return false
            if (2 <= i && (3 > sameFaceValues && 3 > sequenceLength)) {
                return false;
            }
        }
        // If both melds have same cards then adjust the points of sequence meld
        if ((4 == sameFaceValues && 4 == sequenceLength) || (3 == sameFaceValues && 5 == sequenceLength)) {
            sequenceLength--;
        }
        if (7 == sameFaceValues || 7 == sequenceLength) {
            return true;
        } else if (3 == sameFaceValues && 4 == sequenceLength) {
            return true;
        } else if (3 == sequenceLength && 4 == sameFaceValues) {
            return true;
        } else {
            return false;
        }
    }

    String getName() {
        return name;
    }

    List<Card> getCards() {
        return cards;
    }

    Integer getMissingTurns() {
        return missingTurns;
    }

    void setMissingTurns(Integer missingTurns) {
        this.missingTurns = missingTurns;
    }

    Integer getScore() {
        return score;
    }

    void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "\t" + name + ": " + cards;
    }
}
