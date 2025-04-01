package frc.robot.opmodes.auto;

import frc.robot.hardware.Grinder;
import frc.robot.opmodes.Opmode;
import frc.robot.states.LinearMachine;
import frc.robot.states.LinearMachineBuilder;
import frc.robot.states.StateMachine;
import frc.robot.states.StateMachineBuilder;

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
