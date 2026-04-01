package core.runnable.WallpapersChanger;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WallpaperChanger implements Runnable{
    public void run() {
        int targetLine = 0;
        try {
            String imageUrl = Files.lines(Paths.get("/home/stormrize/Pictures/wallpapers/UrlOfImages"))
                    .skip(targetLine)
                    .findFirst()
                    .orElse(null);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(imageUrl)).build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            java.nio.file.Files.write(
                    java.nio.file.Paths.get("/home/stormrize/Pictures/wallpapersTaskManager/wallpaper0" + targetLine +".jpg"),
                    response.body()
            );
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        String command = "gsettings set org.gnome.desktop.background picture-uri-dark " +
                "\"file:///home/stormrize/Pictures/wallpapersTaskManager/wallpaper" + targetLine + "\"";

        try {
            Runtime.getRuntime().exec(command);
            System.out.println("Wallpaper changed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
