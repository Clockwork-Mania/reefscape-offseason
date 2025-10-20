package frc.team4013.frc2025.hardware;

import frc.team4013.frc2025.Utility;

public class Wrist extends Motor {
    static final double KP = 2;
    static final double CLOSE_KP = 2;
    static final double CLOSE_THRESH = 0.1;
    static final double KI = 0.005;
    static final double INT_CAP = 0.2/KI;
    // static final double DECAY = .9997;
    static final double KC = .1;
    static final double KD = 0;//-0.3;

    public static final double MIN = 0.046, MAX = 0.616; //.74

    // wrist horizontal value when the elbow is also perfectly horizontal
    public static final double HORIZ = 0.329;

    // https://www.desmos.com/calculator/itw0kyeqhy

    public Wrist() {
        super(25, "canivore");
        setEnc(EncoderType.DUTY_CYCLE, 7);
        // setDir(Direction.CCW);
    }

    public double target;
    public void setTarget(double target) {
        this.target = target;
        integral = 0;
    }

    double prevErr = 0;
    double integral = 0;
    public void goTo(double target) {
        double err = target - getPos();
        // integral *= DECAY;
        integral = Utility.clamp(integral, -INT_CAP, INT_CAP);
        integral += err;
        set(
            (Math.abs(err)<CLOSE_THRESH?CLOSE_KP:KP)*err+
            KI*integral+
            KD*(err-prevErr)
        );
        prevErr = err;
    }

    static final double KP2 = 1.4;
    static final double KI2 = 0.002;
    static final double INT_CAP2 = 0.2/KI2;
    static final double KC2 = -0.04; //i figured out why its negative its cus the wrist is flipped
    static final double KC_CORAL = -0.08;
    static final double POW_CAP = .4;//0.6;

    public void goTo(double target, double elbowTheta) {
        goTo(target, elbowTheta, false);
    }

    //elbowTheta in radians
    public void goTo(double target, double elbowTheta, boolean coral) {
        double wristTheta = (HORIZ - getPos()) * 2.0 * Math.PI;
        double angle = wristTheta + elbowTheta;
        
        double err = target - getPos();
        integral = Utility.clamp(integral, -INT_CAP, INT_CAP);
        integral += err;
        // double pow = KP2*err+KI2*integral+
        //     (coral ? KC_CORAL : KC) * Math.cos(angle);
        // set(Utility.clamp(pow, -POW_CAP, POW_CAP));
        set(
            Utility.clamp(
                KP*err, -POW_CAP, POW_CAP
            )
            +KI*integral
        );
        prevErr = err;
    }

    public void goToTarget() {
        goTo(target);
    }

    public void goToTarget(double elbowTheta) {
        goTo(target, elbowTheta);
    }

    public void goToTarget(double elbowTheta, boolean coral) {
        goTo(target, elbowTheta, coral);
    }
    
    public void adjust(double by) {
        target += by;
        target = Utility.clamp(target, MIN, MAX);
    }
}