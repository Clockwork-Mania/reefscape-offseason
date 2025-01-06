package frc.robot.opmodes.teleop;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.SwerveModule;

public class ModuleTest implements Opmode {
    public XboxController controller;
    SwerveModule mod;
    // public TalonFX motor0, motor1;

    public String name() {return "Module Testing";}

    public void init() {
        controller = new XboxController(0);
        mod = new SwerveModule(4, 8, 12, 0);
    }

    public void periodic() {
        mod.powerMotor.set(-controller.getLeftY());
        mod.spinMotor.set(-controller.getRightY());
        SmartDashboard.putNumber("mp", mod.spinPos());
        SmartDashboard.putNumber("mv", mod.spinVel());
    }
}
