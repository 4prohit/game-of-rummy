package com.prohit.game.rummy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class Deck {

    private Stack<Card> stock;

    private Boolean includeJoker;

    private Stack<Card> discardedPile;

    private Map<String, Integer> missedTurnCount;

    Deck(Boolean includeJoker) {
        this.stock = new Stack<>();
        this.discardedPile = new Stack<>();
        this.missedTurnCount = new HashMap<>();
        this.includeJoker = includeJoker;
        initialize();
        shuffle();
        this.discardedPile.push(this.stock.pop());
    }

    Stack<Card> getStock() {
        // If current stock is empty during the game then put discarded pile back into stock.
        if (stock.isEmpty()) {
            Card discardedCard = this.discardedPile.pop();
            stock.addAll(discardedPile);
            shuffle();
            discardedPile.empty();
            discardedPile.push(discardedCard);
        }
        return stock;
    }

    public Stack<Card> getDiscardedPile() {
        return discardedPile;
    }

    public Map<String, Integer> getMissedTurnCount() {
        return missedTurnCount;
    }

    private void initialize() {
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
    }

    void shuffle() {
        Collections.shuffle(stock);
    }

    void distribute(List<Player> players) {
        // Distribute 7 cards to each player
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.getCards().add(stock.pop());
            }
            player.sort();
            System.out.println(player);
        }
    }

    @Override
    public String toString() {
        return "Current Deck: " +
                "\n\tStock(" + stock.size() + "): " + stock +
                "\n\tDiscarded Pile(" + discardedPile.size() + "): " + discardedPile;
    }
}
