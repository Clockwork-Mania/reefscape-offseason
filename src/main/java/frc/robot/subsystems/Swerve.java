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
import frc.robot.Utility;

public class Swerve extends SubsystemBase  {
	// order is fl, bl, fr, br
	// public static final int[] powerIds = {3, 4, 2, 5};
	// public static final int[] spinIds = {7, 8, 6, 9};
	// public static final int[] encIds = {11, 12, 10, 13};
	// public static final double[] spinOffs = {.456, .268, .854, .308};

	public SwerveModule frontLeft, backLeft, frontRight, backRight;
	public SwerveModule wheels[] = {frontLeft, backLeft, frontRight, backRight};

	private final ADIS16448_IMU gyro = new ADIS16448_IMU();

	public SwerveDriveOdometry odo;

	double width = 23.5;
	SwerveDriveKinematics kin = new SwerveDriveKinematics(
		new Translation2d( width/2,  width/2),
		new Translation2d( width/2, -width/2),
		new Translation2d(-width/2,  width/2),
		new Translation2d(-width/2, -width/2)
	);

	public Swerve() {
		// frontLeft = new SwerveModule(powerIds[0], spinIds[0], encIds[0], spinOffs[0]);
		// backLeft = new SwerveModule(powerIds[1], spinIds[1], encIds[1], spinOffs[1]);
		// frontRight = new SwerveModule(powerIds[2], spinIds[2], encIds[2], spinOffs[2]);
		// backRight = new SwerveModule(powerIds[3], spinIds[3], encIds[3], spinOffs[3]);

		frontLeft  = new SwerveModule(3, 7, 11, .456);
		backLeft   = new SwerveModule(4, 8, 12, .268);
		frontRight = new SwerveModule(2, 6, 10, .854);
		backRight  = new SwerveModule(5, 9, 13, .308);
		odo = new SwerveDriveOdometry(kin, heading2d(), positions());
		targeting = false;
	}

	public void drive(double x, double y, double r, boolean relative) {
		if(relative) {
			double mag = Math.sqrt(x*x+y*y);
			double ang = Math.atan2(y, x);
			ang += heading();
			x = mag * Math.cos(ang);
			y = mag * Math.sin(ang);
		}
		frontLeft .drive(x, y, r,  Math.PI*.25);
		backLeft  .drive(x, y, r,  Math.PI*.75);
		frontRight.drive(x, y, r, -Math.PI*.25);
		backRight .drive(x, y, r, -Math.PI*.75);
	}

	Pose2d target;
	boolean targeting;

	public void setTarget(Pose2d target) {
		this.target = target;
		targeting = true;
	}

	public void setTarget(double x, double y, double h) {
		setTarget(new Pose2d(x, y, new Rotation2d(h)));
	}

	public void driveto() {
		if(targeting) {
			Translation2d tr = target.minus(pose()).getTranslation();
			double mag = tr.getNorm(), ang = tr.getAngle().getRadians();
			double dh = Utility.fixang(target.getRotation().getRadians()-heading());
			double vy, vx;
			mag = Math.min(0.4, mag/3);
			dh = Math.min(0.2, dh/12);
			vy = mag * Math.sin(ang);
			vx = mag * Math.cos(ang);
			drive(vy, vx, dh, true);
		}
		else stop();
	}

	@Override
	public void periodic() {odo.update(heading2d(), positions());}
	public Pose2d pose() {return odo.getPoseMeters().times(39.3701);}
	public void resetOdo(Pose2d pose) {odo.resetPosition(heading2d(), positions(), pose);}

	public void stop() {drive(0, 0, 0, false);}
	public void resetEncoders() {for(SwerveModule wheel : wheels) wheel.resetEncoders();}
	public void resetGyro() {gyro.reset();}
	public double heading() {return gyro.getGyroAngleZ() * Math.PI/180;}
	public Rotation2d heading2d() {return new Rotation2d(heading());}
	public double turnRate() {return gyro.getRate();}
	public SwerveModulePosition[] positions() {
		return new SwerveModulePosition[] {
			frontLeft.getPosition(),
			backLeft.getPosition(),
			frontRight.getPosition(),
			backRight.getPosition()
		};
	}

}
