package frc.robot.hardware;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

public class MotorPair {
    public Motor first, second;

    public MotorPair(int id0, int id1) {
        first = new Motor(id0);
        second = new Motor(id1);
    }
    
    public void set(double speed) {
        first.set(speed);
        second.set(speed);
    }

    public void goTo(double position, double kp) {
        first.goTo(position, kp);
        second.goTo(position, kp);
    }

    public void setEnc(Motor.EncoderType encType, int id) {
        first.setEnc(encType, id);
    }

    public void setEnc(Motor.EncoderType encType, int id, String bus) {
        first.setEnc(encType, id, bus);
    }

    public double getPos() {
        return first.getPos();
    }
}
