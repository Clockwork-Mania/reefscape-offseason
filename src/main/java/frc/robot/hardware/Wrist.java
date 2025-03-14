package frc.robot.hardware;

public class Wrist extends Motor {
    static final double KP = 1;
    static final double CLOSE_KP = 2;
    static final double CLOSE_THRESH = 0.1;
    static final double KI = 0.002;
    static final double DECAY = .9995;
    static final double KD = -0.2;

    public static final double MIN = 0.2, MAX = 0.9;

    public Wrist() {
        super(24, "canivore");
        setEnc(EncoderType.DUTY_CYCLE, 9);
        setDir(Direction.CCW);
    }

    double target;
    public void setTarget(double target) {
        this.target = target;
    }

    double prevErr = 0;
    double integral = 0;
    public void goTo(double target) {
        double err = target - getPos();
        integral *= DECAY;
        integral += err;
        set(
            (Math.abs(err)<CLOSE_THRESH?CLOSE_KP:KP)*err+
            KI*integral+
            KD*(err-prevErr)
        );
        prevErr = err;
    }

    public void goToTarget() {
        goTo(target);
    }
}