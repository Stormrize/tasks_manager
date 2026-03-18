package gui.stages.commands;

import cli.CommandRegistry;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddCommand {

    public static Scene addCommandScene() {

        TextField taskName = new TextField();
        taskName.setPromptText("Enter task name");

        ChoiceBox<Integer> priority = new ChoiceBox<>();
        priority.getItems().addAll(1,2,3,4,5);

        TextField executeAt = new TextField();
        executeAt.setPromptText("When should it be executed <s;min,h,days>");

        Button add = new Button("Add Task");

        add.setOnAction(e -> {

            String command =
                    "add --n " + taskName.getText()
                            + " --pr " + priority.getValue()
                            + " --in " + executeAt.getText();

            CommandRegistry.get(command);
        });

        VBox root = new VBox(20);
        root.getChildren().addAll(taskName, priority, executeAt, add);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1500, 1000);
        scene.getStylesheets().add(
                AddCommand.class.getResource("/gui/style.css").toExternalForm()
        );

        return scene;
    }
}