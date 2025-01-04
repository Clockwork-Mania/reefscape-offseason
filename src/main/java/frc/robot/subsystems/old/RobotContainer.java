// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems.old;

// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.Robot;
// import frc.robot.Constants.OIConstants;
// import frc.robot.subsystems.DriveSubsystem;
// import frc.robot.subsystems.IntakeSubsystem;
// import frc.robot.subsystems.PivotSubsystem;
// import frc.robot.subsystems.ShooterSubsystem;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.RunCommand;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;

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
//   private final ShooterSubsystem shooter = new ShooterSubsystem();
//   private final PivotSubsystem pivot = new PivotSubsystem();

//   // The driver's controller
//   XboxController driverController = new XboxController(OIConstants.kDriverControllerPort);
//   XboxController mechController = new XboxController(1);
//   double angle = 0;
//   boolean fr = true;
//   boolean pidPivot = true;
//   boolean shooting = false;
//   Timer shootingTimer;
  
//   /** The container for the robot. Contains subsystems, OI devices, and commands. */
//   public RobotContainer() {

// 	pivot.enc.setPositionConversionFactor(180);
// 	pivot.setTarget(pivot.getHeight());
// 	// Configure the button bindings
// 	configureButtonBindings();

// 	// Configure default commands
// 	driveBase.setDefaultCommand(
// 			new RunCommand(
// 				() -> {
// 					double x = driverController.getLeftY();
// 					double y = -driverController.getLeftX();
// 					double r = driverController.getRightX();
// 					r = (Math.abs(r) > 0.1 ? r : 0);
					
// 					double speed = 0.4;
// 					speed += driverController.getRightTriggerAxis() * 0.6; // max .4 + .6 = 1
// 					speed -= driverController.getLeftTriggerAxis() * 0.3; // max .4 - .3 = .1

// 					if(driverController.getRightBumper() && driverController.getLeftBumper()) {
// 						driveBase.zeroHeading();
// 					}
					
// 					driveBase.drive(x*speed, y*speed, r*speed, true);

// 					// ---------------------------------------------------------------- //
// 					// ---------------------------- INTAKE ---------------------------- //
// 					// ---------------------------------------------------------------- //

// 					if(mechController.getLeftBumper()) intake.intake();
// 					else if(mechController.getRightBumper()) intake.output();
// 					else {intake.stop();}


// 					// ---------------------------------------------------------------- //
// 					// ---------------------------- SHOOTER --------------------------- //
// 					// ---------------------------------------------------------------- //

// 					// shooter.shoot();
// 					if(mechController.getXButton()) {shooter.shoot();}
// 					else {shooter.stop();}

// 					// if(mechController.getXButtonReleased()) {
// 					// 	shooting = true;
// 					// 	shootingTimer.reset();
// 					// }

// 					// if(shooting && shootingTimer.get() < 1) {
// 					// 	shooter.channel();
// 					// 	shooter.shoot();
// 					// }
// 					// else {
// 					// 	shooter.stop();
// 					// 	shooter.stopChannel();
// 					// }


// 					// ---------------------------------------------------------------- //
// 					// ---------------------------- HANGER ---------------------------- //
// 					// ---------------------------------------------------------------- //
// 					if(mechController.getRightTriggerAxis() > .5) {
// 						shooter.channel();
// 					}
// 					else if(mechController.getLeftTriggerAxis() > .5) {
// 						shooter.unchannel();
// 					}
// 					else {
// 						shooter.stopChannel();
// 					}


// 					// dpad up
// 					double pivotRate = 0.5;

// 					if(mechController.getPOV() == 0) {
// 						pidPivot = true;
// 						pivot.setTarget(pivot.target + pivotRate);
// 					}
// 					// dpad down
// 					else if(mechController.getPOV() == 180) {
// 						pidPivot = true;
// 						pivot.setTarget(pivot.target - pivotRate);
// 					}

// 					if(mechController.getYButton()) {
// 						// pidPivot = false;
// 						// pivot.lift();
// 						pivot.setTarget(180);
// 					}
// 					else if(mechController.getAButton()) {
// 						// pidPivot = false;
// 						// pivot.lower();
// 					}
// 					else {
// 						// if(!pidPivot) pivot.stop();
// 					}

// 					if(pidPivot) pivot.pidLift(.02);
// 					// pivot.pidLift(.02);


// 					SmartDashboard.putNumber("Pivot height", pivot.getHeight());
// 					SmartDashboard.putNumber("POV", mechController.getPOV());
// 				},
// 				driveBase, intake, shooter, pivot
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
// 	return null;
//   }
// }
