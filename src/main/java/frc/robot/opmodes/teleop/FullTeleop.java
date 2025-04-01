package frc.robot.opmodes.teleop;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.Utility;
import frc.robot.hardware.*;

public class FullTeleop implements Opmode {
    Grinder bot;
    CWController con0, con1;
    Field2d field;
    Timer prepTimer;

    public void init(Grinder bot) {
        this.bot = bot;
        con0 = new CWController(0);
        con1 = new CWController(1);
        prepTimer = new Timer();
        bot.arm.setTarget(Arm.STARTING);
        // bot.arm.elevator.reset();
    }

    boolean coral = true, coralPrep = false;
    double holdSpeed = 0;

    public void periodic() {

        // ------------------------------------- //
        // --------------- BASE ---------------- //
        // ------------------------------------- //

        double
            f = -con0.getLeftY()*.8,
            s = con0.getLeftX()*.8,
            r = con0.getRightX()*.8;
        bot.base.drive(s, f, r, true);
        bot.base.periodic();
        SmartDashboard.putNumber("odo X", bot.base.pose().getX());
        SmartDashboard.putNumber("odo Y", bot.base.pose().getY());
        SmartDashboard.putNumber("odo H", bot.base.pose().getRotation().getRadians());

        if(con0.getLeftBumperButton() && con0.getRightBumperButton()) {
            bot.base.resetGyro();
        }



        if(con1.getM1Button()) {
            // ------------------------------------- //
            // -------------- MANUAL --------------- //
            // ------------------------------------- //

            if(con1.getUpButton()) {
                bot.arm.elevator.adjust(.01);
            }
            if(con1.getDownButton()) {
                bot.arm.elevator.adjust(-.01);
            }
            if(con1.getYButton()) {
                bot.arm.elbow.adjust(.0005);
            }
            if(con1.getAButton()) {
                bot.arm.elbow.adjust(-.0005);
            }
            if(con1.getXButton()) {
                bot.arm.wrist.adjust(-.001);
            }
            if(con1.getBButton()) {
                bot.arm.wrist.adjust(.001);
            }
        }
        else {
            // ------------------------------------- //
            // ---------------- ARM ---------------- //
            // ------------------------------------- //

            if(con1.getUpButton()) {
                bot.arm.setTarget(Arm.ALGAE_L3);
                coral = false;
            }
            if(con1.getRightButton()) {
                bot.arm.setTarget(Arm.ALGAE_L2);
                coral = false;
            }
            if(con1.getLeftButton()) {
                bot.arm.setTarget(Arm.CORAL_PREP);
                coral = true;
            }
            if(con1.getDownButton()) {
                bot.arm.setTarget(Arm.CORAL_INTAKE);
                coral = true;
            }

            if(con1.getXButton()) {
                bot.arm.setTarget(Arm.READY);
            }
            if(con1.getYButton()) {
                bot.arm.setTarget(Arm.CORAL_L4);
            }
            if(con1.getBButton()) {
                bot.arm.setTarget(Arm.CORAL_L3);
                coral = true;
            }
            if(con1.getAButton()) {
                if(coral) bot.arm.setTarget(Arm.CORAL_L2);
                else bot.arm.setTarget(Arm.ALGAE_PROC);
            }
        }

        if(con1.getM1Button() && con1.getM2Button()) {
            bot.arm.elevator.reset();
        }

        bot.arm.goToTarget(coral);
        SmartDashboard.putBoolean("Coral?", coral);
        SmartDashboard.putNumber("Wrist", bot.arm.wrist.getPos());
        SmartDashboard.putNumber("Elbow", bot.arm.elbow.getPos());
        SmartDashboard.putNumber("Elevator", bot.arm.elevator.getPos());
        SmartDashboard.putNumber("Wrist Target", bot.arm.wrist.target);
        SmartDashboard.putNumber("Elbow Target", bot.arm.elbow.target);
        SmartDashboard.putNumber("Elevator Target", bot.arm.elevator.target);

        

        // ------------------------------------- //
        // --------------- CLAW ---------------- //
        // ------------------------------------- //

        if(con0.getRightTriggerButtonReleased()||con1.getRightTriggerButtonReleased()) {
            holdSpeed = coral ? Claw.HOLD_CORAL : Claw.HOLD_ALGAE;
        }
        if(con0.getLeftTriggerButtonReleased()||con1.getLeftTriggerButtonReleased()) {
            holdSpeed = 0;
        }
        if(con0.getRightTriggerButton() || con1.getRightTriggerButton()) {
            bot.arm.claw.set(
                Math.max(
                    con0.getRightTriggerAxis(),
                    con1.getRightTriggerAxis()
                ) * 
                (coral ? Claw.INTAKE_CORAL : Claw.INTAKE_ALGAE)
            );
        }
        else if(con0.getLeftTriggerButton()||con1.getLeftTriggerButton()) {
            bot.arm.claw.set(Math.max(
                    con0.getLeftTriggerAxis(),
                    con1.getLeftTriggerAxis()
                ) * 
                (coral ? Claw.OUTTAKE_CORAL : Claw.OUTTAKE_ALGAE)
            );
        }
        else bot.arm.claw.set(holdSpeed);

        SmartDashboard.putNumber("Claw", bot.arm.claw.get());

        con0.update();
        con1.update();
    }
}