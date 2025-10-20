package frc.team4013.frc2025.opmodes.auto;

import frc.team4013.frc2025.Utility;
import frc.team4013.frc2025.hardware.*;
import frc.team4013.frc2025.opmodes.Opmode;

public class HomingAuto implements Opmode {
    Grinder bot;

    public void init(Grinder bot) {
        this.bot = bot;
        bot.vision.setPipeline(Vision.DETECT_10);
    }

    public void periodic() {
        bot.vision.update();
        // home on x
        double err = bot.vision.getX();
        bot.base.drive(Utility.clamp(err, -1, 1), 0, 0, false);
    }
}
