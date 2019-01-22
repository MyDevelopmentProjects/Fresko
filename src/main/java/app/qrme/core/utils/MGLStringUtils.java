package app.qrme.core.utils;

import java.util.regex.Pattern;

/*
 *  StringUtils.java
 *  Created by George Vashakidze.
 *  Copyright (c) 2015 Selfin LLC. All rights reserved.
 *
 */
public class MGLStringUtils {
    /**
     * Return a substring of the source string up until the first
     * occurrence of the specified character
     *
     * @param source - The source string to operate on
     * @param c      - Character to search for
     * @return - Substring of the original string up until the first occurance of c
     */
    public static String substringUpToFirstOccurance(String source, char c) {
        return !IsNullOrBlank(source) && !Character.isWhitespace(c) ?
                source.substring(0, source.indexOf(c)) : null;
    }

    /**
     * Return a substring of the source string after the first
     * occurrence of the specified character
     *
     * @param source - The source string to operate on
     * @param c      - Character to search for
     * @return - Substring of the original string after the first occurrence of c
     */
    public static String substringFromFirstOccurance(String source, char c) {
        return !IsNullOrBlank(source) && !Character.isWhitespace(c) ?
                source.substring(source.indexOf(c), source.length() - 1) : null;
    }

    /**
     * Return a substring of the source string up until the last
     * occurrence of the specified character
     *
     * @param source - The source string to operate on
     * @param c      - Character to search for
     * @return - Substring of the original string up until the last occurrence of c
     */
    public static String substringUpToLastOccurance(String source, char c) {
        return !IsNullOrBlank(source) && !Character.isWhitespace(c) ?
                source.substring(0, source.lastIndexOf(c)) : null;
    }

    /**
     * Return a substring of the source string after the last
     * occurrence of the specified character
     *
     * @param source - The source string to operate on
     * @param c      - Character to search for
     * @return - Substring of the original string after the last occurrence of c
     */
    public static String substringFromLastOccurance(String source, char c) {
        return !IsNullOrBlank(source) && !Character.isWhitespace(c) ?
                source.substring(source.lastIndexOf(c), source.length() - 1) : null;
    }

    /**
     * Return a substring of the source string after the first occurrence
     * of the search String
     *
     * @param source       - The source string to operate on
     * @param searchString - String to search for
     * @return - Substring of the original string after the first occurrence of searchString
     */
    public static String substringAfterSearchString(String source, String searchString) {
        if (!IsNullOrBlank(source) && !IsNullOrBlank(searchString)) {
            int index = source.indexOf(searchString);
            return index > -1 ? source.substring((index + searchString.length()), source.length() - 1) : null;
        } else {
            return null;
        }
    }

    /**
     * @param param - The source string to operate on
     * @return - Returns True If param is null or param characters length == 0
     */
    public static boolean IsNullOrBlank(String param) {
        if (param != null && !param.isEmpty()) {
            return false;
        }
        return true;
    }

    public static String replaceBetween(String input,
                                        String start, String end,
                                        String replaceWith) {
        return replaceBetween(input, start, end, false, false, replaceWith);
    }

    public static String replaceBetween(String input,
                                        String start,
                                        String end,
                                        boolean startInclusive,
                                        boolean endInclusive,
                                        String replaceWith) {
        start = Pattern.quote(start);
        end = Pattern.quote(end);
        return input.replaceAll("(" + start + ")" + ".*" + "(" + end + ")",
                (startInclusive ? "" : "$1") + replaceWith + (endInclusive ? "" : "$2"));
    }

    //without regex
    public static String replaceBetweenWithoutRegex(String str,
                                                    String start, String end,
                                                    boolean startInclusive,
                                                    boolean endInclusive,
                                                    String replaceWith) {
        int i = str.indexOf(start);
        while (i != -1) {
            int j = str.indexOf(end, i + 1);
            if (j != -1) {
                String data = (startInclusive ? str.substring(0, i) : str.substring(0, i + start.length())) +
                        replaceWith;
                String temp = (endInclusive ? str.substring(j + end.length()) : str.substring(j));
                data += temp;
                str = data;
                i = str.indexOf(start, i + replaceWith.length() + end.length() + 1);
            } else {
                break;
            }
        }
        return str;
    }

}
