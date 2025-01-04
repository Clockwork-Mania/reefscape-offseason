package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

/** A wrapper for two motors that act as a pair
 */
public class MotorPair {
	public CANSparkMax m0, m1;
	
	/** Creates a MotorPair object
	 * @param ports The CAN IDs for the two motors
	 * @param inv0 Whether the top motor is inverted or not
	 * @param inv1 Whether the bottom motor is inverted or not
	 */
	public MotorPair(int[] ports, boolean inv0, boolean inv1) {
		m0 = new CANSparkMax(ports[0], MotorType.kBrushless);
		m1 = new CANSparkMax(ports[1], MotorType.kBrushless);
		m0.setInverted(inv0);
		m1.setInverted(inv1);
	}

	public MotorPair(int[] ports) {new MotorPair(ports, false, true);}

	/**
	 * Sets both motors to a single speed
	 * @param speed The power at which to run the motors
	 */
	public void set(double speed) {
		m0.set(speed);
		m1.set(speed);
	}
}
