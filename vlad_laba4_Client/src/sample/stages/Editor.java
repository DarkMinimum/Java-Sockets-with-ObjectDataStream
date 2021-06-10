package sample.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Editor extends BasicStage {


    public Editor(Stage stage) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/sample/fxml/sampleDrawer.fxml"));
        this.stage = stage;
        stage.setTitle("Client");
        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);



    }


}
