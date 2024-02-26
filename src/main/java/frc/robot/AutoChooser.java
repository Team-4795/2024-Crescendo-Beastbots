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

        autoChooser.addOption("Test Auto", autoCommands.testAuto());
    }

    public Command get() {
        return autoChooser.get();
    }
}
