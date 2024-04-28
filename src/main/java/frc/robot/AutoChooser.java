package frc.robot;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutoCommands;

public class AutoChooser {
    private final LoggedDashboardChooser<Command> autoChooser;
    private final AutoCommands autoCommands;

    public AutoChooser(AutoCommands autoCommands_dep) {
        autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());
        autoCommands = autoCommands_dep;
        autoChooser.addDefaultOption("Example Auto", autoCommands.followTestPath());
        autoChooser.addOption("Example Auto", autoCommands.followTestPath());
        autoChooser.addOption("One Note", autoCommands.oneNote());
        autoChooser.addOption("One Note and Intake", autoCommands.oneNoteAndPickUp());
        autoChooser.addOption("Shoot only", autoCommands.shootOnly());
        autoChooser.addOption("taxi", autoCommands.taxi());
        autoChooser.addOption("Two Note", autoCommands.twoNote());
        autoChooser.addOption("Shoot and taxi amp side", autoCommands.shootTaxiAmpSide());
    }

    public Command get() {
        return autoChooser.get();
    }
}
