package core.runnable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.popups.RandomFactGui;
import javafx.application.Platform;
import storage.DOT.RandomFactData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RandomFact implements Runnable {
    public void run() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://uselessfacts.jsph.pl/api/v2/facts/random"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.body());
            String fact = json.get("text").asText();
            RandomFactData randomFactData = new RandomFactData(fact);

            Platform.startup(()-> {});
            Platform.runLater(() -> {
                RandomFactGui.show(randomFactData);
            });
        } catch(Exception e) {

        }
//        return fact.replaceAll("\\.", "\n");
    }
}
