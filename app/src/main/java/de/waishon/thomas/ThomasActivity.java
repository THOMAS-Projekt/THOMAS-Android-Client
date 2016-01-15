package de.waishon.thomas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.Menu;
import android.view.MotionEvent;

import java.net.URISyntaxException;

import de.waishon.thomas.Sensors.Joystick;
import de.waishon.thomas.WebSocket.ConnectionHandler;
import de.waishon.thomas.WebSocket.MethodHandler;


public class ThomasActivity extends AppCompatActivity {

    public static final String TAG = "THOMAS";
    private Joystick joystick;
    private MethodHandler methodHandler;
    private ConnectionHandler connectionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thomas);

        try {
            connectionHandler = new ConnectionHandler("192.168.3.12");
            connectionHandler.connect();

            methodHandler = new MethodHandler(connectionHandler);

            this.joystick = new Joystick(methodHandler);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent e) {
        if ((e.getSource() & InputDevice.SOURCE_JOYSTICK) != InputDevice.SOURCE_JOYSTICK || e.getAction() != MotionEvent.ACTION_MOVE) {
            return super.dispatchGenericMotionEvent(e);
        }

        joystick.onJoystickChange(e);

        return true;
    }

}
