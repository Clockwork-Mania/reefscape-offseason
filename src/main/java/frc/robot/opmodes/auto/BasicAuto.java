package frc.robot.opmodes.auto;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryParameterizer.TrajectoryGenerationException;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.hardware.*;

public class BasicAuto {
    Grinder bot;
    Trajectory currentTrajectory = null;
    double startTime;

    HolonomicDriveController controller =  new HolonomicDriveController(
        new PIDController(1, 0, 0), new PIDController(1, 0, 0),  //fuck it we ball
        new ProfiledPIDController(1, 0, 0,
            new TrapezoidProfile.Constraints(6.28, 3.14)
        )
    );

    Timer trajTimer = new Timer();
    Trajectory line;
    int autoState = 0;
    boolean onEnter = true;

    public void init(Grinder bot) {
        this.bot = bot;
        bot.base.resetGyro();
        bot.base.resetOdo(new Pose2d());

        initializeTrajectories();
        trajTimer.reset();
    }

    public void periodic() {
        bot.base.periodic();
        drive();
        update();
        
        SmartDashboard.putNumber("X",bot.base.odo.getPoseMeters().getX());
        SmartDashboard.putNumber("Y",bot.base.odo.getPoseMeters().getY());

    }

    //main autonomous code (big ass switch)
    public void update() {
        switch (autoState) {
            case 0:
                if (onEnter) {
                    // on enter code
                    // setCurrentTrajectory(line);
                    onEnter = false;
                }

                //loop code

                
                //transition to next state
                if (true) {
                    onEnter = true;
                    autoState++;
                }
                break;
        
            default:
                break;
        }
    }


    public void drive() {
        if (currentTrajectory == null) return;
        Trajectory.State targetState = currentTrajectory.sample(trajTimer.get());
        ChassisSpeeds targetSpeeds = controller.calculate(
            bot.base.odo.getPoseMeters(),targetState, Rotation2d.fromDegrees(0)
        );
        bot.base.setSpeeds(targetSpeeds);
    }



    public void setCurrentTrajectory(Trajectory target) {
        currentTrajectory = target;
        trajTimer.reset();
    }

    

    public void initializeTrajectories() {
        List<Translation2d> list = new LinkedList<>();
        list.add(new Translation2d());
        line = TrajectoryGenerator.generateTrajectory(new Pose2d(), list, new Pose2d(2, 0, Rotation2d.fromDegrees(0)),
                        new TrajectoryConfig(5, 10));
    }
}
