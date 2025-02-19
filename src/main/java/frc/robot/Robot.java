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
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.*;
import frc.robot.opmodes.teleop.AllWheels;
import frc.robot.opmodes.teleop.ModuleTest;
import frc.robot.opmodes.teleop.TalonTest;
import frc.robot.subsystems.Grinder;
import frc.robot.opmodes.teleop.SwerveTeleop;

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

	Grinder bot;
    XboxController con;
    Field2d field;

	// String teles[] = {"AllWheels", "TalonTest", "ModuleTest"};
	SendableChooser<Class<?>> autoPicker, telePicker, testPicker;

	XboxController controller = new XboxController(0);

	// SendableChooser<Class> opmodePicker(String type) {
	// 	SendableChooser<Class> picker = new SendableChooser<>();
	// 	File f = new File("opmodes/"+type);
	// 	String[] ss = f.list();
	// 	for(String s : ss) s = s.substring(0, s.length()-5);
	// 	picker.setDefaultOption(ss[0], ss[0]);
	// 	for(int i = 1; i < ss.length; ++i) picker.addOption(ss[i], ss[i]);
	// 	return picker;
	// }

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
		// m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
		// m_chooser.addOption("My Auto", kCustomAuto);
		// SmartDashboard.putData("Auto choices", m_chooser);

		autoPicker = opmodePicker(OpmodeList.auto);
		telePicker = opmodePicker(OpmodeList.teleop);
		testPicker = opmodePicker(OpmodeList.test);
		// telePicker = new SendableChooser<>();
		// telePicker.setDefaultOption("All Wheels", AllWheels.class);
		// telePicker.addOption("Talon Testing", TalonTest.class);
		// telePicker.addOption("Module Testing", ModuleTest.class);
		// telePicker.addOption("Swerve Testing", TestSwerveTeleop.class);
		
		// File ftele = new File("opmodes/teleop");
		// String[] teles = ftele.list();
		// for(String t : teles) t = t.substring(0, t.length()-5);
		// telePicker.setDefaultOption(OpmodeList.teleops[0].name, OpmodeList.teleops[0].mode);
		// telePicker.setDefaultOption(OpmodeList.teleops[0].name, OpmodeList.teleops[0].mode);
		// for(int i = 1; i < teles.length; ++i) telePicker.addOption(teles[i], teles[i]);

		SmartDashboard.putData("Auto", autoPicker);
		SmartDashboard.putData("Teleop", telePicker);
		SmartDashboard.putData("Test", testPicker);

		bot = new Grinder();
        con = new XboxController(0);
        field = new Field2d();
        SmartDashboard.putData("Field", field);
	}

	

	@Override
	public void robotPeriodic() {
		double
            f = -con.getLeftY()*.4,
            s = con.getLeftX()*.4,
            r = con.getRightX()*.4;
        bot.base.drive(-s, -f, -r, true);
        bot.base.periodic();

		System.out.println(bot.base.positions()[0]);
        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", bot.base.heading()*180/Math.PI);
        Pose2d pose = bot.base.pose();
        SmartDashboard.putNumber("odo.x", pose.getX());
        SmartDashboard.putNumber("odo.y", pose.getY());

        if(con.getLeftBumperButton() && con.getRightBumperButton()) {
            bot.base.resetGyro();
        }

        field.setRobotPose(bot.base.odo.getPoseMeters());
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
		// System.out.println(tele);
		// switch(tele) {
		// 	case "AllWheels": teleop = new AllWheels(); break;
		// 	case "TalonTest": teleop = new TalonTest(); break;
		// 	case "ModuleTest": teleop = new ModuleTest(); break;
		// 	case "TestSwerveTeleop": teleop = new TestSwerveTeleop(); break;
		// }

		// bot = new Grinder();
        // con = new XboxController(0);
        // field = new Field2d();
        // SmartDashboard.putData("Field", field);
	}

	@Override
	public void teleopPeriodic() {
    }

	@Override
	public void testInit() {
		opInit(testPicker);
	}

	// TestSwerve swerve;
	@Override
	public void testPeriodic() {
		op.periodic();
	}
}
