package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
	public static final double intakeSpeed = 0.4;
	public static final double outputSpeed = -0.4;
	public static final int[] ports = {21, 22};

	// CANSparkMax motor = new CANSparkMax(port, MotorType.kBrushless);
	// MotorPair pair = new MotorPair(ports);
	CANSparkMax top = new CANSparkMax(ports[0], MotorType.kBrushless);
	CANSparkMax bottom = new CANSparkMax(ports[1], MotorType.kBrushless);

	public void intake() {
		top.set(intakeSpeed);
		bottom.set(intakeSpeed);
	}

	public void intake(double speed) {
		top.set(intakeSpeed * speed);
		bottom.set(intakeSpeed * speed);
	}

	public void output() {
		top.set(outputSpeed);
		bottom.set(outputSpeed);
	}

	public void output(double speed) {
		top.set(outputSpeed * speed);
		bottom.set(outputSpeed * speed);
	}

	public void stop() {
		top.set(0);
		bottom.set(0);
	}

	// public Command intakeCommand() {
	// 	return this.startEnd(
	// 		()->pair.set(intakeSpeed),
	// 		()->pair.set(0)
	// 	);
	// }
}
