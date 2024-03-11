package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.intake.Intake;
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
            )
        );
    }
}
