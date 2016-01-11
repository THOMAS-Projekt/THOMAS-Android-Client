package de.waishon.thomas.de.waishon.thomas.WebSocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import de.waishon.thomas.MainActivity;


/**
 * Created by Sören on 10.01.2016.
 */
public class ConnectionHandler extends WebSocketClient{

    public ConnectionHandler(final String ip) throws URISyntaxException {
        super(new URI("ws://" + ip + ":8080/socket"), new Draft_17(), new HashMap<String, String>(){{
            put("origin", ip);
        }}, 5);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(MainActivity.TAG, "Verbindung hergestellt");
    }

    @Override
    public void onMessage(String message) {
        Log.d(MainActivity.TAG, "Neue Nachricht: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(MainActivity.TAG, "Verbindung geschlossen");
    }

    @Override
    public void onError(Exception ex) {
        Log.d(MainActivity.TAG, "Error:" + ex.getStackTrace());
    }
}
