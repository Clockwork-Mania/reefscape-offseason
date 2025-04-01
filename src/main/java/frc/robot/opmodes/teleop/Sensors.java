package frc.robot.opmodes.teleop;

import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.Grinder;
import frc.robot.opmodes.Opmode;

public class Sensors implements Opmode {
    public DigitalInput beambreak;
    public Rev2mDistanceSensor dist;

    public void init(Grinder bot) {
        beambreak = new DigitalInput(5);
        dist = new Rev2mDistanceSensor(Port.kOnboard);
        dist.setAutomaticMode(true);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("beambreak", beambreak.get());
        SmartDashboard.putBoolean("distworks", dist.isRangeValid());
        SmartDashboard.putNumber("distance", dist.getRange());
    }
}