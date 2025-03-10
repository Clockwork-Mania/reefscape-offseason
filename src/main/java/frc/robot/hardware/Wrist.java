package frc.robot.hardware;

public class Wrist extends Motor {
    public Wrist() {
        super(24);
        this.kp = 0;
        setEnc(EncoderType.DUTY_CYCLE, 9);
    }
}