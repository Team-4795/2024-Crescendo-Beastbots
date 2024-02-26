package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
public class AutoCommands {
    public AutoCommands() {
    }

    public Command testAuto(){
        return new InstantCommand(() -> {
            
        });
    }
}
