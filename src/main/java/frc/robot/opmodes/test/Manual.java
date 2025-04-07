package frc.robot.opmodes.test;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.opmodes.teleop.CWController;
import frc.robot.Utility;
import frc.robot.hardware.*;

public class Manual implements Opmode {
    Grinder bot;
    CWController con;

    public boolean holding = false;

    public void init(Grinder bot) {
        this.bot = bot;
        con = new CWController(1);
    }

    public void periodic() {
        double
            f = -Utility.sgnsqr(con.getLeftY())*.4,
            s = Utility.sgnsqr(con.getLeftX())*.4,
            r = Utility.sgnsqr(con.getRightX())*.4;
        bot.base.drive(s, f, r, false);
        bot.base.periodic();


        double pow = 0.5*Utility.sgnsqr(con.getRightTriggerAxis());
        SmartDashboard.putNumber("pow", pow);
        if(con.getLeftBumperButton() && con.getRightBumperButton()) {
            bot.base.resetGyro();
        }

        if(con.getUpButton()) {
            bot.arm.wrist.set(pow);
        }
        else if(con.getDownButton()) {
            bot.arm.wrist.set(-pow);
        }
        else {
            bot.arm.wrist.set(0);
        }

        if(con.getAButton()) {
            bot.arm.elbow.set(-pow);
        }
        else if(con.getYButton()) {
            bot.arm.elbow.set(pow);
        }
        else {
            bot.arm.elbow.set(0);
        }

        if(con.getXButton()) {
            bot.arm.elevator.set(pow);
        }
        else if(con.getBButton()) {
            bot.arm.elevator.set(-pow);
        }
        else {
            bot.arm.elevator.set(0);
        }


        if(con.getRightBumperButton()) {
            holding = true;
            bot.arm.claw.set(0.2);
        }
        else if(con.getLeftBumperButton()) {
            bot.arm.claw.set(-0.2);
            holding = false;
        }
        else {
            if(holding) {
                bot.arm.claw.set(0.1);
            }
            else {
                bot.arm.claw.stop();
            }
        }

        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", bot.base.heading());
        
        SmartDashboard.putNumber("wrist", bot.arm.wrist.getPos());
        SmartDashboard.putNumber("elbow", bot.arm.elbow.getPos());
        SmartDashboard.putNumber("elev", bot.arm.elevator.getPos());
        
        // SmartDashboard.putNumber("wristpow", bot.arm.wrist.get());
        // SmartDashboard.putNumber("elbowpow", bot.arm.elbow.get());
        // SmartDashboard.putNumber("elevpow", bot.arm.elevator.get());

        // SmartDashboard.putNumber("fl", bot.base.frontLeft.enc.getPosition().getValueAsDouble());
        // SmartDashboard.putNumber("fr", bot.base.frontRight.enc.getPosition().getValueAsDouble());
        // SmartDashboard.putNumber("br", bot.base.backRight.enc.getPosition().getValueAsDouble());
        // SmartDashboard.putNumber("bl", bot.base.backLeft.enc.getPosition().getValueAsDouble());
    }
}
