package frc.robot.subsystems;

public class Elevator {
    MotorPair mp;
    double kp = 0.1;

    public Elevator() {
        mp = new MotorPair(0, 0, Motor.CW, Motor.CW); //might be wrong direction
    }

    public void set(double power) {
        mp.set(power);
    }

    public void lift2(double position) {
        
    }

    public void goTo(double position) {
        mp.goTo(position, kp);
    }

    public double getHeight() {
        return mp.first.getPosition().getValueAsDouble();
    }

}
