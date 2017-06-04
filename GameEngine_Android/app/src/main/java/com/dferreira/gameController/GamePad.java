package com.dferreira.gameController;

/**
 * Used handle the entering of data from the user
 */
public class GamePad {

    /**
     * List of booleans to indicate if the key was pressed or not
     */
    private static final boolean[] keysAreDown = new boolean[GamePadKey.numOfKeys.getValue()];

    /**
     * @param key key to check
     * @return Flag that indicates if the key was pressed or not
     */
    public static boolean isDown(GamePadKey key) {
        return keysAreDown[key.getValue()];
    }

    /**
     * Called when the user presses on of the virtual buttons
     *
     * @param key     The key that the user have clicked
     * @param clicked Flag that indicates if is to indicate clicked or not
     */
    public static void set(GamePadKey key, boolean clicked) {
        keysAreDown[key.getValue()] = clicked;
    }
}
