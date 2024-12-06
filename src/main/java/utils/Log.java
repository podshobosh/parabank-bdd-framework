package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    // This method automatically gets the class of the calling method using StackTrace
    private static Logger getLogger() {
        // Get the name of the class from the calling method using StackTrace
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();  // [2] points to the calling class
        return LogManager.getLogger(className);
    }

    // Info log method
    public static void info(String message) {
        getLogger().info(message);
    }

    // Debug log method
    public static void debug(String message) {
        getLogger().debug(message);
    }

    // Error log method
    public static void error(String message, Throwable throwable) {
        getLogger().error(message, throwable);
    }

    // Warn log method
    public static void warn(String message) {
        getLogger().warn(message);
    }
}