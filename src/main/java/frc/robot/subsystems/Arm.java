package frc.robot.subsystems;

public class Arm {
    public Elevator elevator;
    public Wrist wrist;
    public Claw claw;
    public Elbow elbow;

    public Arm() {
        elevator = new Elevator();
        wrist = new Wrist();
        claw = new Claw();
        elbow = new Elbow();
    }
}
