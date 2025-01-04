// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.old;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystemOrig extends SubsystemBase {
	// -------------------- CONSTANTS -------------------- //
	// order is fl, rl, fr, rr
	public static final int[] powerPorts = {8, 2, 4, 6};
	public static final int[] spinPorts = {1, 3, 5, 7};
	public static final boolean[] powerReversed = {false, true, false, true};
	public static final boolean[] spinReversed = {false, true, false, true};
	// public static final int[] spinEncPorts = {9, 10, 11, 12};

	// Robot swerve modules
	// public final SwerveModule frontLeft =
	//     new SwerveModule(
	//         DriveConstants.flpMotorPort,
	//         DriveConstants.flsMotorPort,
	//         DriveConstants.flpEncReversed,
	//         DriveConstants.flsEncReversed);
	// public final SwerveModule rearLeft =
	//     new SwerveModule(
	//         DriveConstants.rlpMotorPort,
	//         DriveConstants.rlsMotorPort,
	//         DriveConstants.rlpEncReversed,
	//         DriveConstants.rlsEncReversed);
	// public final SwerveModule frontRight =
	//     new SwerveModule(
	//         DriveConstants.frpMotorPort,
	//         DriveConstants.frsMotorPort,
	//         DriveConstants.frpEncReversed,
	//         DriveConstants.frsEncReversed);
	// public final SwerveModule rearRight =
	//     new SwerveModule(
	//         DriveConstants.rrpMotorPort,
	//         DriveConstants.rrsMotorPort,
	//         DriveConstants.rrpEncReversed,
	//         DriveConstants.rrsEncReversed);

	public final SwerveModuleOrig frontLeft = new SwerveModuleOrig(
		powerPorts[0], spinPorts[0],
		powerReversed[0], spinReversed[0], Math.PI/10.4
	);

	public final SwerveModuleOrig rearLeft = new SwerveModuleOrig(
		powerPorts[1], spinPorts[1],
		powerReversed[1], spinReversed[1], Math.PI/10.4
	);

	public final SwerveModuleOrig frontRight = new SwerveModuleOrig(
		powerPorts[2], spinPorts[2],
		powerReversed[2], spinReversed[2], Math.PI/0.54
	);

	public final SwerveModuleOrig rearRight = new SwerveModuleOrig(
		powerPorts[3], spinPorts[3],
		powerReversed[3], spinReversed[3], Math.PI/0.54
	);

	/** An array containing all four wheels */
	public final SwerveModuleOrig wheels[] = {frontLeft, rearLeft, frontRight, rearRight};

	// The gyro sensor
	private final ADIS16448_IMU gyro = new ADIS16448_IMU();

	// Odometry class for tracking robot pose
	SwerveDriveOdometry odometry = new SwerveDriveOdometry(
		DriveConstants.kDriveKinematics,
		getRotation2d(),
		getPositions()
	);

	/** Creates a new DriveSubsystem. */
	public DriveSubsystemOrig() {}

	@Override
	public void periodic() {
		// Update the odometry in the periodic block
		odometry.update(
			getRotation2d(),
			getPositions()
		);
	}

	/**
	 * Returns the currently-estimated pose of the robot.
	 * @return The pose.
	 */
	public Pose2d getPose() {
		return odometry.getPoseMeters();
	}

	/**
	 * Resets the odometry to the specified pose.
	 * @param pose The pose to which to set the odometry.
	 */
	public void resetOdometry(Pose2d pose) {
		odometry.resetPosition(
			getRotation2d(),
			getPositions(),
			pose
		);
	}

	/**
	 * Method to drive the robot using joystick info.
	 * @param xSpeed Speed of the robot in the x direction (forward).
	 * @param ySpeed Speed of the robot in the y direction (sideways).
	 * @param rot Angular rate of the robot.
	 * @param fieldRelative Whether the provided x and y speeds are relative to the field.
	 */
	public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
		setModuleStates(
			DriveConstants.kDriveKinematics.toSwerveModuleStates(
				ChassisSpeeds.discretize(fieldRelative ?
					ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, getRotation2d()):
					new ChassisSpeeds(xSpeed, ySpeed, rot),
					DriveConstants.kDrivePeriod
				)
			)
		);
	}

	/**
	 * Sets the swerve ModuleStates.
	 * @param desiredStates The desired SwerveModule states.
	 */
	public void setModuleStates(SwerveModuleState[] desiredStates) {
		SwerveDriveKinematics.desaturateWheelSpeeds(
				desiredStates, DriveConstants.maxSpeed);
		// for(int i = 0; i < 4; ++i) {
		// 	wheels[i].setDesiredState(desiredStates[i]);
		// }
		frontLeft.setDesiredState(desiredStates[0]);
		frontRight.setDesiredState(desiredStates[1]);
		rearLeft.setDesiredState(desiredStates[2]);
		rearRight.setDesiredState(desiredStates[3]);
	}

	public void runToAngle(double angle, double speed) {
		Rotation2d angle2d = new Rotation2d(angle);
		frontLeft.setDesiredState(new SwerveModuleState(speed, angle2d));
		frontRight.setDesiredState(new SwerveModuleState(speed, angle2d));
		rearLeft.setDesiredState(new SwerveModuleState(speed, angle2d));
		rearRight.setDesiredState(new SwerveModuleState(speed, angle2d));
	}

	/** Resets the drive encoders to currently read a position of 0. */
	public void resetEncoders() {
		frontLeft.resetEncoders();
		rearLeft.resetEncoders();
		frontRight.resetEncoders();
		rearRight.resetEncoders();
	}

	/** Zeroes the heading of the robot. */
	public void zeroHeading() {
		gyro.reset();
	}

	/**
	 * Returns the heading of the robot.
	 * @return the robot's heading in degrees, from -180 to 180
	 */
	public double getHeading() {
		return gyro.getGyroAngleZ();
	}

	/**
	 * Returns the robot's rotation.
	 * @return the robot's rotation as a Rotation2d
	 */
	public Rotation2d getRotation2d() {
		return new Rotation2d(gyro.getGyroAngleZ());
	}

	/**
	 * Returns the positions of each wheel.
	 * @return An array containing the postions of each wheel
	 */
	public SwerveModulePosition[] getPositions() {
		return new SwerveModulePosition[] {
			frontLeft.getPosition(),
			frontRight.getPosition(),
			rearLeft.getPosition(),
			rearRight.getPosition()
		};
	}

	/**
	 * Returns the turn rate of the robot.
	 * @return The turn rate of the robot, in degrees per second
	 */
	public double getTurnRate() {
		return gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
	}
}
