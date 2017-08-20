package com.dferreira.commons.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Utils for every basic stuff
 */
public class Utils {
	public final static String EMPTY_STRING = "";

	/**
	 * Indicates if the entity passed is null or empty
	 *
	 * @param entity
	 *            The entity to check
	 * @return True if is null or empty
	 */
	public static boolean isEmpty(Map entity) {
		return (entity == null) || (entity.isEmpty());
	}

	/**
	 * Indicates if the entity passed is null or empty
	 *
	 * @param entity
	 *            The entity to check
	 * @return True if is null or empty
	 */
	public static boolean isEmpty(List entity) {
		return (entity == null) || (entity.isEmpty());
	}

	/**
	 * Indicates if the entity passed is null or empty
	 *
	 * @param entity
	 *            The entity to check
	 * @return True if is null or empty
	 */
	public static <T> boolean isEmpty(T[] entity) {
		return (entity == null) || (entity.length == 0);
	}

	/**
	 * Returns true if the string is null or 0-length.
	 *
	 * @param str
	 *            the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}

	/**
	 * Indicates if the entity passed is null or empty
	 *
	 * @param entity
	 *            The entity to check
	 * @return True if is null or empty
	 */
	public static boolean isEmpty(Set entities) {
		return (entities == null || entities.size() == 0);
	}

}
