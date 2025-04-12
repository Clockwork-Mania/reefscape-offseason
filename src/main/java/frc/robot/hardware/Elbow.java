package frc.robot.hardware;

import frc.robot.Utility;

public class Elbow extends Motor {
    static final double KP = 4.5;
    static final double CLOSE_KP = 4.5;
    static final double CLOSE_THRESH = 0.04;
    static final double KI = .0003;
    static final double INT_CAP = 0.1/KI;
    static final double DECAY = .9995;
    static final double KC = .045;
    static final double POW_CAP = .3;

    public static final double MIN = .07, MAX = 0.76;
    public static final double HORIZ = 0.182;

    public Elbow() {
        super(23, "canivore");
        setEnc(EncoderType.DUTY_CYCLE, 8);
        // setDir(Direction.CCW);
        setEncDir(Direction.CCW);
        integral = 0;
    }

    public double target;
    public void setTarget(double target) {
        this.target = target;
        integral = 0;
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
            Utility.clamp(
                (Math.abs(err)<CLOSE_THRESH?CLOSE_KP:KP)*err,
                -POW_CAP, POW_CAP
            // KI*integral+
            // KC*Math.cos((getPos()-HORIZ)*2*Math.PI)
            )
            +KI*integral
            +KC*Math.cos((getPos()-HORIZ)*2*Math.PI)
        );
    }

    public void goToTarget() {
        goTo(target);
    }
    
    public void adjust(double by) {
        target += by;
        target = Utility.clamp(target, MIN, MAX);
    }

    public double getAngle() {
        return 2*Math.PI*(getPos()-HORIZ);
    }
}
