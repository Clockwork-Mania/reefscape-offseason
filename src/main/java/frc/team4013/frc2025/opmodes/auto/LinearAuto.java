package frc.team4013.frc2025.opmodes.auto;

import frc.team4013.frc2025.hardware.Grinder;
import frc.team4013.frc2025.opmodes.Opmode;
import frc.team4013.frc2025.states.LinearMachine;
import frc.team4013.frc2025.states.LinearMachineBuilder;

public class LinearAuto implements Opmode {
    LinearMachine mach;
    
    public void init(Grinder bot) {
        mach = new LinearMachineBuilder()
            .init(()->{/* set targets */})
            .loop(()->{/* update positions */})
            .until(()->true /* ready for next? */)
        .then()
            .init(()->{/* set targets */})
            .loop(()->{/* update positions */})
            .until(()->true /* ready for next? */)
        .build();
                
        mach.init();
    }

    public void periodic() {
        mach.periodic();
    }
}
