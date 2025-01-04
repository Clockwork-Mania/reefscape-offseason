package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Utility;

public class PivotSubsystem extends SubsystemBase {
    public static final double maxSpeed = 0.4;
    public static final double maxAccel = 0.1;
    public static final double liftSpeed = 0.1;
    public static final double lowerSpeed = -0.1;
    
	public static final double MIN_HEIGHT = 78;
	public static final double START_HEIGHT = 100;
	public static final double MAX_HEIGHT = 213;

	public static final int[] ports = {27, 28};

	public CANSparkMax left = new CANSparkMax(ports[0], MotorType.kBrushless);
	public CANSparkMax right = new CANSparkMax(ports[1], MotorType.kBrushless);
	public AbsoluteEncoder enc = left.getAbsoluteEncoder();

	public void set(double speed) {
		left.set(speed);
		right.set(-speed);
	}

	public void lift() {
		set(liftSpeed);
	}

	public void lower() {
		set(lowerSpeed);
	}

	public double target = 0;
	public void setTarget(double t) {
		target = Utility.clamp(t, MIN_HEIGHT, MAX_HEIGHT);
	}

	static final double deadband = 3;
	static final double deadspeed = 0.082;
	static final double kp = 0.005, ki = 0.00005, kd = 0;
	double errSum = 0, lastErr = 0, lastPower = 0;

	public void pidLift(double dt) {
		SmartDashboard.putNumber("Pivot height", getHeight());
		SmartDashboard.putNumber("Pivot target", target);
		double err = target - getHeight();

		SmartDashboard.putNumber("Pivot error", err);

		// calculate power using kp, ki, kd
		double power = Math.abs(err) > deadband ?
			kp * err + ki * errSum + kd * ((err-lastErr)/dt)
		:0;

		// SmartDashboard.putNumber("Pivot power", power);

		errSum += err;
		errSum *= .99;
		lastErr = err;

		// clip and set speed
		if(power > 0) {
			if(power - lastPower > maxAccel) power = lastPower + maxAccel;
		}
		else {
			if(power - lastPower < -maxAccel) power = lastPower - maxAccel;
		}
		
		power = (power > maxSpeed ? maxSpeed : (power < -maxSpeed ? -maxSpeed : power));
		
		// if(Math.abs(err) > deadband) {
		// 	if(power > 0) power = Math.max(power, deadspeed);
		// 	if(power < 0) power = Math.min(power, -deadspeed);
		// }
		// power = Utility.clamp(power, maxSpeed, -maxSpeed);
		lastPower = power;
		SmartDashboard.putNumber("Pivot power", power);

		set(power);
	}

	static final double off = 0;
	public double getHeight() {
		return enc.getPosition() - off;
	}

	public void stop() {
		set(0);
	}
}
