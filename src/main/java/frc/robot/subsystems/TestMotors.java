package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestMotors {
	public static final int[] powerPorts = {8, 2, 4, 6};
	public static final int[] spinPorts = {1, 3, 5, 7};
    public static final double[] spinOffs = {0, 0, 0, 0};

    // public final SparkMax[] powerMotors = new SparkMax[4];
	// public final SparkMax[] spinMotors = new SparkMax[4];
	// public final RelativeEncoder[] powerEncs = new RelativeEncoder[4];
	// public final SparkAbsoluteEncoder[] spinEncs = new SparkAbsoluteEncoder[4];

	public final TalonFX[] powerMotors = new TalonFX[4];
	public final TalonFX[] spinMotors = new TalonFX[4];
	public void setup() {
		for(int i = 0; i < 4; ++i) {
			// powerMotors[i] = new SparkMax(powerPorts[i], MotorType.kBrushless);
			powerMotors[i] = new TalonFX(powerPorts[i]);
			// spinMotors[i] = new SparkMax(spinPorts[i], MotorType.kBrushless);
			spinMotors[i] = new TalonFX(spinPorts[i]);
			// powerEncs[i] = powerMotors[i].getEncoder();
			// spinEncs[i] = spinMotors[i].getAbsoluteEncoder();
		}
	}

	public void printEverything() {
		for(int i = 0; i < 4; ++i) {
			// SmartDashboard.putNumber("pv"+i, powerEncs[i].getVelocity());
			// SmartDashboard.putNumber("sp"+i, spinEncs[i].getPosition());
			// SmartDashboard.putNumber("sv"+i, spinEncs[i].getVelocity());

			SmartDashboard.putString("pp"+i, powerMotors[i].getPosition().refresh().toString());
			SmartDashboard.putString("pv"+i, powerMotors[i].getVelocity().refresh().toString());
			SmartDashboard.putString("sp"+i, spinMotors[i].getPosition().refresh().toString());
			SmartDashboard.putString("sv"+i, spinMotors[i].getVelocity().refresh().toString());
		}
	}

	public void move1() {

	}
}
