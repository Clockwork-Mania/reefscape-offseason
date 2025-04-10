package frc.robot.hardware;

import org.photonvision.PhotonCamera;

import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
    public class CamTarget {
        double yaw, pitch, area, skew;
        public CamTarget(double yaw, double pitch, double area, double skew) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.area = area;
            this.skew = skew;
        }
        public CamTarget(double yaw, double pitch, double area) {
            this(yaw, pitch, area, 0);
        }
    }

    public class BaseTarget {
        CamTarget t1, t2;
    }

    public PhotonCamera cam1 = new PhotonCamera("arduzz1"),
                        cam2 = new PhotonCamera("arduzz2");

    public PhotonPipelineResult view1, view2;

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry pipeline = table.getEntry("pipeline");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    // pipeline constants
    public static final int DETECT_10 = 0;

    public Vision() {
        PortForwarder.add(5800, "photonvision.local", 5800);
    }

    public void setPipeline(int n) {
        pipeline.setNumber(n);
    }

    public void update() {
        view1 = cam1.getLatestResult();
        view2 = cam2.getLatestResult();

        SmartDashboard.putNumber("LimelightX", getX());
        SmartDashboard.putNumber("LimelightY", getY());
        SmartDashboard.putNumber("LimelightArea", getA());
    }

    public double getX() {return tx.getDouble(0);}
    public double getY() {return ty.getDouble(0);}
    public double getA() {return ta.getDouble(0);}
    public boolean foundTag() {return ta.getDouble(-1) != -1;}
}
