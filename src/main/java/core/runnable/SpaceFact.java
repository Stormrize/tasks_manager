package core.runnable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.popups.SpaceFactGui;
import javafx.application.Platform;
import storage.DOT.SpaceFactData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpaceFact implements Runnable {

    public void run() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/stormrize/secure/nasaAPI"));
            String apiKey = bufferedReader.readLine();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.nasa.gov/planetary/apod?api_key=" + apiKey))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());

            String title = jsonNode.get("title").asText();
            String explanation = jsonNode.get("explanation").asText();
            String description = explanation.replaceAll("([.!?])\\s*", "$1\n");
            String url = jsonNode.get("url").asText();

            SpaceFactData data = new SpaceFactData(title, description, url);

            Platform.startup(() -> {});
            Platform.runLater(() -> {
                SpaceFactGui.show(data);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

//        return jsonNode;
    }
}
