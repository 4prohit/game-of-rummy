package com.prohit.game.rummy;

enum FaceValue {

    ACE(1, 1),
    TWO(2, 2),
    THREE(3, 3),
    FOUR(4, 4),
    FIVE(5, 5),
    SIX(6, 6),
    SEVEN(7, 7),
    EIGHT(8, 8),
    NINE(9, 9),
    TEN(10, 10),
    JACK(10, 11),
    QUEEN(10, 12),
    KING(10, 13),
    JOKER(25, 14);

    FaceValue(Integer value, Integer order) {
        this.value = value;
        this.order = order;
    }

    private final Integer value;

    private final Integer order;

    public Integer getValue() {
        return this.value;
    }

    public Integer getOrder() {
        return order;
    }
}
