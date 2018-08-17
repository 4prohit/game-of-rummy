package com.prohit.game.rummy;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

class Deck {

    private Stack<Card> stock;

    private Boolean includeJoker;

    private Stack<Card> discardedPile;

    private Integer eliminationScore;

    Deck(Boolean includeJoker, Integer eliminationScore) {
        this.stock = new Stack<>();
        this.discardedPile = new Stack<>();
        if (null == includeJoker) {
            includeJoker = false;
        }
        this.includeJoker = includeJoker;
        if (null == eliminationScore || 0 >= eliminationScore) {
            eliminationScore = 101;
        }
        this.eliminationScore = eliminationScore;
        initialize();
    }

    /**
     * Create deck with 52 cards if joker is not included
     * Otherwise create 54 cards with 2 Jokers
     * Draw one card from new stock and put in discarded pile to start the game for first player
     */
    void initialize() {
        // Clear old stock and discarded pile
        stock.clear();
        discardedPile.clear();

        // Create new deck of cards with 4 suits each with 13 face values.
        for (Suit suit : Suit.values()) {
            for (FaceValue faceValue : FaceValue.values()) {
                if (!FaceValue.JOKER.equals(faceValue)) {
                    stock.add(new Card(suit, faceValue));
                }
            }
        }

        // If Joker is to be included in game, then add 2 Joker cards.
        if (includeJoker) {
            stock.add(new Card(null, FaceValue.JOKER));
            stock.add(new Card(null, FaceValue.JOKER));
            assert stock.size() == 54;
        } else {
            assert stock.size() == 52;
        }

        //  Draw one card from new stock and put in discarded pile for first player
        this.discardedPile.push(this.stock.pop());
    }

    void shuffle() {
        Collections.shuffle(stock);
    }

    /**
     * Distribute 7 cards to each player
     */
    void distribute(List<Player> players) {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.getCards().add(stock.pop());
            }
            player.sort();
            System.out.println(player);
        }
    }

    /**
     * Get current stock.
     * If current stock is empty during the game then put discarded pile back into stock.
     */
    Stack<Card> getStock() {
        if (stock.isEmpty()) {
            Card discardedCard = this.discardedPile.pop();
            stock.addAll(discardedPile);
            shuffle();
            discardedPile.empty();
            discardedPile.push(discardedCard);
        }
        return stock;
    }

    Stack<Card> getDiscardedPile() {
        return discardedPile;
    }

    Integer getEliminationScore() {
        return eliminationScore;
    }

    @Override
    public String toString() {
        return "Current Deck: " +
                "\n\tStock(" + stock.size() + "): " + stock +
                "\n\tDiscarded Pile(" + discardedPile.size() + "): " + discardedPile;
    }
}
