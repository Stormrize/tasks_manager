package storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Scheduler;
import core.Task;
import core.runnable.RunnableRegistry;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author Monke Vladyslav
 * @version 1.1
 */
public class Data {

    public static void loadTasks(Scheduler scheduler) {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.json"))) {
            String line;
            UUID id = null;
            String name = null;
            byte priority = 0;
            Instant executeAt = null;
            Runnable action = null;
            Duration repeatInterval = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // ID-Zeile: "123e4567-e89b-12d3-a456-426614174000": {
                if (line.startsWith("\"") && line.contains("\": {")) {
                    String key = line.substring(1, line.indexOf("\":"));
                    id = UUID.fromString(key);
                }
                // Name-Zeile
                else if (line.startsWith("\"name\"")) {
                    name = line.split(":")[1].trim()
                            .replace("\"", "")
                            .replace(",", "");
                }
                // Priority-Zeile
                else if (line.startsWith("\"priority\"")) {
                    priority = Byte.parseByte(
                            line.split(":")[1].trim().replace(",", "")
                    );
                }
                // executeAt-Zeile
                else if (line.startsWith("\"executeAt\"")) {
                    executeAt = Instant.parse(
                            line.split(":", 2)[1].trim().replace("\"", "").replace(",", "")
                    );
                } else if (line.startsWith("\"action\"")) {
                    action = RunnableRegistry.get(line.split(":")[1].trim()
                            .replace("\"", "")
                            .replace(",", ""));

                } else if (line.startsWith("\"repeatInterval\"")) {
                    String intervalStr = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                    repeatInterval = intervalStr.equals("null") ? null : Duration.parse(intervalStr);
                    scheduler.addTask(id, name, priority, executeAt, action, repeatInterval);      
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public static void saveTasks(Scheduler scheduler) {
        List<Task> tasks = scheduler.snapshot();

        try (FileWriter writer = new FileWriter("tasks.json")) {
            writer.write("{\n");
            int index = 0;
            for (Task task : tasks) {
                writer.write("  \"" + task.getId() + "\": {\n");
                writer.write("    \"name\": \"" + task.getName() + "\",\n");
                writer.write("    \"priority\": " + task.getPriority() + ",\n");
                writer.write("    \"executeAt\": \"" + task.getExecuteAT() + "\",\n");
                writer.write("    \"action\": \"" + task.getAction() + "\", \n");
                writer.write("    \"repeatInterval\": \"" + task.getRepeatInterval() + "\"\n");
                writer.write("  }");
                if (index < tasks.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
                index++;
            }
            writer.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveWallpapersURL(String query, int page) {
        try (BufferedReader apiReader = new BufferedReader(
                new FileReader("/home/stormrize/secure/pexelsAPI"))) {

            String apiKey = apiReader.readLine();
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.pexels.com/v1/search?query=" + query))
                    .header("Authorization", apiKey)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());
            JsonNode photos = jsonNode.get("photos");
            int totalResults = jsonNode.get("total_results").asInt();

            if (photos != null) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(
                        new FileWriter("/home/stormrize/Pictures/wallpapers/UrlOfImages"))) {

                    for (JsonNode photo : photos) {
                        String imgUrl = photo.get("src").get("original").asText();
                        bufferedWriter.write(imgUrl);
                        bufferedWriter.newLine();
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void deleteWallpapersURL() {
        File folder = new File("/home/stormrize/Pictures/wallpapers/UrlOfImages");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file: files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }
}
