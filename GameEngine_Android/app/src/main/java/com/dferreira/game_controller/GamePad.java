package com.dferreira.game_controller;

/**
 * Used handle the entering of data from the user
 */
public class GamePad {
    public static final int KEY_UP = 0;
    public static final int KEY_DOWN = 1;
    public static final int KEY_LEFT = 2;
    public static final int KEY_RIGHT = 3;
    public static final int KEY_X = 4;
    public static final int KEY_TRIANGLE = 5;
    public static final int KEY_SQUARE = 6;
    public static final int KEY_CIRCLE = 7;

    private static boolean keysAreDown[] = new boolean[KEY_CIRCLE + 1];

    /**
     *
     * @param selectedKey key to check
     *
     * @return  Flag that indicates if the key was pressed or not
     */
    public static boolean isKeyDown(int selectedKey) {
        boolean bValue = keysAreDown[selectedKey];
        return bValue;
    }

    /**
     * Called when the user presses on of the virtual buttons
     *
     * @param selectedKey the key that the user have clicked
     * @param clicked       Flag that indicates if is to indicate clicked or not
     */
    public static void setKey(int selectedKey, boolean clicked) {
        keysAreDown[selectedKey] = clicked;
    }
}
