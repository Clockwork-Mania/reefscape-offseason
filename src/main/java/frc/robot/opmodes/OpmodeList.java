package frc.robot.opmodes;
import frc.robot.opmodes.teleop.*;

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
    };

    public static NamedOpmode teleop[] = {
        new NamedOpmode("All Wheels", AllWheels.class),
        new NamedOpmode("Module Testing", ModuleTest.class),
        new NamedOpmode("Talon Testing", TalonTest.class),
        new NamedOpmode("Swerve Testing", TestSwerveTeleop.class)
    };

    public static NamedOpmode test[] = {
    };
}
