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
    }

    boolean coral = false, coralPrep = false;
    double holdSpeed = 0;

    public void periodic() {

        // ------------------------------------- //
        // --------------- BASE ---------------- //
        // ------------------------------------- //

        double
            f = -con0.getLeftY()*.8,
            s = con0.getLeftX()*.8,
            r = con0.getRightX()*.8;
        bot.base.drive(-s, -f, -r, true);
        bot.base.periodic();

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
                bot.arm.elevator.adjust(.01);
            }
            if(con1.getYButton()) {
                bot.arm.elbow.adjust(.0005);
            }
            if(con1.getAButton()) {
                bot.arm.elbow.adjust(.0005);
            }
            if(con1.getXButton()) {
                bot.arm.wrist.adjust(.001);
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
            }
            if(con1.getAButton()) {
                if(coral) bot.arm.setTarget(Arm.CORAL_L2);
                else bot.arm.setTarget(Arm.ALGAE_PROC);
            }
        }

        bot.arm.goToTarget();
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

        if(con1.getRightTriggerButtonReleased()) {
            holdSpeed = coral ? Claw.HOLD_CORAL : Claw.HOLD_ALGAE;
        }
        if(con1.getLeftTriggerButtonReleased()) {
            holdSpeed = 0;
        }
        if(con1.getRightTriggerButton()) {
            bot.arm.claw.set(
                con1.getRightTriggerAxis() * 
                (coral ? Claw.INTAKE_CORAL : Claw.INTAKE_ALGAE)
            );
        }
        else if(con1.getLeftTriggerButton()) {
            bot.arm.claw.set(
                con1.getLeftTriggerAxis() *
                (coral ? Claw.OUTTAKE_CORAL : Claw.OUTTAKE_ALGAE)
            );
        }
        else bot.arm.claw.set(holdSpeed);

        SmartDashboard.putNumber("Claw", bot.arm.claw.get());


        con0.update();
        con1.update();
    }
}

