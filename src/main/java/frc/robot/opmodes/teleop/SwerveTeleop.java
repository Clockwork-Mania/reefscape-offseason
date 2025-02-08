package frc.robot.opmodes.teleop;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Swerve;

public class SwerveTeleop implements Opmode {
    Swerve drive;
    XboxController controller;

    public void init() {
        drive = new Swerve();
        controller = new XboxController(0);
    }

    public void periodic() {
        double
            f = -controller.getLeftY()*.4,
            s = controller.getLeftX()*.4,
            r = controller.getRightX()*.4;
        drive.drive(-s, -f, -r, false);
        drive.periodic();

        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", drive.getHeading());

        if(controller.getLeftBumperButton() && controller.getRightBumperButton()) {
            drive.zeroHeading();
        }
    }
}
