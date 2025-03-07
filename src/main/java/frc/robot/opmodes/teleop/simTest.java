package frc.robot.opmodes.teleop;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Grinder;
import frc.robot.subsystems.Swerve;

public class simTest implements Opmode {
    XboxController con;
    Grinder bot;
    Field2d field;

    double x = 0;
    double y = 0;
    public void init(){
        bot = new Grinder();
        con = new XboxController(0);
        field = new Field2d();
        SmartDashboard.putData("Field", field);
    }
    public void periodic(){
        double
            f = -con.getLeftY()*.2,
            s = con.getLeftX()*.2,
            r = con.getRightX()*.2;
        bot.base.drive(-s, -f, -r, true);
        bot.base.periodic();

		//System.out.println(bot.base.positions()[0].distanceMeters);

        SmartDashboard.putNumber("forward", f);
        SmartDashboard.putNumber("strafe", s);
        SmartDashboard.putNumber("rotation", r);
        SmartDashboard.putNumber("heading", bot.base.heading()*180/Math.PI);
        Pose2d pose = bot.base.pose();
        SmartDashboard.putNumber("odo.x", pose.getX());
        SmartDashboard.putNumber("odo.y", pose.getY());

		//bot.base.odo.update(bot.base.heading2d(), bot.base.positions());
        if(con.getLeftBumperButton() && con.getRightBumperButton()) {
            bot.base.resetGyro();
        }	
		Pose2d testP = new Pose2d(bot.base.pos, bot.base.rot);
        field.setRobotPose(testP);
    }
}
