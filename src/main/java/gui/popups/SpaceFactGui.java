package gui.popups;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import storage.DOT.SpaceFactData;

public class SpaceFactGui {

    public static void show(SpaceFactData data) {
        Stage stage = new Stage();

        VBox vbox = new VBox(10);

        Text title = new Text(data.getTitle());
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Text description = new Text(data.getDescription());
        description.setWrappingWidth(600);
        description.setStyle("-fx-font-size: 18px;");

        ImageView imageView = new ImageView(new Image(data.getUri()));
        imageView.setFitWidth(600);
        imageView.setPreserveRatio(true);

        vbox.getChildren().addAll(title, imageView, description);

        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 800, 1080);

        stage.setScene(scene);
        stage.setTitle("Space Fact");
        stage.show();
    }
}