package frc.robot.opmodes.auto;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.*;
import frc.robot.hardware.Arm.Position;
import frc.robot.opmodes.Opmode;

public class LauraAuto implements Opmode {
    Grinder bot;
    double startTime;

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
        stateTimer = new Timer();
        stateTimer.start();
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
        SmartDashboard.putBoolean("ready", bot.base.ready());
        SmartDashboard.putNumber("state", autoState);
        SmartDashboard.putNumber("state timer", stateTimer.get());
        SmartDashboard.putNumber("elevator yay!", bot.arm.elevator.getPos());
        // SmartDashboard.putBoolean("apriltag?", bot.vision.foundTag());
    }

    //main autonomous code (big ass switch)
    public void update() {
        switch (autoState) {
            case 0:
                enter(() -> {
                    bot.arm.setTarget(Arm.STARTING);
                });
                transition(stateTimer.hasElapsed(1));
                break;
            case 1:
                enter(() -> {
                    bot.base.setBaseVisionTarget(bot.vision.REEF_RIGHT, 0);
                    bot.arm.setTarget(Arm.READY);
                });
            default:
                break;
        }
    }

    public void enter(Runnable runnable) {
        if (onEnter) {
            stateTimer.reset();
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
