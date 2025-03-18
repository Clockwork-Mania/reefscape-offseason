package frc.robot.hardware;

import frc.robot.Utility;

public class Elbow extends Motor {
    static final double KP = 2.8;
    static final double CLOSE_KP = 1.2;
    static final double CLOSE_THRESH = 0.1;
    static final double KI = .0003;
    static final double INT_CAP = 0.2/KI;
    static final double DECAY = .9995;
    static final double KC = .1;

    public static final double MIN = .27, MAX = .54;
    public static final double HORIZ = 0.32;

    public Elbow() {
        super(26, "canivore");
        setEnc(EncoderType.DUTY_CYCLE, 8);
        setEncDir(Direction.CCW);
        integral = 0;
    }

    public double target;
    public void setTarget(double target) {
        this.target = target;
    }

    double integral;
    public void goTo(double target) {
        double err = target - getPos();
        integral += err;
        integral = Utility.clamp(integral, -INT_CAP, INT_CAP);
        // set((Math.abs(err)<CLOSE_THRESH?CLOSE_KP:KP)*err+KI*integral+
        //     KC*Math.cos(getPos()-HORIZ*2*Math.PI)                
        // );
        set(
            KP*err+
            KI*integral+
            KC*Math.cos((getPos()-HORIZ)*2*Math.PI)
        );
    }

    public void goToTarget() {
        goTo(target);
    }
}
