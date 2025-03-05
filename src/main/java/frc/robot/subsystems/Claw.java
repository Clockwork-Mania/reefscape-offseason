package frc.robot.subsystems;

public class Claw {
    // TODO: determine values
    public static final double INTAKE_CORAL = 0.0;
    public static final double OUTTAKE_CORAL = 0.0;
    public static final double INTAKE_ALGAE = 0.0;
    public static final double OUTTAKE_ALGAE = 0.0;

    Motor motor = new Motor(0); // FIXME: id not finalized

    public void set(double power) {
        motor.set(power);
    }
}
