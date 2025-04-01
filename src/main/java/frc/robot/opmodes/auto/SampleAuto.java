package frc.robot.opmodes.auto;

import frc.robot.hardware.Grinder;
import frc.robot.opmodes.Opmode;
import frc.robot.states.StateMachine;
import frc.robot.states.StateMachineBuilder;

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
