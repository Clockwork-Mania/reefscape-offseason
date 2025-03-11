package frc.robot.hardware;

public class Elbow extends Motor {
    public Elbow() {
        super(26, "canivore");
        this.kp = 0; // TODO
        setEnc(EncoderType.DUTY_CYCLE, 8);
    }
}
