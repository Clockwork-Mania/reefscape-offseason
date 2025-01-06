package frc.robot.opmodes.teleop;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Swerve;

public class AllWheels implements Opmode {
    // Swerve drive;
    XboxController controller;
    TalonFX motor0, motor1, motor2, motor3, motor4, motor5, motor6, motor7;

    public void init() {
        controller = new XboxController(0);
        motor0 = new TalonFX(2, "rio");
        motor1 = new TalonFX(3, "rio");
        motor2 = new TalonFX(4, "rio");
        motor3 = new TalonFX(5, "rio");
        motor4 = new TalonFX(6, "rio");
        motor5 = new TalonFX(7, "rio");
        motor6 = new TalonFX(8, "rio");
        motor7 = new TalonFX(9, "rio");
    }

    public void periodic() {
        double y = controller.getLeftY()*0.5;
        
        motor0.set(y);
        motor1.set(y);
        motor2.set(y);
        motor3.set(y);
        motor4.set(y);
        motor5.set(y);
        motor6.set(y);
        motor7.set(y);
    }
}
