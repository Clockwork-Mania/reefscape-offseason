package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

public class Motor extends TalonFX {
    public static final InvertedValue CW = InvertedValue.Clockwise_Positive;
    public static final InvertedValue CCW = InvertedValue.CounterClockwise_Positive;

    public Motor(int id) {
        super(id);
    }

    public Motor(int id, InvertedValue dir) {
        super(id);
        direct(dir);
    }

    public Motor(int id, CANBus bus, InvertedValue dir) {
        super(id, bus);
        direct(dir);
    }

    public Motor(int id, String bus, InvertedValue dir) {
        super(id, bus);
        direct(dir);
    }

    // Set the direction of a motor
    public void direct(InvertedValue dir) {
        MotorOutputConfigs con = new MotorOutputConfigs();
        con.Inverted = dir;
        getConfigurator().apply(con);
    }
}
