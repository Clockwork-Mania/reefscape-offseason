package frc.robot.opmodes.auto;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryParameterizer.TrajectoryGenerationException;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.*;
import frc.robot.hardware.Arm.Position;
import frc.robot.opmodes.Opmode;
import frc.robot.opmodes.teleop.CWController;

public class NewAuto implements Opmode {
    Grinder bot;
    Trajectory currentTrajectory = null;
    double startTime;
    XboxController con;

    HolonomicDriveController controller;

    Timer stateTimer;
    Trajectory line;
    int autoState = 0;
    boolean onEnter = true;

    public void init(Grinder bot) {
        this.bot = bot;
        bot.base.setTarget(0, 0, 0);
        bot.base.resetGyro();
        bot.base.resetOdo();
        bot.arm.setTarget(new Position(
            bot.arm.elevator.getPos(),
            bot.arm.wrist.getPos(), 
            bot.arm.elbow.getPos()
        ));
        con = new XboxController(0);
        stateTimer = new Timer();
        stateTimer.start();
        bot.base.addVision(bot.vision);
        bot.base.setVisionTarget(0);
    }

    public void periodic() {
        bot.base.periodic();
        bot.base.driveto();
        bot.arm.goToTarget();
        update();
        
        SmartDashboard.putNumber("X", bot.base.odo.getPoseMeters().getX());
        SmartDashboard.putNumber("Y", bot.base.odo.getPoseMeters().getY());
        SmartDashboard.putBoolean("ready", bot.base.ready());
        SmartDashboard.putNumber("state", autoState);
        SmartDashboard.putNumber("state timer", stateTimer.get());
        SmartDashboard.putNumber("elevator yay!", bot.arm.elevator.getPos());

    }

        //main autonomous code (big ass switch)
        public void update() {
            switch (autoState) {
                case 0:
                    if (onEnter) {
                        stateTimer.reset();
                        // on enter code
                        bot.arm.setTarget(Arm.READY);
                        bot.arm.claw.set(Claw.HOLD_CORAL);
                        onEnter = false;
                    }
    
                    //loop code
                    
                    //transition to next state
                    if (stateTimer.hasElapsed(1.2)) {
                        onEnter = true;
                        autoState = 1;
                    }

                    break;
                case 1:
                    if (onEnter) {
                        stateTimer.reset();

                        bot.base.setTarget(0, -1.3, 0);
                        bot.arm.setTarget(Arm.CORAL_L3);

                        onEnter = false;
                    }

                    if (bot.base.ready()) {
                        onEnter = true;
                        autoState++;
                    }
                    break;
                case 2:
                    if (onEnter) {
                        stateTimer.reset();

                        bot.arm.claw.set(Claw.OUTTAKE_CORAL);

                        onEnter = false;
                    }

                    if (stateTimer.hasElapsed(1)) {
                        onEnter = true;
                        autoState++;
                    }
                    break;
                default:
                    break;
            }
        }
}
