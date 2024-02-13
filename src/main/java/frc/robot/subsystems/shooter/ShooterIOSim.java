package frc.robot.subsystems.shooter;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ShooterIOSim implements ShooterIO {
    DCMotorSim motor = new DCMotorSim(DCMotor.getNEO(2), 1, 0.001);
    private double appliedVolts = 0.0;

    
}
