package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.stages.Editor;

public class Program extends Application {

    public static Editor editor;

    @Override
    public void start(Stage primaryStage) throws Exception{

        editor = new Editor(primaryStage);
        editor.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
