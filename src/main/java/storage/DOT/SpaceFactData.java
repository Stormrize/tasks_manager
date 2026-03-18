package storage.DOT;

public class SpaceFactData {
    private final String title;
    private final String description;
    private final String URI;

    public SpaceFactData(String title, String description, String URI) {
        if (title != null && description != null && URI != null) {
            this.title = title;
            this.description = description;
            this.URI = URI;
        } else {
            throw new IllegalArgumentException("parameters should not be null");
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return URI;
    }
}