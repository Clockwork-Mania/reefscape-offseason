package frc.team4013.frc2025.opmodes.auto;

import java.util.List;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4013.frc2025.hardware.Grinder;
import frc.team4013.frc2025.opmodes.Opmode;

public class AprilTagAuto implements Opmode {
    Grinder bot;

    @Override
    public void init(Grinder bot) {
        this.bot = bot;
    }

    @Override
    public void periodic() {
        List<PhotonPipelineResult> camResults = bot.vision.rightCam.getAllUnreadResults();
        if(camResults.size() > 0) {
            if (camResults.get(0).hasTargets()) {
                SmartDashboard.putNumber("Camera Detection", camResults.get(0).getBestTarget().fiducialId);
            }   
        }
        else {
            SmartDashboard.putNumber("Camera Detection",-1);
        }
    }
    
}
