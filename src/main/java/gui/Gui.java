package gui;

import core.Scheduler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application {

    public void start(Stage stage) {
        stage.setScene(StartingScene.startScene(stage));
        stage.setTitle("Tasks Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}