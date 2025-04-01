package frc.robot.opmodes.teleop;

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

    public void init(Grinder bot) {
        beambreak = new DigitalInput(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("beambreak", beambreak.get());
    }
}