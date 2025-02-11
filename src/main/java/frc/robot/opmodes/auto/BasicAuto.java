package frc.robot.opmodes.auto;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.*;

public class BasicAuto {
    Grinder bot;
    XboxController con;

    public void init() {
        bot = new Grinder();
        con = new XboxController(0);
        bot.base.setTarget(0, 40, 0);
    }

    public void periodic() {
        bot.base.periodic();
        bot.base.driveto();
    }
}
