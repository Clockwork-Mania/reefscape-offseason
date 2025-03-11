package frc.robot.opmodes.auto;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.hardware.*;

public class BasicAuto {
    Grinder bot;

    public void init(Grinder bot) {
        this.bot = bot;
        bot.base.setTarget(0, 40, 0);
    }

    public void periodic() {
        bot.base.periodic();
        bot.base.driveto();
    }
}
