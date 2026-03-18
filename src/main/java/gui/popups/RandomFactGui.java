package gui.popups;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import storage.DOT.RandomFactData;

public class RandomFactGui {
    public static void show(RandomFactData randomFactData) {
        Stage stage = new Stage();
        VBox vBox = new VBox(10);
        Label fact = new Label(randomFactData.getFact());
        fact.setWrapText(true);
        fact.setStyle("-fx-font-size: 20px;");
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        vBox.getChildren().add(fact);
        Scene scene = new Scene(vBox, 400, 200);
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();
        stage.setScene(scene);
        stage.setX(bounds.getWidth() - sceneWidth);
        stage.setY(bounds.getHeight() - sceneHeight);
        stage.setTitle("Useless Fact");
        stage.show();
    }
}