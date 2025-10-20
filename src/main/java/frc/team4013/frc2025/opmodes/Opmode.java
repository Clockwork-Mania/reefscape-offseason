package frc.team4013.frc2025.opmodes;

import frc.team4013.frc2025.hardware.Grinder;

public interface Opmode {
    void init(Grinder bot);
    void periodic();
}
