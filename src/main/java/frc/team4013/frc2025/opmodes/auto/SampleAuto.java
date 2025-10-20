package frc.team4013.frc2025.opmodes.auto;

import frc.team4013.frc2025.hardware.Grinder;
import frc.team4013.frc2025.opmodes.Opmode;
import frc.team4013.frc2025.states.StateMachine;
import frc.team4013.frc2025.states.StateMachineBuilder;

public class SampleAuto implements Opmode {
    StateMachine mach;
    
    public void init(Grinder bot) {
        mach = new StateMachineBuilder()
            .state("START")
                .start(()->{/* set swerve target */})
                .run(()->{/* follow target */})
                .end(()->{/* stop movement */})
                .next(()->{/* reached target */return true;}, "LIFT")
            .state("LIFT")
                .start(()->{/* set arm target */})
                .run(()->{/* follow target */})
                .end(()->{/* stop movement */})
                .next(()->{/* reached target */return true;}, "")
            .build();
                
        mach.start();
    }

    public void periodic() {
        mach.run();
    }
}
