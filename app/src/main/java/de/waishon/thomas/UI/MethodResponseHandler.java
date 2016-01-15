package de.waishon.thomas.UI;

import de.waishon.thomas.WebSocket.MethodHandler;
import de.waishon.thomas.listeners.MethodResponseListener;

/**
 * Created by Sören on 15.01.2016.
 */
public class MethodResponseHandler implements MethodResponseListener {

    private MethodHandler methodHandler;
    private int streamID = -1;

    public MethodResponseHandler(MethodHandler methodHandler) {
        this.methodHandler = methodHandler;
    }

    @Override
    public void setMotorSpeedResponse(boolean success) {

    }

    @Override
    public void accelerateToMotorSpeedResponse(boolean success) {

    }

    @Override
    public void setCamPositionResponse(boolean success) {

    }

    @Override
    public void changeCamPositionResponse(boolean success) {

    }

    @Override
    public void setRelayResponse(boolean success) {

    }

    @Override
    public void startCameraStreamResponse(int streamID) {
        this.streamID = streamID;
        // Set Default Stream Options
        methodHandler.setCameraStreamOptions(streamID, 20, 20);
    }

    @Override
    public void stopStreamReponseResponse(boolean success) {

    }

    @Override
    public void setCameraStreamOptionsResponse(boolean success) {

    }


    @Override
    public void startNewScanResponse(int mapID) {

    }

    @Override
    public void stopScanResponse(boolean success) {

    }

    @Override
    public void forceTelemetryUpdateResponse(boolean success) {

    }

    @Override
    public void setAutomationStateResponse(boolean success) {

    }

    @Override
    public void cameraStreamRegistredResponse(int streamID) {

    }

    public int getStreamID() {
        return this.streamID;
    }
}
