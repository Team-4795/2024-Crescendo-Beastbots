package frc.robot.commands;

import java.security.KeyPair;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.drive.Drive;

public class AlignToSpeaker extends Command {

    //constants TODO: fill in constants
    private final Pose2d SpeakerPos = new Pose2d(0,0,new Rotation2d(0)); 
    private final double SpeakerHeight = 0;

    //not tuned yet
    private final double kP = 1;
    private final double kI = -.1;
    private final double kD = -.5;

    private Drive drive;

    private ProfiledPIDController PID = new ProfiledPIDController(kP, kI, kD, new Constraints(3.0, 4.0)); //must be tuned
    

    public AlignToSpeaker(Drive drive) {
        this.drive = drive;
    }

    @Override
    public void execute() {
        drive.addChild(getName(), drive);

        Pose2d drivePose = drive.getPose();
        Translation2d toSpeaker = drivePose.relativeTo(SpeakerPos).getTranslation();

        double AngleToSpeaker = Math.atan(toSpeaker.getX()/toSpeaker.getY());

        


         
    }
}
