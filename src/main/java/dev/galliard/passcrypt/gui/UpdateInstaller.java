package dev.galliard.passcrypt.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class UpdateInstaller implements Runnable {
    public static final String DOWNLOADS_FOLDER = System.getProperty("user.home") + "\\Downloads";

    public static void downloadFile(String url, String targetDirectory) throws IOException {
        URL fileUrl = new URL(url);
        try (InputStream in = fileUrl.openStream()) {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            Path target = new File(targetDirectory + File.separator + fileName).toPath();
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void executeDownloadedExe(String directory, String fileName) throws IOException {
        String exePath = directory + File.separator + fileName;
        ProcessBuilder processBuilder = new ProcessBuilder(exePath);
        processBuilder.start();
    }

    @Override
    public void run() {
        try {
            String LATEST_VERSION = UpdateChecker.getLatestRelease("https://api.github.com/repos/ExceptionMaster/ExPasswordManager/releases/latest");
            String DOWNLOAD_URL = "https://github.com/ExceptionMaster/ExPasswordManager/releases/download/" + LATEST_VERSION + "/ExPassManagerSetup-" + LATEST_VERSION.replace("v","") + ".exe";
            downloadFile(DOWNLOAD_URL, DOWNLOADS_FOLDER);
            executeDownloadedExe(DOWNLOADS_FOLDER, "ExPassManagerSetup-" + LATEST_VERSION.replace("v","") + ".exe");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
