package frc.robot.opmodes.test;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;

public class ThroughboreTest implements Opmode {
    DutyCycleEncoder enc;

    public void init() {
        enc = new DutyCycleEncoder(9);
    }

    public void periodic() {
        SmartDashboard.putNumber("enc", enc.get());
    }
}
