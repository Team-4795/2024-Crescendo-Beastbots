package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.intake.IntakeIO.IntakeIOInputs;
import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class Intake extends SubsystemBase {

    private double coolintakespeed = 0.0;
    private IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    private static Intake instance;
    public Intake(IntakeIO io) {
        this.io = io;
    }

    public static Intake getInstance() {
        return instance;
    }

    public static void init(IntakeIO io) {
        if (instance == null) {
            instance = new Intake(io);
        }
    }

    public void setIntakeSpeed(double speed){
        coolintakespeed = speed;
    }

    @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);
        io.setIntakeSpeed(coolintakespeed);
    }
}
