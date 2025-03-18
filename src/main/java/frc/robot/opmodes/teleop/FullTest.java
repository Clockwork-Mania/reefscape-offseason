package frc.robot.opmodes.teleop;

import java.util.List;
import java.util.function.Predicate;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.hardware.*;

public class FullTest implements Opmode {
    Grinder bot;
    CWController con0, con1;
    List<CWController> cons;
    Field2d field;
    Timer prepTimer;
    String state;

    public void init(Grinder bot) {
        this.bot = bot;
        con0 = new CWController(0);
        con1 = new CWController(1);
        cons = List.of(con0, con1);
        prepTimer = new Timer();
        state = "";
        bot.arm.setTarget(Arm.STARTING);
        bot.arm.elevator.reset();
    }

    boolean intake = true, coral = false, coralPrep = false;

    boolean either(Predicate<CWController> cond) {
        return cond.test(con0) || cond.test(con1);
    }

    public void periodic() {
        con0.update();
        con1.update();

        double
            f = -con1.getLeftY()*.4,
            s = con1.getLeftX()*.4,
            r = con1.getRightX()*.4;
        bot.base.drive(-s, -f, -r, true);
        bot.base.periodic();

        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", bot.base.heading()*180/Math.PI);

        SmartDashboard.putNumber("wrist", bot.arm.wrist.getPos());
        SmartDashboard.putNumber("elbow", bot.arm.elbow.getPos());
        SmartDashboard.putNumber("elev", bot.arm.elevator.getPos());
        
        SmartDashboard.putNumber("wrist pow", bot.arm.wrist.get());
        SmartDashboard.putNumber("elbow pow", bot.arm.elbow.get());
        SmartDashboard.putNumber("elev pow", bot.arm.elevator.get());

        SmartDashboard.putNumber("wrist target", bot.arm.wrist.target);
        SmartDashboard.putNumber("elbow target", bot.arm.elbow.target);
        SmartDashboard.putNumber("elev target", bot.arm.elevator.target);
        // ALGAE PROC
        // CORAL L3
        // CORAL INTAKE
        // CORAL L2

        if(con1.getUpButton()) {
            bot.arm.setTarget(Arm.READY);
        }
        if(con1.getLeftButton()) {
            bot.arm.setTarget(Arm.CORAL_PREP);
        }
        if(con1.getRightButton()) {
            bot.arm.setTarget(Arm.CORAL_INTAKE);
        }
        if(con1.getYButton()) {
            bot.arm.setTarget(Arm.CORAL_L3);
        }
        if(con1.getBButton()) {
            bot.arm.setTarget(Arm.CORAL_L2);
        }
        // if(con1.getXButton()) {
        //     bot.arm.setTarget(Arm.ALGAE_PROC);
        // }
        if(con1.getXButton()) {
            bot.arm.setTarget(Arm.CORAL_L4);
        }
        bot.arm.goToTarget();

        // ---------------- CLAW --------------- //
        bot.arm.claw.set(.2*(
            con1.getLeftTriggerAxis()-con1.getRightTriggerAxis()
        ));


    }
}

