package frc.robot.opmodes.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.*;
import frc.robot.opmodes.Opmode;

public class TimedAuto implements Opmode {
    Grinder bot;
    Timer timer;

    public void init(Grinder bot) {
        this.bot = bot;
        timer = new Timer();
        timer.reset();
    }

    public void periodic() {
        if(timer.get() < 2) {
            bot.base.drive(0, -0.3, 0, true);
        }
        else {
            bot.base.stop();
        }
    }
}
