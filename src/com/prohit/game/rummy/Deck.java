package com.prohit.game.rummy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {

    private List<Card> stock = new ArrayList();

    private Boolean withJoker;

    private Stack<Card> discardedPile = new Stack();

    public Deck(Boolean withJoker) {
        this.withJoker = withJoker;
        initialize();
    }

    private void initialize() {
        for (Suit suit : Suit.values()) {
            for (FaceValue faceValue : FaceValue.values()) {
                if (!FaceValue.JOKER.equals(faceValue)) {
                    stock.add(new Card(suit, faceValue));
                }
            }
        }
        if (withJoker) {
            stock.add(new Card(Suit.NA, FaceValue.JOKER));
            stock.add(new Card(Suit.NA, FaceValue.JOKER));
            assert stock.size() == 54;
        } else {
            assert stock.size() == 52;
        }
    }

    public void shuffle(List<Card> stock) {
        Collections.shuffle(stock);
    }
}
