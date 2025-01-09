package frc.robot.opmodes.test;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Swerve;

public class CompressorTest implements Opmode {
    XboxController controller;
    Compressor comp;

    public void init() {
        comp = new Compressor(1, PneumaticsModuleType.CTREPCM);
    }

    public void periodic() {
        SmartDashboard.putNumber("curr", comp.getCurrent());
        SmartDashboard.putBoolean("on?", comp.isEnabled());
        SmartDashboard.putBoolean("valve?", comp.getPressureSwitchValue());
    }
}
