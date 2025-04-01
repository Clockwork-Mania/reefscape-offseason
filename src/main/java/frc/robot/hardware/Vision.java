package frc.robot.hardware;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry pipeline = table.getEntry("pipeline");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    // pipeline constants
    public static final int DETECT_10 = 0;

    public Vision() {}

    public void setPipeline(int n) {
        pipeline.setNumber(n);
    }

    public void update() {
        SmartDashboard.putNumber("LimelightX", getX());
        SmartDashboard.putNumber("LimelightY", getY());
        SmartDashboard.putNumber("LimelightArea", getA());
    }

    public double getX() {return tx.getDouble(0);}
    public double getY() {return ty.getDouble(0);}
    public double getA() {return ta.getDouble(0);}
    public boolean foundTag() {return ta.getDouble(-1) != -1;}
}
