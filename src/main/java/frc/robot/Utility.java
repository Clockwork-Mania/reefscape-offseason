package frc.robot;

public class Utility {
    public static double ticks2Meters(double ticks) {
        return ticks * Constants.gearRatio  
         / Constants.encCPR * Constants.wheelDiam * Math.PI;
    }

    public static double ticks2Rad(double ticks) {
        return ticks * Constants.gearRatio / Constants.kAbsEncoderCPR * 2 * Math.PI;
    }
}
