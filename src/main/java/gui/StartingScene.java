package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartingScene {
    public static Scene startScene(Stage stage) {
        Text text = new Text("Tasks Manager");
        text.setId("text");
        Button start = new Button("start");
        start.setOnMouseClicked(mouseEvent -> {
            stage.setScene(TasksScene.tasks(stage));
        });
        Button info = new Button("information");
        info.setOnMouseClicked(mouseEvent -> {
            stage.setScene(InformationScene.info(stage));
        });
        Button exit = new Button("exit");
        exit.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        VBox root = new VBox(20);
        root.getChildren().addAll(text, start, info, exit);
        root.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.BASELINE_CENTER);
        Scene scene = new Scene(root, 1500, 1000);
        scene.getStylesheets().add(StartingScene.class.getResource("style.css").toExternalForm());
        return scene;
    }
}
