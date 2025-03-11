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
        new NamedOpmode("Basic", BasicAuto.class),
        new NamedOpmode("Homing", HomingAuto.class),
    };

    public static NamedOpmode teleop[] = {
        new NamedOpmode("Swerve Testing", SwerveTeleop.class),
        new NamedOpmode("Odometry Testing", SwerveOdo.class),
        new NamedOpmode("Positioning", PositioningTele.class),
        new NamedOpmode("Full", FullTeleop.class),
        new NamedOpmode("Targeting", Targeting.class),
    };

    public static NamedOpmode test[] = {
        new NamedOpmode("Position Test", PositionTesting.class),
        new NamedOpmode("Elevator Test", ElevatorTest.class)
    };
}
