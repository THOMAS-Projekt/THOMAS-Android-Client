package de.waishon.thomas.listeners;

/**
 * Created by Sören on 12.01.2016.
 */
public interface MethodResponseListener {
    void setMotorSpeedResponse(boolean success);
    void accelerateToMotorSpeedResponse(boolean success);
    void setCamPositionResponse(boolean success);
    void changeCamPositionResponse(boolean success);
    void setRelayResponse(boolean success);
    void startCameraStreamResponse(int streamID);
    void stopStreamReponseResponse(boolean success);

    void setCameraStreamOptionsResponse(boolean success);
    void startNewScanResponse(int mapID);
    void stopScanResponse(boolean success);
    void forceTelemetryUpdateResponse(boolean success);
    void setAutomationStateResponse(boolean success);

    void cameraStreamRegistredResponse(int streamID);
}
