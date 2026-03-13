package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InformationScene {
    public static Scene info(Stage stage) {
        Text info = new Text("This Program was done by Vladyslav Monke and is free to use");
        info.setId("text");
        Button exit = new Button("back");
        exit.setOnMouseClicked(mouseEvent -> {
            stage.setScene(StartingScene.startScene(stage));
        });
        VBox root = new VBox(20);
        root.getChildren().addAll(info, exit);
        root.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.BASELINE_CENTER);
        Scene scene = new Scene(root, 1500, 1000);
        scene.getStylesheets().add(StartingScene.class.getResource("style.css").toExternalForm());
        return scene;
    }
}
