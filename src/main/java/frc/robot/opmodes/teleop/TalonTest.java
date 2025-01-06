package frc.robot.opmodes.teleop;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.opmodes.Opmode;

public class TalonTest implements Opmode {
    public XboxController controller;
    public TalonFX motor0, motor1;

    public void init() {
        controller = new XboxController(0);
        motor0 = new TalonFX(2, "rio");
        motor1 = new TalonFX(6, "rio");
    }

    public void periodic() {
        motor0.set(controller.getLeftY());
        motor1.set(controller.getRightY());
    }
}
