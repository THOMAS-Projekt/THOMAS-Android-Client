package de.waishon.thomas.VideoStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import de.waishon.thomas.listeners.FrameReceivedListener;

/**
 * Created by Waishon on 12.08.2015.
 */
public class UDPServer extends Thread {

    public static final int UDP_PORT = 4222;
    // UDP-Socket
    DatagramSocket udpSocket;
    // Enthält alle Bild-Daten
    List<Byte> buffer = new ArrayList<Byte>();
    private Activity activity;
    private FrameReceivedListener frameReceivedListener;
    private Bitmap receivedBitmap;
    private BitmapFactory.Options options;

    public UDPServer(Activity activity, FrameReceivedListener frameReceivedListener) {
        this.activity = activity;
        this.frameReceivedListener = frameReceivedListener;

        // TODO: Evtl. Einstellungseite dafür erstellen
        this.options = new BitmapFactory.Options();
        this.options.inSampleSize = 2;
    }

    @Override
    public void run() {
        createServer(UDP_PORT);

        while (true) {
            receivedBitmap = receiveImageData();

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    frameReceivedListener.received(receivedBitmap);
                }
            });
        }

    }

    private void createServer(int port) {
        // Fehler abfangen
        try {
            // Neuen UDPServer erstellen
            udpSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private Bitmap receiveImageData() {

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

        // Enthält die Bild Bytes
        byte[] imageBytes = new byte[65535];

        // Data-Package
        DatagramPacket imageDataPacket;

        int receivedBytes = 0;

        do {
            // Daten-Paket abrufen
            imageDataPacket = new DatagramPacket(imageBytes, imageBytes.length);

            // Fehler abfangen
            try {
                // Daten empfangen
                udpSocket.receive(imageDataPacket);

                // Daten in Stream schreiben
                byteOutputStream.write(imageDataPacket.getData(), 0, imageDataPacket.getLength());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Solange die Paketlänge größer ist als das Array, sollen die Daten gespeichert werden
        } while (imageDataPacket.getLength() >= 64000);

        // Bild Daten zurückgeben
        return BitmapFactory.decodeStream(new ByteArrayInputStream(byteOutputStream.toByteArray()), null, options);

    }
}
