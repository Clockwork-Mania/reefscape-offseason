package frc.robot.hardware;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

public class MotorPair extends Motor {
    public Motor other;

    public MotorPair(int id0, int id1) {
        super(id0);
        other = new Motor(id1);
    }
    
    public MotorPair(int id0, int id1, String bus) {
        super(id0, bus);
        other = new Motor(id1, bus);
    }
    
    public void set(double speed) {
        super.set(speed);
        other.set(speed);
    }

    public void setDir(Direction dir0, Direction dir1) {
        super.setDir(dir0);
        other.setDir(dir1);
    }

    public void stop() {
        set(0);
    }

    public void goTo(double position, double kp) {
        super.goTo(position, kp);
        other.goTo(position, kp);
    }

    public void setEnc(Motor.EncoderType encType, int id) {
        super.setEnc(encType, id);
    }

    public void setEnc(Motor.EncoderType encType, int id, String bus) {
        super.setEnc(encType, id, bus);
    }

    public double getPos() {
        return super.getPos();
    }
}
