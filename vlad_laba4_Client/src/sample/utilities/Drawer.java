package sample.utilities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import org.jdom2.Element;


public class Drawer {

    private static int height = 400;
    private static int width = 700;
    private static int  minRadius = 50;
    private static int  maxRadius = 150;

    public static void drawCircle(GraphicsContext gc, Color color, LinearGradient linearGradient) {

        for (int i = 0; i < Controller.counter; ++i) {
            int x = getRandomNumber(0, 700);
            int y = getRandomNumber(0, 400);
            int size = getRandomNumber(minRadius, maxRadius);

            Ellipse ellipse = new Ellipse(x, y, size, size);

            gc.setFill(color);
            gc.fillOval(x - 2, y - 2, size + 4, size + 4);
            gc.setFill(linearGradient);
            gc.fillOval(x, y, size, size);

            saveToXML(ellipse, linearGradient.getStops().get(0).getColor(), linearGradient.getStops().get(1).getColor(), color);

        }



    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void saveToXML(Shape shape, Color color1, Color color2, Color colorBorder) {

        Element element = new Element("Ellipse");
        Ellipse s = (Ellipse) shape;

        element.addContent(new Element("x").setText(String.valueOf(s.getCenterX())));
        element.addContent(new Element("y").setText(String.valueOf(s.getCenterY())));
        element.addContent((new Element("radiusX").setText(String.valueOf(s.getRadiusX()))));
        element.addContent((new Element("radiusY").setText(String.valueOf(s.getRadiusY()))));

        element.addContent((new Element("color1").setText(color1.toString())));
        element.addContent((new Element("color2").setText(color2.toString())));
        element.addContent((new Element("colorBorder").setText(colorBorder.toString())));
        Controller.doc.getRootElement().addContent(element);
    }
}
