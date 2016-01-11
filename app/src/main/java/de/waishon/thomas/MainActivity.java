package de.waishon.thomas;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.net.URISyntaxException;
import java.util.UUID;

import de.waishon.thomas.de.waishon.thomas.WebSocket.Arguments;
import de.waishon.thomas.de.waishon.thomas.WebSocket.ConnectionHandler;
import de.waishon.thomas.de.waishon.thomas.WebSocket.MethodBuilder;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "THOMAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Arguments args = new Arguments();
        args.addArg("motor", "left");
        args.addArg("speed", 255);

        final MethodBuilder builder = new MethodBuilder();
        builder.setMethodName("setMotorSpeed");
        builder.setArgs(args);

        try {
            final ConnectionHandler handler = new ConnectionHandler("192.168.3.12");
            handler.connect();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.send(builder.getJSON());
                    Log.i(TAG, builder.getJSON());
                }
            }, 2000);
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
}
