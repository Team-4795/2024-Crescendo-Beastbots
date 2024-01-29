package frc.robot.commands;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class NamedCommandManager {
    public static void register() {
        NamedCommands.registerCommand("hello", new InstantCommand(() -> {
            System.out.println("hello world");
        }));
    }
}
