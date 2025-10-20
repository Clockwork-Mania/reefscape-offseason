package frc.team4013.frc2025.hardware;

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
