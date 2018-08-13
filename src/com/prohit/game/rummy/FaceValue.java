package com.prohit.game.rummy;

enum FaceValue {

    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    JOKER(25);

    FaceValue(Integer value) {
        this.value = value;
    }

    private final Integer value;

    public Integer getValue() {
        return this.value;
    }
}
