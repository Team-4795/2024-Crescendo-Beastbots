package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class Shooter extends SubsystemBase {
// IO is input output
// Autolog, auto logs. 
    private ShooterIO io;
    private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();
    // Defines requested speed as 0.0
    private double requestedSpeed = 0.0;

    //Constructor, creates an instant of a class. 
    public Shooter(){

    }

    // 
    public void setRequestedSpeed(double requestedSpeed) {
        this.requestedSpeed = requestedSpeed;
    }


    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Shooter", inputs);
        // Clamp clamps the voltage from 12,-12. 
        io.setShooterVoltage(MathUtil.clamp(requestedSpeed * 12, -12, 12));
    }
}
