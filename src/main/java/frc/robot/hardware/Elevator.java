package frc.robot.hardware;

import frc.robot.hardware.Motor.EncoderType;

public class Elevator extends MotorPair {
    double kp = 0.1;

    public Elevator() {
        super(21, 23);
        setEnc(EncoderType.DUTY_CYCLE, 7);
    }

    public void goTo(double position) {
        goTo(position, kp);
    }

    public double height() {
        return getPos();
    }

}
