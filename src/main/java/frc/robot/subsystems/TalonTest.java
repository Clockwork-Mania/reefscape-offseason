package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

public class TalonTest {
    public TalonFX motor;

    public void init() {
        motor = new TalonFX(1);
    }

    public void set(double speed) {
        motor.set(speed);
    }
}
