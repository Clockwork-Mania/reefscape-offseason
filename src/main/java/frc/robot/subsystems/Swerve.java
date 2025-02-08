package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Swerve extends SubsystemBase  {
	// order is fl, bl, fr, br
	public static final int[] powerPorts = {3, 4, 2, 5}; //{2, 3, 5, 4};
	public static final int[] spinPorts = {7, 8, 6, 9}; // {6, 7, 9, 8};
	public static final int[] spinEncPorts = {11, 12, 10, 13}; //{10, 11, 13, 12}; //{13, 10, 12, 11};
	public static final double[] spinOffs = {.456, .268, .854, .308}; //{.354, .956, .808, .768}; //{.808, .354, .768, .956};

	public SwerveModule frontLeft, backLeft, frontRight, backRight;
	public SwerveModule wheels[] = {frontLeft, backLeft, frontRight, backRight};

	private final ADIS16448_IMU gyro = new ADIS16448_IMU();

	public SwerveDriveOdometry odo;

	double width = 23.5;
	SwerveDriveKinematics kin = new SwerveDriveKinematics(
		new Translation2d(width/2, width/2),
		new Translation2d(width/2, -width/2),
		new Translation2d(-width/2, width/2),
		new Translation2d(-width/2, -width/2)
	);

	public Swerve() {
		frontLeft = new SwerveModule(powerPorts[0], spinPorts[0], spinEncPorts[0], spinOffs[0]);
		backLeft = new SwerveModule(powerPorts[1], spinPorts[1], spinEncPorts[1], spinOffs[1]);
		frontRight = new SwerveModule(powerPorts[2], spinPorts[2], spinEncPorts[2], spinOffs[2]);
		backRight = new SwerveModule(powerPorts[3], spinPorts[3], spinEncPorts[3], spinOffs[3]);
		odo = new SwerveDriveOdometry(kin, getRotation2d(), getPositions());
	}

	@Override
	public void periodic() {
		odo.update(
			getRotation2d(),
			getPositions()
		);
	}

	public Pose2d getPose() {
		return odo.getPoseMeters();
	}

	public void resetOdometry(Pose2d pose) {
		odo.resetPosition(
			getRotation2d(),
			getPositions(),
			pose
		);
	}

	public void drive(double x, double y, double r, boolean fieldRelative) {
		// if field relative, fix angle
		if(fieldRelative) {
			double mag = Math.sqrt(x*x+y*y);
			double ang = Math.atan2(y, x);
			SmartDashboard.putNumber("heading", getHeading());
			ang += getHeading();
			x = mag * Math.cos(ang);
			y = mag * Math.sin(ang);
		}
		frontLeft.drive(x, y, r, Math.PI*.25);
		backLeft.drive(x, y, r, Math.PI*.75);
		frontRight.drive(x, y, r, Math.PI*-.25);
		backRight.drive(x, y, r, Math.PI*-.75);
	}

	public void resetEncoders() {
		for(SwerveModule wheel : wheels) wheel.resetEncoders();
	}

	public void zeroHeading() {
		gyro.reset();
	}

	public double getHeading() {
		return gyro.getGyroAngleZ() * Math.PI/180;
	}

	public Rotation2d getRotation2d() {
		return new Rotation2d(getHeading());
	}

	public SwerveModulePosition[] getPositions() {
		return new SwerveModulePosition[] {
			frontLeft.getPosition(),
			backLeft.getPosition(),
			frontRight.getPosition(),
			backRight.getPosition()
		};
	}

	public double getTurnRate() {
		return gyro.getRate() * (DriveConstants.gyroReversed ? -1 : 1);
	}
}
