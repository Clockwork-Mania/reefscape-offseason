package frc.robot.hardware;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
	Vision vision;

	double width = 23.5;
	SwerveDriveKinematics kin = new SwerveDriveKinematics(
		new Translation2d( width/2,  width/2),
		new Translation2d( width/2, -width/2),
		new Translation2d(-width/2,  width/2),
		new Translation2d(-width/2, -width/2)
	);

	public Swerve() {
		frontLeft  = new SwerveModule(3, 7, 11, .457);
		backLeft   = new SwerveModule(4, 8, 12, .268);
		frontRight = new SwerveModule(2, 6, 10, .030);
		backRight  = new SwerveModule(5, 9, 13, .309);
		odo = new SwerveDriveOdometry(kin, heading2d(), positions());
		targetMode = TargetMode.None;
	}

	public void addVision(Vision vision) {
		this.vision = vision;
	}

	public void setSpeeds(ChassisSpeeds speeds) {
		SwerveModuleState[] moduleStates = kin.toSwerveModuleStates(speeds);
		SwerveModuleState fL = moduleStates[0];
		SwerveModuleState bL = moduleStates[1];
		SwerveModuleState fR = moduleStates[2];
		SwerveModuleState bR = moduleStates[3];

		frontLeft.pidSpin(fL.angle.getRadians(), 0.02, fL.speedMetersPerSecond);
		frontRight.pidSpin(fR.angle.getRadians(), 0.02, fR.speedMetersPerSecond);
		backLeft.pidSpin(bL.angle.getRadians(), 0.02, bL.speedMetersPerSecond);
		backRight.pidSpin(bR.angle.getRadians(), 0.02, bR.speedMetersPerSecond);
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
	double visionTarget;
	enum TargetMode {
		None,
		Odometry,
		Vision
	};
	TargetMode targetMode;

	public void setTarget(Pose2d target) {
		this.target = target;
		targetMode = TargetMode.Odometry;
	}

	public void setVisionTarget(double target) {
		this.visionTarget = target;
		targetMode = TargetMode.Vision;
	}

	public void setTarget(double x, double y, double h) {
		setTarget(new Pose2d(x, y, new Rotation2d(h)));
	}

	public void driveto() {
		switch(targetMode) {
			case Odometry:
				drivetoOdo();
				break;
			case Vision:
				drivetoVision();
				break;
			default:
				stop();
		}
	}

	double MAG_KP = .4, DH_KP = .1;
	double MAG_CAP = .5, DH_CAP = .2;
	public void drivetoOdo() {
		Translation2d tr = target.minus(pose()).getTranslation();
		double mag = tr.getNorm(), ang = tr.getAngle().getRadians();
		double dh = Utility.fixang(target.getRotation().getRadians()-heading());
		double vy, vx;
		mag = Math.min(mag*MAG_KP, 0.5);
		dh = Utility.clamp(dh*DH_KP, -DH_CAP, DH_CAP);
		vx = mag * Math.cos(ang);
		vy = mag * Math.sin(ang);
		drive(vx, vy, -dh, true);
	}

	double VIS_KP = 0.01;
	public void drivetoVision() {
		if(vision.foundTag()) {
			double err = visionTarget-vision.getX();
			drive(VIS_KP*err, 0, 0, false);
		}
		else stop();
	}

	double TR_THRESH = 0.05, ROT_THRESH = Math.PI/8;
	double VIS_THRESH = 0.2;
	public boolean ready() {
		switch(targetMode) {
			case Odometry:
				Transform2d err = target.minus(pose());
				return
					err.getTranslation().getNorm() < TR_THRESH &&
					err.getRotation().getRadians() < ROT_THRESH;
			case Vision:
				return Math.abs(visionTarget-vision.getX())<VIS_THRESH;
			default: return true;
		}
	}

	@Override
	public void periodic() {odo.update(heading2d(), positions());}
	public Pose2d pose() {return odo.getPoseMeters();}
	public void resetOdo() {resetOdo(new Pose2d());}
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
