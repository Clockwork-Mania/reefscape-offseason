package frc.robot.opmodes.test;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.hardware.*;

public class ElevatorTest implements Opmode {
    public XboxController controller;
    public Motor elev0, elev1;
    public DutyCycleEncoder enc;

    public void init(Grinder bot) {
        controller = new XboxController(0);
        elev0 = new Motor(21, "canivore");
        elev1 = new Motor(23, "canivore");
        enc = new DutyCycleEncoder(9);
    }

    public void periodic() {
        // SmartDashboard.putNumber("power!", 0.5*controller.getLeftY());
        // SmartDashboard.putNumber("enc!", enc.get());
        elev0.set(0.5*controller.getLeftY());
        elev1.set(-0.5*controller.getLeftY());
    }
}
