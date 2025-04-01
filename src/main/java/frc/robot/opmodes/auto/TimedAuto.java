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
        bot.base.drive(0, 0.3, 0, true);
    }

    public void periodic() {
        if(timer.hasElapsed(1)) bot.base.stop();
        else bot.base.drive(0, -0.3, 0, false);
    }
}
