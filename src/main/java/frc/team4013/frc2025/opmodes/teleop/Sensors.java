package frc.team4013.frc2025.opmodes.teleop;

// import com.revrobotics.Rev2mDistanceSensor;
// import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4013.frc2025.hardware.Grinder;
import frc.team4013.frc2025.opmodes.Opmode;

public class Sensors implements Opmode {
    public DigitalInput beambreak;
    // public Rev2mDistanceSensor dist;

    public void init(Grinder bot) {
        beambreak = new DigitalInput(5);
        // dist = new Rev2mDistanceSensor(Port.kOnboard);
        // dist.setAutomaticMode(true);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("beambreak", beambreak.get());
        // SmartDashboard.putBoolean("distworks", dist.isRangeValid());
        // SmartDashboard.putNumber("distance", dist.getRange());
    }
}