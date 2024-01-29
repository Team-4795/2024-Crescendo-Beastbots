package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.flywheel.Flywheel;

public class AutoCommands {
    private final Drive drive;
    private final Flywheel flywheel;
    
    public AutoCommands(Drive drive_dep, Flywheel flywheel_dep) {
        drive = drive_dep;
        flywheel = flywheel_dep;
    }

    public Command testAuto(){
        return new InstantCommand(() -> {
            
        });
    }

    public Command driveFFCharacterization(){
        return new FeedForwardCharacterization(
            drive, (volts) -> drive.driveVolts(volts, volts), drive::getCharacterizationVelocity
        );
    }

    public Command flywheelFFCharacterization(){
        return new FeedForwardCharacterization(
            flywheel, flywheel::runVolts, flywheel::getCharacterizationVelocity
        );
    }
}
