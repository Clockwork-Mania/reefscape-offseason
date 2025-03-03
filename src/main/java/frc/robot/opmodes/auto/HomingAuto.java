package frc.robot.opmodes.auto;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.*;

public class HomingAuto {
    Grinder bot;

    public void init() {
        bot = new Grinder();
        bot.vision.setPipeline(Vision.DETECT_10);
    }

    public void periodic() {
        bot.vision.update();
        // home on x
        double err = bot.vision.getX();
        bot.base.drive(Math.min(1, err), 0, 0, false);
    }
}
