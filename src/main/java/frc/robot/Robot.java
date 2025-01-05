// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.TalonTest;
import frc.robot.subsystems.TestSwerve;

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

	XboxController controller = new XboxController(0);

	public Robot() {
		m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
		m_chooser.addOption("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
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

	@Override
	public void teleopInit() {
		
	}

	@Override
	public void teleopPeriodic() {
        
    }

	TalonTest talon;

	@Override
	public void testInit() {
		// swerve = new TestSwerve();
		SmartDashboard.putString("status", "ready");
		talon.init();
	}

	TestSwerve swerve;
	@Override
	public void testPeriodic() {
		// swerve.printEverything();
		SmartDashboard.putString("status", "running");
		if(controller.getAButton()) talon.set(0.5);
		if(controller.getBButton()) talon.set(-0.5);
		if(controller.getXButton()) talon.set(0);
	}
}
