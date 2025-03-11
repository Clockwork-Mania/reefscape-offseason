package frc.robot.hardware;

import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class WrappingDutyCycleEncoder extends DutyCycleEncoder {
    int revs;
    double last_pos = 0;

    public WrappingDutyCycleEncoder(int port) {
        super(port);
        revs = 0;
    }

    public void periodic() {
        if(get()-last_pos > .8) {
            // underflow
            --revs;
        }
        else if(get()-last_pos < -.8) {
            // overflow
            ++revs;
        }
        last_pos = get();
    }

    public double get() {
        return super.get()+revs;
    }
}