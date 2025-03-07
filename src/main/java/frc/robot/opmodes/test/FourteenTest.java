package frc.robot.opmodes.test;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.opmodes.Opmode;

public class FourteenTest implements Opmode {
    public XboxController controller;
    public int base_ids[] = {2, 3, 4, 5};
    public int can_ids[] = {21, 22, 23, 24, 25, 26};
    public TalonFX motors[];

    public void init() {
        controller = new XboxController(0);
        motors = new TalonFX[10];
        for(int i = 0; i < base_ids.length; ++i) {
            motors[i] = new TalonFX(base_ids[i]);
        }
        for(int i = 0; i < can_ids.length; ++i) {
            motors[i+base_ids.length] =
                new TalonFX(can_ids[i], "canivore");
        }
    }

    public void periodic() {
        for(TalonFX motor : motors) {
            motor.set(controller.getLeftY());
        }
    }
}
