package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.intake.IntakeIO.IntakeIOInputs;
import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class Intake extends SubsystemBase {

    private double coolintakespeed = 0.0;
    private IntakeIOInputs io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    public Intake() {
        
    }

    public void setIntakeSpeed(double speeed){
        coolintakespeed = speeed;
    }


    @Override
    public void periodic(){
        // coolintake.set(coolintakespeed);
    }
}
