package com.dferreira.game_controller;

/**
 * Used handle the entering of data from the user
 */
public class GamePad {

    private static final boolean[] keysAreDown = new boolean[GamePadKey.numOfKeys.getValue()];

    /**
     *
     * @param selectedKey key to check
     *
     * @return  Flag that indicates if the key was pressed or not
     */
    public static boolean isKeyDown(GamePadKey selectedKey) {
        return keysAreDown[selectedKey.getValue()];
    }

    /**
     * Called when the user presses on of the virtual buttons
     *
     * @param selectedKey the key that the user have clicked
     * @param clicked       Flag that indicates if is to indicate clicked or not
     */
    public static void setKey(GamePadKey selectedKey, boolean clicked) {
        keysAreDown[selectedKey.getValue()] = clicked;
    }
}
