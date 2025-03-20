package frc.robot.hardware;

public class Claw extends Motor {
    // TODO: determine values
    public static final double INTAKE_CORAL = -0.2;
    public static final double OUTTAKE_CORAL = 0.12;
    public static final double INTAKE_ALGAE = 0.3;
    public static final double OUTTAKE_ALGAE = -0.3;
    public static final double HOLD_CORAL = -0.12;
    public static final double HOLD_ALGAE = 0.2;

    public Claw() {
        super(25, "canivore");
        setDir(Direction.CCW);
    }
}
