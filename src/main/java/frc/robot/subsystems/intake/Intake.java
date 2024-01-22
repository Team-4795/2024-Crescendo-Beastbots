package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class Intake extends SubsystemBase {

    private CANSpark coolintake = new CANSpark.Motor(Controller.MAX, IntakeConstants.canID).configure();
    private double coolintakespeed = 0.0;

    


}
