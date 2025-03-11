package frc.robot.opmodes.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.Grinder;

public class RightAuto implements Opmode{
    Grinder bot;
    Field2d field;
    String state;
    int counter = 0;
    boolean newState = false;

    public void init(){
        bot = new Grinder();
        field = new Field2d();
        SmartDashboard.putData("Field", field);  
        this.state = "Start";
    }
    public void periodic(){
        switch(state){
            case "Start":
                bot.base.setTarget(0.0, 0.20, Math.PI/6);

                this.state = "Drive";

                break;
            
            case "Drive":
                counter++;
                if(counter <= 20){
                    bot.base.driveto();
                    Pose2d testP = new Pose2d(bot.base.pos, bot.base.rot);
                    field.setRobotPose(testP);
                }
                else {
                    this.state = "End";
                }
            case "End":
                return;
        }
    }
}
