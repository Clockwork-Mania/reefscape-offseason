package frc.robot.hardware;

import org.opencv.imgproc.IntelligentScissorsMB;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utility;
import frc.robot.hardware.Motor.EncoderType;

public class Elevator extends MotorPair {
    static final double KP = 7;
    // static final double KP_DOWN = .3;
    static final double KI = 0.002;
    // static final double DECAY = .999;
    static final double INT_CAP = 0.2/KI;
    // static final double POW_CAP = 0.8;
    static final double POW_CAP = 0.5;

    public static final double MIN = .46, MAX = .845;
    
    public Elevator() {
        super(21, 22, "canivore");
        setEnc(EncoderType.DUTY_CYCLE, 9);
        setDir(Direction.CCW, Direction.CCW);
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
        if (Math.abs(integral) > INT_CAP) {
            integral = 0;
        }
        integral = Utility.clamp(integral, -INT_CAP, INT_CAP);
        set(
            KP*err+
            KI*integral
        );
        SmartDashboard.putNumber("eleverr", err);
        SmartDashboard.putNumber("elevpow", err/2+integral*0.01);
        SmartDashboard.putNumber("elevint", integral);
        // goTo(position, kp);
    }

    public void adjust(double by) {
        target += by;
        // target = Utility.clamp(target, MIN, MAX);
        target = Math.min(target, MAX);
    }
}
