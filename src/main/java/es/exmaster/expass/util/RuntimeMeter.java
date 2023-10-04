package es.exmaster.expass.util;

public class RuntimeMeter {
    private static long startTime;
    private static long elapsedTime;

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static void stop() {
        long stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
    }

    public static long getElapsedTime() {
        return elapsedTime;
    }
}
