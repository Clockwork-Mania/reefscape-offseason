// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.old;

// import com.ctre.phoenix6.hardware.CANcoder;
// import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ModuleConstants;

public class SwerveModuleOrig {
	public final CANSparkMax powerMotor;
	public final CANSparkMax spinMotor;

	public final RelativeEncoder powerEnc;
	public final RelativeEncoder spinEnc;
	// public final AbsoluteEncoder spinEnc;
	// public final CANcoder spinEnc;


	private final PIDController powerPID =
			new PIDController(ModuleConstants.kPModuleDriveController, 0, 0);

	// Using a TrapezoidProfile PIDController to allow for smooth turning
	private final ProfiledPIDController spinPID =
			new ProfiledPIDController(
					ModuleConstants.kPModuleTurningController,
					0,
					0,
					new TrapezoidProfile.Constraints(
							ModuleConstants.kMaxModuleAngularSpeedRadiansPerSecond,
							ModuleConstants.kMaxModuleAngularAccelerationRadiansPerSecondSquared));

	// /**
	//  * Constructs a SwerveModule.
	//  *
	//  * @param driveMotorChannel The channel of the drive motor.
	//  * @param turningMotorChannel The channel of the turning motor.
	//  * @param driveEncoderReversed Whether the drive encoder is reversed.
	//  * @param turningEncoderReversed Whether the turning encoder is reversed.
	//  */
	// public SwerveModule(
	//     int driveMotorChannel,
	//     int turningMotorChannel,
	//     boolean driveEncoderReversed,
	//     boolean turningEncoderReversed) {
	//   powerMotor = new CANSparkMax(driveMotorChannel, MotorType.kBrushless);
	//   spinMotor = new CANSparkMax(turningMotorChannel, MotorType.kBrushless);
		

	//   // powerEnc = new Encoder(driveEncoderChannels[0], driveEncoderChannels[1]);

	//   // spinEnc = new Encoder(turningEncoderChannels[0], turningEncoderChannels[1]);

	//   powerEnc = powerMotor.getEncoder();
	//   // spinEnc = spinMotor.getEncoder();
	//   // spinEnc = spinMotor.getAbsoluteEncoder();

	//   // Set the distance per pulse for the drive encoder. We can simply use the
	//   // distance traveled for one rotation of the wheel divided by the encoder
	//   // resolution.

	//   powerEnc.setPositionConversionFactor(ModuleConstants.kDriveEncoderDistancePerPulse);

	//   // Set whether drive encoder should be reversed or not
	//   // powerEnc.setInverted(driveEncoderReversed);

	//   // Set the distance (in this case, angle) in radians per pulse for the turning encoder.
	//   // This is the the angle through an entire rotation (2 * pi) divided by the
	//   // encoder resolution.
	//   spinEnc.setPositionConversionFactor(ModuleConstants.kTurningEncoderDistancePerPulse);


	//   // Set whether turning encoder should be reversed or not
	//   // spinEnc.setInverted(turningEncoderReversed);

	//   // Limit the PID Controller's input range between -pi and pi and set the input
	//   // to be continuous.
	//   spinPID.enableContinuousInput(-Math.PI, Math.PI);
	// }

	/**
	 * Constructs a SwerveModule.
	 *
	 * @param driveMotorChannel The channel of the drive motor.
	 * @param turningMotorChannel The channel of the turning motor.
	 * @param driveEncoderReversed Whether the drive encoder is reversed.
	 * @param turningEncoderReversed Whether the turning encoder is reversed.
	 */
	public SwerveModuleOrig(
			int driveMotorChannel,
			int turningMotorChannel,
			boolean driveEncoderReversed,
			boolean turningEncoderReversed,
			double spinEncConversion) {
		powerMotor = new CANSparkMax(driveMotorChannel, MotorType.kBrushless);
		spinMotor = new CANSparkMax(turningMotorChannel, MotorType.kBrushless);
		

		// powerEnc = new Encoder(driveEncoderChannels[0], driveEncoderChannels[1]);

		// spinEnc = new Encoder(turningEncoderChannels[0], turningEncoderChannels[1]);

		powerEnc = powerMotor.getEncoder();
		spinEnc = spinMotor.getEncoder();
		// spinEnc = spinMotor.getAbsoluteEncoder();
		// spinEnc = new CANcoder(spinEncPort);

		// Set the distance per pulse for the drive encoder. We can simply use the
		// distance traveled for one rotation of the wheel divided by the encoder
		// resolution.

		powerEnc.setPositionConversionFactor(ModuleConstants.kDriveEncoderDistancePerPulse);
		spinEnc.setPositionConversionFactor(spinEncConversion);

		// Set whether drive encoder should be reversed or not
		// powerEnc.setInverted(driveEncoderReversed);

		// Set the distance (in this case, angle) in radians per pulse for the turning encoder.
		// This is the the angle through an entire rotation (2 * pi) divided by the
		// encoder resolution.
		// spinEnc.setPositionConversionFactor(ModuleConstants.kTurningEncoderDistancePerPulse);


		// Set whether turning encoder should be reversed or not
		// spinEnc.setInverted(turningEncoderReversed);

		// Limit the PID Controller's input range between -pi and pi and set the input
		// to be continuous.
		spinPID.enableContinuousInput(-Math.PI, Math.PI);
	}

	/**
	 * Returns the current state of the module.
	 *
	 * @return The current state of the module.
	 */
	public SwerveModuleState getState() {
		return new SwerveModuleState(
				// powerEnc.getRate(), new Rotation2d(spinEnc.getDistance()));
				powerEnc.getVelocity(), new Rotation2d(spinEnc.getPosition()));
	}

	/**
	 * Returns the current position of the module.
	 *
	 * @return The current position of the module.
	 */
	public SwerveModulePosition getPosition() {
		return new SwerveModulePosition(
				powerEnc.getPosition(), new Rotation2d(spinEnc.getPosition()));
	}

	/**
	 * Sets the desired state for the module.
	 *
	 * @param desiredState Desired state with speed and angle.
	 */
	public void setDesiredState(SwerveModuleState desiredState) {
		var encoderRotation = new Rotation2d(spinEnc.getPosition());

		// Optimize the reference state to avoid spinning further than 90 degrees
		SwerveModuleState state = SwerveModuleState.optimize(desiredState, encoderRotation);

		// Scale speed by cosine of angle error. This scales down movement perpendicular to the desired
		// direction of travel that can occur when modules change directions. This results in smoother
		// driving.
		state.speedMetersPerSecond *= state.angle.minus(encoderRotation).getCos();

		// Calculate the drive output from the drive PID controller.
		final double driveOutput =
				powerPID.calculate(powerEnc.getVelocity(), state.speedMetersPerSecond);

		// Calculate the turning motor output from the turning PID controller.
		final double turnOutput =
				spinPID.calculate(spinEnc.getPosition(), state.angle.getRadians());

		// Calculate the turning motor output from the turning PID controller.
		powerMotor.set(driveOutput);
		spinMotor.set(turnOutput);
	}

	public void pidSpin(double targetAngle, double nenc) {
		double current = spinEnc.getPosition();
		int deadband = 10;
		double kp = nenc / 10;

		double power = kp * Math.abs(spinEnc.getPosition() - targetAngle);

		if (Math.abs(current - targetAngle) > nenc / deadband) {
			power = -kp * (spinEnc.getPosition() - targetAngle);
			spinMotor.set(power);
		}
		else {
			power = 0;
			spinMotor.set(power);
		}

		SmartDashboard.putNumber("Power" + powerMotor.getDeviceId(), power);
		SmartDashboard.putNumber("Offset" + powerMotor.getDeviceId(), current - targetAngle);
	}

	/** Zeroes all the SwerveModule encoders. */
	public void resetEncoders() {
		powerEnc.setPosition(0);
		spinEnc.setPosition(0);
	}
}
