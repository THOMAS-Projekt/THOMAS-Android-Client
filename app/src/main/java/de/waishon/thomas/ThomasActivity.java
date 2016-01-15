package de.waishon.thomas;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import de.waishon.thomas.Sensors.Joystick;
import de.waishon.thomas.UI.MethodResponseHandler;
import de.waishon.thomas.UI.SignalHandler;
import de.waishon.thomas.VideoStream.UDPServer;
import de.waishon.thomas.WebSocket.ConnectionHandler;
import de.waishon.thomas.WebSocket.MethodHandler;
import de.waishon.thomas.listeners.FrameReceivedListener;
import de.waishon.thomas.util.SystemUiHider;


public class ThomasActivity extends Activity {

    public static final String TAG = "THOMAS";

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 7000;
    private static final boolean TOGGLE_ON_CLICK = true;
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    Handler mHideHandler = new Handler();
    private SystemUiHider mSystemUiHider;
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    private View controlsView;
    private View contentView;
    private View telementryView;
    private ImageView imageView;
    private Joystick joystick;
    private MethodHandler methodHandler;
    private ConnectionHandler connectionHandler;
    private MethodResponseHandler methodResponseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thomas);

        controlsView = findViewById(R.id.thomas_content_controls);
        contentView = findViewById(R.id.thomas_layout_content);
        telementryView = findViewById(R.id.thomas_telemetry_layout);

        imageView = (ImageView) findViewById(R.id.thomas_imageview);

        new UDPServer(this, new FrameReceivedListener() {
            @Override
            public void received(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }).start();

        try {
            connectionHandler = new ConnectionHandler("192.168.3.12");
            connectionHandler.connect();

            connectionHandler.addSignalListener(new SignalHandler(this));

            methodHandler = new MethodHandler(connectionHandler);
            methodResponseHandler = new MethodResponseHandler(methodHandler);

            connectionHandler.addMethodResponseListener(methodResponseHandler);

            this.joystick = new Joystick(methodHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                methodHandler.startCameraStream("192.168.3.4", UDPServer.UDP_PORT);
                methodHandler.forceTelemetryUpdate();
            }
        }, 2000);


        ((SeekBar) findViewById(R.id.thomas_seekbar_quality)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                methodHandler.setCameraStreamOptions(methodResponseHandler.getStreamID(), progress, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
            // Cached values.
            int mControlsHeight;
            int telemetryViewHeight;
            int mShortAnimTime;

            @Override
            public void onVisibilityChange(boolean visible) {
                if (mControlsHeight == 0) {
                    mControlsHeight = controlsView.getHeight();
                }

                if (telemetryViewHeight == 0) {
                    telemetryViewHeight = controlsView.getHeight();
                }

                if (mShortAnimTime == 0) {
                    mShortAnimTime = getResources().getInteger(
                            android.R.integer.config_shortAnimTime);
                }
                controlsView.animate()
                        .translationY(visible ? 0 : mControlsHeight)
                        .setDuration(mShortAnimTime);

                telementryView.animate().
                        translationY(visible ? 0 : -telemetryViewHeight)
                        .setDuration(mShortAnimTime);

                if (visible && AUTO_HIDE) {
                    // Schedule a hide().
                    delayedHide(AUTO_HIDE_DELAY_MILLIS);
                }
            }
        });

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(100);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}
