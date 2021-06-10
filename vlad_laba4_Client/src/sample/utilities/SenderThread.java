package sample.utilities;

import javafx.scene.paint.Color;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import sample.Program;


import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class SenderThread extends Thread {

    private final static int SERVER_PORT = 8071;
    private Document doc;

    public SenderThread(Document doc) {

        this.doc = doc;
    }

    public void run() {

        if (doc.getRootElement().getContentSize() == 0) {
            System.err.println("No figures to send");
            return;
        }

       System.out.println("Sending figures information to the server");

        try {
            send(doc);
        } catch (IOException e) {
            System.err.println("Failed to send figures info");
        }

    }

    private void send(Document doc) throws IOException {

        Socket s = new Socket(InetAddress.getLocalHost(), SERVER_PORT);

        ObjectOutputStream ds = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
        int count = doc.getRootElement().getContentSize();

        System.out.println("Count of object: " + count);
        ds.writeInt(count);

        try {
            for(int i = 0; i < count; ++i) {

                String fullName = doc.getRootElement().getContent(i).toString();
                char name = fullName.charAt(fullName.lastIndexOf("<") + 1);
                String[] values = doc.getRootElement().getContent(i).getValue().split("\\.0");
                System.out.println(name + "\t" + doc.getRootElement().getContent(i).getValue());

                for (int j = 0; j < values.length - 1; ++j) {
                    System.out.println("\t - " + values[j]);


                }

                String colorThree = values[values.length - 1];
                String[] colors = colorThree.split("(?<=\\G..........)");
                SenderObject.Color_[] colors_list = new SenderObject.Color_[colors.length];

                for (int j = 0; j < colors.length; j++) {

                    Color color = Color.web(colors[j]);
                    colors_list[j] = new SenderObject.Color_(color.getRed(), color.getGreen(), color.getBlue());
                }

                SenderObject senderObject = new SenderObject(Integer.parseInt(values[0]),
                                                             Integer.parseInt(values[1]),
                                                             Integer.parseInt(values[2]),
                                                             colors_list[0], colors_list[1], colors_list[2]);

                ds.writeObject(senderObject);

            }


            ds.flush();
        }
        catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

    }




}
