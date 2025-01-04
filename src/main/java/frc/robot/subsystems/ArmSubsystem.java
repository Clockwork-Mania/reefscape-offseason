package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    public static final int[] elevPorts = {100, 100};
    public static final int pivotPort = 100;
    public static final double elevKp = 1, elevKi = 0, elevKd = 0;
    public static final double pivotKp = 1, pivotKi = 0, pivotKd = 0;

    public PIDController elevPID = new PIDController(elevKp, elevKi, elevKd);
    public PIDController pivotPID = new PIDController(pivotKp, pivotKi, pivotKd);

    public CANSparkMax
        elevLeft = new CANSparkMax(elevPorts[0], MotorType.kBrushless),
        elevRight = new CANSparkMax(elevPorts[1], MotorType.kBrushless),
        pivot = new CANSparkMax(pivotPort, MotorType.kBrushless);

    public RelativeEncoder
        elevLeftEnc = elevLeft.getEncoder(),
        elevRightEnc = elevRight.getEncoder(),
        pivotEnc = pivot.getEncoder();
    
    public Command elevateCommand(double height) {
        return new PIDCommand(
            elevPID, 
            this::getHeight,
            height,
            null,
            this
        );
    }

    public Command pivotCommand(double angle) {
        return new PIDCommand(
            pivotPID, 
            this::getAngle,
            angle,
            null,
            this
        );
    }

    public double getHeight() {
        return elevLeftEnc.getPosition();
    }

    public double getAngle() {
        return pivotEnc.getPosition();
    }
}
