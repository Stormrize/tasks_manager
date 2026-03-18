package gui;

import gui.stages.StartingScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGui extends Application {

    public void start(Stage stage) {
        stage.setScene(StartingScene.startScene(stage));
        stage.setTitle("Tasks Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}