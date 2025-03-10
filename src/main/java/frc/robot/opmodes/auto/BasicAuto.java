package frc.robot.opmodes.auto;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.hardware.*;

public class BasicAuto {
    Grinder bot;

    public void init() {
        bot = new Grinder();
        bot.base.setTarget(0, 40, 0);
    }

    public void periodic() {
        bot.base.periodic();
        bot.base.driveto();
    }
}
