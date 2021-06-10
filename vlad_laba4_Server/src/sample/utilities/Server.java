package sample.utilities;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import sample.Program;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int requestsCount = 0;
    private static final int SERVER_PORT = 8071;
    public static void run() {

        try {
            ServerSocket serv = new ServerSocket(SERVER_PORT);
            System.out.println("Server is started");

            while (true) {
                Socket sock = serv.accept();
                processRequest(sock);
                sock.close();
            }

        } catch (Exception e) {
            System.err.println("Error while running the server application:\t - " + e.getMessage());
        }
    }
    private static void processRequest(Socket sock) throws IOException, ClassNotFoundException {

        requestsCount++;
        Controller.canvas_main.getGraphicsContext2D().clearRect(0, 0, Controller.canvas_main.getWidth(), Controller.canvas_main.getHeight());

        System.out.println("Received " + requestsCount + " client request");
        ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
        int count = is.readInt();

        if(count > 0) {
            for (int i = 0; i < count; ++i) {


            SenderObject p=(SenderObject)is.readObject();
            Stop[] stops = new Stop[]
                    {
                            new Stop(0, new Color(p.getColorGradient_1().r, p.getColorGradient_1().g, p.getColorGradient_1().b, 1)),
                            new Stop(1, new Color(p.getColorGradient_2().r, p.getColorGradient_2().g, p.getColorGradient_2().b, 1))
                    };

            LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            drawCircle(Controller.canvas_main.getGraphicsContext2D(), new Color(p.getColorBorder().r, p.getColorBorder().g, p.getColorBorder().b, 1), lg1,  p.getCenterX(), p.getCenterY(), p.getSize());

            }
        }



        sock.close();

        Platform.runLater(new Runnable(){
            public void run(){
                Program.viewer.show();
                Controller.label_count.setText("Request number: " + requestsCount + " | Figures number: " + count);
            }
        });


    }
    public static void drawCircle(GraphicsContext gc, Color color, LinearGradient linearGradient,int x,int y, int size) {

        gc.setFill(color);
        gc.fillOval(x - 2, y - 2, size + 4, size + 4);
        gc.setFill(linearGradient);
        gc.fillOval(x, y, size, size);

    }
}
