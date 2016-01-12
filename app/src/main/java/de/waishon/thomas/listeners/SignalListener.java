package de.waishon.thomas.listeners;

/**
 * Created by Sören on 08.01.2016.
 */
public interface SignalListener {
    void cpuLoadChanged(double cpuLoad);
    void memoryUsageChanged(double memoryUsage);
    void netLoadChanged(long bytesIn, long bytesOut);
    void freeDriveSpaceChanged(int megabytes);
    void wifiSignalStrengthChanged(int signalStrength);
    void wifiSsidChanged(String ssid);
}
