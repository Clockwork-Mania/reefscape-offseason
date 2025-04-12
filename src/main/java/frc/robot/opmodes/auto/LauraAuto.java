package frc.robot.opmodes.auto;

import java.time.format.TextStyle;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.*;
import frc.robot.hardware.Arm.Position;
import frc.robot.hardware.Swerve.TargetMode;
import frc.robot.opmodes.Opmode;
import org.photonvision.PhotonUtils;

public class LauraAuto implements Opmode {
    Grinder bot;
    double startTime;

    Timer timer;
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
        timer = new Timer();
        timer.start();
        bot.base.addVision(bot.vision);
    }

    public void periodic() {
        bot.base.periodic();
        bot.base.driveto();
        bot.arm.goToTarget(true);
        bot.vision.update();
        update();

        SmartDashboard.putNumber("X", bot.base.odo.getPoseMeters().getX());
        SmartDashboard.putNumber("Y", bot.base.odo.getPoseMeters().getY());
        SmartDashboard.putBoolean("base ready", bot.base.ready());
        SmartDashboard.putNumber("state", autoState);
        SmartDashboard.putNumber("timer", timer.get());

        if(bot.vision.rightView.getBestTarget() != null) {
            SmartDashboard.putBoolean("right target", true);
            SmartDashboard.putNumber("yaw right", bot.vision.rightView.getBestTarget().getYaw());
            SmartDashboard.putNumber("area right", bot.vision.rightView.getBestTarget().getArea());
            SmartDashboard.putNumber("id right", bot.vision.rightView.getBestTarget().getFiducialId());
        }
        else {
            SmartDashboard.putBoolean("right target", false);
        }
        if(bot.vision.leftView.getBestTarget() != null) {
            SmartDashboard.putBoolean("left target", true);
            SmartDashboard.putNumber("yaw left", bot.vision.leftView.getBestTarget().getYaw());
            SmartDashboard.putNumber("area left", bot.vision.leftView.getBestTarget().getArea());
            SmartDashboard.putNumber("id left", bot.vision.leftView.getBestTarget().getFiducialId());
            SmartDashboard.putNumber("photondist",
                PhotonUtils.calculateDistanceToTargetMeters(.22, .39, Math.PI/18,
                    Math.toRadians(bot.vision.leftView.getBestTarget().getPitch())
                )
            );
        }
        else {
            SmartDashboard.putBoolean("left target", false);
        }

        if(bot.base.targetMode == TargetMode.BaseVision) {
            SmartDashboard.putNumber("left yaw target", bot.base.baseVisionTarget.left.yaw);
            SmartDashboard.putNumber("left area target", bot.base.baseVisionTarget.left.area);
            SmartDashboard.putNumber("right yaw target", bot.base.baseVisionTarget.right.yaw);
            SmartDashboard.putNumber("right area target", bot.base.baseVisionTarget.right.area);
        }
        // SmartDashboard.putBoolean("apriltag?", bot.vision.foundTag());
    }

    //main autonomous code (big ass switch) (FSM)
    public void update() {
        switch (autoState) {
            case 0:
                enter(() -> {
                    bot.arm.claw.set(Claw.HOLD_CORAL);
                    bot.arm.setTarget(Arm.STARTING);
                });

                transition(timer.hasElapsed(1));
                break;
            case 1:
                enter(() -> {
                    bot.base.setBaseVisionTarget(bot.vision.REEF_RIGHT, 0);
                    // bot.base.setBasePitchVisionTarget(bot.vision.P_REEF_RIGHT, 0);
                    bot.arm.setTarget(Arm.CORAL_L4);
                });
                transition(bot.base.ready());
                break;
            case 2:
                enter(() -> {
                    bot.base.resetOdo();
                    bot.base.setTarget(0.02, 0.5, 0);
                    bot.arm.claw.set(Claw.INTAKE_CORAL);
                });
                transition(timer.hasElapsed(.1));
                break;
            case 3:
                enter(() -> {
                    bot.arm.claw.set(0);
                });
                transition(bot.base.ready()||timer.hasElapsed(.5));
                break;
            case 4:
                enter(() -> {
                    bot.arm.claw.set(Claw.OUTTAKE_CORAL);
                });
                transition(timer.hasElapsed(1));
                break;
            case 5:
                enter(() -> {
                    bot.base.setTarget(0, 0, 0);
                });
                transition(bot.base.ready());
            case 6:
                enter(()->{
                    bot.arm.claw.set(0);
                    bot.arm.setTarget(Arm.READY);
                });
                break;
            default:
                break;
        }
    }

    public void enter(Runnable runnable) {
        if (onEnter) {
            timer.reset();
            runnable.run();
            onEnter = false;
        }
    }

    public void transition(boolean transitionCondition, Runnable exitRunnable) {
        if (transitionCondition) {
            exitRunnable.run();
            onEnter = true;
            autoState++;
        }
    }

    public void transition(boolean transitionCondition) {
        if (transitionCondition) {
            onEnter = true;
            autoState++;
        }
    }
}
