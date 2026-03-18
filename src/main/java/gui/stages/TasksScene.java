package gui.stages;

import gui.stages.commands.AddCommand;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TasksScene {
    public static Scene tasks(Stage stage) {
        Text text = new Text("Tasks");
        text.setId("text");
        Button start = new Button("New Task");
        start.setOnMouseClicked(mouseEvent -> {
            stage.setScene(AddCommand.addCommandScene());
        });
        Button info = new Button("Task list");
        info.setOnMouseClicked(mouseEvent -> {

        });
        Button remove = new Button("Remove Task");
        remove.setOnMouseClicked(mouseEvent -> {

        });
        Button sort = new Button("Sort Tasks");
        sort.setOnMouseClicked(mouseEvent -> {

        });
        Button change = new Button("Change Task");
        change.setOnMouseClicked(mouseEvent -> {

        });
        Button help = new Button("Help");
        help.setOnMouseClicked(mouseEvent -> {

        });

        Button exit = new Button("back");
        exit.setOnMouseClicked(mouseEvent -> {
            stage.setScene(StartingScene.startScene(stage));
        });
        VBox root = new VBox(20);
        root.getChildren().addAll(text, start, info, remove, sort, change, help, exit);
        root.setAlignment(Pos.BASELINE_CENTER);

        Scene scene = new Scene(root, 1500, 1000);
        scene.getStylesheets().add(StartingScene.class.getResource("style.css").toExternalForm());
        return scene;
    }
}
