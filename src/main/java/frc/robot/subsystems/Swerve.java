package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Swerve extends SubsystemBase  {
	// -------------------- CONSTANTS -------------------- //
	// order is fl, bl, fr, br 2 3 5 4
	public static final int[] powerPorts = {5, 2, 4, 3};
	public static final int[] spinPorts = {9, 6, 8, 7};
	public static final int[] spinEncPorts = {13, 10, 12, 11};
	public static final double[] spinOffs = {.808, .354, .768, .956};

	// public static final int[] spinEncPorts = {9, 10, 11, 12};


	public SwerveModule frontLeft;
	public SwerveModule rearLeft;
	public SwerveModule frontRight;
	public SwerveModule rearRight;
	
	// public final SwerveModule frontLeft = new SwerveModule(2, 6, 10, spinOffs[0]);
	// public final SwerveModule rearLeft = new SwerveModule(3, 7, 11, spinOffs[1]);
	// public final SwerveModule frontRight = new SwerveModule(4, 8, 12, spinOffs[2]);
	// public final SwerveModule rearRight = new SwerveModule(5, 9, 13, spinOffs[3]);

	/** An array containing all four wheels */
	// public SwerveModule wheels[] = new SwerveModule[4];
	public SwerveModule wheels[] = {frontLeft, rearLeft, frontRight, rearRight};

	/** The IMU/gyro */
	private final ADIS16448_IMU gyro = new ADIS16448_IMU();

	// Odometry class for tracking robot pose
	// SwerveDriveOdometry odometry = new SwerveDriveOdometry(
	// 	DriveConstants.kDriveKinematics,
	// 	getRotation2d(),
	// 	getPositions()
	// );

	public Swerve() {
		frontLeft = new SwerveModule(powerPorts[0], spinPorts[0], spinEncPorts[0], spinOffs[0]);
		rearLeft = new SwerveModule(powerPorts[1], spinPorts[1], spinEncPorts[1], spinOffs[1]);
		frontRight = new SwerveModule(powerPorts[2], spinPorts[2], spinEncPorts[2], spinOffs[2]);
		rearRight = new SwerveModule(powerPorts[3], spinPorts[3], spinEncPorts[3], spinOffs[3]);
	}

	@Override
	public void periodic() {
		// Update the odometry in the periodic block
		// odometry.update(
		// 	getRotation2d(),
		// 	getPositions()
		// );
	}

	/**
	 * Returns the currently-estimated pose of the robot.
	 * @return The pose.
	 */
	public Pose2d getPose() {
		// return odometry.getPoseMeters();
		return null;
	}

	/**
	 * Resets the odometry to the specified pose.
	 * @param pose The pose to which to set the odometry.
	 */
	public void resetOdometry(Pose2d pose) {
		// odometry.resetPosition(
		// 	getRotation2d(),
		// 	getPositions(),
		// 	pose
		// );
	}

	/**
	 * Method to drive the robot using joystick info.
	 * @param xSpeed Speed of the robot in the x direction (forward).
	 * @param ySpeed Speed of the robot in the y direction (sideways).
	 * @param rot Angular rate of the robot.
	 * @param fieldRelative Whether the provided x and y speeds are relative to the field.
	 */
	public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
		// if field relative, fix angle
		if(fieldRelative) {
			double mag = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2));
			double ang = Math.atan2(ySpeed, xSpeed);
			SmartDashboard.putNumber("heading", getHeading());
			ang += getHeading();
			xSpeed = mag * Math.cos(ang);
			ySpeed = mag * Math.sin(ang);
		}

		// frontLeft.drive(xSpeed, ySpeed, rot, Math.PI*-.25);
		// frontRight.drive(xSpeed, ySpeed, rot, Math.PI*.25);
		// rearRight.drive(xSpeed, ySpeed, rot, Math.PI*.75);
		// rearLeft.drive(xSpeed, ySpeed, rot, Math.PI*1.25);
		// frontLeft.drive(xSpeed, ySpeed, rot, Math.PI*-.25);
		// rearLeft.drive(xSpeed, ySpeed, rot, Math.PI*.25);
		// rearRight.drive(xSpeed, ySpeed, rot, Math.PI*.75);
		// frontRight.drive(xSpeed, ySpeed, rot, Math.PI*1.25);
		// frontLeft.drive(xSpeed, ySpeed, rot, Math.PI*.25);
		// rearLeft.drive(xSpeed, ySpeed, rot, Math.PI*.75);
		// rearRight.drive(xSpeed, ySpeed, rot, Math.PI*1.25);
		// frontRight.drive(xSpeed, ySpeed, rot, Math.PI*1.75);
		frontLeft.drive(xSpeed, ySpeed, rot, Math.PI*.25);
		frontRight.drive(xSpeed, ySpeed, rot, Math.PI*.75);
		rearRight.drive(xSpeed, ySpeed, rot, Math.PI*1.25);
		rearLeft.drive(xSpeed, ySpeed, rot, Math.PI*1.75);
	}

	/** Resets the drive encoders to currently read a position of 0. */
	public void resetEncoders() {
		for(SwerveModule wheel : wheels) wheel.resetEncoders();
	}

	/** Zeroes the heading of the robot. */
	public void zeroHeading() {
		gyro.reset();
	}

	/**
	 * Returns the heading of the robot.
	 * @return the robot's heading in radians, from -180 to 180
	 */
	public double getHeading() {
		return gyro.getGyroAngleZ() * Math.PI/180;
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
		return gyro.getRate() * (DriveConstants.gyroReversed ? -1.0 : 1.0);
	}
}
