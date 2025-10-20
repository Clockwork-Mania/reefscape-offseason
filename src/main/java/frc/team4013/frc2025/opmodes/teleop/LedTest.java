package frc.team4013.frc2025.opmodes.teleop;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4013.frc2025.hardware.Grinder;
import frc.team4013.frc2025.opmodes.Opmode;

public class LedTest implements Opmode {
    private AddressableLED led;
    private AddressableLEDBuffer buffer;
    private final int LED_COUNT = 60; // number of leds
    private final int PORT = 5; // pwm port number

    public void init(Grinder bot) {
        SmartDashboard.putBoolean("initialized", false);
        led = new AddressableLED(PORT);
        led.setLength(LED_COUNT);
        buffer = new AddressableLEDBuffer(LED_COUNT);

        // for (int i = 0; i < LED_COUNT; i++) {
        //     buffer.setRGB(i, 255, 0, 0); //red test
        // }

        led.setData(buffer);
        led.start();
        SmartDashboard.putBoolean("initialized", true);
    }

    @Override
    public void periodic() {
        // change of led color

        LEDPattern rainbow = LEDPattern.rainbow(255, 128);
        rainbow.applyTo(buffer);
        // for (int i = 0; i < LED_COUNT; i++) {
            // buffer.setRGB(i, 0, 255, 0); // color - green test
        // }
        led.setData(buffer);
    }
}