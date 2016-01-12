package de.waishon.thomas.de.waishon.thomas.WebSocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Policy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import de.waishon.thomas.MainActivity;
import de.waishon.thomas.listeners.MethodResponseListener;
import de.waishon.thomas.listeners.SignalListener;


/**
 * Created by Sören on 10.01.2016.
 */
public class ConnectionHandler extends WebSocketClient {

    private ArrayList<SignalListener> signalListeners = new ArrayList<SignalListener>();
    private ArrayList<MethodResponseListener> methodResponseListeners = new ArrayList<MethodResponseListener>();

    public ConnectionHandler(final String ip) throws URISyntaxException {
        super(new URI("ws://" + ip + ":8080/socket"), new Draft_17(), new HashMap<String, String>() {{
            put("origin", ip);
        }}, 5);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(MainActivity.TAG, "Verbindung hergestellt");
    }

    @Override
    public void onMessage(String message) {
        try {
            JSONObject root = new JSONObject(message);

            switch (root.getString("action")) {
                case "methodResponse":
                    handleMethodResponse(root);
                    break;

                case "signalCalled":
                    handleSignal(root);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(MainActivity.TAG, "Verbindung geschlossen");
    }

    @Override
    public void onError(Exception ex) {
        Log.d(MainActivity.TAG, "Error:" + ex.getStackTrace());
    }

    public void addSignalListener(SignalListener listener) {
        this.signalListeners.add(listener);
    }

    public void addMethodResponseListener(MethodResponseListener listener) {
        this.methodResponseListeners.add(listener);
    }

    private void handleSignal(JSONObject root) throws JSONException {
        for (SignalListener sL : signalListeners) {

            Class<?>[] paramTypes = new Class<?>[0];
            String methodName = root.getString("signalName");

            for (Method method : SignalListener.class.getMethods()) {
                if (methodName.equals(method.getName())) {
                    paramTypes = method.getParameterTypes();
                }
            }

            try {
                Method method = SignalListener.class.getMethod(methodName, paramTypes);
                method.invoke(sL, this.getArgs(root.getJSONObject("args")));
            } catch (NoSuchMethodException e) {
                Log.w(MainActivity.TAG, "Not found - " + methodName);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleMethodResponse(JSONObject root) throws JSONException {
        Log.i(MainActivity.TAG, root.toString());

        for (MethodResponseListener mRL : methodResponseListeners) {

            Class<?>[] paramTypes = new Class<?>[0];
            String methodName = root.getString("methodName") + "Response";

            for (Method method : MethodResponseListener.class.getMethods()) {
                if (methodName.equals(method.getName())) {
                    paramTypes = method.getParameterTypes();
                }
            }

            try {
                Method method = MethodResponseListener.class.getMethod(methodName, paramTypes);
                method.invoke(mRL, root.get("returnedValue"));
            } catch (NoSuchMethodException e) {
                Log.w(MainActivity.TAG, "Not found - " + methodName);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object[] getArgs(JSONObject args) throws JSONException {
        Iterator keys = args.keys();
        ArrayList<Object> objects = new ArrayList<Object>();

        while (keys.hasNext()) {
            objects.add(args.get((String) keys.next()));
        }

        return objects.toArray();
    }
}
