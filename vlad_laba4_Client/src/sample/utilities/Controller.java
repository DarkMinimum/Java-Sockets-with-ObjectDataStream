package sample.utilities;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import sample.Program;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public static final Logger log = Logger.getLogger(Program.class);
    FileChooser fc;
    public static int counter;
    @FXML
    Label label_current_tool;
    @FXML
    Canvas canvas_field;
    @FXML
    ColorPicker color_picker;
    @FXML
    ColorPicker color_picker1;
    @FXML
    ColorPicker color_picker2;
    @FXML
    private Spinner<Integer> spinner;
    public static Document doc = new Document().setRootElement(new Element("Objects"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(0);
        valueFactory.increment(2);

        spinner.setValueFactory(valueFactory);
    }

    @FXML
    private void generateCircles(ActionEvent event) {

        if (spinner.getValue() % 2 != 0) {
            label_current_tool.setTextFill(Color.RED);
            label_current_tool.setText("Last action: Failed to generate circles, the number of circles has to be even and in range [2;100]");
            return;

        }

        Stop[] stops = new Stop[]{new Stop(0, color_picker.getValue()), new Stop(1, color_picker1.getValue())};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        counter = spinner.getValue();

        Drawer.drawCircle(canvas_field.getGraphicsContext2D(), color_picker2.getValue(), lg1);

        label_current_tool.setTextFill(Color.BLACK);
        label_current_tool.setText("Last action: Generation was done");
    }

    @FXML
    private void eraserButton(ActionEvent event) {
        canvas_field.getGraphicsContext2D().clearRect(0, 0, canvas_field.getWidth(), canvas_field.getHeight());
        label_current_tool.setText("Last action: Erase was done");
        doc = new Document();
        doc.setRootElement(new Element("Ellipses"));
    }

    @FXML
    private void sendToServer(ActionEvent event) {

        try {
            SenderThread thread = new SenderThread(doc);
            thread.run();
            label_current_tool.setText("Last action: Send to server");
        }
        catch (Exception exception) {
            label_current_tool.setText("Last action: Send to server err " + exception.getMessage());
        }


    }


}
