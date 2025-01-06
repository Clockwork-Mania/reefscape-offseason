package frc.robot.subsystems;

public class Elevator {
    MotorPair mp;
    public Elevator() {
        mp = new MotorPair(0, 0, Motor.CW, Motor.CCW);
    }


}
