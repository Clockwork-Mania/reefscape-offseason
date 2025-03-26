import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.Grinder;
import frc.robot.opmodes.Opmode;

public class LedTest implements Opmode {
    private AddressableLED led;
    private AddressableLEDBuffer buffer;
    private final int LED_COUNT = 60; // number of leds
    private final int PORT = 9; // pwm port number

    public void init(Grinder bot) {
        led = new AddressableLED(PORT);
        led.setLength(LED_COUNT);
        buffer = new AddressableLEDBuffer(LED_COUNT);

        for (int i = 0; i < LED_COUNT; i++) {
            buffer.setRGB(i, 255, 0, 0);
        }

        led.setData(buffer);
        led.start();
    }

    @Override
    public void periodic() {
        // change of led color
        for (int i = 0; i < LED_COUNT; i++) {
            buffer.setRGB(i, 0, 255, 0); // color - green test
        }
        led.setData(buffer);
    }
}