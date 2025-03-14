package frc.robot.opmodes.test;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.opmodes.teleop.CWController;
import frc.robot.Utility;
import frc.robot.hardware.*;

public class Targeting implements Opmode {
    Grinder bot;
    CWController con;

    public void init(Grinder bot) {
        this.bot = bot;
        con = new CWController(0);
        bot.arm.elevator.reset();
    }

    Arm.Position target;

    public void periodic() {
        double
            f = -Utility.sgnsqr(con.getLeftY())*.4,
            s = Utility.sgnsqr(con.getLeftX())*.4,
            r = Utility.sgnsqr(con.getRightX())*.4;
        bot.base.drive(-s, -f, -r, false);
        bot.base.periodic();


        if(con.getAButton()) {
            target = Arm.CORAL_PREP;
        }
        else if(con.getBButton()) {
            target = Arm.STARTING;
        }
        else if(con.getXButton()) {
            target = Arm.CORAL_INTAKE;
        }
        else if(con.getYButton()) {
            target = Arm.READY;
        }
        else if(con.getUpButton()) {
            target = Arm.HELD_READY;
        }
        else if(con.getDownButton()) {
            target = Arm.CORAL_L2;
        }

        // if(con.getAButton()) {
        //     target = Arm.ALGAE_GROUND;
        // }
        // if(con.getXButton()) {
        //     target = Arm.READY_OUT;
        // }
        // if(con.getBButton()) {
        //     target = Arm.ALGAE_PROC;
        // }
        // if(con.getYButton()) {
        //     target = Arm.READY;
        // }

        if(target != null) {
            bot.arm.goTo(target);
        }
        else {
            bot.arm.stop();
        }

        if(con.getRightBumperButton()) {
            bot.arm.claw.set(Claw.INTAKE_CORAL);
        }
        else if(con.getLeftBumperButton()) {
            bot.arm.claw.set(Claw.OUTTAKE_CORAL);
        }
        else {
            bot.arm.claw.stop();
        }

        // if(con.getAButton()) {
        //     bot.arm.elbow.goTo(.52, 1);
        // }
        // else if(con.getBButton()) {
        //     bot.arm.elbow.goTo(.7, 1);
        // }
        // else {
        //     bot.arm.elbow.stop();
        // }

        // if(con.getDownButton()) {
        //     bot.arm.elevator.goTo(1);
        // }
        // else if(con.getUpButton()) {
        //     bot.arm.elevator.goTo(1.8);
        // }
        // else if(con.getLeftButton()) {
        //     bot.arm.elevator.goTo(.2);
        // }
        // else if(con.getRightButton()) {
        //     bot.arm.elevator.goTo(3);
        // }
        // else {
        //     bot.arm.elevator.set(0);
        // }

        SmartDashboard.putNumber("elevator pow", bot.arm.elbow.get());
        SmartDashboard.putNumber("wrist pow", bot.arm.wrist.get());
        SmartDashboard.putNumber("elbow pow", bot.arm.elbow.get());

        // double pow = 0.5*Utility.sgnsqr(con.getRightTriggerAxis());
        // if(con.getLeftBumperButton() && con.getRightBumperButton()) {
        //     bot.base.resetGyro();
        // }

        // if(con.getUpButton()) {
        //     bot.arm.wrist.set(-pow);
        // }
        // else if(con.getDownButton()) {
        //     bot.arm.wrist.set(pow);
        // }
        // else {
        //     bot.arm.wrist.set(0);
        // }

        // if(con.getAButton()) {
        //     bot.arm.elbow.set(pow);
        // }
        // else if(con.getYButton()) {
        //     bot.arm.elbow.set(-pow);
        // }
        // else {
        //     bot.arm.elbow.set(0);
        // }

        // if(con.getXButton()) {
        //     bot.arm.elevator.set(pow);
        // }
        // else if(con.getBButton()) {
        //     bot.arm.elevator.set(-pow);
        // }
        // else {
        //     bot.arm.elevator.set(0);
        // }

        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", bot.base.heading());
        
        SmartDashboard.putNumber("wrist", bot.arm.wrist.getPos());
        SmartDashboard.putNumber("elbow", bot.arm.elbow.getPos());
        SmartDashboard.putNumber("elev", bot.arm.elevator.getPos());
        SmartDashboard.putNumber("elev raw", bot.arm.elevator.wrapEnc.getRaw());
    }
}
