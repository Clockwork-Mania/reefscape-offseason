package frc.robot.subsystems;

public class Grinder {
	public Swerve base;
	public Arm arm;
	public Vision vision;

	public Grinder() {
		base = new Swerve();
		arm = new Arm();
		vision = new Vision();
	}
}
