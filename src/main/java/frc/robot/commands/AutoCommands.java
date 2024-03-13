package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.shooter.Shooter;

public class AutoCommands {
    private Drive drive;

    public AutoCommands(Drive drive) {
        this.drive = drive;
    }

    public Command oneNote(){
        return new SequentialCommandGroup(
            new ParallelRaceGroup(
                new StartEndCommand(
                    () -> Shooter.getInstance().setVelocity(1),
                    () -> Shooter.getInstance().setVelocity(0)
                ),
                new SequentialCommandGroup(
                    new WaitCommand(2),
                    new StartEndCommand(
                        () -> Intake.getInstance().setVelocity(1),
                        () -> Intake.getInstance().setVelocity(0)
                    ).withTimeout(2)
                )
            ),
            new StartEndCommand(
                () -> drive.driveVelocity(-1, -1),
                () -> drive.driveVelocity(0, 0)
            ).withTimeout(0.5)
        );
    }

    public Command shootOnly() {
        return new ParallelRaceGroup(
            new StartEndCommand(
                () -> Shooter.getInstance().setVelocity(1),
                () -> Shooter.getInstance().setVelocity(0)
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

    public Command oneNoteAndPickUp() {
        return new SequentialCommandGroup(
            new ParallelRaceGroup(
                new StartEndCommand(
                    () -> Shooter.getInstance().setVelocity(1),
                    () -> Shooter.getInstance().setVelocity(0)
                ),
                new SequentialCommandGroup(
                    new WaitCommand(2),
                    new StartEndCommand(
                        () -> Intake.getInstance().setVelocity(1),
                        () -> Intake.getInstance().setVelocity(0)
                    ).withTimeout(2)
                )
            ),
            new StartEndCommand(
                () -> Pivot.getInstance().setVelocity(-0.5),
                () -> Pivot.getInstance().setVelocity(0)
            ).withTimeout(1.5),
            new ParallelRaceGroup(
                new StartEndCommand(
                    () -> Intake.getInstance().setVelocity(1),
                    () -> Intake.getInstance().setVelocity(0)
                ).withTimeout(.2),
                new StartEndCommand(
                    () -> drive.driveVelocity(-.5, -.5),
                    () -> drive.driveVelocity(0, 0)
                ).withTimeout(.35)
            )
        );
    }

    public Command followTestPath() {
       return AutoBuilder.buildAuto("Example Auto");
    }
}
