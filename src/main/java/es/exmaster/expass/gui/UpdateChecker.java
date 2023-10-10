package es.exmaster.expass.gui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.exmaster.expass.ExPasswordManager;
import es.exmaster.expass.util.ExLogger;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class UpdateChecker implements Runnable{
    private int answer = 0;
    private static final String GITHUB_ACCESS_TOKEN = "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";
    private final String VERSION_URL = "https://github.com/ExceptionMaster/ExPasswordManager/releases/latest";
    private final String API_URL = "https://api.github.com/repos/ExceptionMaster/ExPasswordManager/releases/latest";
    public UpdateChecker() {
        super();
    }

    @Override
    public void run() {
        while(true) {
            try {
                if(getLatestRelease(API_URL).compareTo(ExPasswordManager.VERSION) > 0) {
                    answer = JOptionPane.showConfirmDialog(UIExPass.getFrame(),"Hay una nueva versión disponible. ¿Quieres descargarla?","Actualización disponible",JOptionPane.YES_NO_OPTION);
                    if(answer == JOptionPane.YES_OPTION) {
                        try {
                            Desktop.getDesktop().browse(new URI(VERSION_URL));
                        } catch (IOException | URISyntaxException e) {
                            new ExLogger(ExPasswordManager.class).error(e.getMessage());
                        }
                    } else {
                        break;
                    }

                }
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Thread.sleep();
        }
    }

    private static String getLatestRelease(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

        String auth = "token " + GITHUB_ACCESS_TOKEN;
        connection.setRequestProperty("Authorization", auth);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());


                // Obtén el valor de 'tag_name' que contiene la versión.
                String version = jsonNode.get("tag_name").asText();
                new ExLogger(UpdateChecker.class).info("Versión nueva: " + version);

                return version;
            }
        } else {
            throw new IOException("Error en la solicitud HTTP: " + responseCode);
        }
    }


}
