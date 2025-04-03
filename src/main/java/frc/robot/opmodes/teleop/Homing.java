package frc.robot.opmodes.teleop;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.Utility;
import frc.robot.hardware.*;

public class Homing implements Opmode {
    Grinder bot;
    CWController con;

    public void init(Grinder bot) {
        this.bot = bot;
        con = new CWController(0);
    }

    Arm.Position target;

    public void periodic() {
        double
            f = -Utility.sgnsqr(con.getLeftY())*.4,
            s = Utility.sgnsqr(con.getLeftX())*.4,
            r = Utility.sgnsqr(con.getRightX())*.4;
        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", bot.base.heading());
        SmartDashboard.putNumber("vision x", bot.vision.getX());
        SmartDashboard.putNumber("vision y", bot.vision.getY());
        SmartDashboard.putNumber("vision a", bot.vision.getA());

        bot.base.periodic();
        if(con.getAButton()) {
            bot.base.drive(s, f, r, true);
        }
        else {
            bot.base.drive(-bot.vision.getX()/100, 0., 0., true);
        }
    }
}
