package frc.robot.hardware;

import java.util.List;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.Timer;

public class Arm {
    public static class Position {
        public double elev, wrist, elbow;
        public Position(double elev, double wrist, double elbow) {
            this.elev = elev;
            this.wrist = wrist;
            this.elbow = elbow;
        }

        public void set(Position to) {
            this.elev = to.elev;
            this.wrist = to.wrist;
            this.elbow = to.elbow;
        }
    }

    public static class Sequence {
        public Timer timer;
        List<Pair<Position, Double>> steps;
        int index;
        public Sequence(List<Pair<Position, Double>> steps) {
            this.steps = steps;
            index = 0;
        }
    }

    public static final double elevBase = 0.93;
    public static final Position STARTING     = new Position(elevBase, 0.85, 0.436);
    public static final Position CORAL_L4     = new Position(elevBase+5.05, .6, 0.5);
    public static final Position CORAL_L3     = new Position(elevBase+3, .59, .48);
    public static final Position CORAL_L2     = new Position(elevBase+1.2, .59, .48);
    public static final Position CORAL_L1     = new Position(elevBase+0, 0, 0);
    public static final Position READY        = new Position(elevBase+1.71+0.35, 0.85, 0.436);
    public static final Position READY_OUT    = new Position(elevBase+1.71+0.35, 0.39, 0.228);
    public static final Position CORAL_PREP   = new Position(elevBase+2.29, 0.70, 0.32);
    public static final Position CORAL_INTAKE = new Position(elevBase+2.29, 0.75, 0.28);
    public static final Position HELD_READY   = new Position(elevBase+2.5+0.35, 0.78, 0.17);
    public static final Position ALGAE_BARGE  = new Position(elevBase+0, 0, 0);
    public static final Position ALGAE_L3     = new Position(elevBase+0, 0, 0);
    public static final Position ALGAE_L2     = new Position(elevBase+0, 0, 0);
    public static final Position ALGAE_GROUND = new Position(elevBase+-.4+0.35, 0.39, 0.228);
    public static final Position ALGAE_PROC   = new Position(elevBase+-.3+0.35, 0.31, 0.227);

    public static final Sequence CORAL_INTAKE_SEQ = new Sequence(List.of(
        Pair.of(CORAL_PREP, 2.),
        Pair.of(CORAL_INTAKE, -1.)  
    )); 
    public static final Sequence CORAL_READY_SEQ = new Sequence(List.of(
        Pair.of(READY, -1.)
    ));
    public static final Sequence CORAL_L2_SEQ = new Sequence(List.of(
        Pair.of(HELD_READY, 2.),
        Pair.of(CORAL_L2, -1.)
    ));

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

    public void setTarget(Position p) {
        elevator.setTarget(p.elev);
        elbow.setTarget(p.elbow);
        wrist.setTarget(p.wrist);
    }

    public void goTo(Position p) {
        elevator.goTo(p.elev);
        elbow.goTo(p.elbow);
        wrist.goTo(p.wrist);
    }
    
    public void goToTarget() {
        elevator.goToTarget();
        elbow.goToTarget();
        wrist.goToTarget();
    }

    public Sequence currSeq = null;
    public void startSeq(Sequence seq) {
        setTarget(seq.steps.get(0).getFirst());
        seq.timer.reset();
        currSeq = seq;
    }

    public void endSeq() {
        currSeq = null;
    }

    public void runSeq() {
        if(currSeq != null) runSeq(currSeq);
    }

    public void runSeq(Sequence seq) {
        if(
            seq.index < seq.steps.size()-1 &&
            seq.timer.get() > seq.steps.get(seq.index).getSecond()
        ) {
            ++seq.index;
            setTarget(seq.steps.get(seq.index).getFirst());
            seq.timer.reset();
        }
        goToTarget();
    }

    public Position getPos() {
        return new Position(
            elevator.getPos(),
            elbow.getPos(),
            wrist.getPos()
        );
    }

    public void stop() {
        elevator.stop();
        elbow.stop();
        wrist.stop();
    }
}
