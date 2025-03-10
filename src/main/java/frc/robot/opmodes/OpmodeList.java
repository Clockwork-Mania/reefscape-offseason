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
        // new NamedOpmode("All Wheels", AllWheels.class),
        // new NamedOpmode("Module Testing", ModuleTest.class),
        // new NamedOpmode("Talon Testing", TalonTest.class),
        new NamedOpmode("Swerve Testing", SwerveTeleop.class),
        new NamedOpmode("Odometry Testing", SwerveOdo.class),
        new NamedOpmode("Main", MainTeleop.class),
        new NamedOpmode("Full", FullTeleop.class),
    };

    public static NamedOpmode test[] = {
        new NamedOpmode("Debug Printing", Printing.class),
        new NamedOpmode("Fourteen Motors", FourteenTest.class),
        new NamedOpmode("Throughbore Test", ThroughboreTest.class)
    };
}
