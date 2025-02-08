package frc.robot.opmodes.teleop;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Swerve;

public class SwerveOdo implements Opmode {
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
        drive.drive(-s, -f, -r, true);
        drive.periodic();

        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", drive.getHeading()*180/Math.PI);
        Pose2d pose = drive.odo.getPoseMeters();
        SmartDashboard.putNumber("odo.x", pose.getX()*39.37);
        SmartDashboard.putNumber("odo.y", pose.getY()*39.37);
        SmartDashboard.putNumber("odo.h", pose.getRotation().getDegrees());

        if(controller.getLeftBumperButton() && controller.getRightBumperButton()) {
            drive.zeroHeading();
        }

        if(controller.getXButton()) drive.frontLeft.powerMotor.set(.3);
        if(controller.getAButton()) drive.backLeft.powerMotor.set(.3);
        if(controller.getYButton()) drive.frontRight.powerMotor.set(.3);
        if(controller.getBButton()) drive.backRight.powerMotor.set(.3);
    }
}
