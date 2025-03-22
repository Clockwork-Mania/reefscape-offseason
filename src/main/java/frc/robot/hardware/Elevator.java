package frc.robot.hardware;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utility;
import frc.robot.hardware.Motor.EncoderType;

public class Elevator extends MotorPair {
    static final double KP = .5;
    static final double KP_DOWN = .3;
    static final double KI = .001;
    // static final double DECAY = .999;
    static final double INT_CAP = 0.2/KI;
    static final double POW_CAP = 0.8;

    public static final double MIN = -.6, MAX = 4.6;
    
    public Elevator() {
        super(21, 23, "canivore");
        setEnc(EncoderType.WRAPPING_DC, 7);
        setDir(Direction.CCW, Direction.CW);
        setEncDir(Direction.CCW);
        integral = 0;
    }

    public double target;
    public void setTarget(double target) {
        this.target = target;
    }

    public void goToTarget() {
        goTo(target);
    }

    double integral;
    public void goTo(double target) {
        double err = target-getPos();
        integral += err;
        integral = Utility.clamp(integral, -INT_CAP, INT_CAP);
        set(
            (err>0?KP:KP_DOWN)*err+
            KI*integral
        );
        SmartDashboard.putNumber("eleverr", err);
        SmartDashboard.putNumber("elevpow", err/2+integral*0.01);
        SmartDashboard.putNumber("elevint", integral);
        // goTo(position, kp);
    }

    public void reset() {
        wrapEnc.reset();
    }

    public void adjust(double by) {
        target += by;
        // target = Utility.clamp(target, MIN, MAX);
        target = Math.min(target, MAX);
    }
}
