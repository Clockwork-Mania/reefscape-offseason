package frc.robot.opmodes.test;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.opmodes.Opmode;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;

public class ShuffleTest implements Opmode {
    public XboxController controller;
    ShuffleboardTab tab;
    GenericEntry aButton;

    public void init() {
        tab = Shuffleboard.getTab("new tab");
        Shuffleboard.selectTab("new tab");
        aButton = tab.add("a pressed?", false).getEntry();
    }

    // vision display
    // coral/algae mode
    // heading
    // arm preset
    // reef image (top-down)

    public void periodic() {
        aButton.setBoolean(controller.getAButton());
    }
}
