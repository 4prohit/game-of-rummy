package com.prohit.game.rummy;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {

    private Stack<Card> stock = new Stack<>();

    private Boolean includeJoker;

    private Stack<Card> discardedPile = new Stack<>();

    public Deck(Boolean includeJoker) {
        this.includeJoker = includeJoker;
        initialize();
        shuffle();
    }

    private void initialize() {
        for (Suit suit : Suit.values()) {
            for (FaceValue faceValue : FaceValue.values()) {
                if (!FaceValue.JOKER.equals(faceValue)) {
                    stock.add(new Card(suit, faceValue));
                }
            }
        }
        if (includeJoker) {
            stock.add(new Card(null, FaceValue.JOKER));
            stock.add(new Card(null, FaceValue.JOKER));
            assert stock.size() == 54;
        } else {
            assert stock.size() == 52;
        }
        System.out.println("Stock: " + stock);
    }

    public void shuffle() {
        Collections.shuffle(stock);
    }

    public void distribute(List<Player> players) {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.getCards().add(stock.pop());
            }
            System.out.println(player.getName() + ": " + player.getCards());
        }
    }
}
