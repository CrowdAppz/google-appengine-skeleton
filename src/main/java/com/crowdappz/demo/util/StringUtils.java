package com.crowdappz.demo.util;

import java.util.Random;


public class StringUtils {

    // ================ Constants ============================================== //

    // ================ Members ================================================ //

    // ================ Constructors & Main ==================================== //

    // ================ Methods for/from SuperClass / Interfaces =============== //

    // ================ Methods ================================================ //

    /**
     * Checks whether the given string is null or empty.
     *
     * @param string the string to check
     * @return true if the string is null or empty otherwise returns false
     */
    public static boolean isNullOrEmpty(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Strips surrounding quoatationmarks from the given string and returns a cleaned version.
     * When there are no quotationmarks nothing happens. When the given string is null an empty string is returned
     *
     * @param name the string to clean
     * @return a cleaned string
     */
    public static String clearQuotationMarks(String name) {
        if (name == null) {
            return "";
        }

        if (name.startsWith("\"") && name.endsWith("\"")) {
            return name.substring(1, name.length() - 1);
        }
        return name;
    }

    public static String createRandomString(int randomStringLength) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray(); // allowed chars
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < randomStringLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static Long convertToLong(String string, Long defaultValue) {
        if (StringUtils.isNullOrEmpty(string)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }

    }

    // ================ Getter & Setter ======================================== //

    // ================ Inner & Anonymous Classes ============================== //
}