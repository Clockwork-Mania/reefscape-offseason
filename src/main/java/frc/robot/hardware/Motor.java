package frc.robot.hardware;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class Motor extends TalonFX {
    public static enum Direction {CW, CCW};
    public Direction dir = Direction.CW;

    public static enum EncoderType {
        NONE,
        CAN,
        DUTY_CYCLE
    };
    EncoderType encType = EncoderType.NONE;
    public CANcoder canEnc = null;
    public DutyCycleEncoder dcEnc = null;

    public double kp = 0;

    public Motor(int id) {
        super(id);
    }

    public Motor(int id, CANBus bus) {
        super(id, bus);
    }

    public Motor(int id, String bus) {
        super(id, bus);
    }

    public void setEnc(EncoderType encType, int id) {
        switch(encType) {
            case CAN:
                setCanEnc(id);
                break;
            case DUTY_CYCLE:
                setDcEnc(id);
                break;
            default: break;
        }
    }

    public void setEnc(EncoderType encType, int id, String bus) {
        setCanEnc(id, bus);
    }

    @Override
    public void set(double speed) {
        set(dir == Direction.CW ? speed : -speed);
    }

    void setCanEnc(int id) {setCanEnc(id, "rio");}

    void setCanEnc(int id, String bus) {
        canEnc = new CANcoder(id, bus);
        encType = EncoderType.CAN;
    }

    void setDcEnc(int id) {
        dcEnc = new DutyCycleEncoder(id);
        encType = EncoderType.DUTY_CYCLE;
    }

    public double getPos() {
        switch(encType) {
            case CAN:
                return canEnc.getPosition().getValueAsDouble();
            case DUTY_CYCLE:
                return dcEnc.get();
            default:
                return getPosition().getValueAsDouble();
        }
    }

    public void goTo(double target) {
        goTo(target, kp);
    }

    public void goTo(double target, double kp) {
        double error = target - getPos();
        set(kp * error);
    }

}
