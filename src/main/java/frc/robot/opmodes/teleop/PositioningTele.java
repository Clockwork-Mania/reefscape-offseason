package frc.robot.opmodes.teleop;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.Utility;
import frc.robot.hardware.*;
import frc.robot.hardware.Arm.Position;

public class PositioningTele implements Opmode {
    Grinder bot;
    CWController con0, con1;
    String state;
    Timer timer;

    public boolean holding = false;
    Position target;

    public void init(Grinder bot) {
        this.bot = bot;
        con0 = new CWController(0);
        con1 = new CWController(1);
        bot.arm.elevator.reset();
        target = new Position(
            bot.arm.elevator.getPos(),
            bot.arm.wrist.getPos(),
            bot.arm.elbow.getPos()
        );
        timer = new Timer();
    }

    public double absmax(double a, double b) {
        if(Math.abs(a) > Math.abs(b)) return a;
        else return b;
    }

    public void periodic() {
        double
            ly = con0.getLeftY(),
            lx = con0.getLeftX(),
            rx = con0.getRightX();
        double
            f = -Utility.sgnsqr(ly),
            s = Utility.sgnsqr(lx),
            r = Utility.sgnsqr(rx);
        bot.base.drive(-s, -f, -r, true);
        bot.base.periodic();

        double pow = 0.005*(1+4*con1.getLeftTriggerAxis());
        if(con1.getUpButton()) {
            target.elev += 4*pow;
        }
        if(con1.getDownButton()) {
            target.elev -= 4*pow;
        }
        target.elev = Utility.clamp(target.elev, Elevator.MIN, Elevator.MAX);
        if(con1.getYButton()) {
            target.elbow += pow;
        }
        if(con1.getAButton()) {
            target.elbow -= pow;
        }
        target.elbow = Utility.clamp(target.elbow, Elbow.MIN, Elbow.MAX);
        if(con1.getXButton()) {
            target.wrist += pow;
        }
        if(con1.getBButton()) {
            target.wrist -= pow;
        }
        target.wrist = Utility.clamp(target.wrist, Wrist.MIN, Wrist.MAX);

        if(con1.getLeftButton()) {
            target = Arm.CORAL_PREP;
            state = "coral intake";
            timer.reset();
        }

        if(con1.getLeftStickButton()) {
            target = Arm.CORAL_L3;
        }
        // if(state == "coral intake" && timer.get() > .5) {
        //     target = Arm.CORAL_INTAKE;
        //     state = "";
        // }

        if(con1.getRightButton()) {
            target = Arm.ALGAE_PROC;
        }

        // if(con1.getRightButton()) {
        //     target = Arm.HELD_READY;
        //     state = "coral l2";
        //     timer.reset();
        // }
        // if(state == "coral l2" && timer.get() > 2) {
        //     target = Arm.CORAL_L2;
        //     state = "";
        // }

        bot.arm.goTo(target);

        if(con0.getRightBumperButton()||con1.getRightBumperButton()) {
            bot.arm.claw.set(0.2);
        }
        else if(con0.getLeftBumperButton()||con1.getLeftBumperButton()) {
            bot.arm.claw.set(-0.3);
        }
        else {
            bot.arm.claw.stop();
        }

        if(con0.getLeftBumperButton() && con0.getRightBumperButton()) {
            bot.base.resetGyro();
        }

        SmartDashboard.putNumber("wrist", bot.arm.wrist.getPos());
        SmartDashboard.putNumber("elbow", bot.arm.elbow.getPos());
        SmartDashboard.putNumber("elev", bot.arm.elevator.getPos());
        
        SmartDashboard.putNumber("wrist pow", bot.arm.wrist.get());
        SmartDashboard.putNumber("elbow pow", bot.arm.elbow.get());
        SmartDashboard.putNumber("elev pow", bot.arm.elevator.get());

        SmartDashboard.putNumber("wrist target", target.wrist);
        SmartDashboard.putNumber("elbow target", target.elbow);
        SmartDashboard.putNumber("elev target", target.elev);
    }
}
