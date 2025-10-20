package frc.team4013.frc2025.hardware;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class Leds {
    AddressableLED strip;
    AddressableLEDBuffer buff;
    public static final int COUNT = 60;
    Timer timer;
    Pattern pat = null;

    public Leds() {
        strip = new AddressableLED(9);
        strip.setLength(COUNT);
        buff = new AddressableLEDBuffer(COUNT);
    }

    @FunctionalInterface
    public interface Pattern {
        Color at(int index, double time);

        static Pattern solid(Color c) {
            return (int i, double t)->c;
        }
    }

    public void set(Pattern pat) {
        this.pat = pat;
        timer.reset();
        update();
    }

    Pattern CWM = (int idx, double time)->new Color("#40131a");

    public void update() {
        if(pat == null) return;
        double t = timer.get();
        for(int i = 0; i < COUNT; ++i) {
            buff.setLED(i, pat.at(i, t));
        }
        strip.setData(buff);
    }
}
