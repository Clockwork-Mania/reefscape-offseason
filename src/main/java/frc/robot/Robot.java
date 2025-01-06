// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.*;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private final SendableChooser<String> m_chooser = new SendableChooser<>();

	// String teles[] = {"AllWheels", "TalonTest", "ModuleTest"};
	SendableChooser<String> autoPicker, telePicker, testPicker;

	XboxController controller = new XboxController(0);

	SendableChooser<String> opmodePicker(String type) {
		SendableChooser<String> picker = new SendableChooser<>();
		File f = new File("opmodes/"+type);
		String[] ss = f.list();
		for(String s : ss) s = s.substring(0, s.length()-5);
		picker.setDefaultOption(ss[0], ss[0]);
		for(int i = 1; i < ss.length; ++i) picker.addOption(ss[i], ss[i]);
		return picker;
	}

	public Robot() {
		m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
		m_chooser.addOption("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);

		autoPicker = opmodePicker("auto");
		telePicker = opmodePicker("teleop");
		testPicker = opmodePicker("test");

		// telePicker.setDefaultOption("All Wheels", "AllWheels");
		// telePicker.addOption("Talon Testing", "TalonTest");
		// telePicker.addOption("Module Testing", "ModuleTest");
		
		// File ftele = new File("opmodes/teleop");
		// String[] teles = ftele.list();
		// for(String t : teles) t = t.substring(0, t.length()-5);
		// telePicker.setDefaultOption(teles[0], teles[0]);
		// for(int i = 1; i < teles.length; ++i) telePicker.addOption(teles[i], teles[i]);


		SmartDashboard.putData("Teleop", telePicker);
	}

	

	@Override
	public void robotPeriodic() {}

	@Override
	public void autonomousInit() {
		// m_autoSelected = m_chooser.getSelected();
		// m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
		// System.out.println("Auto selected: " + m_autoSelected);
	}

	@Override
	public void autonomousPeriodic() {
		// switch (m_autoSelected) {
		// 	case kCustomAuto:
		// 		// Put custom auto code here
		// 		break;
		// 	case kDefaultAuto:
		// 	default:
		// 		// Put default auto code here
		// 		break;
		// }
	}

	Opmode teleop;

	@Override
	public void teleopInit() {
		String tele = telePicker.getSelected();
		try{teleop = (Opmode)Class.forName(tele).getDeclaredConstructor().newInstance();}
		catch(Exception e){}
		teleop.init();
	}

	@Override
	public void teleopPeriodic() {
        teleop.periodic();
    }

	// TalonTest talon = new TalonTest();

	@Override
	public void testInit() {
		SmartDashboard.putString("status", "running");
		// swerve = new TestSwerve();
		// SmartDashboard.putString("status", "ready");
		// talon.init();
	}

	// TestSwerve swerve;
	@Override
	public void testPeriodic() {
		// swerve.printEverything();
		// SmartDashboard.putString("status", "running");
		// if(controller.getAButton()) talon.set(0.5);
		// if(controller.getBButton()) talon.set(-0.5);
		// if(controller.getXButton()) talon.set(0);
	}
}
