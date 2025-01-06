package frc.robot.opmodes.test;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Swerve;

public class Printing implements Opmode {
    Swerve drive;
    XboxController controller;

    public String name() {return "Debug Printing";}

    public void init() {
        drive = new Swerve();
    }

    public void periodic() {
        SmartDashboard.putNumber("fl", drive.frontLeft.spinPos());
        SmartDashboard.putNumber("fr", drive.frontRight.spinPos());
        SmartDashboard.putNumber("bl", drive.rearLeft.spinPos());
        SmartDashboard.putNumber("br", drive.rearRight.spinPos());
    }
}
