package frc.robot.commands;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.shooter.Shooter;

public class NamedCommandManager {
    private Drive drive;
    public NamedCommandManager(Drive drive) {
        this.drive = drive;
    }

    public void register() {
        NamedCommands.registerCommand("hello", new InstantCommand(() -> {
            System.out.println("hello world");
        }));

        NamedCommands.registerCommand("RunShooter", new StartEndCommand(
            () -> Shooter.getInstance().setVelocity(1), 
            () -> Shooter.getInstance().setVelocity(0)
        ).withTimeout(2));

        NamedCommands.registerCommand("RunIntake", new StartEndCommand(
            () -> Intake.getInstance().setVelocity(1), 
            () -> Intake.getInstance().setVelocity(0)
        ).withTimeout(2));

        NamedCommands.registerCommand("ZeroHeading", new InstantCommand(
            () -> drive.zeroHeading()
        ));
    }
}