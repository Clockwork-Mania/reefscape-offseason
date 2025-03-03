package frc.robot.subsystems;

public class Wrist {
    Motor motor = new Motor(0); // FIXME: id not finalized

    public void set(double power) {
        motor.set(power);
    }
}