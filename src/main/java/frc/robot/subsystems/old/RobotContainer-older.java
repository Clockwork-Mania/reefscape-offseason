// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot;

// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.controller.ProfiledPIDController;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.trajectory.Trajectory;
// import edu.wpi.first.math.trajectory.TrajectoryConfig;
// import edu.wpi.first.math.trajectory.TrajectoryGenerator;
// import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.Constants.AutoConstants;
// import frc.robot.Constants.DriveConstants;
// import frc.robot.Constants.OIConstants;
// import frc.robot.subsystems.DriveSubsystem;
// import frc.robot.subsystems.IntakeSubsystem;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.RunCommand;
// import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import java.util.List;

// /*
//  * This class is where the bulk of the robot should be declared.  Since Command-based is a
//  * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
//  * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
//  * (including subsystems, commands, and button mappings) should be declared here.
//  */
// public class RobotContainer {
//   // The robot's subsystems
//   private final DriveSubsystem driveBase = new DriveSubsystem();
//   private final IntakeSubsystem intake = new IntakeSubsystem();

//   // The driver's controller
//   XboxController driverController = new XboxController(OIConstants.kDriverControllerPort);
//   XboxController mechController = new XboxController(1);
//   double angle = 0;
//   boolean fr = true;
  
//   /** The container for the robot. Contains subsystems, OI devices, and commands. */
//   public RobotContainer() {
// 	// Configure the button bindings
// 	configureButtonBindings();

// 	// Configure default commands
// 	driveBase.setDefaultCommand(
// 		// The left stick controls translation of the robot.
// 		// Turning is controlled by the X axis of the right stick.
// 		// new RunCommand(
// 		//     () -> {
// 		//         m_robotDrive.drive(
// 		//             // Multiply by max speed to map the joystick unitless inputs to actual units.
// 		//             // This will map the [-1, 1] to [max speed backwards, max speed forwards],
// 		//             // converting them to actual units.
// 		//             m_driverController.getLeftY() * DriveConstants.kMaxSpeedMetersPerSecond,
// 		//             m_driverController.getLeftX() * DriveConstants.kMaxSpeedMetersPerSecond,
// 		//             m_driverController.getRightX()
// 		//                 * ModuleConstants.kMaxModuleAngularSpeedRadiansPerSecond,
// 		//             false);
// 		//         SmartDashboard.putNumber("Left Y", m_driverController.getLeftY());
// 		//         SmartDashboard.putNumber("Left X", m_driverController.getLeftX());
// 		//         SmartDashboard.putNumber("Right X", m_driverController.getRightX());
// 		//     },
// 		//     m_robotDrive)

// 			new RunCommand(
// 				() -> {
// 					double x = driverController.getLeftY();
// 					double y = -driverController.getLeftX();
// 					double r = driverController.getRightX();
// 					r = (Math.abs(r) > 0.1 ? r : 0);

// 					double speed = (driverController.getLeftTriggerAxis() > 0.5 ? 0.1 : (driverController.getRightTriggerAxis() > 0.5 ? 1
// 					: 0.4));
					
// 					if(driverController.getAButtonPressed()) fr = !fr;
					
// 					if(driverController.getRightBumper() && driverController.getLeftBumper()) {
// 						driveBase.zeroHeading();
// 					}
					
// 					driveBase.drive(
// 						x*speed, y*speed, r*speed,
// 						fr
// 					);
// 					SmartDashboard.putNumber("angle", angle);
// 					SmartDashboard.putNumber("speed", speed);
// 					SmartDashboard.putNumber("rot", r);

// 					if(mechController.getRightBumper()) {
// 						intake.output();
// 					}
// 					else if(mechController.getLeftBumper()) {
// 						intake.intake();
// 					}
// 					else {
// 						intake.stop();
// 					}
// 				},
// 				driveBase
// 			)
// 		);

//   }

//   /**
//    * Use this method to define your button->command mappings. Buttons can be created by
//    * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
//    * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling passing it to a
//    * {@link JoystickButton}.
//    */
//   private void configureButtonBindings() {}

//   /**
//    * Use this to pass the autonomous command to the main {@link Robot} class.
//    *
//    * @return the command to run in autonomous
//    */
//   public Command getAutonomousCommand() {
// 	// Create config for trajectory
// 	TrajectoryConfig config =
// 		new TrajectoryConfig(
// 				AutoConstants.kMaxSpeedMetersPerSecond,
// 				AutoConstants.kMaxAccelerationMetersPerSecondSquared)
// 			// Add kinematics to ensure max speed is actually obeyed
// 			.setKinematics(DriveConstants.kDriveKinematics);

// 	// An example trajectory to follow. All units in meters.
// 	Trajectory exampleTrajectory =
// 		TrajectoryGenerator.generateTrajectory(
// 			// Start at the origin facing the +X direction
// 			new Pose2d(0, 0, new Rotation2d(0)),
// 			// Pass through these two interior waypoints, making an 's' curve path
// 			List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
// 			// End 3 meters straight ahead of where we started, facing forward
// 			new Pose2d(3, 0, new Rotation2d(0)),
// 			config);

// 	var thetaController =
// 		new ProfiledPIDController(
// 			AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
// 	thetaController.enableContinuousInput(-Math.PI, Math.PI);

// 	// SwerveControllerCommand swerveControllerCommand =
// 	// 	new SwerveControllerCommand(
// 	// 		exampleTrajectory,
// 	// 		driveBase::getPose, // Functional interface to feed supplier
// 	// 		DriveConstants.kDriveKinematics,

// 	// 		// Position controllers
// 	// 		new PIDController(AutoConstants.kPXController, 0, 0),
// 	// 		new PIDController(AutoConstants.kPYController, 0, 0),
// 	// 		thetaController,
// 	// 		driveBase::setModuleStates,
// 	// 		driveBase);

// 	// Reset odometry to the initial pose of the trajectory, run path following
// 	// command, then stop at the end.
// 	// return Commands.sequence(
// 	// 	new InstantCommand(() -> driveBase.resetOdometry(exampleTrajectory.getInitialPose())),
// 	// 	swerveControllerCommand,
// 	// 	new InstantCommand(() -> driveBase.drive(0, 0, 0, false)));
// 	return null;
//   }
// }
