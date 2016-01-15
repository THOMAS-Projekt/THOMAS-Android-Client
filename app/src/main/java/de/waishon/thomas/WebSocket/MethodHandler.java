package de.waishon.thomas.WebSocket;

/**
 * Created by Sören on 14.01.2016.
 */
public class MethodHandler {

    ConnectionHandler connectionHandler;

    public MethodHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public boolean accelerateToMotorSpeed(Motor motor, int speed) {
        if (!connectionHandler.isConnected())
            return false;

        this.connectionHandler.send(new MethodBuilder().setMethodName("accelerateToMotorSpeed").setArgs(new Arguments().addArg("motor", motor.getName()).addArg("speed", speed)).getJSON());

        return true;
    }

    public boolean startCameraStream(String ipAddress, int port) {
        if (!connectionHandler.isConnected())
            return false;

        this.connectionHandler.send(new MethodBuilder().setMethodName("startCameraStream").setArgs(new Arguments().addArg("viewerHost", ipAddress).addArg("viewerPort", port)).getJSON());

        return true;
    }

    public boolean setCameraStreamOptions(int streamerID, int imageQuality, int imageDensity) {
        if (!connectionHandler.isConnected())
            return false;

        this.connectionHandler.send(new MethodBuilder().setMethodName("setCameraStreamOptions").setArgs(new Arguments().addArg("streamerId", streamerID).addArg("imageQuality", imageQuality).addArg("imageDensity", imageDensity)).getJSON());

        return true;
    }

    public boolean forceTelemetryUpdate() {
        if (!connectionHandler.isConnected())
            return false;

        this.connectionHandler.send(new MethodBuilder().setMethodName("forceTelemetryUpdate").getJSON());

        return true;
    }

    public enum Motor {
        LEFT(2, "left"),
        RIGHT(1, "right"),
        BOTH(3, "both");

        private int id;
        private String name;

        Motor(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getID() {
            return id;
        }

        public String getName() {
            return this.name;
        }
    }
}
