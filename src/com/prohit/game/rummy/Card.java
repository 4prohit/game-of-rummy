package com.prohit.game.rummy;

class Card {

    private Suit suit;

    private FaceValue faceValue;

    Card(Suit suit, FaceValue faceValue) {
        this.suit = suit;
        this.faceValue = faceValue;
    }

    FaceValue getFaceValue() {
        return faceValue;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        if (FaceValue.JOKER.equals(faceValue)) {
            return FaceValue.JOKER.toString();
        } else {
            return faceValue + "-" + suit;
        }
    }
}
