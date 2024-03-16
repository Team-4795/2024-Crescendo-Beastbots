package frc.robot.commands;

import java.util.List;
import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.drive.Drive;

public class AlignToAmp extends Command {

    //constants
    private final Pose2d redAmpScorePos = new Pose2d(0.0,0.0,new Rotation2d(0.0)); //add desired pose
    private final Pose2d blueAmpScorePos = new Pose2d(0.0,0.0,new Rotation2d(0.0)); //add desired pose
    private Pose2d ScorePos = new Pose2d(0.0,0.0,new Rotation2d(0.0));

    Optional<Alliance> ally = DriverStation.getAlliance();
    {if(ally.isPresent()){
        if (ally.get() == Alliance.Red) {
            ScorePos = redAmpScorePos;
        }
        if (ally.get() == Alliance.Blue) {
            ScorePos = blueAmpScorePos;
        }
    }}

    private Drive drive;
    private double minTurningRadiusMeters;

    private Transform2d bob = new Transform2d();

    public Command GetCommand(){
        List<Translation2d> BezierPoints = PathPlannerPath.bezierFromPoses(
            ScorePos.plus(new Transform2d(new Translation2d(-1,0), Rotation2d.fromDegrees(minTurningRadiusMeters))), 
            ScorePos
        );

        PathPlannerPath path = new PathPlannerPath(
            BezierPoints, 
            new PathConstraints(3, 3, 2 * Math.PI, 4 * Math.PI),
            new GoalEndState(0, Rotation2d.fromDegrees(0))
        );

        return AutoBuilder.followPath(path);
    }
  

//   public AlignToAmp(Drive drive, double minTurningRadiusMeters, String color) {

//     addRequirements(drive);
//   }

  // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {
    
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     Pose2d currentPose = drive.getPose();


//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {
    
//   }

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     return false;
//   }
}
