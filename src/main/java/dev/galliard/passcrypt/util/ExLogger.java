package dev.galliard.passcrypt.util;

public class ExLogger {
    public ExLogger(Class<?> clazz) {

    }

    public void error(String message, Throwable throwable) {
        System.out.println(ConsoleColor.RED + "[ERROR] " + message + "\nException: " + throwable.getMessage() + ConsoleColor.RESET);
    }

    public void error(String message) {
        System.out.println(ConsoleColor.RED + "[ERROR] " + message + ConsoleColor.RESET);
    }

    public void warn(String message) {
        System.out.println(ConsoleColor.YELLOW + "[WARN] " + message + ConsoleColor.RESET);
    }

    public void info(String message) {
        System.out.println(ConsoleColor.BLUE + "[INFO] " + message + ConsoleColor.RESET);
    }

    public void debug(String message) {
        System.out.println(ConsoleColor.CYAN + "[DEBUG] " + message + ConsoleColor.RESET);
    }

    public void success(String message) {
        System.out.println(ConsoleColor.GREEN + "[SUCCESS] " + message + ConsoleColor.RESET);
    }
}

