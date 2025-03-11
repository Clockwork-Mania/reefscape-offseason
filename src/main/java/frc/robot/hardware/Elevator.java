package frc.robot.hardware;

import frc.robot.hardware.Motor.EncoderType;

public class Elevator extends MotorPair {
    double kp = 0.1;

    public Elevator() {
        super(21, 23, "canivore");
        setEnc(EncoderType.WRAPPING_DC, 7);
        setDir(Direction.CCW, Direction.CW);
        setEncDir(Direction.CCW);
    }

    public void goTo(double target) {
        double err = target-getPos();
        set(Math.sqrt(Math.min(.25, err/2)));
        // goTo(position, kp);
    }
}
