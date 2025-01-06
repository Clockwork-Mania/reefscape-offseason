package frc.robot.opmodes.teleop;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Swerve;

public class TestSwerveTeleop implements Opmode {
    Swerve drive;
    XboxController controller;

    public void init() {
        drive = new Swerve();
        controller = new XboxController(0);
    }

    public void periodic() {
        double
            f = controller.getLeftX()*.4,
            s = controller.getLeftY()*.4,
            r = controller.getRightX()*.4;
        drive.drive(f, s, r, true);
        drive.periodic();

        SmartDashboard.putNumber("f", f);
        SmartDashboard.putNumber("s", s);
        SmartDashboard.putNumber("r", r);
    }
}
