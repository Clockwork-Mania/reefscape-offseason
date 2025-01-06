package frc.robot.opmodes.teleop;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.SwerveModule;

public class ModuleTest implements Opmode {
    public XboxController controller;
    SwerveModule mod[];
    // public TalonFX motor0, motor1;
    Swerve sw;

    public void init() {
        controller = new XboxController(0);
        // mod = new SwerveModule[4];
        // mod[0] = new SwerveModule(2, 6, 10, .354);
        // mod[1] = new SwerveModule(3, 7, 11, .956);
        // mod[2] = new SwerveModule(4, 8, 12, .778);
        // mod[3] = new SwerveModule(5, 9, 13, .808);
        sw = new Swerve();
    }

    public void periodic() {
        // for (int i = 0; i < 4; i++) {
        //     mod[i].powerMotor.set(-controller.getLeftY());
        //     mod[i].spinMotor.set(-controller.getRightY()*.2);
        //     SmartDashboard.putNumber("mp"+i, mod[i].spinPos());
        //     // SmartDashboard.putNumber("mv"+i, mod[i].spinVel());   
        // }
        // for(SwerveModule wh : sw.wheels) {
        //     wh
        // }
        // sw.rearRight.pidSpin(Math.PI/2, .02, .4);
        double a = Math.atan2(controller.getRightY(), controller.getRightX());
        sw.rearRight.pidSpin(a, .02, controller.getLeftY());
        SmartDashboard.putNumber("ang", a);
        SmartDashboard.putNumber("lx", controller.getLeftX());
        SmartDashboard.putNumber("rx", controller.getRightX());
        SmartDashboard.putNumber("ry", controller.getRightY());
    }
}
