package dev.galliard.passcrypt.gui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.galliard.passcrypt.PassCrypt;
import dev.galliard.passcrypt.util.ExLogger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker implements Runnable{
    public UpdateChecker() {
        super();
    }

    @Override
    public void run() {
        try {
            String API_URL = "https://api.github.com/repos/ExceptionMaster/ExPasswordManager/releases/latest";
            if(getLatestRelease(API_URL).compareTo(PassCrypt.VERSION) > 0) {
                int answer = JOptionPane.showConfirmDialog(UIExPass.getFrame(), "Hay una nueva versión disponible. ¿Quieres descargarla?", "Actualización disponible", JOptionPane.OK_CANCEL_OPTION);
                if(answer == JOptionPane.OK_OPTION) {
                    new Thread(new UpdateInstaller()).start();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String getLatestRelease(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

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
