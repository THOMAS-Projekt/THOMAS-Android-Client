package de.waishon.thomas.WebSocket;

/**
 * Created by Sören on 14.01.2016.
 */
public class MethodHandler {

    public enum Motor{
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

    ConnectionHandler connectionHandler;

    public MethodHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public boolean accelerateToMotorSpeed(Motor motor, int speed) {
        if(!connectionHandler.isConnected())
            return false;

        this.connectionHandler.send(new MethodBuilder().setMethodName("accelerateToMotorSpeed").setArgs(new Arguments().addArg("motor", motor.getName()).addArg("speed", speed)).getJSON());

        return true;
    }
}
