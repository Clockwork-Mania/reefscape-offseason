package frc.robot.subsystems;

public class Elbow {
    Motor motor = new Motor(0); //id not finalized

    public void set(double power) {
        motor.set(power);
    }
}
