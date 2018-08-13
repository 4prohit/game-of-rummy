package com.prohit.game.rummy;

public class Card {

    private Suit suit;

    private FaceValue faceValue;

    public Card(Suit suit, FaceValue faceValue) {
        this.suit = suit;
        this.faceValue = faceValue;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public FaceValue getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(FaceValue faceValue) {
        this.faceValue = faceValue;
    }

    @Override
    public String toString() {
        if(FaceValue.JOKER.equals(faceValue)){
            return FaceValue.JOKER.toString();
        } else {
            return faceValue + "-" + suit;
        }
    }
}
