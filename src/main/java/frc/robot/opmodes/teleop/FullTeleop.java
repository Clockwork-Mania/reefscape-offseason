package frc.robot.opmodes.teleop;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.opmodes.Opmode;
import frc.robot.subsystems.*;

public class FullTeleop implements Opmode {
    Grinder bot;
    CWController con;
    Field2d field;

    public void init() {
        bot = new Grinder();
        con = new CWController(0);
        field = new Field2d();
        SmartDashboard.putData("Field", field);
    }

    boolean intake = true, coral = false;

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


        // --------------- HOMING -------------- //
        if(con.getLeftStickButton()) {
            if(con.getRightX() < -.3) {
                // home, tilting left
            }
            else if(con.getRightX() > .3) {
                // home, tilting right
            }
            else {
                // home on center
            }
        }

        // ------------- MECHANISM ------------- //
        if(con.getUpButtonPressed()) {
            // Algae from L3
            coral = false;
            intake = true;
        }
        else if(con.getRightButtonPressed()) {
            // Algae from L2
            coral = false;
            intake = true;
        }
        else if(con.getDownButtonPressed()) {
            // Algae from ground
            coral = false;
            intake = true;
        }
        else if(con.getLeftButtonPressed()) {
            // Coral from funnel
            coral = true;
            intake = true;
        }
        else if(con.getYButtonPressed()) {
            if(coral) {
                // Coral to L4
            }
            else {
                // Algae to barge
            }
            intake = false;
        }
        else if(con.getBButtonPressed()) {
            // Coral to L3
            intake = false;
        }
        else if(con.getAButtonPressed()) {
            if(coral) {
                // Coral to L2
            }
            else {
                // Algae to processor
            }
            intake = false;
        }
        else if(con.getXButtonPressed()) {
            // Coral to L1
            intake = false;
        }
        
        SmartDashboard.putBoolean("Coral?", coral);
        SmartDashboard.putBoolean("Intaking?", intake);

        // ---------------- CLAW --------------- //
    
        if(con.getRightBumperButtonPressed()) {
            if (coral) {
                if (intake) {
                    bot.arm.claw.set(Claw.INTAKE_CORAL);
                }
                else {
                    bot.arm.claw.set(Claw.OUTTAKE_CORAL);
                }
            } 
            else {
                if (intake) {
                    bot.arm.claw.set(Claw.INTAKE_ALGAE);
                }
                else{
                    bot.arm.claw.set(Claw.OUTTAKE_ALGAE);
                }
            }
        }
    
    }
}

