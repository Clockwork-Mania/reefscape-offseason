package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    public static final double shootSpeed = -0.5;
    public static final double ampSpeed = -0.2;
    public static final double channelSpeed = -0.2;
    public static final double unchannelSpeed = -0.1;
    
	public static final int[] shooterPorts = {26, 25};
	public static final int[] channelPorts = {24, 23};

	// CANSparkMax motor = new CANSparkMax(port, MotorType.kBrushless);
	// MotorPair pair = new MotorPair(ports);
	CANSparkMax shooterTop = new CANSparkMax(shooterPorts[0], MotorType.kBrushless);
	CANSparkMax shooterBottom = new CANSparkMax(shooterPorts[1], MotorType.kBrushless);
	CANSparkMax channelTop = new CANSparkMax(channelPorts[0], MotorType.kBrushless);
	CANSparkMax channelBottom = new CANSparkMax(channelPorts[1], MotorType.kBrushless);

	// MotorPair shooter = new MotorPair(shooterPorts, false, false);
	// MotorPair channel = new MotorPair(channelPorts, true, true);

	public void shoot() {
		shooterTop.set(shootSpeed);
		shooterBottom.set(shootSpeed);
	}
	
	public void shoot(double speed) {
		shooterTop.set(shootSpeed * speed);
		shooterBottom.set(shootSpeed * speed);
	}

	public void ampShoot(double speed) {
		shooterTop.set(ampSpeed * speed);
		shooterBottom.set(ampSpeed * speed);
	}

	public void channel() {
		channelTop.set(channelSpeed);
		channelBottom.set(channelSpeed);
	}

    public void unchannel() {
		channelTop.set(-channelSpeed);
		channelBottom.set(-channelSpeed);
    }

	public void stop() {
		shooterTop.set(0);
		shooterBottom.set(0);
	}

	public void stopChannel() {
		channelTop.set(0);
		channelBottom.set(0);
	}
}
