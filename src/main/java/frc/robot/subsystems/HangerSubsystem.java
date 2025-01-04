package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangerSubsystem extends SubsystemBase {
	public static final double raiseSpeed = 0.8;
	public static final double lowerSpeed = -0.8;
	public static final int[] ports = {21, 22};

	// CANSparkMax motor = new CANSparkMax(port, MotorType.kBrushless);
	// MotorPair pair = new MotorPair(ports);
	CANSparkMax top = new CANSparkMax(ports[0], MotorType.kBrushless);
	CANSparkMax bottom = new CANSparkMax(ports[1], MotorType.kBrushless);
	MotorPair pair = new MotorPair(ports, true, true);

	public void raise() {
		pair.set(raiseSpeed);
	}

	public void lower() {
		pair.set(lowerSpeed);
	}

	public void stop() {
		pair.set(0);
	}
}
