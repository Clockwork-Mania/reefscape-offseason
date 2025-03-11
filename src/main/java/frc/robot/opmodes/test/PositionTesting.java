package frc.robot.opmodes.test;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.Grinder;
import frc.robot.opmodes.Opmode;

public class PositionTesting implements Opmode {
    DutyCycleEncoder wrist, elbow, elev;

    public void init(Grinder bot) {
        wrist = new DutyCycleEncoder(9);
        elbow = new DutyCycleEncoder(8);
        elev  = new DutyCycleEncoder(7);
    }

    public void periodic() {
        SmartDashboard.putNumber("wrist", wrist.get());
        SmartDashboard.putNumber("elbow", elbow.get());
        SmartDashboard.putNumber("elev", elev.get());
    }
}
