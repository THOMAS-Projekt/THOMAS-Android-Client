package de.waishon.thomas.Sensors;

import android.view.InputDevice;
import android.view.MotionEvent;

import de.waishon.thomas.WebSocket.MethodHandler;

/**
 * Created by Sören on 14.01.2016.
 */
public class Joystick implements JoystickEventListener{

    private static final int MAX_AXIS_VALUE = 255;
    private static final int MAX_MOTOR_SPEED = 255;

    private MethodHandler methodHandler;

    public Joystick(MethodHandler methodHandler) {
        this.methodHandler = methodHandler;
    }

    @Override
    public void onJoystickChange(MotionEvent e) {
        InputDevice device = e.getDevice();

        int[] speeds = calculateMotorSpeed(e.getAxisValue(MotionEvent.AXIS_X), e.getAxisValue(MotionEvent.AXIS_Y));

        methodHandler.accelerateToMotorSpeed(MethodHandler.Motor.LEFT , speeds[0]);
        methodHandler.accelerateToMotorSpeed(MethodHandler.Motor.RIGHT , speeds[1]);
    }

    public int[] calculateMotorSpeed(float rawX, float rawY) {

        int valueX = (Math.abs(rawX * 255) < 25) ? 0 : (int) (rawX * 255);
        int valueY = (Math.abs(rawY * 255) < 25) ? 0 : (int) (rawY * 255);

        int axisSum = Math.abs(valueX) + Math.abs(valueY);
        int[] speeds = new int[2];

        if(axisSum <= MAX_AXIS_VALUE) {
            speeds[0] = -(-valueX + valueY);
            speeds[1] = -(valueX + valueY);
        } else {
            speeds[0] = -(int) (((double) MAX_MOTOR_SPEED / axisSum) * (-valueX + valueY));
            speeds[1] = -(int) (((double) MAX_MOTOR_SPEED / axisSum) * (valueX + valueY));
        }

        return speeds;
    }
}
