// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import com.ctre.phoenix6.hardware.CANcoder;
// import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ModuleConstants;

public class SwerveModule {
	public final CANSparkMax powerMotor;
	public final CANSparkMax spinMotor;

	public final RelativeEncoder powerEnc;
	public final RelativeEncoder spinEnc;

	/**
	 * Constructs a SwerveModule.
	 *
	 * @param driveMotorChannel The channel of the drive motor.
	 * @param turningMotorChannel The channel of the turning motor.
	 * @param driveEncoderReversed Whether the drive encoder is reversed.
	 * @param turningEncoderReversed Whether the turning encoder is reversed.
	 */
	public SwerveModule(
			int driveMotorChannel,
			int turningMotorChannel,
			boolean driveEncoderReversed,
			boolean turningEncoderReversed,
			double spinEncConversion) {

		powerMotor = new CANSparkMax(driveMotorChannel, MotorType.kBrushless);
		spinMotor = new CANSparkMax(turningMotorChannel, MotorType.kBrushless);

		powerEnc = powerMotor.getEncoder();
		spinEnc = spinMotor.getEncoder();

		powerEnc.setPositionConversionFactor(ModuleConstants.kDriveEncoderDistancePerPulse);
		spinEnc.setPositionConversionFactor(spinEncConversion);

		// spinPID.enableContinuousInput(-Math.PI, Math.PI);
	}

	/**
	 * Returns the current state of the module.
	 * @return The current state of the module.
	 */
	public SwerveModuleState getState() {
		return new SwerveModuleState(
				// powerEnc.getRate(), new Rotation2d(spinEnc.getDistance()));
				powerEnc.getVelocity(), new Rotation2d(spinEnc.getPosition()));
	}

	/**
	 * Returns the current position of the module.
	 * @return The current position of the module.
	 */
	public SwerveModulePosition getPosition() {
		return new SwerveModulePosition(
				powerEnc.getPosition(), new Rotation2d(spinEnc.getPosition()));
	}

	// /**
	//  * Sets the desired state for the module.
	//  * @param desiredState Desired state with speed and angle.
	//  */
	// public void setDesiredState(SwerveModuleState desiredState) {
	// 	var encoderRotation = new Rotation2d(spinEnc.getPosition());

	// 	// Optimize the reference state to avoid spinning further than 90 degrees
	// 	SwerveModuleState state = SwerveModuleState.optimize(desiredState, encoderRotation);

	// 	// Scale speed by cosine of angle error. This scales down movement perpendicular to the desired
	// 	// direction of travel that can occur when modules change directions. This results in smoother
	// 	// driving.
	// 	state.speedMetersPerSecond *= state.angle.minus(encoderRotation).getCos();

	// 	// Calculate the drive output from the drive PID controller.
	// 	final double driveOutput =
	// 			powerPID.calculate(powerEnc.getVelocity(), state.speedMetersPerSecond);

	// 	// Calculate the turning motor output from the turning PID controller.
	// 	final double turnOutput =
	// 			spinPID.calculate(spinEnc.getPosition(), state.angle.getRadians());

	// 	// Calculate the turning motor output from the turning PID controller.
	// 	powerMotor.set(driveOutput);
	// 	spinMotor.set(turnOutput);
	// }

	static final double spinDeadband = 0.01;
	static final double kpSpin = 0.4, kiSpin = 0, kdSpin = 0;
	double errSum = 0;
	double lastErr = 0;

	public double maxAccel = 0.01;
	public double lastPower = 0; 
	public void pidSpin(double target, double dt, double speed) {
		double err = target - spinEnc.getPosition();
		boolean reversed = false;

		// clip error to (-PI, PI), reversing if necessary
		while(err > Math.PI) {err -= Math.PI; reversed = !reversed;}
		while(err < -Math.PI) {err += Math.PI; reversed = !reversed;}
		
		// flip error if >PI and reverse
		if (Math.abs(err) > Math.PI / 2) {
			err = (err < -Math.PI / 2 ? err + Math.PI: err - Math.PI);
			reversed = !reversed;
		}

		
		// SmartDashboard.putNumber(powerMotor.getDeviceId()+" error", err);

		// calculate power using kp, ki, kd
		double power = Math.abs(err) > spinDeadband ?
			kpSpin * err + kiSpin * errSum + kdSpin * ((err-lastErr)/dt)
		:0;

		// SmartDashboard.putNumber(powerMotor.getDeviceId()+" power", power);


		spinMotor.set(power);

		// update integral and derivative quanitites
		errSum += err;
		lastErr = err;

		// clip and set speed
		if(speed > 0) {
			if(speed - lastPower > maxAccel) speed = lastPower + maxAccel;
		}
		else {
			if(speed - lastPower < -maxAccel) speed = lastPower - maxAccel;
		}
		// if(speed - lastPower > maxAccel) speed = lastPower + Math.signum(lastPower) * maxAccel;
		speed = (speed > 1 ? 1 : (speed < -1 ? -1 : speed));
		powerMotor.set(reversed ? -speed : speed);
		lastPower = speed;
	}

	public void drive(double x, double y, double r, double theta) {
		// SmartDashboard.putNumber(powerMotor.getDeviceId()+" x", x);
		// SmartDashboard.putNumber(powerMotor.getDeviceId()+" y", y);
		double vx = x + r * Math.cos(theta);
		double vy = y + r * Math.sin(theta);
		pidSpin(Math.atan2(vy, vx), .02, Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)));
	}

	public void setPower(double power) {
		// powerMotor.set(backwards ? -power : power);
	}

	/** Zeroes all the SwerveModule encoders. */
	public void resetEncoders() {
		powerEnc.setPosition(0);
		spinEnc.setPosition(0);
	}
}
