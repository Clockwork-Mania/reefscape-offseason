package frc.robot.hardware;

public class Arm {
    public static class Position {
        public double elev, wrist, elbow;
        public Position(double elev, double wrist, double elbow) {
            this.elev = elev;
            this.wrist = wrist;
            this.elbow = elbow;
        }
    }

    public static final Position CORAL_L4     = new Position(0, 0, 0);
    public static final Position CORAL_L3     = new Position(0, 0, 0);
    public static final Position CORAL_L2     = new Position(0, 0, 0);
    public static final Position CORAL_L1     = new Position(0, 0, 0);
    public static final Position CORAL_INTAKE = new Position(0, 0, 0);
    public static final Position ALGAE_BARGE  = new Position(0, 0, 0);
    public static final Position ALGAE_L3     = new Position(0, 0, 0);
    public static final Position ALGAE_L2     = new Position(0, 0, 0);
    public static final Position ALGAE_GROUND = new Position(0, 0, 0);

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

    public void goTo(Position p) {
        elevator.goTo(p.elev);
        elbow.goTo(p.elbow);
        wrist.goTo(p.wrist);
    }
}
