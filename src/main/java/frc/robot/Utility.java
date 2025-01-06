package frc.robot;

public class Utility {
    public final static double TAU = 2*Math.PI;

    public static double ticks2Meters(double ticks) {
        return ticks * Constants.gearRatio / Constants.encCPR * Constants.wheelDiam * Math.PI;
    }

    public static double ticks2Rad(double ticks) {
        return fixang(ticks*2*Math.PI);
    }

    public static double fixang(double a) {
        return ((a%TAU+Math.PI)%TAU)-Math.PI;
    }
}
