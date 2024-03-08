package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;

public class AlignToSpeaker extends Command {
    private Drive drive;
    public AlignToSpeaker(Drive drive) {
        this.drive = drive;
    }

    @Override
    public void execute() {
        drive.addChild(getName(), drive);
    }
}
