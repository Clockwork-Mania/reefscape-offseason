package frc.team4013.frc2025.opmodes.test;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4013.frc2025.opmodes.Opmode;
import frc.team4013.frc2025.opmodes.teleop.CWController;
import frc.team4013.frc2025.Utility;
import frc.team4013.frc2025.hardware.*;

public class NewTargeting implements Opmode {
    Grinder bot;
    CWController con;

    public boolean holding = false;

    public void init(Grinder bot) {
        this.bot = bot;
        con = new CWController(1);
        // bot.arm.elevator.setTarget();
        // bot.arm.elbow.setTarget(.1);
        // bot.arm.wrist.setTarget(.35);
        bot.arm.hold();
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

        if(con.getYButton()) {
            bot.arm.elevator.setTarget(0.7);
        }
        if(con.getAButton()) {
            bot.arm.elevator.setTarget(0.6);
        }
        if(con.getXButton()) {
            bot.arm.elevator.setTarget(0.5);
        }
        if(con.getBButton()) {
            bot.arm.elevator.setTarget(0.4);
        }
        bot.arm.elevator.goToTarget();

        if(con.getRightButton()) {
            bot.arm.elbow.setTarget(.3);
        }
        if(con.getUpButton()) {
            bot.arm.elbow.setTarget(.2);
        }
        if(con.getLeftButton()) {
            bot.arm.elbow.setTarget(.15);
        }
        if(con.getDownButton()) {
            bot.arm.elbow.setTarget(.1);
        }
        bot.arm.elbow.goToTarget();

        if(con.getLeftBumperButton()) {
            bot.arm.wrist.setTarget(.2);
        }
        if(con.getM1Button()) {
            bot.arm.wrist.setTarget(.35);
        }
        if(con.getM2Button()) {
            bot.arm.wrist.setTarget(.55);
        }
        if(con.getRightBumperButton()) {
            bot.arm.wrist.setTarget(.7);
        }
        bot.arm.wrist.goToTarget((bot.arm.elbow.getPos()-Elbow.HORIZ)*2*Math.PI);

        bot.arm.claw.set(
            .2*con.getLeftTriggerAxis()
            -.2*con.getRightTriggerAxis()
        );

        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", bot.base.heading());
        
        SmartDashboard.putNumber("wrist", bot.arm.wrist.getPos());
        SmartDashboard.putNumber("elbow", bot.arm.elbow.getPos());
        SmartDashboard.putNumber("elev", bot.arm.elevator.getPos());
        
        SmartDashboard.putNumber("wristpow", bot.arm.wrist.get());
        SmartDashboard.putNumber("elbowpow", bot.arm.elbow.get());
        SmartDashboard.putNumber("elevpow", bot.arm.elevator.get());

        SmartDashboard.putNumber("fl", bot.base.frontLeft.enc.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("fr", bot.base.frontRight.enc.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("br", bot.base.backRight.enc.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("bl", bot.base.backLeft.enc.getPosition().getValueAsDouble());
    }
}
