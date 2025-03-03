package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

public class MotorPair {
    public Motor first, second;

    public MotorPair(int id0, int id1) {
        first = new Motor(id0);
        second = new Motor(id1);
    }

    public MotorPair(int id0, int id1, InvertedValue dir0, InvertedValue dir1) {
        first = new Motor(id0, dir0);
        second = new Motor(id1, dir1);
    }
    
    public void set(double speed) {
        first.set(speed);
        second.set(speed);
    }

    public void toPos(double pos) {
        first.toPos(pos);
        second.toPos(pos);
    }

    public void goTo(double position, double kp) {
        first.goTo(position, kp);
        second.goTo(position, kp);
    }

}
