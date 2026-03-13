package core.runnable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpaceFact {
    public static JsonNode getSpaceFact() throws URISyntaxException, IOException, InterruptedException {
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
        String urlImage = jsonNode.get("url").asText();
        String explanation = jsonNode.get("explanation").asText();
        explanation = explanation = explanation.replaceAll("([.!?])\\s*", "$1\n");

        return jsonNode;
    }
}
