package frc.robot.opmodes.teleop;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.hardware.*;

public class MainTeleop implements Opmode {
    Grinder bot;
    XboxController con;
    Field2d field;

    public void init() {
        bot = new Grinder();
        con = new XboxController(0);
        field = new Field2d();
        SmartDashboard.putData("Field", field);
    }

    public void periodic() {
        double
            f = -con.getLeftY()*.4,
            s = con.getLeftX()*.4,
            r = con.getRightX()*.4;
        bot.base.drive(-s, -f, -r, true);
        bot.base.periodic();

        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", bot.base.heading()*180/Math.PI);
        Pose2d pose = bot.base.pose();
        SmartDashboard.putNumber("odo.x", pose.getX());
        SmartDashboard.putNumber("odo.y", pose.getY());

        if(con.getLeftBumperButton() && con.getRightBumperButton()) {
            bot.base.resetGyro();
        }

        field.setRobotPose(bot.base.odo.getPoseMeters());
    }
}
