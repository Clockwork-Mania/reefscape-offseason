package frc.robot.hardware;

public class Elbow extends Motor {
    static final double KP = 0.8;
    static final double KI = .0003;
    static final double DECAY = .999;

    public static final double MIN = .12, MAX = .95;

    public Elbow() {
        super(26, "canivore");
        setEnc(EncoderType.DUTY_CYCLE, 8);
        integral = 0;
    }

    double target;
    public void setTarget(double target) {
        this.target = target;
    }

    double integral;
    public void goTo(double target) {
        double err = target - getPos();
        integral *= DECAY;
        integral += err;
        set(KP*err+KI*integral);
    }

    public void goToTarget() {
        goTo(target);
    }
}
