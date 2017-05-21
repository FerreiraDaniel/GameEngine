package com.dferreira.commons.utils;


import java.util.HashMap;
import java.util.List;

/**
 * Utils for every basic stuff
 */
public class Utils {
    public final static String EMPTY_STRING = "";

    /**
     * Indicates if the entity passed is null or empty
     *
     * @param entity The entity to check
     * @return True if is null or empty
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isEmpty(HashMap entity) {
        return (entity == null) || (entity.isEmpty());
    }

    /**
     * Indicates if the entity passed is null or empty
     *
     * @param entity The entity to check
     * @return True if is null or empty
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isEmpty(List entity) {
        return (entity == null) || (entity.isEmpty());
    }

    /**
     * Indicates if the entity passed is null or empty
     *
     * @param entity The entity to check
     * @return True if is null or empty
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static <T> boolean isEmpty(T[] entity) {
        return (entity == null) || (entity.length == 0);
    }
}
