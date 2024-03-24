package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.shooter.Shooter;

public class AutoCommands {
    private Drive drive;
    private AlignToSpeaker alignToSpeaker;

    public AutoCommands(Drive drive) {
        this.drive = drive;
        this.alignToSpeaker = new AlignToSpeaker();
    }

    public Command taxi() {
        return new RunCommand(
            () -> drive.driveVelocity(10, 10)
        ).withTimeout(1.5);
    }

    public Command shootTaxiAmpSide() {
        return new SequentialCommandGroup(
            shootOnly(), 
            new RunCommand(
                () -> drive.driveArcade(0.5, 0)
            ).withTimeout(1),
            new RunCommand(
                () -> drive.driveArcade(0, -0.5)
            ).withTimeout(1),
            taxi()
        );
    }

    public Command shootOnly() {
        return new ParallelRaceGroup(
            new StartEndCommand(
                () -> Shooter.getInstance().setVelocity(1),
                () -> Shooter.getInstance().setVelocity(0)
            ),
            new StartEndCommand(
                () -> Pivot.getInstance().setVelocity(0.5),
                () -> Pivot.getInstance().setVelocity(0)
            ),
            new SequentialCommandGroup(
                new WaitCommand(2),
                new StartEndCommand(
                    () -> Intake.getInstance().setVelocity(1),
                  () -> Intake.getInstance().setVelocity(0)
                ).withTimeout(2)
            )
         );
    }

    public Command oneNote(){
        return new SequentialCommandGroup(
            shootOnly(),
            taxi()
        );
    }

    public Command oneNoteAndPickUp() {
        return new SequentialCommandGroup(
            shootOnly(),
            new StartEndCommand(
                () -> Pivot.getInstance().setVelocity(-0.5),
                () -> Pivot.getInstance().setVelocity(0)
            ).withTimeout(1),
            new ParallelRaceGroup(
                new StartEndCommand(
                    () -> Intake.getInstance().setVelocity(1),
                    () -> Intake.getInstance().setVelocity(0)
                ),
                new RunCommand(
                    () -> drive.driveVelocity(10, 10)
                ).withTimeout(1)
            )
        );
    }

    public Command twoNote() {
        return new SequentialCommandGroup(
            oneNoteAndPickUp(),
            new RunCommand(
                () -> drive.driveVelocity(-10, -10)
            ).withTimeout(1),
            shootOnly(),
            new RunCommand(
                () -> drive.driveVelocity(10, 10)
            ).withTimeout(1)
        );
    }

    public Command followTestPath() {
        PathPlannerPath path = PathPlannerPath.fromPathFile("testpath");
        return new SequentialCommandGroup(
            new InstantCommand(
                () -> drive.setPose(path.getStartingDifferentialPose())
            ),
            AutoBuilder.followPath(path),
            alignToSpeaker
        );
    }
}
