package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

public class Motor extends TalonFX {
    public static final InvertedValue CW = InvertedValue.Clockwise_Positive;
    public static final InvertedValue CCW = InvertedValue.CounterClockwise_Positive;
    public boolean hasEncoder = false;
    public CANcoder enc = null;

    public Motor(int id) {
        super(id);
    }

    public Motor(int id, CANBus bus) {
        super(id, bus);
    }

    public Motor(int id, String bus) {
        super(id, bus);
    }

    public void setEnc(int id) {setEnc(id, "rio");}

    public void setEnc(int id, CANBus bus) {
        hasEncoder = true;
        enc = new CANcoder(id, bus);
    }

    public void setEnc(int id, String bus) {
        hasEncoder = true;
        enc = new CANcoder(id, bus);
    }

    // Set the direction of a motor
    public void setDir(InvertedValue dir) {
        MotorOutputConfigs con = new MotorOutputConfigs();
        con.Inverted = dir;
        getConfigurator().apply(con);
    }

    public void setPID(double kp, double ki, double kd) {
        Slot0Configs con = new Slot0Configs();
        con.kP = kp; // 2.4; // An error of 1 rotation results in 2.4 V output
        con.kI = ki; // 0; // no output for integrated error
        con.kD = kd; // 0.1; // A velocity of 1 rps results in 0.1 V output
        getConfigurator().apply(con);
    }

    public void toPos(double pos) {
        PositionVoltage req = new PositionVoltage(0).withSlot(0);
        setControl(req.withPosition(pos));
    }
    
    public void toVel(double vel) {
        VelocityVoltage req = new VelocityVoltage(0).withSlot(0);
        setControl(req.withVelocity(vel));
    }

    public double getPos() {
        if(hasEncoder) {
            return enc.getPosition().getValueAsDouble();
        }
        else {
            return getPosition().getValueAsDouble();
        }
    }

    public void goTo(double target, double kp) {
        double error = target - getPos();
        set(kp * error);
    }

}
