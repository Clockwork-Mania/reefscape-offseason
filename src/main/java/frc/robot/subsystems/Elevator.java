package frc.robot.subsystems;

public class Elevator {
    MotorPair mp;
    double kp = 0.1;

    public Elevator() {
        mp = new MotorPair(21, 23, Motor.CW, Motor.CW);
    }

    public void set(double power) {
        mp.set(power);
    }

    public void goTo(double position) {
        mp.goTo(position, kp);
    }

    public double height() {
        return mp.first.getPosition().getValueAsDouble();
    }

}
