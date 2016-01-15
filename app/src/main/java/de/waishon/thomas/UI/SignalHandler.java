package de.waishon.thomas.UI;

import android.app.Activity;
import android.text.format.Formatter;
import android.widget.TextView;

import de.waishon.thomas.R;
import de.waishon.thomas.listeners.SignalListener;

/**
 * Created by Sören on 15.01.2016.
 */
public class SignalHandler implements SignalListener {

    private Activity activity;

    private TextView telemtryCPU;
    private TextView telemtryMemory;
    private TextView telemtryNetLoad;
    private TextView telemtryDisk;

    public SignalHandler(Activity activity) {
        this.activity = activity;

        this.telemtryCPU = (TextView) activity.findViewById(R.id.thomas_telemetry_cpu);
        this.telemtryMemory = (TextView) activity.findViewById(R.id.thomas_telemetry_memory);
        this.telemtryNetLoad = (TextView) activity.findViewById(R.id.thomas_telemetry_netload);
        this.telemtryDisk = (TextView) activity.findViewById(R.id.thomas_telemetry_disk);
    }

    @Override
    public void cpuLoadChanged(double cpuLoad) {
        this.telemtryCPU.setText("CPU: " + Math.round(cpuLoad * 10.0) / 10.0 + "%");
    }

    @Override
    public void memoryUsageChanged(double memoryUsage) {
        this.telemtryMemory.setText(" | RAM: " + memoryUsage + "MB");
    }

    @Override
    public void netLoadChanged(long bytesIn, long bytesOut) {
        this.telemtryNetLoad.setText(" | In: " + Formatter.formatShortFileSize(activity, bytesIn) + " Out: " + Formatter.formatShortFileSize(activity, bytesOut));
    }

    @Override
    public void freeDriveSpaceChanged(int megabytes) {
        this.telemtryDisk.setText(" | Disk: " + megabytes / 1000 + "GB");
    }

    @Override
    public void wifiSignalStrengthChanged(int signalStrength) {

    }

    @Override
    public void wifiSsidChanged(String ssid) {

    }
}
