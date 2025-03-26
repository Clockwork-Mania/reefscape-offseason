package frc.robot.opmodes;
import frc.robot.opmodes.auto.*;
import frc.robot.opmodes.teleop.*;
import frc.robot.opmodes.test.*;

public class OpmodeList {
    public static class NamedOpmode {
        public String name;
        public Class<?> mode;
        NamedOpmode(String name, Class<?> mode) {
            this.name = name;
            this.mode = mode;
        }
    }

    public static NamedOpmode auto[] = {
        new NamedOpmode("Clipped", ClippedAuto.class),
        new NamedOpmode("Vision", VisionAuto.class),
        new NamedOpmode("Leave", TimedAuto.class),
        new NamedOpmode("None", NoAuto.class),
        // new NamedOpmode("Basic", BasicAuto.class),
        // new NamedOpmode("New", NewAuto.class),
        // new NamedOpmode("Homing", HomingAuto.class),
    };

    public static NamedOpmode teleop[] = {
        // new NamedOpmode("(THIS ONE) Positioning", PositioningTele.class),
        new NamedOpmode("Full", FullTeleop.class),
        new NamedOpmode("April Tag Align", AprilTagAlign.class),
        // new NamedOpmode("Full Test", FullTest.class),
        // new NamedOpmode("Pre-Full", PreFull.class),
    };

    public static NamedOpmode test[] = {
        new NamedOpmode("Manual", Manual.class),
        // new NamedOpmode("Targeting", Targeting.class)
        // new NamedOpmode("Position Test", PositionTesting.class),
        // new NamedOpmode("Elevator Test", ElevatorTest.class)
    };
}
