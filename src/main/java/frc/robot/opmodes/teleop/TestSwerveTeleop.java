package frc.robot.opmodes.teleop;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Swerve;

public class TestSwerveTeleop implements Opmode {
    Swerve drive;
    XboxController controller;

    public String name() {return "Swerve Testing";}

    public void init() {
        drive = new Swerve();
        controller = new XboxController(0);
    }

    public void periodic() {
        double
            x = controller.getLeftX()*.4,
            y = controller.getLeftY()*.4,
            r = controller.getRightX()*.4;
        drive.drive(-y, -x, r, true);
        drive.periodic();

        SmartDashboard.putNumber("x", x);
        SmartDashboard.putNumber("y", y);
        SmartDashboard.putNumber("r", r);
    }
}
