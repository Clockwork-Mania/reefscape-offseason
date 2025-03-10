// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.*;
import frc.robot.opmodes.teleop.AllWheels;
import frc.robot.opmodes.teleop.ModuleTest;
import frc.robot.opmodes.teleop.TalonTest;
import frc.robot.hardware.*;
import frc.robot.opmodes.teleop.SwerveTeleop;

public class Robot extends TimedRobot {
	Grinder bot;
    XboxController con;
    Field2d field;
	ShuffleboardTab main;

	SendableChooser<Class<?>> autoPicker, telePicker, testPicker;

	XboxController controller = new XboxController(0);

	SendableChooser<Class<?>> opmodePicker(OpmodeList.NamedOpmode modes[]) {
		SendableChooser<Class<?>> picker = new SendableChooser<>();
		if(modes.length > 0) picker.setDefaultOption(modes[0].name, modes[0].mode);
		for(int i = 1; i < modes.length; ++i) picker.addOption(modes[i].name, modes[i].mode);
		return picker;
	}

	void opInit(SendableChooser<Class<?>> picker) {
		Class<?> mode = picker.getSelected();
		try{op = (Opmode)mode.getDeclaredConstructor().newInstance();}
		catch(Exception e){}
		op.init();
	}

	public Robot() {
		autoPicker = opmodePicker(OpmodeList.auto);
		telePicker = opmodePicker(OpmodeList.teleop);
		testPicker = opmodePicker(OpmodeList.test);

		main = Shuffleboard.getTab("Opmode Selection");
		main.add("Auto", autoPicker);
		main.add("Teleop", autoPicker);
		main.add("Test", autoPicker);
        // SmartDashboard.putData("Field", field);

		bot = new Grinder();
        con = new XboxController(0);
        field = new Field2d();
	}

	@Override
	public void robotPeriodic() {
	}

	Opmode op;

	@Override
	public void autonomousInit() {
		opInit(autoPicker);
	}

	@Override
	public void autonomousPeriodic() {
		op.periodic();
	}

	@Override
	public void teleopInit() {
		opInit(telePicker);
	}

	@Override
	public void teleopPeriodic() {
		op.periodic();
    }

	@Override
	public void testInit() {
		opInit(testPicker);
	}

	@Override
	public void testPeriodic() {
		op.periodic();
	}
}
