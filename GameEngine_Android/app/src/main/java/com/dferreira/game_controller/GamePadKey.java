package com.dferreira.game_controller;

import com.dferreira.commons.IEnum;

/**
 * represents the keys of the virtual Pad in the game
 */
public enum GamePadKey implements IEnum {

    up,
    down,
    left,
    right,
    x,
    triangle,
    square,
    circle,

    numOfKeys;


    /**
     * The value of the enumeration
     */
    @Override
    public int getValue() {
        return this.ordinal();
    }
}
