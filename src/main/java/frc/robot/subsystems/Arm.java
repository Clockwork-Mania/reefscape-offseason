package frc.robot.subsystems;

public class Arm {
    public MotorPair pair1 = new MotorPair(0, 0);
    public MotorPair pair2 = new MotorPair(0, 0);

    public Arm(MotorPair pair1, MotorPair pair2) {
        this.pair1 = pair1;
        this.pair2 = pair2;
    }

    public void setPosition(double x, double y) {
        pair1.set(x);
        pair2.set(y);
    };
}