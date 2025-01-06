package frc.robot.opmodes;

public interface Opmode {
    String name();
    void init();
    void periodic();
}
