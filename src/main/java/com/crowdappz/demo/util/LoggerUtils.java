package com.crowdappz.demo.util;

public class LoggerUtils {
    // ================ Constants ============================================== //
    private static boolean LOGGING_ENABLED = true;
    private static boolean ERROR_LOGGING_ENABLED = true;
    // ================ Members ================================================ //

    // ================ Constructors & Main ==================================== //

    // ================ Methods for/from SuperClass / Interfaces =============== //
    public static void log(String textToLog) {
        if(LOGGING_ENABLED == true) {
            System.out.println(textToLog);
        }
    }

    public static void logError(String text, Throwable t){
        if(ERROR_LOGGING_ENABLED == true){
            System.err.println(text + " : " + t.getMessage());
        }
    }

    // ================ Methods ================================================ //

    // ================ Getter & Setter ======================================== //
    public static void disableLogging() {
        LOGGING_ENABLED = false;
    }

    public static void enableLogging() {
        LOGGING_ENABLED = true;
    }

    // ================ Inner & Anonymous Classes ============================== //
}