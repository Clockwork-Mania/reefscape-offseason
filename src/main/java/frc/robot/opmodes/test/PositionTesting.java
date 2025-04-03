package frc.robot.opmodes.test;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.opmodes.teleop.CWController;
import frc.robot.Utility;
import frc.robot.hardware.*;
import frc.robot.hardware.Arm.Position;

public class PositionTesting implements Opmode {
    Grinder bot;
    CWController con;

    public void init(Grinder bot) {
        this.bot = bot;
        con = new CWController(1);
        bot.arm.setTarget(bot.arm.getPos());
    }

    public void periodic() {
        double
            f = -Utility.sgnsqr(con.getLeftY())*.4,
            s = Utility.sgnsqr(con.getLeftX())*.4,
            r = Utility.sgnsqr(con.getRightX())*.4;
        bot.base.drive(s, f, r, false);
        bot.base.periodic();


        if(con.getAButton()) {
            bot.arm.setTarget(Arm.STARTING);
        }
        if(con.getXButton()) {
            bot.arm.setTarget(Arm.READY);
        }
        bot.arm.goToTarget();

        bot.arm.claw.set(
            .2*con.getLeftTriggerAxis()
            -.2*con.getRightTriggerAxis()
        );

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

        SmartDashboard.putNumber("elevator pow", bot.arm.elevator.get());
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
    }
}
