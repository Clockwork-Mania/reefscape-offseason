package frc.robot.hardware;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.Motor.EncoderType;

public class Elevator extends MotorPair {
    double kp = 0.1;

    public Elevator() {
        super(21, 23, "canivore");
        setEnc(EncoderType.WRAPPING_DC, 7);
        setDir(Direction.CCW, Direction.CW);
        setEncDir(Direction.CCW);
        integral = 0;
    }

    double integral;
    public void goTo(double target) {
        double err = target-getPos();
        integral *= .999;
        integral += err;
        // set(Math.signum(err)*Math.sqrt(Math.min(.25, Math.abs(err/2))));
        set(err/2+integral*0.001);
        SmartDashboard.putNumber("eleverr", err);
        SmartDashboard.putNumber("elevpow", err/2+integral*0.01);
        SmartDashboard.putNumber("elevint", integral);
        // goTo(position, kp);
    }
}
