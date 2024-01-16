package frc.robot.paths;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj2.command.Command;

public class TestPath implements AutoPath {
    public Command load(){
       return AutoBuilder.buildAuto("TestPath");
    }
}
